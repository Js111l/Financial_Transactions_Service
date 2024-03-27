package dao.table

import dao.entities.Invoice
import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape


class InvoiceTable(tag: Tag) extends Table[Invoice](tag, "invoice") {

  private def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  private def grossAmount = column[Long]("gross_amount")

  private def netAmount = column[Long]("net_amount")

  private def vatAmount = column[Long]("vat_amount")
  def * : ProvenShape[Invoice] = (id, grossAmount, netAmount, vatAmount) <> (Invoice.tupled, Invoice.unapply)
}
