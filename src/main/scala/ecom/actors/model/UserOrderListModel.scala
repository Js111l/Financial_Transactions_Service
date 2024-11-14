package ecom.actors.model

case class UserOrderListModel(productId:Long,
                              name: String,
                              quantity: Long,
                              price: BigDecimal,
                              imageUrl: String) {

}
