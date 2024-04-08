package ecom.actors

import akka.actor.{Actor, ActorLogging}
import com.google.inject.{Inject, Singleton}
import ecom.actors.model.PaymentResponse
import ecom.service.TransactionService
import ecom.utils.FinancialDocumentUtil

@Singleton
class FinancialDocumentActor @Inject()(transactionService: TransactionService) extends Actor with ActorLogging {
  override def receive: Receive = {
    case response: PaymentResponse => {
      val transactionDocument = FinancialDocumentUtil.getFinancialDocumentFromResponse(response)
      transactionService.saveTransaction(transactionDocument)
    }
  }
}
