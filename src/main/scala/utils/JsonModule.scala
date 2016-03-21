package utils

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat
import persistence.entities._
import spray.json._

/**
  * Created by davenpcm on 3/16/2016.
  */
trait JsonModule {
  implicit object DateTimeFormat extends RootJsonFormat[DateTime] {
    val formatter = ISODateTimeFormat.basicDateTimeNoMillis
    def write(obj: DateTime): JsValue = {
      JsString(formatter.print(obj))
    }
    def read(json: JsValue): DateTime = json match {
      case JsString(s) => try {
        formatter.parseDateTime(s)
      }
      catch {
        case t: Throwable => error(s)
      }
      case _ => error(json.toString())
    }
    def error(v: Any): DateTime = {
      val example = formatter.print(0)
      deserializationError(f"'$v' is not a valid date value. Dates must be in compact ISO-8601 format, e.g. '$example'")
    }
  }

  implicit val supplierFormat: RootJsonFormat[Supplier]
  implicit val simpleSupplierFormat: RootJsonFormat[SimpleSupplier]

  implicit val printerFormat: RootJsonFormat[Printer]
  implicit val simplePrinterFormat: RootJsonFormat[SimplePrinter]

  implicit val vendorFormat:  RootJsonFormat[Vendor]
  implicit val simpleVendorFormat: RootJsonFormat[SimpleVendor]

  implicit val manufacturerFormat: RootJsonFormat[Manufacturer]
  implicit val simpleManufacturerFormat: RootJsonFormat[SimpleManufacturer]

  implicit val buildingFormat:  RootJsonFormat[Building]
  implicit val simpleBuildingFormat: RootJsonFormat[SimpleBuilding]

  implicit val assetGroupFormat: RootJsonFormat[Asset_Group]
  implicit val simpleAssetGroupFormat : RootJsonFormat[SimpleAsset_Group]

}

trait JsonModuleImpl extends DefaultJsonProtocol with JsonModule with SprayJsonSupport{
  override val supplierFormat = jsonFormat9(Supplier)
  override val simpleSupplierFormat = jsonFormat2(SimpleSupplier)

  override val printerFormat = jsonFormat11(Printer)
  override val simplePrinterFormat =  jsonFormat4(SimplePrinter)

  override val vendorFormat = jsonFormat11(Vendor)
  override val simpleVendorFormat = jsonFormat4(SimpleVendor)

  override val manufacturerFormat = jsonFormat10(Manufacturer)
  override val simpleManufacturerFormat = jsonFormat3(SimpleManufacturer)

  override val buildingFormat = jsonFormat9(Building)
  override val simpleBuildingFormat = jsonFormat2(SimpleBuilding)

  override val assetGroupFormat = jsonFormat10(Asset_Group)
  override val simpleAssetGroupFormat = jsonFormat3(SimpleAsset_Group)

}