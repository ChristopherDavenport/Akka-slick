package routing

import akka.http.javadsl.server.HttpServiceBase
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.PathMatchers.{IntNumber, Segment}
import org.joda.time.DateTime
import utils.{JsonModuleImpl, PersistenceModuleImpl}
import slick.driver.PostgresDriver.api._
import persistence.entities.{SimpleVendor, Vendor}
import spray.json._

import scala.util.{Failure, Success}

/**
  * Created by davenpcm on 3/17/2016.
  */
trait vendorRouter extends HttpServiceBase{
  this: PersistenceModuleImpl with JsonModuleImpl =>

  val vendorRouter ={
    pathPrefix("vendor"){
      pathEndOrSingleSlash{
        get {
          onComplete(vendorsDal.findAll) {
            case Success(vendors) => complete(vendors.toJson)
            case Failure(ex) => complete(InternalServerError, s"An error occurred: ${ex.getMessage}")
          }
        } ~
          post {
            entity(as[Seq[SimpleVendor]]) { vendorsToInsert =>
              onComplete(
                vendorsDal.insert ( for (vendor <- vendorsToInsert) yield
                  Vendor(id = 0,
                    vendor_pk = vendor.vendor_pk,
                    banner_id = vendor.banner_id,
                    notes = vendor.notes,
                    customer_number = vendor.customer_number,
                    activity_date = new DateTime(),
                    activity_user = "System",
                    created_date = new DateTime(),
                    created_user = "System",
                    status_ck =  "A",
                    status_date = new DateTime()
                  )
                )
              ) {
                case Success(insertedEntities) => complete(Created)
                case Failure(ex) => complete(InternalServerError, s"An error occurred: ${ex.getMessage}")
              }
            }
          }
      } ~
        pathPrefix(IntNumber){ vendorId =>
          pathEnd{
            get {
              onComplete(vendorsDal.findById(vendorId).mapTo[Option[Vendor]]) {
                case Success(vendorOpt) =>
                  vendorOpt match {
                    case Some(print) => complete( print.toJson )
                    case None => complete(NotFound, s"The supplier doesn't exist")
                  }
                case Failure(ex) => complete(InternalServerError, s"An error occurred: ${ex.getMessage}")
              }
            } ~
              put {
                entity(as[Vendor]) { vendorToUpdate =>
                  onComplete( vendorsDal.update(vendorToUpdate)) {
                    case Success(updatedEntity) => complete(Created)
                    case Failure(ex) => complete(InternalServerError, s"An error occurred: ${ex.getMessage}")
                  }
                }
              }
          }
        } ~
        pathPrefix(Segment){ firstParam =>
          pathEndOrSingleSlash{
            get{
              onComplete(vendorsDal.findByFilterToOne(_.vendor_pk === firstParam).mapTo[Option[Vendor]]){
                case Success(vendorOpt) => vendorOpt match {
                  case Some(ven) => complete(ven.toJson)
                  case None => complete(NotFound, s"The supplier - $firstParam - doesn't exist - First Param Response")
                }
                case Failure(ex) => complete(InternalServerError, s"An error occurred: ${ex.getMessage}")
              }
            }
          } ~
            pathPrefix(Segment){ secondParam =>
              pathEndOrSingleSlash{
                complete(NotFound, s"The supplier doesn't exist - Second Param Response")
              } ~
                pathPrefix(Segment){ thirdParam =>
                  pathEndOrSingleSlash{
                    complete(NotFound, s"The supplier doesn't exist - Third Param Response")
                  } ~
                    pathPrefix(Segment){ fourthParam =>
                      complete(NotFound, s"The supplier doesn't exist - Fourth Param Response")
                    }
                }
            }
        }
    }
  }
}