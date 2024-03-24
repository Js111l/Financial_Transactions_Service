package db

import scala.concurrent.Future

trait BaseRepository[T] {
  def findAll(): Future[List[T]]

  def findById(id: Long): Future[Option[T]]

  def save(entity: T): T

  def saveAll(entities: List[T]): Future[List[T]]

  def delete(id: Long): Future[Boolean]

  def deleteAll(): Future[Boolean]
}