package routing

import akka.http.javadsl.server.HttpServiceBase
import entities.JsonProtocol
import persistence.entities.Printer
import utils.PersistenceModule
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import JsonProtocol._
import SprayJsonSupport._
import spray.json._


import scala.util.{Failure, Success}

/**
  * Created by chris on 3/14/16.
  */
trait printerRouter extends HttpServiceBase {
  this: PersistenceModule =>

  val printerRouter ={
    pathPrefix("printer"){
      pathEndOrSingleSlash{
        get {
          complete("This is the root ( / ) printer page")
        }
      } ~
      pathPrefix(LongNumber){ printerId =>
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
          }
        }
      }
    }
  }
}
