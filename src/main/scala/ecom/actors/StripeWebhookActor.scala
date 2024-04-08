package ecom.actors

import akka.actor.{Actor, ActorLogging}

// TODO: listener for stripe webhook events
// TODO: learn how to do it even and what is it, and even if its useful to create it at all
class StripeWebhookActor extends Actor with ActorLogging{
  override def receive: Receive = ???
}
