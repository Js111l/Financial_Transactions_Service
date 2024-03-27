package service

import akka.http.scaladsl.marshalling.ToResponseMarshallable
import com.google.inject.{Inject, Singleton}
import dao.entities.{Adjustment, FinancialDocument, Invoice, Receipt, Refund}
import dao.repository.{AdjustmentRepository, InvoiceRepository, ReceiptRepository, RefundRepository}

import scala.concurrent.Future
@Singleton
class TransactionService @Inject()(invoiceRepository: InvoiceRepository,
                         receiptRepository: ReceiptRepository,
                         refundRepository: RefundRepository,
                         adjustmentRepository: AdjustmentRepository) {

  def saveTransaction(financialDoc: FinancialDocument): Future[FinancialDocument] = {

    financialDoc match {
      case invoice: Invoice =>
        invoiceRepository.save(invoice)
      case receipt: Receipt => {
        receiptRepository.save(receipt)
      }
      case refund: Refund => {
        refundRepository.save(refund)
      }
      case adjustment: Adjustment => {
        adjustmentRepository.save(adjustment)
      }
      case _ => Future.failed(new IllegalArgumentException("Unsupported FinancialDocument type"))
    }
  }

  def save2(financialDocument: Invoice): Unit = {
    invoiceRepository.save2(financialDocument)
  }
  def getAllInvoices(): Future[Seq[Invoice]] ={
    this.invoiceRepository.findAll2()
  }
  def getActiveProducts(): Future[List[FinancialDocument]] = ???

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
