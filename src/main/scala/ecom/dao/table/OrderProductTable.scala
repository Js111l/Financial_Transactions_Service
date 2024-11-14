package ecom.dao.table


import ecom.dao.entities.OrderProduct
import slick.jdbc.PostgresProfile.api._


class OrderProductTable(tag: Tag) extends Table[OrderProduct](tag, "order_product_map") {
  def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)

  private def productId = column[Long]("product_id")

  private def orderId = column[Long]("order_id")

  private def name = column[String]("name")

  private def pricePerPiece = column[BigDecimal]("price")

  private def quantity = column[Long]("quantity")

  private def imageUrl = column[String]("image_url")

  def * = (id, productId, name, pricePerPiece, quantity, imageUrl, orderId) <> (OrderProduct.tupled, OrderProduct.unapply)

  def order = foreignKey("user_order", orderId, ordersTQ)(_.id)

  private lazy val ordersTQ = TableQuery[OrderTable]

}