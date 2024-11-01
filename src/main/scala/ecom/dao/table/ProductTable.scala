package ecom.dao.table

import ecom.dao.entities.Product

import slick.jdbc.PostgresProfile.api._


class ProductTable(tag: Tag) extends Table[Product](tag, "PRODUCT") {
  def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)

  private def quantity = column[Long]("quantity")

  def * = (id, quantity) <> (Product.tupled, Product.unapply)

}