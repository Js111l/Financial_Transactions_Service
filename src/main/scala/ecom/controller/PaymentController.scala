package ecom.controller

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Directives.pathPrefix
import akka.http.scaladsl.server.{Directives, Route}
import akka.pattern.ask
import akka.util.Timeout
import com.google.inject.{Inject, Singleton}
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import ecom.actors.model.{PaymentRequest, PaymentResponse}
import ecom.actors.payment.PaymentService
import ecom.enums.DocumentType.DocumentType
import ecom.enums.PaymentType.PaymentType
import ecom.enums.{DocumentType, PaymentType}
import io.circe.{Decoder, Encoder}

import scala.concurrent.Future
import scala.concurrent.duration.DurationInt
import io.circe.syntax._

import scala.concurrent.impl.Promise
import scala.util.{Failure, Success}

@Singleton
class PaymentController extends FailFastCirceSupport {

  import io.circe.generic.auto._

  implicit val system: ActorSystem = ActorSystem("payment-controller")
  private val paymentActor: ActorRef = system.actorOf(Props[PaymentService])

  implicit val documentTypeEncoder: Encoder[DocumentType] = Encoder.encodeString.contramap(_.toString)
  implicit val documentTypeDecoder: Decoder[DocumentType] = Decoder.decodeString.emapTry { str =>
    scala.util.Try(DocumentType.withName(str))
  }
  implicit val paymentTypeEncoder: Encoder[PaymentType] = Encoder.encodeString.contramap(_.toString)
  implicit val paymentTypeDecoder: Decoder[PaymentType] = Decoder.decodeString.emapTry { str =>
    scala.util.Try(PaymentType.withName(str))
  }

  implicit val timeout: Timeout = Timeout(5.seconds)
  val routes: Route =
    pathPrefix("payments") {
      path("process") {
        post {
          entity(as[PaymentRequest]) { paymentRequest =>
            val paymentResponseFuture: Future[PaymentResponse] = (paymentActor ? paymentRequest).mapTo[PaymentResponse]
            onComplete(paymentResponseFuture) {
              case Failure(exception) => {
                complete(StatusCodes.InternalServerError, s"An error occurred: ${exception.getMessage}")
              }
              case Success(value) => {
                complete(StatusCodes.OK, value.asJson)
              }
            }

          }
        }
      }
    }
}