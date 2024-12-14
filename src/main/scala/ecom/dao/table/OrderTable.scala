package ecom.dao.table

import ecom.dao.entities.Order
import slick.ast.ColumnOption.{AutoInc, PrimaryKey}
import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape

import java.time.LocalDateTime

class OrderTable(tag: Tag) extends Table[Order](tag, "user_order") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def userId = column[Long]("user_id")
  def createDate = column[LocalDateTime]("create_date")
  def paymentMethod = column[Option[String]]("payment_method")
  def paymentStatus = column[Option[String]]("payment_status")
  def email = column[Option[String]]("email")
  def shippingAddress = column[Option[String]]("shipping_address")
  def billingAddress = column[Option[String]]("bill_address")

  def * = (id, userId, email, createDate, paymentStatus, paymentMethod, shippingAddress, billingAddress) <> (Order.tupled, Order.unapply)
}
