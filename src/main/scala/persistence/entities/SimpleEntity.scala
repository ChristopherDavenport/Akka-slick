package persistence.entities

/**
  * Created by davenpcm on 3/21/2016.
  */
trait SimpleEntity {
  def pk: String
  def pk_2: Option[String]
  def pk_3: Option[String]
  def pk_4: Option[String]
}
