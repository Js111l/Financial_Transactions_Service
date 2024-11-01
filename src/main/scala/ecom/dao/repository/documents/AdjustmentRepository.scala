package ecom.dao.repository.documents

import com.google.inject.Singleton
import ecom.dao.entities.Adjustment
import ecom.dao.repository.BaseCrudRepository
import ecom.dao.table.AdjustmentTable
import slick.jdbc.GetResult
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future


@Singleton
class AdjustmentRepository() extends BaseCrudRepository[Adjustment] {
  val adjustments = TableQuery[AdjustmentTable]
  implicit val getResult = GetResult(r => Adjustment(r.nextLong(), r.nextLong(), r.nextLong(), r.nextLong()))

  override def findAll(): Future[List[Adjustment]] = ???

  override def findById(id: Long): Future[Option[Adjustment]] = ???

  override def save(entity: Adjustment): Future[Adjustment] = {
    val insertAction = (adjustments returning adjustments) += entity
    db.run(insertAction)
  }

  def getSaveAction(entity: Adjustment): DBIO[Adjustment] = {
    (adjustments returning adjustments) += entity
  }

  override def saveAll(entities: List[Adjustment]): Future[List[Adjustment]] = ???

  override def delete(id: Long): Future[Boolean] = ???

  override def deleteAll(): Future[Boolean] = ???
  def runTransactionally(): Unit = {

  }
}
