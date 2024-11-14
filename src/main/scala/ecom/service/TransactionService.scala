package ecom.service

import com.google.inject.{Inject, Singleton}
import com.stripe.model.PaymentIntent
import com.stripe.param.PaymentIntentCreateParams
import ecom.actors.model.{CustomerData, PaymentDetails, PaymentIntentRequestModel, TokenResponse}
import ecom.dao.entities.{Adjustment, FinancialDocument, Invoice, Order, OrderProduct, PaymentIntentEntity, Receipt, Refund}
import ecom.dao.repository.UserOrderRepository
import ecom.dao.repository.documents.{AdjustmentRepository, InvoiceRepository, ReceiptRepository, RefundRepository}
import ecom.utils.TokenUtil

import java.time.LocalDateTime
import scala.concurrent.Future
import scala.util.{Failure, Success}
import slick.jdbc.PostgresProfile.api._

import java.util.UUID

// JESLI USER INICJUCJE PLATNOSC TO TAKIE FLOW:

//1. Zapisuje sie order, potem ten intent itp., wszystko po staremu.
//2. Musi byc wygenerowany token do sesji platnosci, na 10 minut,
// musi byc unikalny i scisle powiazny z obecna sesja.
//3. user jesli ma poprawny token itp. to moze byc na tym widoku platnosci i zainicjować zapłatę
//4. Otherwise, wywali błąd i nie wpuści go.
//5. Klient bedzie mial mozliwosc ponowienia zakpupu, jezeli produkt bedzie dostepny itp.
//   Wtedy bedzie takie flow, ze generuje sie token do ponownej zapłaty, powiazany z sesja i payment intentem
//6. wszystkie tokeny w http only cookie.


@Singleton
class TransactionService @Inject()(invoiceRepository: InvoiceRepository,
                                   receiptRepository: ReceiptRepository,
                                   refundRepository: RefundRepository,
                                   adjustmentRepository: AdjustmentRepository,
                                   orderRepository: UserOrderRepository) extends BaseService {

  def saveTransaction(financialDoc: FinancialDocument): Future[FinancialDocument] = {
    financialDoc match {
      case invoice: Invoice =>
        invoiceRepository.save(invoice)
      case receipt: Receipt => {
        receiptRepository.save(receipt)
      }
      case refund: Refund => {
        refundRepository.save(refund)
      }
      case adjustment: Adjustment => {
        adjustmentRepository.save(adjustment)
      }
      case _ =>
        Future.failed(new IllegalArgumentException("Unsupported FinancialDocument type"))
    }

  }

  def getPaymentDetails(uuid: String): Future[PaymentDetails] = {
    val resultTuple = sql"""
         select p.payment_intent_id, o.user_id
         from user_order o join payment_intent p on
         o.id = p.order_id
         where p.uuid = $uuid
       """.as[(String, Long)]
    db.run(resultTuple.transactionally).map {
      x => PaymentDetails(x(0)._1, x(0)._2)
    }
  }

  def getAllInvoices(): Future[Seq[Invoice]] = {
    this.invoiceRepository.findAll2()
  }


  def getIdSecretTupleAndSaveNewOrder(intentModel: PaymentIntentRequestModel, clientData: CustomerData): Future[(Long, String, String)] = {
    this.createUserOrder(intentModel, clientData)
      .transformWith {
        case Failure(ex) =>
          val exMessage = ex.getMessage
          logger.warn(s"Error occurred during saving new order: $exMessage")
          Future.failed(new IllegalArgumentException("Order save failed", ex))
        case Success(orderId) =>
          this.getPaymentIntentIdSecretTuple(intentModel, orderId).map((tuple) => {
            (orderId, tuple._1, tuple._2)
          }).recoverWith {
            case ex: Throwable =>
              val exMessage = ex.getMessage
              logger.warn(s"Error occurred during fetching client secret for payment intent: $exMessage")
              Future.failed(new IllegalArgumentException("Failed to get secret", ex))
          }
      }
  }

  def getSecretByUUID(uuid: String, token: Option[String]): Future[TokenResponse] = {
    val action = (orderRepository.getClientSecretByUUID(uuid)).transactionally
    db.run(action).map { x =>
      TokenResponse(x._1, x._2, x._3)
    }
  }

  private def setupPaymentMethods(builder: PaymentIntentCreateParams.Builder): Unit = {
    val methods = this.appConfig.getPaymentMethods();
    methods.forEach {
      x => {
        builder.addPaymentMethodType(x.unwrapped().toString) //dodaj metody platnosci z configa
      }
    }
  }


  private def getPaymentIntentEntity(intent: PaymentIntent, uuid: String, orderId: Long): PaymentIntentEntity = {
    PaymentIntentEntity(
      0,
      uuid,
      intent.getClientSecret,
      orderId
    )
  }

  private def getPaymentIntentIdSecretTuple(intentModel: PaymentIntentRequestModel, orderId: Long): Future[(String, String)] = {
    val paymentIntentCreateParamsBuilder = PaymentIntentCreateParams
      .builder()
      .setCurrency("eur")
      .setAmount(intentModel.amount.longValue)

    this.setupPaymentMethods(paymentIntentCreateParamsBuilder)
    val intent = PaymentIntent.create(paymentIntentCreateParamsBuilder.build())
    //W MODELU MASZ PRZEKAZAC UUID, UUID KLUCZ WARTOSC klient secret
    val entity = this.getPaymentIntentEntity(intent, intentModel.uuid, orderId)
    val action = orderRepository.savePaymentIntent(entity).transactionally
    db.run(action).map(_ => (entity.uuid, intent.getClientSecret))
  }

  private def createNewOrder(intentRequestModel: PaymentIntentRequestModel, clientData: CustomerData): Order = {
    Order(
      0,
      clientData.id,
      intentRequestModel.email,
      LocalDateTime.now(),
      "",
      "",
      "",
      ""
    )
  }

  private def createUserOrder(intentRequestModel: PaymentIntentRequestModel, clientData: CustomerData): Future[Long] = {
    val action =
      (for {
        orderId <- orderRepository.save(this.createNewOrder(intentRequestModel, clientData))
        //        orders = {
        //          this.validateUser() test exceptions
        //          List()
        //        }
        products = intentRequestModel.products.map { x => OrderProduct(0, x.id, x.name,x.price,x.quantity, x.imageUrl, orderId) }
        _ <- orderRepository.saveProductOrders(products)
      } yield (orderId)).transactionally
    db.run(action)
  }

}
