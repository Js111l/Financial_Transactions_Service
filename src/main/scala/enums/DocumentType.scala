package enums

object DocumentType extends Enumeration {
  type DocumentType = Value

  val INVOICE: enums.DocumentType.Value = Value("INVOICE")
  val RECEIPT: enums.DocumentType.Value = Value("INVOICE")
  val ADJUSTMENT: enums.DocumentType.Value = Value("INVOICE")
  val REFUND: enums.DocumentType.Value = Value("INVOICE")
}
