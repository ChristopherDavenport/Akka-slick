package utils

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat
import persistence.entities._
import spray.json._

/**
  * Created by davenpcm on 3/16/2016.
  */
trait JsonModule extends DefaultJsonProtocol with SprayJsonSupport{
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

}

trait JsonModuleImpl extends JsonModule{
  implicit val supplierFormat = jsonFormat3(Supplier)
  implicit val simpleSupplierFormat = jsonFormat2(SimpleSupplier)

  implicit val printerFormat = jsonFormat9(Printer)

  implicit val vendorFormat = jsonFormat11(Vendor)
  implicit val simpleVendorFormat = jsonFormat4(SimpleVendor)

}