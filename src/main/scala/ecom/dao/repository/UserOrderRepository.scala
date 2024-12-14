package ecom.dao.repository

import com.google.inject.{Inject, Singleton}
import ecom.actors.model.{OrderDetailsModel, PaymentDetails, UserOrderListModel, UserOrdersModel}
import ecom.dao.entities.{Order, OrderProduct, PaymentIntentEntity}
import ecom.dao.table.{OrderProductTable, OrderTable, PaymentIntentTable}
import slick.jdbc.GetResult
import slick.lifted.TableQuery
import slick.jdbc.PostgresProfile.api._

import java.time.LocalDateTime
import java.util
import scala.collection.mutable
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserOrderRepository @Inject()(implicit val ec: ExecutionContext) extends BaseRepository {
  private lazy val userOrdersTQ = TableQuery[OrderTable]
  private lazy val ordersTQ = TableQuery[OrderProductTable]
  private lazy val paymentIntentTQ = TableQuery[PaymentIntentTable]
  implicit val localDateTime = GetResult[LocalDateTime](
    r => r.nextTimestamp().toLocalDateTime
  )
  implicit val getResult = GetResult[(Long, Long, String, BigDecimal, Long, LocalDateTime, String, String, String, String, String, String)](
    r => (
      r.<<[Long],
      r.<<[Long],
      r.<<[String],
      r.<<[BigDecimal],
      r.<<[Long],
      r.<<[LocalDateTime],
      r.<<[String],
      r.<<[String],
      r.<<[String],
      r.<<[String],
      r.<<[String],
      r.<<[String]
    )
  )

  def save(order: Order): DBIO[Long] = {
    userOrdersTQ returning userOrdersTQ.map {
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
      .map { x => (x.orderId, x.uuid, x.paymentIntentId) }
      .result
      .headOption
      .map { x => x.get }
  }


  def getUserOrdersList(userId: Long): Future[List[UserOrdersModel]] = {
    val resultTuple = sql"""
        SELECT
          product_id,
          order_id,
          name,
          price,
          quantity,
          create_date,
          payment_status,
          email,
          image_url
        FROM
          order_product_map opm
        JOIN
          user_order uo
        ON
          uo.id = opm.order_id
         WHERE uo.user_id = $userId
         """.as[(Long, Long, String, BigDecimal, Long, LocalDateTime, String, String, String)]
    // order id i timestamp -> lista zamowien
    val map = mutable.HashMap[(Long, LocalDateTime), List[UserOrderListModel]]()
    db.run(resultTuple.transactionally).map {
      x => {
        x.foreach {
          tuple => {
            val orderId: Long = tuple._2
            val createDate: LocalDateTime = tuple._6
            val key: (Long, LocalDateTime) = (orderId, createDate)

            val list = map.getOrElse(key, List()) //ORDER_ID TIMESTAMP

            map.put(key, list :+ UserOrderListModel(
              tuple._1,
              tuple._3,
              tuple._5,
              tuple._4,
              tuple._9
            ))
          }
        }

        map.keys.map {
          x =>
            UserOrdersModel(
              x._1,
              x._2,
              map(x)
            )
        }.toList
      }
    }
  }

  def getOrderDetails(orderId: Long): Future[OrderDetailsModel] = {
      val resultTuple = sql"""
        SELECT
          order_id,
          product_id, --product
          name, --product
          price, --product
          quantity, --product
          create_date,
          payment_method,
          shipping_address,
          bill_address,
          payment_status,
          email,
          image_url
        FROM
          order_product_map opm
        JOIN
          user_order uo
        ON
          uo.id = opm.order_id
         WHERE uo.id = $orderId
         """.as[(Long, Long, String, BigDecimal, Long, LocalDateTime, String, String, String, String, String, String)]

      val map = mutable.HashMap[OrderDetailsModel, List[UserOrderListModel]]()
      var key: OrderDetailsModel = null
      db.run(resultTuple.transactionally).map {
        x => {
          x.foreach {
            tuple => {
              val orderId: Long = tuple._1
              val createDate: LocalDateTime = tuple._6

              if (key == null) key = OrderDetailsModel( //assign once
                orderId,
                createDate,
                tuple._7, //paymentMethod
                List(),
                BigDecimal.apply("0"),
                BigDecimal.apply("0"),
                BigDecimal.apply("0"),
                tuple._8,
                tuple._9
              )
              val list = map.getOrElse(key, List())

              map.put(key, list :+ UserOrderListModel( //dodawanie produktow
                tuple._1,
                tuple._3,
                tuple._5,
                tuple._4,
                tuple._12
              ))
            }
          }
        }
          key.products = map.getOrElse(key, List())
          key
      }
  }
}