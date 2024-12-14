package ecom.dao.entities

import java.time.{LocalDate, LocalDateTime}

case class Order(id: Long,
                 userId: Long,
                 email: Option[String],
                 createDate: LocalDateTime,
                 paymentMethod: Option[String],
                 paymentStatus: Option[String], //narazie taki string, doeclowo enum
                 shippingAddress: Option[String], //narazi string, docelowo model/encja
                 billAddress: Option[String] //narazie takA KLASA, docelowo model/encja dla adresu do rachunku,
                ) {

}
