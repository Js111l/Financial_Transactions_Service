package registry

import akka.actor.ActorSystem
import com.google.inject.AbstractModule
import controller.{PaymentController, TransactionController}
import db.{AdjustmentRepository, InvoiceRepository, ReceiptRepository, RefundRepository}
import net.codingwell.scalaguice.ScalaModule
import service.{PaymentService, TransactionService}
import com.google.inject.{Singleton}

//CONFIGURE DEPENDENCIES
class MainModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    bind[ActorSystem].toInstance(ActorSystem("main"))
    bind[RefundRepository].in[Singleton]
    bind[InvoiceRepository].in[Singleton]
    bind[AdjustmentRepository].in[Singleton]
    bind[ReceiptRepository].in[Singleton]
    bind[PaymentService].in[Singleton]
    bind[TransactionService].in[Singleton]
    bind[PaymentController].in[Singleton]
    bind[TransactionController].in[Singleton]

  }
}
