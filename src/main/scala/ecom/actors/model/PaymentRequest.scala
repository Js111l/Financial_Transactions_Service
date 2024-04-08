package ecom.actors.model

import ecom.enums.PaymentType.PaymentType

import java.time.LocalDateTime

case class PaymentRequest(amount: Long,
                          currency: String,
                          client: Client,
                          stripeToken: String,
                          //items: Seq[Product],
                          shippingAddress: Address,
                          billingAddress: Address,
                          orderId: String,
                          paymentType: PaymentType
                          //paymentDate: LocalDateTime
                         )
