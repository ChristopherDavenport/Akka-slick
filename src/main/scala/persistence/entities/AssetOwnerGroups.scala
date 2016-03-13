package persistence.entities
import java.sql.Date
import slick.driver.PostgresDriver.api._
/**
  * Created by chris on 3/13/16.
  */
case class AssetOwnerGroup(
                             id: Long,
                             asset_owner_group_pk: String,
                             asset_owner_group_desc: String,
                             status_ck: String,
                             status_date: Option[Date] = None,
                             orgn_code: Option[String] = None,
                             activity_date: Option[Date] = None,
                             user_id: Option[String]
                           )

class AssetOwnerGroupsTable(tag: Tag) extends BaseTable[AssetOwnerGroup](tag, "asset_owner_group") {
  def asset_owner_group_pk = column[String]("asset_owner_group_pk")
  def asset_owner_group_desc = column[String]("asset_owner_group_desc")
  def status_ck = column[String]("status_ck")
  def status_date = column[Option[Date]]("status_date")
  def orgn_code = column[Option[String]]("orgn_code")
  def activity_date = column[Option[Date]]("activity_date")
  def user_id = column[Option[String]]("user_id")

  def * = (id, asset_owner_group_pk, asset_owner_group_desc, status_ck, status_date , orgn_code, activity_date , user_id ) <> (AssetOwnerGroup.tupled, AssetOwnerGroup.unapply _)
}


