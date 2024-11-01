package ecom

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpMethods.{DELETE, GET, OPTIONS, PATCH, POST, PUT}
import akka.http.scaladsl.model.headers.HttpOrigin
import com.google.inject.Guice
import ecom.config.registry.{FlywayConfig, MainModule}
import ecom.controller.{OrderController, PaymentController}
import ecom.tasks.TaskScheduler
import akka.http.scaladsl.server.Directives._
import ch.megard.akka.http.cors.scaladsl.CorsDirectives.cors
import ch.megard.akka.http.cors.scaladsl.model.{HttpHeaderRange, HttpOriginMatcher}
import ch.megard.akka.http.cors.scaladsl.settings.CorsSettings
import ecom.config.AppConfig

import scala.concurrent.ExecutionContextExecutor

object Main extends App {

  implicit val system: ActorSystem = ActorSystem("main")
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher
  val injector = Guice.createInjector(new MainModule())

  val taskScheduler = injector.getInstance(classOf[TaskScheduler])
  taskScheduler.startScheduler(system)
  val routes = //injector.getInstance(classOf[TransactionController]).routes ~
    injector.getInstance(classOf[PaymentController]).routes ~ injector.getInstance(classOf[OrderController]).routes


  val (host, port) = ("0.0.0.0", 8082)
  Http().newServerAt(host, port).bind(cors(
    CorsSettings.defaultSettings
      .withAllowedOrigins(HttpOriginMatcher(HttpOrigin("http://localhost:3000")))
      .withAllowCredentials(true)
      .withAllowedMethods(Seq(GET, POST, PUT, DELETE, OPTIONS, PATCH))
      .withAllowedHeaders(HttpHeaderRange.apply("Content-Type", "Authorization")) // Ensure proper header configuration
  ) {
    routes
  })
}
