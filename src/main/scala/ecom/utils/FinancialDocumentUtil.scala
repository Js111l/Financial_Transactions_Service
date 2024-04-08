package ecom.utils

import ecom.actors.model.PaymentResponse
import ecom.dao.entities.{FinancialDocument, Invoice}
import ecom.enums.DocumentType.{ADJUSTMENT, INVOICE, RECEIPT, REFUND}

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
