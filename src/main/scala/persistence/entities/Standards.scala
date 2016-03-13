package persistence.entities
import slick.driver.PostgresDriver.api._
/**
  * Created by chris on 3/13/16.
  */
trait Standards {
  def status_ck = column[]
}
