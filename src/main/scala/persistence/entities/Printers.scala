package persistence.entities


import slick.driver.PostgresDriver.api._
import com.github.tototoshi.slick.PostgresJodaSupport._
import org.joda.time.DateTime


/**
  * Created by chris on 3/13/16.
  */
trait PrinterEntity{
  def printer_pk: String
  def printer_desc: String
  def pages_per_min: Option[Int]
  def charge_back: Option[Double]
}

case class SimplePrinter(
                        printer_pk: String,
                        printer_desc: String,
                        pages_per_min: Option[Int],
                        charge_back: Option[Double]
                        ) extends PrinterEntity

case class Printer (
                     id: Long,

                     printer_pk: String,
                     printer_desc: String,
                     pages_per_min: Option[Int] = None,
                     charge_back: Option[Double] = None,

                     status_ck: String,
                     status_date: DateTime,

                     activity_user: String,
                     activity_date: DateTime,

                     created_user: String,
                     created_date: DateTime


                   ) extends StandardEntity with PrinterEntity


class PrintersTable(tag: Tag) extends StandardTable[Printer](tag, "printers") {
  def printer_pk = column[String]("printer_pk")
  def printer_desc = column[String]("printer_desc")
  def pages_per_min = column[Option[Int]]("pages_per_min")
  def charge_back = column[Option[Double]]("charge_back")


  def * = (
    id,

    printer_pk,
    printer_desc,
    pages_per_min,
    charge_back,

    status_ck,
    status_date,
    activity_user,
    activity_date,
    created_user,
    created_date) <> (Printer.tupled, Printer.unapply)

  def idx = index("printer_pk", printer_pk, unique = true)
  val pk = printer_pk
}
