package dao.repository.documents

import com.google.inject.{Inject, Singleton}
import dao.entities.Invoice
import dao.repository.BaseCrudRepository
import dao.table.InvoiceTable
import slick.jdbc.GetResult
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class InvoiceRepository @Inject()(implicit val ec: ExecutionContext) extends BaseCrudRepository[Invoice] {
  val invoices = TableQuery[InvoiceTable]
  implicit val getResult: GetResult[Invoice] = GetResult(r => Invoice(r.nextLong(),r.nextLong(), r.nextLong(), r.nextLong()))
   def findAll2(): Future[Seq[Invoice]] = {
    db.run(invoices.result)
  }

  override def findAll(): Future[List[Invoice]] = {
    db.run(invoices.result).map(_.toList)
  }
  override def findById(id: Long): Future[Option[Invoice]] = ???
  override def save(entity: Invoice): Future[Invoice] = ???

  def save2(entity: Invoice): Unit = {
    db.run(invoices += entity)
  }
  override def saveAll(entities: List[Invoice]): Future[List[Invoice]] = ???

  override def delete(id: Long): Future[Boolean] = ???

  override def deleteAll(): Future[Boolean] = ???
}
