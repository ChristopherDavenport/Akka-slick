package persistence.entities

import org.joda.time.DateTime
import slick.driver.PostgresDriver.api._
import com.github.tototoshi.slick.PostgresJodaSupport._

/**
  * Created by chris on 3/13/16.
  */
trait BuildingEntity{
  def building_code_pk: String
  def building_desc: String
}

case class SimpleBuilding(
                         building_code_pk: String,
                         building_desc: String
                         ) extends BuildingEntity

case class Building(
                    id: Long,

                    building_code_pk: String,
                    building_desc: String,

                    status_ck: String,
                    status_date: DateTime,

                    activity_user: String,
                    activity_date: DateTime,

                    created_user: String,
                    created_date: DateTime

                    ) extends BaseEntity with ActivityEntity with CreatedEntity with StatusEntity with BuildingEntity

class BuildingsTable(tag: Tag) extends StandardTable[Building](tag, "buildings") {
  def building_code_pk = column[String]("building_code_pk")
  def building_desc = column[String]("building_desc")

  def * = (
    id,

    building_code_pk,
    building_desc,

    status_ck,
    status_date,

    activity_user,
    activity_date,

    created_user ,
    created_date

    ) <> ( Building.tupled , Building.unapply )

  def idx = index("building_code_pk", building_code_pk, unique = true)

  val pk = building_code_pk

}