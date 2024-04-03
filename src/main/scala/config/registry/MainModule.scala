package config.registry

import actors.payment.{BankTransferPaymentHandler, CreditCardPaymentHandler, PaymentHandler, PaymentHandlerDispatcher}
import actors.{FinancialDocumentActor, PaymentService}
import akka.actor.ActorSystem
import com.google.inject.AbstractModule
import com.google.inject.multibindings.Multibinder
import controller.{PaymentController, TransactionController}
import net.codingwell.scalaguice.ScalaModule
import service.TransactionService
import com.google.inject.Singleton
import config.DatabaseConfig
import dao.repository.documents.{AdjustmentRepository, InvoiceRepository, ReceiptRepository, RefundRepository}

import scala.concurrent.ExecutionContext

//CONFIGURE DEPENDENCIES
class MainModule extends AbstractModule with ScalaModule {
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
    bind[TransactionController].in[Singleton]
    bind[ExecutionContext].toInstance(scala.concurrent.ExecutionContext.Implicits.global)
    bind[FinancialDocumentActor].in[Singleton]

    val multibinder = Multibinder.newSetBinder(binder(), classOf[PaymentHandler])
    multibinder.addBinding().to(classOf[BankTransferPaymentHandler])
    multibinder.addBinding().to(classOf[CreditCardPaymentHandler])

    bind[PaymentHandlerDispatcher].in[Singleton]
  }
}
