package service

import akka.http.scaladsl.marshalling.ToResponseMarshallable
import com.google.inject.{Inject, Singleton}
import db.{AdjustmentRepository, InvoiceRepository, ReceiptRepository, RefundRepository}
import entity.BaseFinancialDocument

import scala.concurrent.Future
@Singleton
class TransactionService @Inject()(invoiceRepository: InvoiceRepository,
                         receiptRepository: ReceiptRepository,
                         refundRepository: RefundRepository,
                         adjustmentRepository: AdjustmentRepository) {

  def getAllProducts(): Future[List[BaseFinancialDocument]] = ???
  def getActiveProducts(): Future[List[BaseFinancialDocument]] = ???

  def getMinStockThreshold(id: Int): ToResponseMarshallable = ???

  def getProductsByCategory(): ToResponseMarshallable = ???

  def updateProductStock(id: Int, requestBody: String): ToResponseMarshallable = ???


  def getOutOfStockProducts(): ToResponseMarshallable = ???

  def getTopSellingProducts(): ToResponseMarshallable = ???

  def getTotalStock(): ToResponseMarshallable = ???

  def getAverageStock(): ToResponseMarshallable = ???

  def getAllProductsWithLowStock(): ToResponseMarshallable = ???

  def getProductStock(id: Int): _root_.akka.http.scaladsl.marshalling.ToResponseMarshallable = ???


}
