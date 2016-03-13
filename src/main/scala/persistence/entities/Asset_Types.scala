package persistence.entities

import java.sql.Date
import slick.driver.PostgresDriver.api._

/**
  * Created by chris on 3/13/16.
  */
case class Asset_Type(
                      id: Long,
                      asset_type_pk: String,
                      asset_type_desc: String,
                      status_ck: String,
                      status_date: Option[Date],
                      activity_date: Option[Date],
                      user_id: Option[String],
                      asset_group_fk: Option[Long]
                      )
class Asset_TypesTable(tag: Tag) extends BaseTable[Asset_Type](tag, "Asset_Types") {
  def asset_type_pk = column[String]("asset_type_pk")
  def asset_type_desc = column[String]("asset_type_desc")
  def status_ck = column[String]("status_ck")
  def status_date = column[Option[Date]]("status_date")
  def activity_date = column[Option[Date]]("activity_date")
  def user_id = column[Option[String]]("user_id")
  def asset_group_fk= column[Option[Long]]("asset_group_fk")

  def * = (id, asset_type_pk, asset_type_desc, status_ck, status_date, activity_date, user_id, asset_group_fk) <> (Asset_Type.tupled, Asset_Type.unapply _)
}