package dao.repository.documents

import com.google.inject.Singleton
import dao.entities.Refund
import dao.repository.BaseCrudRepository
import dao.table.RefundTable
import slick.jdbc.GetResult
import slick.lifted.TableQuery

import scala.concurrent.Future


@Singleton
class RefundRepository() extends BaseCrudRepository[Refund] {
  val refunds = TableQuery[RefundTable]
  implicit val getResult = GetResult(r => Refund(r.nextLong(), r.nextLong(), r.nextLong(), r.nextLong()))

  override def findAll(): Future[List[Refund]] = ???

  override def findById(id: Long): Future[Option[Refund]] = ???

  override def save(entity: Refund): Future[Refund] = ???
  override def saveAll(entities: List[Refund]): Future[List[Refund]] = ???

  override def delete(id: Long): Future[Boolean] = ???

  override def deleteAll(): Future[Boolean] = ???
}
