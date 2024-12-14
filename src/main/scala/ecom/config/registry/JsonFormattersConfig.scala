package ecom.config.registry

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import ecom.actors.model.{Address, AddressModel, Client, CustomerData, OrderDetailsModel, PaymentDetails, PaymentIntentRequestModel, PaymentRequest, PaymentResponse, ProductModel, TokenResponse, UserOrderListModel, UserOrdersModel}
import ecom.dao.entities.Order
import ecom.enums.{DocumentType, PaymentType}
import spray.json.{DefaultJsonProtocol, DeserializationException, JsString, JsValue, JsonFormat, RootJsonFormat}

import java.time.{LocalDate, LocalDateTime}
import java.time.format.DateTimeFormatter

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

  implicit val localDateTimeFormat: RootJsonFormat[LocalDateTime] = new RootJsonFormat[LocalDateTime] {
    def write(dt: LocalDateTime): JsValue = JsString(dt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))

    def read(value: JsValue): LocalDateTime = value match {
      case JsString(s) => LocalDateTime.parse(s, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
      case _ => throw DeserializationException("LocalDateTime expected")
    }
  }

  implicit val localDateFormat: RootJsonFormat[LocalDate] = new RootJsonFormat[LocalDate] {
    def write(dt: LocalDate): JsValue = JsString(dt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))

    def read(value: JsValue): LocalDate = value match {
      case JsString(s) => LocalDate.parse(s, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
      case _ => throw DeserializationException("LocalDate expected")
    }
  }

  implicit val addressJson: RootJsonFormat[Address] = jsonFormat5(Address.apply)
  implicit val clientJson: RootJsonFormat[Client] = jsonFormat6(Client.apply)
  implicit val paymentResponseJson: RootJsonFormat[PaymentResponse] = jsonFormat6(PaymentResponse.apply)
  implicit val paymentRequestJson: RootJsonFormat[PaymentRequest] = jsonFormat6(PaymentRequest.apply)
  implicit val tokenRespJson: RootJsonFormat[TokenResponse] = jsonFormat3(TokenResponse.apply)
  implicit val userOrderListModelJson = jsonFormat5(UserOrderListModel.apply)

  implicit val customerDataJson = jsonFormat4(CustomerData.apply)
  implicit val productModelJson = jsonFormat5(ProductModel.apply)
  implicit val addressModelJson = jsonFormat5(AddressModel.apply)
  implicit val paymentIntentJson = jsonFormat8(PaymentIntentRequestModel.apply)
  implicit val paymentDetailsJson = jsonFormat2(PaymentDetails.apply)
  implicit val userOrdersJson = jsonFormat3(UserOrdersModel.apply)
  implicit val orderDetailsJson = jsonFormat9(OrderDetailsModel.apply)
  implicit val orderJson = jsonFormat8(Order.apply)
}
