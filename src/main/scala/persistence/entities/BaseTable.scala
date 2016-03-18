package persistence.entities

import com.github.tototoshi.slick.PostgresJodaSupport._
import slick.driver.PostgresDriver.api._
import slick.lifted.Tag
import org.joda.time.DateTime



abstract class BaseTable[T](tag: Tag, name: String) extends Table[T](tag, name) {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
}

abstract class StandardTable[T](tag: Tag, name: String) extends BaseTable[T](tag, name){
  def status_ck = column[String]("status_ck")
  def status_date = column[DateTime]("status_date")
  def activity_date = column[DateTime]("activity_date")
  def activity_user = column[String]("activity_user")
  def created_date = column[DateTime]("created_date")
  def created_user = column[String]("created_user")
}