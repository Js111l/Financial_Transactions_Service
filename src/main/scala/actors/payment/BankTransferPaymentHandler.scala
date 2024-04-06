package actors.payment


import actors.model.{PaymentRequest, PaymentResponse}
import com.google.inject.Singleton
import com.stripe.Stripe
import com.stripe.model.{PaymentIntent, PaymentMethod}
import com.stripe.param.{PaymentIntentCreateParams, PaymentMethodCreateParams}
import enums.{DocumentType, PaymentType}
import enums.PaymentType.PaymentType

import scala.concurrent.Future

@Singleton
class BankTransferPaymentHandler extends PaymentHandler {
  override def charge(paymentRequest: PaymentRequest): Future[PaymentResponse] = {
    Stripe.apiKey = System.getenv().get("STRIPE_API_KEY")

    Future {
      //todo  platnosci przelewem ???????
      //przelew:
      val params =
      PaymentMethodCreateParams.builder()
        .setType(PaymentMethodCreateParams.Type.SEPA_DEBIT)
        .setSepaDebit(PaymentMethodCreateParams
          .SepaDebit
          .builder()
          .setIban("AT611904300234573201")
          .build()
        ).setBillingDetails(PaymentMethodCreateParams.BillingDetails.builder()
          .setName("jgguiuhkh")
          .setEmail("jakub@wp.pl")
          .setPhone("7555656565")
          .build())

        .build()
      val method = PaymentMethod.create(params)


      val paymentIntentCreateParams = PaymentIntentCreateParams
        .builder()
        .setCurrency(paymentRequest.currency)
        .setAmount(paymentRequest.amount)
        .setPaymentMethod(method.getId)
        .addPaymentMethodType("sepa_debit")
        .build()
      val paymentIntent = PaymentIntent.create(paymentIntentCreateParams)

      PaymentResponse(
        paymentIntent.getId,
        paymentIntent.getAmount,
        paymentIntent.getCurrency,
        status = paymentIntent.getStatus,
        paymentRequest.client,
        // TODO: na jakiej podstawie ma przekaza info jaki dokument ma byc?
        DocumentType.INVOICE)
    }
  }

  override def getPaymentMethod(): PaymentType = {
    PaymentType.SEPA._1
  }
}