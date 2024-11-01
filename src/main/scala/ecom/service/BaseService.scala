package ecom.service

import akka.event.slf4j.Logger
import ecom.Main.system
import ecom.config.AppConfig
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.ExecutionContextExecutor

abstract class BaseService {
  protected val appConfig = new AppConfig()
  protected val db = Database.forConfig("mydb") //run repositories actions transactionally
  protected implicit val executionContext: ExecutionContextExecutor = system.dispatcher
  protected val logger = Logger(getClass.getName)

}
