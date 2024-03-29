package actors.model

import enums.PaymentType.PaymentType

import java.time.LocalDateTime

case class PaymentRequest(amount: Long,
                          currency: String,
                          client: Client,
                          paymentMethod: PaymentType,
                          items: Seq[Product],
                          shippingAddress: Address,
                          billingAddress: Address,
                          orderId: String,
                          paymentDate: LocalDateTime)
