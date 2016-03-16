package persistence.entities


import org.joda.time.DateTime
import slick.driver.PostgresDriver.api._
/**
  * Created by chris on 3/13/16.
  */
trait VendorEntity{
  val vendor_pk: String
  val banner_id: String
  val notes: String
  val customer_number: String
}

case class Vendor(id: Long,
                  status_date: DateTime,
                  status_ck: String,
                  activity_date: DateTime,
                  activity_user: String,
                  created_date: DateTime,
                  created_user: String,
                  vendor_pk: String,
                  banner_id: String,
                  notes: String,
                  customer_number: String
                 ) extends BaseEntity with ActivityEntity with CreatedEntity with StatusEntity with VendorEntity

case class SimpleVendor(vendor_pk: String, banner_id: String, notes: String, customer_number: String) extends VendorEntity

class VendorsTable(tag: Tag) extends StandardTable[Vendor](tag, "vendors") {
  def vendor_pk = column[String]("vendor_pk")
  def banner_id = column[String]("banner_id")
  def notes = column[Option[String]]("notes")
  def customer_numbers = column[Option[String]]("customer_numbers")

  def * = (id, vendor_pk, banner_id, notes, customer_numbers, status_ck, status_date, activity_date, activity_user, created_date, created_user) <> (Vendor.tupled, Vendor.unapply )
}

