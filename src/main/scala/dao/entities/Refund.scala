package dao.entities

case class Refund(id: Long, grossAmount: Long, netAmount: Long, vatAmount: Long) extends FinancialDocument {

}
