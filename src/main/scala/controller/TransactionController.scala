package controller

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.util.Timeout
import com.google.inject.{Inject, Singleton}
import dao.entities.{FinancialDocument, Invoice}
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import service.TransactionService

import scala.concurrent.duration.DurationInt

@Singleton
class TransactionController @Inject()(transactionService: TransactionService)(implicit val system: ActorSystem) extends FailFastCirceSupport {

  import io.circe.generic.auto._

  implicit val timeout: Timeout = Timeout(5.seconds)

  val routes: Route = logRequestResult("akka-http-logger", Logging.InfoLevel) {
    pathPrefix("transactions") {
      path("save") {
        post {
          entity(as[Invoice]) { invoice =>
            transactionService.save2(invoice)
            complete(StatusCodes.OK, "")
          }
        }
      } ~
        path("list") {
          get {
            complete(StatusCodes.OK, transactionService.getAllInvoices())
          }
        }
    }
  }


}