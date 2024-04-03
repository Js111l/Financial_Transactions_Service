package controller

import actors.model.{PaymentRequest, PaymentResponse}
import actors.PaymentService
import akka.actor.{ActorRef, ActorSystem, Props}
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Directives.pathPrefix
import akka.http.scaladsl.server.{Directives, Route}
import akka.pattern.ask
import akka.util.Timeout
import com.google.inject.{Inject, Singleton}
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import enums.{DocumentType, PaymentType}
import enums.DocumentType.DocumentType
import enums.PaymentType.PaymentType
import io.circe.{Decoder, Encoder}

import scala.concurrent.Future
import scala.concurrent.duration.DurationInt
import io.circe.syntax._

@Singleton
class PaymentController extends FailFastCirceSupport {

  import io.circe.generic.auto._

  implicit val timeout: Timeout = Timeout(5.seconds)
  implicit val system: ActorSystem = ActorSystem("payment-controller")
  private val paymentActor: ActorRef = system.actorOf(Props[PaymentService])

//  implicit val paymentRequestUnmarshaller: FromEntityUnmarshaller[PaymentRequest] = {
//    implicitly[FromEntityUnmarshaller[PaymentRequest]]
//  }
  implicit val documentTypeEncoder: Encoder[DocumentType] = Encoder.encodeString.contramap(_.toString)
  implicit val documentTypeDecoder: Decoder[DocumentType] = Decoder.decodeString.emapTry { str =>
    scala.util.Try(DocumentType.withName(str))
  }
  implicit val paymentTypeEncoder: Encoder[PaymentType] = Encoder.encodeString.contramap(_.toString)
  implicit val paymentTypeDecoder: Decoder[PaymentType] = Decoder.decodeString.emapTry { str =>
    scala.util.Try(PaymentType.withName(str))
  }


  val routes: Route =
    pathPrefix("payments") {
      path("process") {
        post {
          entity(as[PaymentRequest]) { paymentRequest =>
            val paymentResponseFuture: Future[Any] = (paymentActor ? paymentRequest).mapTo[Any]
            onSuccess(paymentResponseFuture) {
              case response: PaymentResponse =>
                complete(StatusCodes.OK, response.asJson)
              case exception: Throwable =>
                complete(StatusCodes.InternalServerError, s"An error occurred: ${exception.getMessage}")
            }
          }
        }
      }
    }
}