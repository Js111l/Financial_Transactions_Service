package ecom.config.registry

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import ecom.actors.model.{Address, Client, CustomerData, PaymentDetails, PaymentIntentRequestModel, PaymentRequest, PaymentResponse, ProductModel, TokenResponse, UserOrderListModel}
import ecom.enums.{DocumentType, PaymentType}
import spray.json.{DefaultJsonProtocol, DeserializationException, JsString, JsValue, RootJsonFormat}

//JSON MARSHALLING AND UNMARSHALLING CONFIG CLASS
class JsonFormattersConfig extends SprayJsonSupport with DefaultJsonProtocol {

  implicit val documentTypeJsonFormat: RootJsonFormat[DocumentType.Value] = new RootJsonFormat[DocumentType.Value] {
    def write(dt: DocumentType.Value): JsValue = JsString(dt.toString)

    def read(value: JsValue): DocumentType.Value = value match {
      case JsString(s) => DocumentType.withName(s)
      case _ => throw new DeserializationException("DocumentType expected")
    }
  }
  implicit val paymentTypeJsonFormat: RootJsonFormat[PaymentType.Value] = new RootJsonFormat[PaymentType.Value] {
    def write(dt: PaymentType.Value): JsValue = JsString(dt.toString)

    def read(value: JsValue): PaymentType.Value = value match {
      case JsString(s) => PaymentType.withName(s)
      case _ => throw new DeserializationException("DocumentType expected")
    }
  }
  implicit val addressJson: RootJsonFormat[Address] = jsonFormat5(Address.apply)
  implicit val clientJson: RootJsonFormat[Client] = jsonFormat6(Client.apply)
  implicit val paymentResponseJson: RootJsonFormat[PaymentResponse] = jsonFormat6(PaymentResponse.apply)
  implicit val paymentRequestJson: RootJsonFormat[PaymentRequest] = jsonFormat6(PaymentRequest.apply)
  implicit val tokenRespJson: RootJsonFormat[TokenResponse] = jsonFormat3(TokenResponse.apply)
  implicit val userOrderListModelJson = jsonFormat1(UserOrderListModel.apply)

  implicit val customerDataJson = jsonFormat4(CustomerData.apply)
  implicit val productModelJson = jsonFormat2(ProductModel.apply)
  implicit val paymentIntentJson = jsonFormat5(PaymentIntentRequestModel.apply)
  implicit val paymentDetailsJson = jsonFormat2(PaymentDetails.apply)

}
