package actors.model

import dao.entities.FinancialDocument
import enums.DocumentType.DocumentType

case class PaymentResponse(amount: Long,
                           currency: String,
                           status: String,
                           client: Client,
                           documentType: DocumentType){

}