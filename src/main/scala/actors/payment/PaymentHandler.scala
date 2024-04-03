package actors.payment

import actors.model.{PaymentRequest, PaymentResponse}
import enums.PaymentType.PaymentType

import scala.concurrent.{ExecutionContext, Future}


abstract class PaymentHandler {

  protected implicit val executionContext: ExecutionContext = ExecutionContext.global

  def getPaymentMethod(): PaymentType

  def charge(paymentRequest: PaymentRequest): Future[PaymentResponse]
}
