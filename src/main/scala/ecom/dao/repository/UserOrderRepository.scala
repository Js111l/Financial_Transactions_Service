package ecom.dao.repository

import com.google.inject.{Inject, Singleton}
import ecom.dao.entities.{Order, OrderProduct, PaymentIntentEntity}
import ecom.dao.table.{OrderProductTable, OrderTable, PaymentIntentTable}
import slick.lifted.TableQuery
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.ExecutionContext

@Singleton
class UserOrderRepository @Inject()(implicit val ec: ExecutionContext) extends BaseRepository {
  private lazy val userOrders = TableQuery[OrderTable]
  private lazy val ordersTQ = TableQuery[OrderProductTable]
  private lazy val paymentIntentTQ = TableQuery[PaymentIntentTable]

  def save(order: Order): DBIO[Long] = {
    userOrders returning userOrders.map {
      _.id
    } += order
  }

  def savePaymentIntent(paymentIntent: PaymentIntentEntity): DBIO[Long] = {
    paymentIntentTQ returning paymentIntentTQ.map {
      _.id
    } += paymentIntent
  }

  def saveProductOrders(productOrders: List[OrderProduct]): DBIO[Object] = {
    ordersTQ ++= productOrders
  }

  def getClientSecretByUUID(uuid: String): DBIO[(Long, String, String)] = {
    paymentIntentTQ
      .filter(_.uuid === uuid)
      .map { x => (x.orderId,x.uuid, x.paymentIntentId) }
      .result
      .headOption
      .map { x => x.get }
  }
}
