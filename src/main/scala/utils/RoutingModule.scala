package utils

import akka.http.scaladsl.server.Directives._
import routing._
import akka.http.scaladsl.server.Route

/**
  * Created by chris on 3/14/16.
  */
trait RoutesModule{

}

trait RoutingModuleImpl extends RoutesModule
  with homeRouter
  with printerRouter
  with supplierRouter {
  this: PersistenceModuleImpl with JsonModuleImpl =>

    val routes : Route = {
      printerRouter ~ supplierRouter ~ homeRouter ~ printerRouter
    }
}
