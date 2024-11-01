package ecom.actors.payment

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import com.google.inject.{Guice, Singleton}
import ecom.actors.EmailService
import ecom.actors.model.{PaymentRequest, PaymentResponse}

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

@Singleton
class PaymentService extends Actor with ActorLogging {

  protected implicit val executionContext: ExecutionContext = ExecutionContext.global
  implicit val system: ActorSystem = ActorSystem("payment")
  private val paymentDispatcher: PaymentHandlerDispatcher = Guice.createInjector().getInstance(classOf[PaymentHandlerDispatcher]);

  override def receive: Receive = {
    case request: PaymentRequest =>
      val senderRef = sender()
      paymentDispatcher.charge(request).onComplete {
        case Success(paymentResponse: PaymentResponse) =>
          val emailActor = system.actorOf(Props[EmailService])
          emailActor ! paymentResponse
          senderRef ! paymentResponse //
        case Failure(exception) =>
          senderRef ! exception
      }
  }


}