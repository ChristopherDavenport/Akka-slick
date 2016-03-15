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
import scala.util.{Failure, Success}
import JsonProtocol._

/**
  * Created by chris on 3/14/16.
  */
trait supplierRouter extends HttpServiceBase{
  this: PersistenceModule =>

  val supplierRouter = {
    pathPrefix("supplier") {
      pathEndOrSingleSlash{
        get {
          complete("This is the root ( / ) supplier page")
        } ~
        post {
          entity(as[SimpleSupplier]) { supplierToInsert => onComplete(suppliersDal.insert(
            Supplier(0, supplierToInsert.name, supplierToInsert.desc))) {
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
        }
      }
    }
  }
}
