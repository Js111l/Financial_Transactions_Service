package enums

object PaymentType extends Enumeration {
  type PaymentType = Value
  val SEPA: enums.PaymentType.Value = Value("SEPA")
  val CARD: enums.PaymentType.Value = Value("CARD")
  val BLIK: enums.PaymentType.Value = Value("BLIK")
}
