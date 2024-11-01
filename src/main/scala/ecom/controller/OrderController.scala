package ecom.controller

import akka.actor.ActorSystem
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.google.inject.{Inject, Singleton}
import ecom.service.OrderService
import spray.json._

import scala.concurrent.ExecutionContext

@Singleton
class OrderController @Inject()(orderService: OrderService, implicit val ec: ExecutionContext) extends BaseController {
  implicit val system: ActorSystem = ActorSystem("order_controller")


  val routes: Route =
    logRequestResult("orders-logging") {
      pathPrefix("orders") { //no "/" in paths
        path(LongNumber) { userId =>
          get {
            val list = orderService.getOrderListForUser(userId).map(x => x.toJson).toJson
            complete(StatusCodes.OK, list)
          }
        }
      }
    }
}
