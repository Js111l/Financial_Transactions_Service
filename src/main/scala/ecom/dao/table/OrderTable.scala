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
  def paymentMethod = column[String]("payment_method")
  def paymentStatus = column[String]("payment_status")
  def email = column[String]("email")
  def shippingAddressId = column[Long]("shippingAddressId")
  def billingAddressId = column[Long]("billingAddressId")

  def * = (id, userId, email, createDate, paymentStatus, paymentMethod, shippingAddressId, billingAddressId) <> (Order.tupled, Order.unapply)

}