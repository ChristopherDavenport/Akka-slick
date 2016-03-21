package utils

import akka.http.scaladsl.server.Directives._
import routing._
import akka.http.scaladsl.server.Route
import persistence.entities.{Manufacturer, ManufacturersTable, SimpleManufacturer}
import routing.baseRouterImpl


/**
  * Created by chris on 3/14/16.
  */
trait RoutesModule{
 val routes: Route
}

trait RoutingModuleImpl extends RoutesModule
  with homeRouter
  with printerRouter
  with supplierRouter
  with vendorRouter{
  this: PersistenceModuleImpl with JsonModuleImpl =>

  val manufacturerRouter =
    new baseRouterImpl[Manufacturer, ManufacturersTable, SimpleManufacturer]("manufacturer", manufacturersDal).route

    val routes : Route = {
      printerRouter ~ supplierRouter ~ homeRouter ~ printerRouter ~ vendorRouter ~ manufacturerRouter
    }
}
