package config.json

import dao.entities.Invoice
import spray.json.{DefaultJsonProtocol, DeserializationException, JsString, JsValue, RootJsonFormat}

import java.time.LocalDate


object MyJsonProtocol extends DefaultJsonProtocol {
  implicit object LocalDateJsonFormat extends RootJsonFormat[LocalDate] {
    override def write(obj: LocalDate): JsValue = JsString(obj.toString)

    override def read(json: JsValue): LocalDate = json match {
      case JsString(str) => LocalDate.parse(str)
      case _ => throw DeserializationException("Date expected")
    }
  }
  implicit val invoiceFormat: RootJsonFormat[Invoice] = jsonFormat4(Invoice.apply)


  //implicit val productEntityFormat: RootJsonFormat[ProductEntity] = jsonFormat6(ProductEntity)
 // implicit val productEntityListFormat: RootJsonFormat[List[ProductEntity]] = listFormat(productEntityFormat)
}