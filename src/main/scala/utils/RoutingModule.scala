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

  val printerRouter: BaseRouter[Printer, PrintersTable, SimplePrinter]
  val manufacturerRouter: BaseRouter[Manufacturer, ManufacturersTable, SimpleManufacturer]
  val buildingRouter: BaseRouter[Building, BuildingsTable, SimpleBuilding]
  val vendorRouter: BaseRouter[Vendor, VendorsTable, SimpleVendor]

  def routes: Route
}

trait RoutingModuleImpl extends RoutesModule
  with homeRouter
  with supplierRouter {
  this: PersistenceModuleImpl with JsonModuleImpl =>

  override val printerRouter =
    new BaseRouterImpl[Printer, PrintersTable, SimplePrinter]("printer", printersDal)

  override val manufacturerRouter =
    new BaseRouterImpl[Manufacturer, ManufacturersTable, SimpleManufacturer]("manufacturer", manufacturersDal)

  override val buildingRouter =
    new BaseRouterImpl[Building, BuildingsTable, SimpleBuilding]("building", buildingsDal)

  override val vendorRouter =
    new BaseRouterImpl[Vendor, VendorsTable, SimpleVendor]("vendor", vendorsDal)


  override val routes : Route = {
    printerRouter.route ~ supplierRouter ~ homeRouter ~
      vendorRouter.route ~ manufacturerRouter.route ~ buildingRouter.route
  }


}
