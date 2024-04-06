import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import com.google.inject.Guice
import controller.{PaymentController, TransactionController}
import akka.http.scaladsl.server.Directives._
import config.registry.MainModule
import tasks.TaskScheduler

import scala.concurrent.ExecutionContextExecutor

object Main extends App {
  implicit val system: ActorSystem = ActorSystem("main")
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher
  val injector = Guice.createInjector(new MainModule())
  val taskScheduler = injector.getInstance(classOf[TaskScheduler])
  taskScheduler.startScheduler(system)


  val routes = injector.getInstance(classOf[TransactionController]).routes ~ injector.getInstance(classOf[PaymentController]).routes
  val (host, port) = ("localhost", 9091)
  Http().newServerAt(host,port).bind(routes)
}
