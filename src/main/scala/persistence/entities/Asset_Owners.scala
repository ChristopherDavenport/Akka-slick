package persistence.entities

import java.sql.Date
import slick.driver.PostgresDriver.api._
/**
  * Created by chris on 3/13/16.
  */
case class Asset_Owner (
                          id: Long,
                          asset_owner_pk: String,
                          asset_owner_desc:String,
                          status_ck:String,
                          status_date: Option[Date],
                          orgn_code: Option[String],
                          activity_date: Option[Date],
                          user_id: Option[String],
                          asset_owner_group_fk: Long
                        )

class AssetOwnersTable(tag: Tag) extends BaseTable[Asset_Owner](tag, "asset_owner") {
  def asset_owner_group_pk = column[String]("asset_owner_pk")
  def asset_owner_group_desc = column[String]("asset_owner_desc")
  def status_ck = column[String]("status_ck")
  def status_date = column[Option[Date]]("status_date")
  def orgn_code = column[Option[String]]("orgn_code")
  def activity_date = column[Option[Date]]("activity_date")
  def user_id = column[Option[String]]("user_id")
  def asset_owner_group_fk = column[Option[Long]]("asset_owner_group_fk")

  def * = (id, asset_owner_group_pk, asset_owner_group_desc, status_ck, status_date , orgn_code, activity_date , user_id, asset_owner_group_fk ) <> (Asset_Owner.tupled, Asset_Owner.unapply _)
}