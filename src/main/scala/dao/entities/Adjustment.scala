package dao.entities


case class Adjustment(id: Long, grossAmount: Long, netAmount: Long, vatAmount: Long) extends FinancialDocument