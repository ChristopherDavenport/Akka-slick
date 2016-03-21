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
  val supplierRouter: BaseRouter[Supplier, SuppliersTable, SimpleSupplier]
  val assetGroupRouter: BaseRouter[Asset_Group, Asset_GroupsTable, SimpleAsset_Group]

  def routes: Route
}

trait RoutingModuleImpl extends RoutesModule
  with homeRouter {
  this: PersistenceModule with JsonModule =>

  override val printerRouter =
    new BaseRouterImpl[Printer, PrintersTable, SimplePrinter]("printer", printersDal)

  override val manufacturerRouter =
    new BaseRouterImpl[Manufacturer, ManufacturersTable, SimpleManufacturer]("manufacturer", manufacturersDal)

  override val buildingRouter =
    new BaseRouterImpl[Building, BuildingsTable, SimpleBuilding]("building", buildingsDal)

  override val vendorRouter =
    new BaseRouterImpl[Vendor, VendorsTable, SimpleVendor]("vendor", vendorsDal)

  override val supplierRouter =
    new BaseRouterImpl[Supplier, SuppliersTable, SimpleSupplier]("supplier", suppliersDal)

  override val assetGroupRouter =
    new BaseRouterImpl[Asset_Group, Asset_GroupsTable, SimpleAsset_Group]("asset-group", asset_groupsDal)


  override val routes : Route = {
    printerRouter.route ~ supplierRouter.route ~ homeRouter ~
      vendorRouter.route ~ manufacturerRouter.route ~ buildingRouter.route ~ assetGroupRouter.route
  }


}
