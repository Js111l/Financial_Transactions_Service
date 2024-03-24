import Main.injector
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.google.inject.Guice
import controller.{PaymentController, TransactionController}
import akka.http.scaladsl.server.Directives._
import registry.MainModule
import tasks.TaskScheduler

import scala.::
import scala.concurrent.ExecutionContextExecutor

object Main extends App {
  // Create ActorSystem and Materializer
  implicit val system: ActorSystem = ActorSystem("main")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  val injector = Guice.createInjector(new MainModule())

  val routes = injector.getInstance(classOf[PaymentController]).routes ~ injector.getInstance(classOf[TransactionController]).routes

  val taskScheduler = injector.getInstance(classOf[TaskScheduler])
  taskScheduler.startScheduler(system)

  // Start the server by providing the ActorSystem
  Http().newServerAt("localhost", 9090).bind(routes)
}
