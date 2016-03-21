package utils

import akka.http.scaladsl.server.Directives._
import routing._
import akka.http.scaladsl.server.Route
import persistence.entities._
import routing.{BaseRouter, BaseRouterImpl}


/**
  * Created by chris on 3/14/16.
  */
trait RoutesModule{

 val routes: Route
}

trait RoutingModuleImpl extends RoutesModule
  with homeRouter
  with supplierRouter
  with vendorRouter{
  this: PersistenceModuleImpl with JsonModuleImpl =>

  val printerRouter =
    new BaseRouterImpl[Printer, PrintersTable, SimplePrinter](
      "printer", printersDal).route

  val manufacturerRouter =
    new BaseRouterImpl[Manufacturer, ManufacturersTable, SimpleManufacturer](
      "manufacturer", manufacturersDal).route

  val buildingRouter =
    new BaseRouterImpl[Building, BuildingsTable, SimpleBuilding](
      "building", buildingsDal
    ).route

  val routes : Route = {
    printerRouter ~ supplierRouter ~ homeRouter ~ vendorRouter ~ manufacturerRouter ~ buildingRouter
  }
}
