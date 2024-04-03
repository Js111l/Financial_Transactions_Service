package enums

import actors.payment.{BankTransferPaymentHandler, CreditCardPaymentHandler, PaymentHandler}

object PaymentType extends Enumeration {
  type PaymentType = Value
  val SEPA: (enums.PaymentType.Value, Class[_ <: PaymentHandler]) = (Value("SEPA"), classOf[BankTransferPaymentHandler])
  val CARD: (enums.PaymentType.Value, Class[_ <: PaymentHandler]) = (Value("CARD"), classOf[CreditCardPaymentHandler])
  //val BLIK: (enums.PaymentType.Value, Class[_ <: PaymentHandler]) = (Value("BLIK"), classOf[AnotherPaymentHandler])
}