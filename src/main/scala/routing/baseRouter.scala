package routing

import akka.http.javadsl.server.HttpServiceBase
import akka.http.scaladsl.server.Route
import persistence.entities.{SimpleEntity, StandardTable}
import persistence.dal.BaseDal
import spray.json.RootJsonFormat
import utils.{JsonModuleImpl, PersistenceModuleImpl}

import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by davenpcm on 3/21/2016.
  */

trait BaseRouter{
  def route: Route
}

class baseRouterImpl[C <: SimpleEntity, T <: StandardTable[C], A <: SimpleEntity]
(name: String, dal: BaseDal[T, C])
(implicit val persistenceModuleImpl: PersistenceModuleImpl,
 implicit val JsonModuleImpl: JsonModuleImpl,
 implicit val classJsonFormat: RootJsonFormat[C],
 implicit val simpleClassJsonFormat: RootJsonFormat[A]
)

  extends HttpServiceBase with BaseRouter with JsonModuleImpl{

  import akka.http.scaladsl.model.StatusCodes._
  import akka.http.scaladsl.server.Directives._
  import scala.util.{Failure, Success}
  import spray.json._

  override  def route: Route ={
    pathPrefix(name){
      pathEndOrSingleSlash{
        get{
          onComplete(dal.findAll) {
            case Success(values) => complete(values.toJson)
            case Failure(ex) => complete(InternalServerError, s"An error occurred: ${ex.getMessage}")
          }
        }
      }
    } ~
      pathPrefix(IntNumber){ Id =>
        pathEnd{
          get {
            onComplete(dal.findById(Id).mapTo[Option[C]]) {
              case Success(classOpt) =>
                classOpt match {
                  case Some(print) => complete( print.toJson )
                  case None => complete(NotFound, s"The supplier doesn't exist")
                }
              case Failure(ex) => complete(InternalServerError, s"An error occurred: ${ex.getMessage}")
            }
          } ~
            put {
              entity(as[C]) { vendorToUpdate =>
                onComplete( dal.update(vendorToUpdate)) {
                  case Success(updatedEntity) => complete(Created)
                  case Failure(ex) => complete(InternalServerError, s"An error occurred: ${ex.getMessage}")
                }
              }
            }
        }
      }
  }
}