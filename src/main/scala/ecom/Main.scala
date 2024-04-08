package ecom

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import com.google.inject.Guice
import ecom.config.registry.MainModule
import ecom.controller.{PaymentController, TransactionController}
import ecom.tasks.TaskScheduler

import scala.concurrent.ExecutionContextExecutor

object Main extends App {
  implicit val system: ActorSystem = ActorSystem("main")
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher
  val injector = Guice.createInjector(new MainModule())
  val taskScheduler = injector.getInstance(classOf[TaskScheduler])
  taskScheduler.startScheduler(system)


  val routes = injector.getInstance(classOf[TransactionController]).routes ~ injector.getInstance(classOf[PaymentController]).routes
  val (host, port) = ("0.0.0.0",80 )
  Http().newServerAt(host,port).bind(routes)
}
