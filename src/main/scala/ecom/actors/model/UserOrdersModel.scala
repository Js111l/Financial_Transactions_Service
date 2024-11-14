package ecom.actors.model

import java.time.LocalDateTime

case class UserOrdersModel(orderId: Long,
                           createDate: LocalDateTime,
                           orderedProducts: List[UserOrderListModel]
                          ) {
}
