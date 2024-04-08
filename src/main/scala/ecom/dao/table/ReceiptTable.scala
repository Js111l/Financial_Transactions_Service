package ecom.dao.table

import ecom.dao.entities.Receipt
import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape

class ReceiptTable(tag: Tag) extends Table[Receipt](tag, "receipt") {

  private def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  private def grossAmount = column[Long]("grossAmount")

  private def netAmount = column[Long]("netAmount")

  private def vatAmount = column[Long]("vatAmount")


  def * : ProvenShape[Receipt] = (id, grossAmount, netAmount, vatAmount) <> (Receipt.tupled, Receipt.unapply)
}
