package persistence.entities

import java.sql.Date
import slick.driver.PostgresDriver.api._

/**
  * Created by chris on 3/13/16.
  */
case class Printer (
                     id: Long,
                     printer_pk: String,
                     printer_desc: String,
                     status_ck: String,
                     status_date: Option[Date] = None,
                     pages_per_min: Option[Int] = None,
                     charge_back: Option[Double] = None,
                     activity_date: Option[Date]= None,
                     user_id: Option[String] = None
                   ) extends BaseEntity


class PrintersTable(tag: Tag) extends BaseTable[Printer](tag, "printers") {
  def printer_pk = column[String]("printer_pk")
  def printer_desc = column[String]("printer_desc")
  def status_ck = column[String]("status_ck")
  def status_date = column[Option[Date]]("status_date")
  def pages_per_min = column[Option[Int]]("pages_per_min")
  def charge_back = column[Option[Double]]("charge_back")
  def activity_date = column[Option[Date]]("activity_date")
  def user_id = column[Option[String]]("user_id")

  def * = (id, printer_pk, printer_desc, status_ck, status_date , pages_per_min, charge_back, activity_date , user_id ) <> (Printer.tupled, Printer.unapply _)
}
