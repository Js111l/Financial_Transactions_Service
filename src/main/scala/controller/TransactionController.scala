package controller

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.google.inject.{Inject, Singleton}
import dao.entities.{FinancialDocument, Invoice}
import service.TransactionService
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport

import scala.concurrent.Future
import config.json.MyJsonProtocol._

@Singleton
class TransactionController @Inject()(transactionService: TransactionService) extends SprayJsonSupport {
  implicit val system: ActorSystem = ActorSystem("transaction-controller")

  val routes: Route = logRequestResult("akka-http-logger",Logging.InfoLevel) {
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