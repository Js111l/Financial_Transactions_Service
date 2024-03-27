package dao.entities

case class Invoice(id: Long, grossAmount: Long, netAmount: Long, vatAmount: Long) extends FinancialDocument
