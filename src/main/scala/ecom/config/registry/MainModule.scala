package ecom.config.registry

import akka.actor.ActorSystem
import com.google.inject.AbstractModule
import com.google.inject.multibindings.Multibinder
import net.codingwell.scalaguice.ScalaModule
import com.google.inject.Singleton
import com.stripe.Stripe
import ecom.actors.FinancialDocumentActor
import ecom.actors.payment.{BankTransferPaymentHandler, CreditCardPaymentHandler, PaymentHandler, PaymentHandlerDispatcher, PaymentService}
import ecom.config.{AppConfig, DatabaseConfig}
import ecom.controller.{OrderController, PaymentController}
import ecom.dao.repository.UserOrderRepository
import ecom.dao.repository.documents.{AdjustmentRepository, InvoiceRepository, ReceiptRepository, RefundRepository}
import ecom.service.{OrderService, TransactionService}

import scala.concurrent.ExecutionContext

//CONFIGURE DEPENDENCIES
class MainModule extends AbstractModule with ScalaModule {
  val appConfig = new AppConfig();

  override def configure(): Unit = {
    bind[DatabaseConfig].in[Singleton]
    bind[ActorSystem].toInstance(ActorSystem("main"))
    bind[RefundRepository].in[Singleton]
    bind[InvoiceRepository].in[Singleton]
    bind[AdjustmentRepository].in[Singleton]
    bind[ReceiptRepository].in[Singleton]
    bind[PaymentService].in[Singleton]
    bind[TransactionService].in[Singleton]
    bind[PaymentController].in[Singleton]
    bind[OrderService].in[Singleton]
    //bind[TransactionController].in[Singleton]
    bind[ExecutionContext].toInstance(scala.concurrent.ExecutionContext.Implicits.global)
    bind[FinancialDocumentActor].in[Singleton]
    bind[UserOrderRepository].in[Singleton]
    bind[OrderService].in[Singleton]

    val multibinder = Multibinder.newSetBinder(binder(), classOf[PaymentHandler])
    multibinder.addBinding().to(classOf[BankTransferPaymentHandler])
    multibinder.addBinding().to(classOf[CreditCardPaymentHandler])

    bind[PaymentHandlerDispatcher].in[Singleton]
    bind[OrderController].in[Singleton]

    //FLYWAY
    FlywayConfig.getFlyway().migrate()

    //STRIPE
    Stripe.apiKey = this.appConfig.getApiKey()
  }
}
