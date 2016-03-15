import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import utils.{RoutingModuleImpl, PersistenceModuleImpl, ActorModuleImpl, ConfigurationModuleImpl}

object Main extends App {
  // configuring modules for application, cake pattern for DI
  val modules = new ConfigurationModuleImpl with ActorModuleImpl with PersistenceModuleImpl with RoutingModuleImpl
  implicit val system = modules.system
  implicit val materializer = ActorMaterializer()
  implicit val ec = modules.system.dispatcher

  modules.asset_groupsDal.createTable()
  modules.asset_mastersDal.createTable()
  modules.asset_owner_groupsDal.createTable()
  modules.asset_ownersDal.createTable()
  modules.asset_typesDal.createTable()
  modules.buildingsDal.createTable()
  modules.manufacturersDal.createTable()
  modules.vendorsDal.createTable()
  modules.suppliersDal.createTable()
  modules.printersDal.createTable()


  val bindingFuture = Http().bindAndHandle(modules.routes, "localhost", 8080)
 
  println(s"Server online at http://localhost:8080/")

}