package dao.repository

import com.google.inject.Singleton
import dao.entities.Receipt
import dao.table.ReceiptTable
import slick.jdbc.GetResult
import slick.lifted.TableQuery
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

@Singleton
class ReceiptRepository() extends BaseCrudRepository [Receipt]{
  val invoices = TableQuery[ReceiptTable]
  implicit val getResult: GetResult[Receipt] = GetResult(r => Receipt(r.nextLong(), r.nextLong(), r.nextLong(), r.nextLong()))

  override def findAll(): Future[List[Receipt]] = ???

  override def findById(id: Long): Future[Option[Receipt]] = ???

  override def save(entity: Receipt): Future[Receipt] = ???

  override def saveAll(entities: List[Receipt]): Future[List[Receipt]] = ???

  override def delete(id: Long): Future[Boolean] = ???

  override def deleteAll(): Future[Boolean] = ???
}
