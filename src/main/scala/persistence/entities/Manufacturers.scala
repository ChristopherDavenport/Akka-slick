package persistence.entities

import org.joda.time.DateTime
import slick.driver.PostgresDriver.api._
import com.github.tototoshi.slick.PostgresJodaSupport._
/**
  * Created by chris on 3/13/16.
  */
trait ManufacturerEntity extends SimpleEntity{
  def manufacturer_pk: String
  def manufacturer_desc: String
  def notes: Option[String]

  def pk = manufacturer_pk
  def pk_2 = None
  def pk_3 = None
  def pk_4 = None
}

case class Manufacturer(
                        id: Long,

                        manufacturer_pk: String,
                        manufacturer_desc: String,
                        notes: Option[String],
                       
                        status_ck: String,
                        status_date: DateTime,

                        activity_user: String,
                        activity_date: DateTime,

                        created_user: String,
                        created_date: DateTime


                        ) extends BaseEntity with ActivityEntity with CreatedEntity with StatusEntity with ManufacturerEntity

case class SimpleManufacturer(
                             manufacturer_pk: String,
                             manufacturer_desc: String,
                             notes: Option[String]
                             ) extends ManufacturerEntity

class ManufacturersTable(tag: Tag) extends StandardTable[Manufacturer](tag, "manufacturers") {
  def manufacturer_pk = column[String]("manufacturer_pk")
  def manufacturer_desc = column[String]("manufacturer_desc")
  def notes = column[Option[String]]("notes")

  def * = (
    id,

    manufacturer_pk,
    manufacturer_desc,
    notes,

    status_ck,
    status_date,

    activity_user,
    activity_date,

    created_user,
    created_date

    ) <> (Manufacturer.tupled, Manufacturer.unapply)

  def idx = index("manufacturer_pk" , manufacturer_pk, unique = true)
}
