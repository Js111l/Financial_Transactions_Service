package ecom.actors.model

import java.time.{LocalDate, LocalDateTime}

case class OrderDetailsModel(id: Long,
                             orderDate: LocalDateTime,
                             paymentMethod: String, //TODO narazie str
                             var products: List[UserOrderListModel],
                             productsTotalPrice: BigDecimal,
                             shippingCost: BigDecimal,
                             totalPrice: BigDecimal,
                             shippingAddress: String,
                             billingAddress: String
                            ) {

}
