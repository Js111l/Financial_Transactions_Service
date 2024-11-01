package ecom.actors

import akka.actor.{Actor, ActorLogging}
import akka.event.slf4j.Logger
import com.google.inject.{Inject, Singleton}
import ecom.Main.system
import ecom.actors.model.PaymentResponse
import ecom.service.TransactionService
import ecom.utils.FinancialDocumentUtil

import scala.concurrent.ExecutionContextExecutor
import scala.util.{Failure, Success}

@Singleton
class FinancialDocumentActor @Inject()(transactionService: TransactionService) extends Actor with ActorLogging {
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher
  val logger = Logger(getClass.getName)

  override def receive: Receive = {

    case response: PaymentResponse => {
      val transactionDocument = FinancialDocumentUtil.getFinancialDocumentFromResponse(response)
      transactionService.saveTransaction(transactionDocument).onComplete {
        case Failure(exception) =>
          val message = exception.getMessage
          logger.warn(s"Error occurred during saving financial document: $message")
        case Success(value) =>
          val id = value.id
          logger.info(s"Financial document successfully saved: $id")
      }
    }
  }
}
