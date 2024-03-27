package dao.table

import dao.entities.Refund
import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape

class RefundTable(tag: Tag) extends Table[Refund](tag, "refund") {

  private def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  private def grossAmount = column[Long]("grossAmount")

  private def netAmount = column[Long]("netAmount")

  private def vatAmount = column[Long]("vatAmount")


  def * : ProvenShape[Refund] = (id, grossAmount, netAmount, vatAmount) <> (Refund.tupled, Refund.unapply)
}
