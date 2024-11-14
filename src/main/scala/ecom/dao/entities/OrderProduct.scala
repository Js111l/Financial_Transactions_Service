package ecom.dao.entities

case class OrderProduct(id: Long,
                        productId: Long,
                        name: String,
                        pricePerPiece: BigDecimal,
                        quantity: Long,
                        imageUrl: String,
                       // attributes: Map[String,String],
                        orderId: Long) {
}
