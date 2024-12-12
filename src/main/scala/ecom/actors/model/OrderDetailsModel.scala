package ecom.actors.model

import java.time.LocalDate

case class OrderDetailsModel(id: Long,
                             orderDate: LocalDate,
                             paymentMethod: String, //TODO narazie str
                             products: List[Long],
                             productsTotalPrice: BigDecimal,
                             shippingCost: BigDecimal,
                             totalPrice: BigDecimal,
                            //adresy ?
                            ) {

}
