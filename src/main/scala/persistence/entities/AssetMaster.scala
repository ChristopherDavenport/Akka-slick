package persistence.entities

import java.sql.Date
import slick.driver.PostgresDriver.api._
/**
  * Created by chris on 3/13/16.
  */
case class Asset (
                   id: Long,
                   asset_tag_pk: String,
                   tag_type_pk_ck: String,
                   asset_desc: String,
                   status_ck: String,
                   status_date: Option[Date] = None,
                   serial_number_u: String,
                   asset_type_fk: Long,
                   model_number: String,
                   manufacturer_fk: Long,
                   vendor_fk: Long,
                   asset_owner_fk: Long,
                   staff_userid: String,
                   building_fk: Long,
                   location: String,
                   activity_date: Option[Date]= None,
                   user_id: Option[String]= None,
                   encrypted_ck: Option[String]= None,
                   last_inv_verify: Option[Date]= None
                 )

class AssetMasterTable(tag: Tag) extends BaseTable[Asset](tag, "asset_master") {
  def asset_tag_pk = column[String]("asset_tag_pk")
  def tag_type_pk_ck = column[String]("tag_type_pk_ck")
  def asset_desc = column[String]("asset_desc")
  def status_ck = column[String]("status_ck")
  def status_date = column[Option[Date]]("status_date")
  def serial_number_u = column[String]("serial_number_u")
  def asset_type_fk = column[Long]("asset_type_fk")
  def model_number = column[String]("model_number")
  def manufacturer_fk = column[Long]("manufacturer_fk")
  def vendor_fk = column[Long]("vendor_fk")
  def asset_owner_fk = column[Long]("asset_owner_fk")
  def staff_userid = column[String]("staff_userid")
  def building_fk = column[Long]("building_fk")
  def location = column[String]("location")
  def activity_date = column[Option[Date]]("activity_date")
  def user_id = column[Option[String]]("user_id")
  def encrypted_ck = column[Option[String]]("encrypted_ck")
  def last_inv_verify = column[Option[Date]]("last_inv_verify")

  def * = (
    id,
    asset_tag_pk,
    tag_type_pk_ck,
    asset_desc,
    status_ck,
    status_date,
    asset_type_fk,
    model_number,
    manufacturer_fk,
    vendor_fk,
    asset_owner_fk,
    staff_userid,
    building_fk,
    location,
    activity_date,
    user_id,
    encrypted_ck,
    last_inv_verify
    ) <> (Asset.tupled, Asset.unapply _)

}

