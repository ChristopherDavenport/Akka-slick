package persistence.entities

import com.github.tototoshi.slick.PostgresJodaSupport._
import slick.driver.PostgresDriver.api._
import org.joda.time.DateTime

case class Supplier(id: Long,

                    name: String,
                    desc: String,

                    status_ck: String,
                    status_date: DateTime,

                    activity_user: String,
                    activity_date: DateTime,

                    created_user: String,
                    created_date: DateTime

                   ) extends StandardEntity

case class SimpleSupplier(name: String, desc: String)

class SuppliersTable(tag: Tag) extends StandardTable[Supplier](tag, "SUPPLIERS") {
  def name = column[String]("userID")
  def desc = column[String]("last_name")
  def * = (id, name, desc, status_ck, status_date, activity_user, activity_date, created_user, created_date) <> (Supplier.tupled, Supplier.unapply)
  val pk = name
}