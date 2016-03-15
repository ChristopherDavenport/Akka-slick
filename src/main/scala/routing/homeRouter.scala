package routing

import akka.http.javadsl.server.HttpServiceBase
import akka.http.scaladsl.server.Directives._

/**
  * Created by chris on 3/14/16.
  */
trait homeRouter extends HttpServiceBase{
  val homeRouter = pathEndOrSingleSlash{
    get{
      complete("This is not the string you are looking for - BASE ")
    }
  }
}
