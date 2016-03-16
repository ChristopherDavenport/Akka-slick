package routing

import akka.http.javadsl.server.HttpServiceBase
import entities.JsonProtocol
import utils.PersistenceModule
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import persistence.entities.{SimpleSupplier, Supplier}
import SprayJsonSupport._
import spray.json._
import scala.concurrent.Future
import scala.util.{Failure, Success}
import JsonProtocol._
import slick.driver.H2Driver.api._


/**
  * Created by chris on 3/14/16.
  */
trait supplierRouter extends HttpServiceBase{
  this: PersistenceModule =>

  val supplierRouter = {
    pathPrefix("supplier") {
      pathEndOrSingleSlash{
        get {
          onComplete(suppliersDal.findAll){
            case Success(suppliers) => complete(suppliers.toJson)
            case Failure(ex) => complete(InternalServerError, s"An error occurred: ${ex.getMessage}")
          }
        } ~
        post {
          entity(as[Seq[SimpleSupplier]]) { suppliersToInsert =>
            onComplete(
              suppliersDal.insert (
                for (supplier <- suppliersToInsert) yield
                  Supplier(0, supplier.name, supplier.desc)
              )
            ) {
              // ignoring the number of insertedEntities because in this case it should always be one, you might check this in other cases
              case Success(insertedEntities) => complete(Created)
              case Failure(ex) => complete(InternalServerError, s"An error occurred: ${ex.getMessage}")
            }
          }
        }
      } ~
      pathPrefix(IntNumber) { (supId) =>
        get {
          onComplete(suppliersDal.findById(supId).mapTo[Option[Supplier]]) {
            case Success(supplierOpt) => supplierOpt match {
              case Some(sup) => complete(sup.toJson)
              case None => complete(NotFound, s"The supplier doesn't exist")
            }
            case Failure(ex) => complete(InternalServerError, s"An error occurred: ${ex.getMessage}")
          }
        } ~
        put {
          entity(as[Supplier]) { supplierToUpdate =>
            onComplete(
              suppliersDal.update(supplierToUpdate)
            ) {
              case Success(updatedEntity) => complete(Created)
              case Failure(ex) => complete(InternalServerError, s"An error occurred: ${ex.getMessage}")
            }
          }
        }
      } ~
      pathPrefix(Segment){ firstParam =>
        pathEndOrSingleSlash{
          get{
            onComplete(suppliersDal.findByFilterToOne(_.name === firstParam).mapTo[Option[Supplier]]){
              case Success(supplierOpt) => supplierOpt match {
                case Some(sup) => complete(sup.toJson)
                case None => complete(NotFound, s"The supplier doesn't exist - First Param Response")
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
