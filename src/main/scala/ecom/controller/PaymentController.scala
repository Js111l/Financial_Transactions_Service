package ecom.controller

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives.{entity, pathPrefix, _}
import akka.http.scaladsl.server.Route
import ch.megard.akka.http.cors.scaladsl.CorsDirectives.cors
import com.google.inject.{Inject, Singleton}
import ecom.actors.model.{PaymentIntentRequestModel, TokenResponse}
import ecom.actors.payment.PaymentService
import ecom.service.TransactionService

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}


@Singleton
class PaymentController @Inject()(service: TransactionService)(implicit val ec: ExecutionContext) extends BaseController {

  implicit val system: ActorSystem = ActorSystem("payment-controller")
  private val paymentActor: ActorRef = system.actorOf(Props[PaymentService])

  val routes: Route =
    pathPrefix("payments") {
      path("intent") {
        // Handle POST requests
        post {
          entity(as[PaymentIntentRequestModel]) { model =>
            onComplete(service.getIdSecretTupleAndSaveNewOrder(model)) {
              case Failure(exception) =>
                val exMessage = exception.getMessage
                complete(HttpResponse(StatusCodes.InternalServerError, entity = exMessage))
              case Success((orderId, intentId, token)) =>
                complete(TokenResponse(orderId, intentId, token))
            }
          }
        } ~
          // Handle GET requests
          get {
            parameters("uuid") { uuid =>
              onComplete(service.getSecretByUUID(uuid)) {
                case Failure(exception) =>
                  val exMessage = exception.getMessage
                  complete(HttpResponse(StatusCodes.InternalServerError, entity = exMessage))
                case Success(response) =>
                  complete(response)
              }
            }
          }
      }
    }
}

