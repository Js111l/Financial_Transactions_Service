package ecom.controller

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives.{entity, pathPrefix, _}
import akka.http.scaladsl.server.Route
import com.google.inject.{Inject, Singleton}
import ecom.actors.model.{PaymentIntentRequestModel, TokenResponse}
import ecom.actors.payment.PaymentService
import ecom.service.TransactionService
import ecom.utils.TokenUtil

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}


@Singleton
class PaymentController @Inject()(service: TransactionService)(implicit val ec: ExecutionContext) extends BaseController {

  implicit val system: ActorSystem = ActorSystem("payment-controller")

  val routes: Route =
    pathPrefix("payments") {
      path("intent") {
        post {
          headerValueByName("Authorization") { token =>
            new TokenUtil().verifyToken(token)
            val customer = new TokenUtil().getCustomerDataFromToken(token)

            entity(as[PaymentIntentRequestModel]) { model =>
              onComplete(service.getIdSecretTupleAndSaveNewOrder(model, customer)) {
                case Failure(exception) =>
                  val exMessage = exception.getMessage
                  complete(HttpResponse(StatusCodes.InternalServerError, entity = exMessage))
                case Success((orderId, intentId, token)) =>
                  complete(TokenResponse(orderId, intentId, token))
              }
            }
          }
        }
      } ~
        get {
          parameters("uuid") { uuid =>
            optionalHeaderValueByName("Authorization") { token =>
              onComplete(service.getSecretByUUID(uuid, token)) {
                case Failure(exception) =>
                  val exMessage = exception.getMessage
                  complete(HttpResponse(StatusCodes.InternalServerError, entity = exMessage))
                case Success(response) =>
                  complete(response)
              }
            }
          }
        } ~
        path("intent" / Segment) { uuid =>
          get {
            onComplete(service.getPaymentDetails(uuid)) {
              case Failure(exception) =>
                val exMessage = exception.getMessage
                complete(HttpResponse(StatusCodes.InternalServerError, entity = exMessage))
              case Success(value) =>
                complete(value)
            }
          }
        }

    }

}

