package persistence.entities

import java.sql.Date
import slick.driver.PostgresDriver.api._
/**
  * Created by chris on 3/13/16.
  */
case class Manufacturer(
                        id: Long,
                        manufacturer_pk: String,
                        manufacturer_desc: String,
                        status_ck: String,
                        status_date: Option[Date],
                        activity_date: Option[Date],
                        user_id: Option[String],
                        notes: Option[String]
                        ) extends BaseEntity

class ManufacturersTable(tag: Tag) extends BaseTable[Manufacturer](tag, "manufacturers") {
  def manufacturer_pk = column[String]("manufacturer_pk")
  def manufacturer_desc = column[String]("manufacturer_desc")
  def status_ck = column[String]("status_ck")
  def status_date = column[Option[Date]]("status_date")
  def activity_date = column[Option[Date]]("activity_date")
  def user_id = column[Option[String]]("user_id")
  def notes = column[Option[String]]("notes")

  def * = (id, manufacturer_pk, manufacturer_desc, status_ck, status_date , activity_date , user_id, notes ) <> (Manufacturer.tupled, Manufacturer.unapply _)
}
