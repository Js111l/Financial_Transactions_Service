package ecom.dao.entities

import java.time.{LocalDate, LocalDateTime}

case class Order(id: Long,
                 userId: Long,
                 createDate: LocalDateTime,
                 paymentMethod: String,
                 paymentStatus: String, //narazie taki string, doeclowo enum
                 shippingAddress: String, //narazi string, docelowo model/encja
                 billAddress: String //narazie taki string, docelowo model/encja,
                ) {

}
