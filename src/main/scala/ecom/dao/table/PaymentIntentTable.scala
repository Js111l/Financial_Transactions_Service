package ecom.dao.table

import ecom.dao.entities.{Order, PaymentIntentEntity}
import slick.ast.ColumnOption.{AutoInc, PrimaryKey}
import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape



class PaymentIntentTable(tag: Tag) extends Table[PaymentIntentEntity](tag, "payment_intent") {
  def id = column[Long]("id", PrimaryKey, AutoInc)

  //private def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def uuid = column[String]("uuid")

  def paymentIntentId = column[String]("payment_intent_id")

  def orderId = column[Long]("order_id")
  def * : ProvenShape[PaymentIntentEntity] = (id, uuid, paymentIntentId, orderId) <> (PaymentIntentEntity.tupled, PaymentIntentEntity.unapply)

  def order = foreignKey("user_order", orderId, ordersTQ)(_.id)

  private lazy val ordersTQ = TableQuery[OrderTable]
  //userOrderId
  //TABLE QUERIES
}