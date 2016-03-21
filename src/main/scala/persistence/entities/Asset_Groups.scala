package persistence.entities


import com.github.tototoshi.slick.PostgresJodaSupport._
import org.joda.time.DateTime
import slick.driver.PostgresDriver.api._

/**
  * Created by chris on 3/13/16.
  */
trait Asset_GroupEntity{
  def asset_group_pk: String
  def asset_group_desc: String
  def ip_rpt_ck: Option[String]
}
case class SimpleAsset_Group(
                            asset_group_pk: String,
                            asset_group_desc: String,
                            ip_rpt_ck: Option[String]
                            ) extends Asset_GroupEntity

case class Asset_Group(
                      id: Long,

                      asset_group_pk: String,
                      asset_group_desc: String,
                      ip_rpt_ck: Option[String],

                      status_ck: String,
                      status_date: DateTime,

                      activity_user: String,
                      activity_date: DateTime,

                      created_user: String,
                      created_date: DateTime

                      ) extends StandardEntity with Asset_GroupEntity

  class Asset_GroupsTable(tag: Tag) extends StandardTable[Asset_Group](tag, "asset_groups") {
    def asset_group_pk = column[String]("asset_group_pk")
    def asset_group_desc = column[String]("asset_group_desc")
    def ip_rpt_ck = column[Option[String]]("ip_rpt_ck")
    def * = (
      id,

      asset_group_pk,
      asset_group_desc,
      ip_rpt_ck,

      status_ck,
      status_date,
      activity_user,
      activity_date,
      created_user,
      created_date ) <> (Asset_Group.tupled, Asset_Group.unapply)
    val pk = asset_group_pk
  }