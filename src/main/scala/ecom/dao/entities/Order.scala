package ecom.dao.entities

import java.time.{LocalDate, LocalDateTime}

case class Order(id: Long,
                 userId: Long,
                 email: String,
                 createDate: LocalDateTime,
                 paymentMethod: String,
                 paymentStatus: String, //narazie taki string, doeclowo enum
                 shippingAddressId: Long, //narazi string, docelowo model/encja
                 billAddressId: Long //narazie takA KLASA, docelowo model/encja dla adresu do rachunku,
                ) {

}
