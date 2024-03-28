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

import scala.concurrent.ExecutionContextExecutor
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
  //    ~ path("products") {
  //      get {
  //        onComplete(inventoryService.getAllProducts()) {
  //          case Success(products) => complete(StatusCodes.OK, products.toJson.toString())
  //          case Failure(ex) => complete(StatusCodes.InternalServerError, s"An error occurred: ${ex.getMessage}")
  //        }
  //      }
  //    } ~ path("products/low-stock") {
  //      Directives.get {
  //        complete(inventoryService.getAllProductsWithLowStock())
  //      }
  //    } ~ path("products/out-of-stock") {
  //      Directives.get {
  //        complete(inventoryService.getOutOfStockProducts())
  //      }
  //    } ~ parameters("id".as[Int]) { id =>
  //      path("products/stock") {
  //        Directives.post {
  //          entity(as[String]) { requestBody =>
  //            complete(inventoryService.updateProductStock(id, requestBody))
  //          }
  //        }
  //      } ~ path("/metrics/average-stock") {
  //        Directives.get {
  //          complete(inventoryService.getAverageStock())
  //        }
  //      } ~ path("metrics/total-stock") {
  //        Directives.get {
  //          complete(inventoryService.getTotalStock())
  //        }
  //      } ~ path("products/top-selling") {
  //        Directives.get {
  //          complete(inventoryService.getTopSellingProducts())
  //        }
  //      } ~ path("products/category") {
  //        Directives.get {
  //          complete(inventoryService.getProductsByCategory())
  //        }
  //      }


  //       ~ parameters("id".as[Int]) { id =>
  //        path("products" / "min-stock-threshold") {
  //          Directives.put {
  //            entity(as[MinStockThresholdRequest]) { thresholdRequest =>
  //              complete(inventoryService.setMinStockThreshold(id, thresholdRequest))
  //            }
  //          }
  //        } ~ parameters("id".as[Int]) { id =>
  //          path("products" / "min-stock-threshold/") {
  //            Directives.get {
  //              complete(inventoryService.getMinStockThreshold(id))
  //            }
  //          }
  //        }
  //      } ~ path("products" / IntNumber / "stock-alerts") { productId =>
  //        Directives.post {
  //          entity(as[StockAlertSubscriptionRequest]) { alertSubscription =>
  //            complete(inventoryService.subscribeToStockAlert(productId, alertSubscription))
  //          }
  //        } ~
  //          Directives.delete {
  //            complete(inventoryService.unsubscribeFromStockAlert(productId))
  //          }
  //      }


}