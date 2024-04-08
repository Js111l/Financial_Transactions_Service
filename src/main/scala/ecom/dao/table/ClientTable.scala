package ecom.dao.table

import ecom.actors.model.{Address, Client}
import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape

class ClientTable(tag: Tag) extends Table[Client](tag, "client") {

  private def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  private def firstName = column[String]("gross_amount")

  private def lastName = column[String]("net_amount")

  private def email = column[String]("vat_amount")

  private def phoneNumber = column[Long]("vat_amount")

  private def address = (
    column[String]("street"),
    column[String]("city"),
    column[String]("state"),
    column[String]("zip_code"),
    column[String]("")
  ).mapTo[Address]

  def * : ProvenShape[Client] = (id, firstName, lastName, email, phoneNumber, address) <> (Client.tupled, Client.unapply)
}
