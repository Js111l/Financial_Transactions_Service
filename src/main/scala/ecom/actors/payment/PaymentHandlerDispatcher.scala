package ecom.actors.payment

import com.google.inject.{Guice, Injector, Singleton}
import ecom.actors.model.{PaymentRequest, PaymentResponse}
import ecom.config.registry.MainModule
import ecom.enums
import ecom.enums.PaymentType

import scala.concurrent.Future

@Singleton
case class PaymentHandlerDispatcher() {
  val injector: Injector = Guice.createInjector(new MainModule())

  def charge(request: PaymentRequest): Future[PaymentResponse] = {
    val handler = request.paymentType match {
      case PaymentType.SEPA._1 =>
        getByPaymentType(enums.PaymentType.SEPA._2)
      case enums.PaymentType.CARD._1 =>
        getByPaymentType(enums.PaymentType.CARD._2)
      //todo integracja z blikiem
      //      case enums.PaymentType.BLIK =>
      //        getByPaymentType(request.paymentType)
    }
    handler.charge(request)
  }

  private def getByPaymentType(clazz: Class[_ <: PaymentHandler]): PaymentHandler = {
    injector.getInstance(clazz)
  }


}
