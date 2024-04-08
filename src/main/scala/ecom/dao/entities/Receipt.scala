package ecom.dao.entities

case class Receipt(id: Long, grossAmount: Long, netAmount: Long, vatAmount: Long) extends FinancialDocument {

}
