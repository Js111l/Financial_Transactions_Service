package ecom.controller

import akka.actor.ActorSystem
import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.google.inject.{Inject, Singleton}
import ecom.actors.model.UserOrderListModel
import ecom.service.OrderService
import ecom.utils.TokenUtil
import spray.json._

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

@Singleton
class OrderController @Inject()(orderService: OrderService, implicit val ec: ExecutionContext) extends BaseController {
  implicit val system: ActorSystem = ActorSystem("order_controller")

  val routes: Route =
    pathPrefix("orders") { //no "/" in paths
      path("list")
      headerValueByName("Authorization") { token =>
        new TokenUtil().verifyToken(token)

        get {
          onComplete(orderService.getOrderListForUser(1)) {
            case Failure(exception) => {
              val exMessage = exception.getMessage
              complete(HttpResponse(StatusCodes.InternalServerError, entity = exMessage))
            }
            case Success(value) => {
              complete(value)
            }
          }
        }
      }
    }
}
