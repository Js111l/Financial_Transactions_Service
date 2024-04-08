package ecom.dao.table

import ecom.dao.entities.Adjustment
import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape

class AdjustmentTable(tag: Tag) extends Table[Adjustment](tag, "adjustment") {

  private def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  private def grossAmount = column[Long]("grossAmount")

  private def netAmount = column[Long]("netAmount")

  private def vatAmount = column[Long]("vatAmount")


  def * : ProvenShape[Adjustment] = (id, grossAmount, netAmount, vatAmount) <> (Adjustment.tupled, Adjustment.unapply)
}
