package ecom.controller

import akka.actor.ActorSystem
import akka.util.Timeout
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport

import scala.concurrent.duration.DurationInt

import akka.actor.ActorSystem
import akka.util.Timeout
import akka.http.scaladsl.server.Directives
import scala.concurrent.duration._

abstract class BaseController  {
  //implicit val system: ActorSystem = ActorSystem("controller")
  implicit val timeout: Timeout = Timeout(5.seconds)
}