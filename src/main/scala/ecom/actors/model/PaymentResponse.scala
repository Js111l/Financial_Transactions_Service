package ecom.actors.model

import ecom.enums.DocumentType.DocumentType

case class PaymentResponse(transactionId: String,
                           amount: Long,
                           currency: String,
                           status: String,
                           client: Client,
                           documentType: DocumentType) {

}