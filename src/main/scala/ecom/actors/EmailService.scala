package ecom.actors

import akka.actor.{Actor, ActorLogging}
import ecom.actors.model.PaymentResponse

class EmailService extends Actor with ActorLogging {
  override def receive: Receive = {
    case payment: PaymentResponse => {
      // TODO: send confirmation to client
    }
  }


  private def sendMessage(): Unit = {


  }
}
