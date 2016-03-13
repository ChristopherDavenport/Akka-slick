package entities

import persistence.entities.{Printer, SimpleSupplier, Supplier}
import spray.json.DefaultJsonProtocol

object JsonProtocol extends DefaultJsonProtocol {
  implicit val supplierFormat = jsonFormat3(Supplier)
  implicit val simpleSupplierFormat = jsonFormat2(SimpleSupplier)
  implicit val printerFormat = jsonFormat9(Printer)
}