package persistence.entities

import java.sql.Date
import slick.driver.PostgresDriver.api._

/**
  * Created by chris on 3/13/16.
  */
case class Asset_Group(
                      id: Long,
                      asset_group_pk: String,
                      asset_group_desc: String,
                      status_ck: String,
                      status_date: Option[Date],
                      activity_date: Option[Date],
                      user_id: Option[String],
                      ip_rpt_ck: Option[String]
                      )

  class Asset_GroupsTable(tag: Tag) extends BaseTable[Asset_Group](tag, "Asset_Groups") {
    def asset_group_pk = column[String]("asset_group_pk")
    def asset_group_desc = column[String]("asset_group_desc")
    def status_ck = column[String]("status_ck")
    def status_date = column[Option[Date]]("status_date")
    def activity_date = column[Option[Date]]("activity_date")
    def user_id = column[Option[String]]("user_id")
    def ip_rpt_ck = column[Option[String]]("ip_rpt_ck")
    def * = (id, asset_group_pk, asset_group_desc, status_ck, status_date, activity_date, user_id, ip_rpt_ck) <> (Asset_Group.tupled, Asset_Group.unapply _)
  }