package persistence.entities

import org.joda.time.DateTime

trait BaseEntity {
  val id : Long
  def isValid : Boolean = true
}

trait CreatedEntity {
  val created_date: DateTime
  val created_user: String
}

trait StatusEntity{
  val status_ck: String
  val status_date: DateTime
}
trait ActivityEntity{
  val activity_date: DateTime
  val activity_user: String
}