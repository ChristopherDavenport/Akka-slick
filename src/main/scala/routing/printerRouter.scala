package routing

import akka.http.javadsl.server.HttpServiceBase

import persistence.entities.Printer
import utils.{PersistenceModuleImpl, JsonModuleImpl}
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import slick.driver.PostgresDriver.api._
import spray.json._
import scala.util.{Failure, Success}

/**
  * Created by chris on 3/14/16.
  *
  * Currently needs to be passed a numeric id on post that will subsequently by ignored.
  */
trait printerRouter extends HttpServiceBase{
  this: PersistenceModuleImpl with JsonModuleImpl=>


  val printerRouter ={
    pathPrefix("printer"){
      pathEndOrSingleSlash{
        get {
          onComplete(printersDal.findAll) {
            case Success(printers) => complete(printers.toJson)
            case Failure(ex) => complete(InternalServerError, s"An error occurred: ${ex.getMessage}")
          }
        } ~
          post {
            entity(as[Seq[Printer]]) { printersToInsert =>
              onComplete(
                printersDal.insert ( printersToInsert
                )
              ) {
                case Success(insertedEntities) => complete(Created)
                case Failure(ex) => complete(InternalServerError, s"An error occurred: ${ex.getMessage}")
              }
            }
          }
      } ~
      pathPrefix(IntNumber){ printerId =>
        pathEnd{
          get {
            onComplete(printersDal.findById(printerId).mapTo[Option[Printer]]) {
              case Success(printerOpt) =>
                printerOpt match {
                  case Some(print) => complete( print.toJson )
                  case None => complete(NotFound, s"The supplier doesn't exist")
                }
              case Failure(ex) => complete(InternalServerError, s"An error occurred: ${ex.getMessage}")
            }
          } ~
          put {
            entity(as[Printer]) { printerToUpdate =>
              onComplete( printersDal.update(printerToUpdate)) {
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
            onComplete(printersDal.findByFilterToOne(_.printer_pk === firstParam).mapTo[Option[Printer]]){
              case Success(supplierOpt) => supplierOpt match {
                case Some(sup) => complete(sup.toJson)
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
