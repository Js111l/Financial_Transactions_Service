package controller

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Directives.pathPrefix
import akka.http.scaladsl.server.{Directives, Route}
import com.google.inject.Singleton

@Singleton
class PaymentController {

//  val routes: Route = pathPrefix("api" / "payments") {
//    path("products/active") {
//      Directives.get {
//        complete(StatusCodes.OK, "")
//      }
//    }
//  }
}
