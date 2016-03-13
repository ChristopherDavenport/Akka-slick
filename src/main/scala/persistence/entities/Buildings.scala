package persistence.entities

import java.sql.Date
import slick.driver.PostgresDriver.api._
/**
  * Created by chris on 3/13/16.
  */
case class Building(
                    id: Long,
                    building_code_pk: String,
                    building_desc: String,
                    activity_date: Option[Date],
                    user_id: Option[String]
                    )

class BuildingsTable(tag: Tag) extends BaseTable[Building](tag, "buildings") {
  def building_code_pk = column[String]("building_code_pk")
  def building_desc = column[String]("building_desc")
  def activity_date = column[Option[Date]]("activity_date")
  def user_id = column[Option[String]]("user_id")

  def * = (id, building_code_pk, building_desc, activity_date , user_id ) <> (Building.tupled, Building.unapply _)

}