package ecom.dao.repository

import scala.concurrent.Future
import slick.jdbc.PostgresProfile.api._

trait BaseCrudRepository[T] {
  protected val db = Database.forConfig("mydb")

  def findAll(): Future[List[T]]

  def findById(id: Long): Future[Option[T]]

  def save(entity: T): Future[T]

  def saveAll(entities: List[T]): Future[List[T]]

  def delete(id: Long): Future[Boolean]

  def deleteAll(): Future[Boolean]
}