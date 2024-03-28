package actors

import enums.Currency
import io.circe.generic.auto._
case class PaymentRequest(amount: Long, currency: String)
