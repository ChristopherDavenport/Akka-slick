package persistence.entities


import java.sql.Date
import slick.driver.PostgresDriver.api._
/**
  * Created by chris on 3/13/16.
  */
case class Vendor(
                  id: Long,
                  vendor_pk: String,
                  status_ck: String,
                  status_date: Option[Date],
                  banner_id: String,
                  notes: Option[String],
                  activity_date: Option[Date],
                  user_id: Option[String],
                  customer_numbers: Option[String]
                  )
class VendorsTable(tag: Tag) extends BaseTable[Vendor](tag, "vendors") {
  def vendor_pk = column[String]("vendor_pk")
  def status_ck = column[String]("status_ck")
  def status_date = column[Option[Date]]("status_date")
  def banner_id = column[String]("banner_id")
  def notes = column[Option[String]]("notes")
  def activity_date = column[Option[Date]]("activity_date")
  def user_id = column[Option[String]]("user_id")
  def customer_numbers = column[Option[String]]("customer_numbers")
  def * = (id, vendor_pk, status_ck, status_date, banner_id, notes, activity_date, user_id, customer_numbers) <> (Vendor.tupled, Vendor.unapply _ )
}

