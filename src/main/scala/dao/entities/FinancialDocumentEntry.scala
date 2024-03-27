package dao.entities

case class FinancialDocumentEntry(id: Long,
                             grossAmount: Long,
                             netAmount: Long,
                             vatAmount: Long,
                             product: Product) {

}
