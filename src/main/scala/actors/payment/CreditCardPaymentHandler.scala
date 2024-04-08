package actors.payment


import actors.model.{PaymentRequest, PaymentResponse}
import actors.payment.PaymentHandler
import com.google.inject.Singleton
import com.stripe.Stripe
import com.stripe.model.PaymentIntent
import com.stripe.param.PaymentIntentCreateParams
import enums.{DocumentType, PaymentType}

import scala.concurrent.Future

@Singleton
class CreditCardPaymentHandler extends PaymentHandler {

  override def charge(paymentRequest: PaymentRequest): Future[PaymentResponse] = {
    Stripe.apiKey = System.getenv().get("STRIPE_API_KEY")

    Future {
      val paymentIntentCreateParams = PaymentIntentCreateParams
        .builder()
        .setCurrency(paymentRequest.currency)
        .setAmount(paymentRequest.amount)
        //todo  osobny call zeby stworzyc customera jesli nie istnieje i jesli jest to retrieve ?
        //.setCustomer(paymentRequest.client.id.toString)
        .setPaymentMethod(paymentRequest.stripeToken)
        // TODO: ?
        .setAutomaticPaymentMethods(PaymentIntentCreateParams
          .AutomaticPaymentMethods.builder()
          .setAllowRedirects(PaymentIntentCreateParams
            .AutomaticPaymentMethods.AllowRedirects.NEVER)
          .setEnabled(true)
          .build()
        )
        //todo potwierdzenie platnosci ?? osobny endpoint ?
        .setConfirm(true)
        .build()


      val paymentIntent = PaymentIntent.create(paymentIntentCreateParams)

      PaymentResponse(
        paymentIntent.getId,
        paymentIntent.getAmount,
        paymentIntent.getCurrency,
        status = paymentIntent.getStatus,
        paymentRequest.client,
        // TODO: na jakiej podstawie ma przekaza info jaki dokument ma byc?
        DocumentType.INVOICE
      )
    }
  }

  override def getPaymentMethod(): PaymentType.Value = {
    PaymentType.CARD._1
  }
}