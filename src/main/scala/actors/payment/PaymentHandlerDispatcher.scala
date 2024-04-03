package actors.payment

import actors.model.{PaymentRequest, PaymentResponse}
import com.google.inject.{Guice, Injector, Singleton}
import config.registry.MainModule

import scala.concurrent.Future

@Singleton
case class PaymentHandlerDispatcher() {
  val injector: Injector = Guice.createInjector(new MainModule())

  def charge(request: PaymentRequest): Future[PaymentResponse] = {
    val handler = request.paymentType match {
      case enums.PaymentType.SEPA._1 =>
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
