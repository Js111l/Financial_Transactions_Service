package ecom.enums

object DocumentType extends Enumeration {
  type DocumentType = Value

  val INVOICE: Value = Value("INVOICE")
  val RECEIPT: Value = Value("INVOICE")
  val ADJUSTMENT: Value = Value("INVOICE")
  val REFUND: Value = Value("INVOICE")
}
