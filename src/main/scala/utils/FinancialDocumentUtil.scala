package utils

import actors.model.PaymentResponse
import dao.entities.{FinancialDocument, Invoice}
import enums.DocumentType.{ADJUSTMENT, INVOICE, RECEIPT, REFUND}

object FinancialDocumentUtil {
  def getFinancialDocumentFromResponse(paymentResponse: PaymentResponse): FinancialDocument = {
    // TODO:
    paymentResponse.documentType match {
      case INVOICE => {
        return Invoice(
          0,
          0,
          0,
          0
        );
      }
      //case RECEIPT => {}
     // case REFUND => {}
      //case ADJUSTMENT => {}
    }
  }
}
