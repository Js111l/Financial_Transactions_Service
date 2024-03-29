package actors

import actors.model.PaymentResponse
import akka.actor.{Actor, ActorLogging}

class EmailService extends Actor with ActorLogging {
  override def receive: Receive = {
    case payment: PaymentResponse => {
      // TODO: send confirmation to client
    }
  }


  private def sendMessage(): Unit = {


  }
}
