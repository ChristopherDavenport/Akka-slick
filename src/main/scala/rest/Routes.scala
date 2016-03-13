package rest

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.RouteResult.Complete
import entities.JsonProtocol
import persistence.entities.{Printer, SimpleSupplier, Supplier}
import utils.{PersistenceModule, Configuration}
import JsonProtocol._
import SprayJsonSupport._
import spray.json._
import scala.util.{Failure, Success}

class Routes(modules: Configuration with PersistenceModule) {

  private val supplierGetRoute = pathPrefix("supplier" / IntNumber) { (supId) =>
    get {
      onComplete((modules.suppliersDal.findById(supId)).mapTo[Option[Supplier]]) {
        case Success(supplierOpt) => supplierOpt match {
          case Some(sup) => complete(sup)
          case None => complete(NotFound, s"The supplier doesn't exist")
        }
        case Failure(ex) => complete(InternalServerError, s"An error occurred: ${ex.getMessage}")
      }
    }
  }

  private val supplierPostRoute = pathPrefix("/supplier") {
    post {
      entity(as[SimpleSupplier]) { supplierToInsert => onComplete((modules.suppliersDal.insert(Supplier(0, supplierToInsert.name, supplierToInsert.desc)))) {
        // ignoring the number of insertedEntities because in this case it should always be one, you might check this in other cases
        case Success(insertedEntities) => complete(Created)
        case Failure(ex) => complete(InternalServerError, s"An error occurred: ${ex.getMessage}")
      }
      }
    }

  }
  private val defaultRoute = path(""){
    get{
      complete("This is not the string you are looking for")
    }
  }

  private val printerRouteAll = pathPrefix("printers" / IntNumber){(printId) =>
    get {
      onComplete((modules.printersDal.findById(printId)).mapTo[Option[Printer]]) {
        case Success(printerOpt) =>
          printerOpt match {
          case Some(print) => complete( print.toJson )
          case None => complete(NotFound, s"The supplier doesn't exist")
        }
        case Failure(ex) => complete(InternalServerError, s"An error occurred: ${ex.getMessage}")
      }
    }
  }


  val routes: Route = supplierPostRoute ~ supplierGetRoute ~ defaultRoute ~ printerRouteAll

}

