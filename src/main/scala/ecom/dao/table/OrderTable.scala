package ecom.dao.table

import ecom.dao.entities.Order
import slick.ast.ColumnOption.{AutoInc, PrimaryKey}
import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape

import java.time.LocalDateTime


class OrderTable(tag: Tag) extends Table[Order](tag, "user_order") {
  def id = column[Long]("id", PrimaryKey, AutoInc)

  //private def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  private def userId = column[Long]("user_id")

  private def createDate = column[LocalDateTime]("create_date")

  private def paymentMethod = column[String]("payment_method")

  private def shippingAddress = column[String]("shipping_address")

  private def billAddress = column[String]("bill_address")


  private def paymentStatus = column[String]("payment_status")


  def * : ProvenShape[Order] = (id, userId, createDate, paymentStatus, paymentMethod, shippingAddress, billAddress) <> (Order.tupled, Order.unapply)



  //TABLE QUERIES
}
