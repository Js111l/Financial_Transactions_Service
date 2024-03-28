package actors

import enums.Currency
import io.circe.generic.auto._
case class PaymentResponse(amount: Long, currency: String, status: String){

}