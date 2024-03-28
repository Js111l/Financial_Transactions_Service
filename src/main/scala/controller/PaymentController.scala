package controller

import actors.{PaymentRequest, PaymentResponse, PaymentService}
import akka.actor.{ActorRef, ActorSystem, Props}
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Directives.pathPrefix
import akka.http.scaladsl.server.{Directives, Route}
import akka.http.scaladsl.unmarshalling.FromEntityUnmarshaller
import akka.pattern.ask
import akka.util.Timeout
import com.google.inject.{Inject, Singleton}
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport

import scala.concurrent.Future
import scala.concurrent.duration.DurationInt

@Singleton
class PaymentController @Inject()(implicit val system: ActorSystem) extends FailFastCirceSupport {

  import io.circe.generic.auto._

  implicit val timeout: Timeout = Timeout(5.seconds)
  private val paymentActor: ActorRef = system.actorOf(Props[PaymentService])

  //  implicit val paymentRequestUnmarshaller: FromEntityUnmarshaller[PaymentRequest] = {
  //    implicitly[FromEntityUnmarshaller[PaymentRequest]]
  //  }

  val routes: Route =
    pathPrefix("payments") {
      path("process") {
        post {
          entity(as[PaymentRequest]) { paymentRequest =>
            // Send payment request to PaymentService actor
            val paymentResponseFuture: Future[Any] = (paymentActor ? paymentRequest).mapTo[Any]
            onSuccess(paymentResponseFuture) {
              case response: PaymentResponse =>
                complete(StatusCodes.OK, response)
              case exception: Throwable =>
                complete(StatusCodes.InternalServerError, s"An error occurred: ${exception.getMessage}")
            }
          }
        }
      }
    }
}