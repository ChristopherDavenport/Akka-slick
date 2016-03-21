package routing

import akka.http.javadsl.server.HttpServiceBase
import akka.http.scaladsl.server.Route
import persistence.entities.StandardTable
import persistence.dal.BaseDal
import spray.json.RootJsonFormat
import utils.JsonModuleImpl



/**
  * Created by davenpcm on 3/21/2016.
  */

trait BaseRouter[C, T, A]{
  def route: Route

}

class BaseRouterImpl[C, T <: StandardTable[C], A](name: String, dal: BaseDal[T, C])
(implicit val classJsonFormat: RootJsonFormat[C],
 implicit val simpleJsonFormat: RootJsonFormat[A])


  extends HttpServiceBase with BaseRouter[C,T,A] with JsonModuleImpl{

  import akka.http.scaladsl.model.StatusCodes._
  import akka.http.scaladsl.server.Directives._
  import scala.util.{Failure, Success}
  import spray.json._
  import slick.driver.PostgresDriver.api._

  override  def route: Route = {
    pathPrefix(name) {
      pathEndOrSingleSlash {
        get {
          onComplete(dal.findAll) {
            case Success(values) => complete(values.toJson)
            case Failure(ex) => complete(InternalServerError, s"An error occurred: ${ex.getMessage}")
          }
        } ~
          post {
            entity(as[Seq[C]]) { valuesToInsert =>
              onComplete(
                dal.insert(valuesToInsert)
              ) {
                case Success(insertedEntities) => complete(Created)
                case Failure(ex) => complete(InternalServerError, s"An error occurred: ${ex.getMessage}")
              }
            }
          }
      } ~
//      pathPrefix("simple"){
//        pathEndOrSingleSlash{
//          post {
//            entity(as[Seq[A]]){
//              valuesToInsert =>
//                onComplete( dal.insert(
//                  for (value <- valuesToInsert) yield convert(value)
//                )) {
//                  case Success(insertedEntities) => complete(Created)
//                  case Failure(ex) => complete(InternalServerError, s"An error occurred: ${ex.getMessage}")
//                }
//            }
//          }
//        }
//      } ~
        pathPrefix(IntNumber) { Id =>
        pathEnd {
          get {
            onComplete(dal.findById(Id).mapTo[Option[C]]) {
              case Success(classOpt) =>
                classOpt match {
                  case Some(print) => complete(print.toJson)
                  case None => complete(NotFound, s"The $name doesn't exist")
                }
              case Failure(ex) => complete(InternalServerError, s"An error occurred: ${ex.getMessage}")
            }
          } ~
            put {
              entity(as[C]) { valueToUpdate =>
                onComplete(dal.update(valueToUpdate)) {
                  case Success(updatedEntity) => complete(Created)
                  case Failure(ex) => complete(InternalServerError, s"An error occurred: ${ex.getMessage}")
                }
              }
            }
        }
      } ~
        pathPrefix(Segment) { firstParam =>
          pathEndOrSingleSlash {
            get {
              onComplete(dal.findByFilterToOne(_.pk === firstParam).mapTo[Option[C]]) {
                case Success(valueOpt) => valueOpt match {
                  case Some(value) => complete(value.toJson)
                  case None => complete(NotFound, s"The $name - $firstParam - doesn't exist - First Param Response")
                }
                case Failure(ex) => complete(InternalServerError, s"An error occurred: ${ex.getMessage}")
              }
            }
          } ~
            pathPrefix(Segment){ secondParam =>
              pathEndOrSingleSlash{
                complete(NotFound, s"The supplier doesn't exist - Second Param Response")
              } ~
                pathPrefix(Segment){ thirdParam =>
                  pathEndOrSingleSlash{
                    complete(NotFound, s"The supplier doesn't exist - Third Param Response")
                  } ~
                    pathPrefix(Segment){ fourthParam =>
                      complete(NotFound, s"The supplier doesn't exist - Fourth Param Response")
                    }
                }
            }
        }
    }
  }
}