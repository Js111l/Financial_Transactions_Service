package ecom.dao.table


import ecom.dao.entities. OrderProduct
import slick.jdbc.PostgresProfile.api._


class OrderProductTable(tag: Tag) extends Table[OrderProduct](tag, "order_product_map") {
  def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)

  private def productId = column[Long]("product_id")

  private def orderId = column[Long]("order_id")

  def * = (id, productId, orderId) <> (OrderProduct.tupled, OrderProduct.unapply)

//  def product = foreignKey("product", productId, productTQ)(_.id)

  def order = foreignKey("user_order", orderId, ordersTQ)(_.id)

  private lazy val ordersTQ = TableQuery[OrderTable]

}