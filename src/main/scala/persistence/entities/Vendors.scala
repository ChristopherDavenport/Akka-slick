package persistence.entities


import org.joda.time.DateTime
import slick.driver.PostgresDriver.api._
import com.github.tototoshi.slick.PostgresJodaSupport._
import utils.{JsonModule, JsonModuleImpl}
/**
  * Created by chris on 3/13/16.
  */
trait VendorEntity{
  val vendor_pk: String
  val banner_id: Option[String]
  val notes: Option[String]
  val customer_number: Option[String]
}

case class Vendor(
                   id: Long,

                  vendor_pk: String,
                  banner_id: Option[String],
                  notes: Option[String],
                  customer_number: Option[String],

                  status_ck: String,
                  status_date: DateTime,

                  activity_user: String,
                  activity_date: DateTime,

                  created_user: String,
                  created_date: DateTime

                 ) extends BaseEntity with ActivityEntity with CreatedEntity with StatusEntity with VendorEntity

case class SimpleVendor(
                         vendor_pk: String,
                         banner_id: Option[String],
                         notes: Option[String],
                         customer_number: Option[String]
                       ) extends VendorEntity


class VendorsTable(tag: Tag) extends StandardTable[Vendor](tag, "vendors") {

  def vendor_pk = column[String]("vendor_pk")
  def banner_id = column[Option[String]]("banner_id")
  def notes = column[Option[String]]("notes")
  def customer_numbers = column[Option[String]]("customer_numbers")

  def * = (
    id,

    vendor_pk,
    banner_id,
    notes,
    customer_numbers,

    status_ck,
    status_date,

    activity_user,
    activity_date,

    created_user,
    created_date

    ) <> (Vendor.tupled , Vendor.unapply)

  def idx = index("vendor_pk", vendor_pk, unique = true)

  val pk = vendor_pk
}

