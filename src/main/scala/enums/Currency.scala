package enums

object Currency extends Enumeration {
  type Currency = Value
  val EUR: enums.Currency.Value = Value("EUR")
  val PLN: enums.Currency.Value = Value("PLN")
}
