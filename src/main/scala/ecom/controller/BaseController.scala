package ecom.controller

import akka.event.slf4j.Logger
import akka.http.scaladsl.model.HttpMethods.{DELETE, GET, OPTIONS, PATCH, POST, PUT}
import akka.http.scaladsl.model.headers.HttpOrigin
import akka.util.Timeout
import ch.megard.akka.http.cors.scaladsl.model.{HttpHeaderRange, HttpOriginMatcher}
import ch.megard.akka.http.cors.scaladsl.settings.CorsSettings

import scala.concurrent.duration.DurationInt
import ecom.config.registry.JsonFormattersConfig

abstract class BaseController extends JsonFormattersConfig {
  protected implicit val timeout: Timeout = Timeout(5.seconds)
  protected val logger = Logger(getClass.getName)
  protected val corsSettings = CorsSettings.defaultSettings
    .withAllowedOrigins(HttpOriginMatcher(HttpOrigin("http://localhost:3000")))
    .withAllowCredentials(true)
    .withAllowedMethods(Seq(GET, POST, PUT, DELETE, OPTIONS, PATCH))
    .withAllowedHeaders(HttpHeaderRange.apply("Content-Type", "Authorization")) // Ensure proper header configuration

}