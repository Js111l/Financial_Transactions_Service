package dao.repository.documents

import com.google.inject.Singleton
import dao.entities.Adjustment
import dao.repository.BaseCrudRepository
import dao.table.AdjustmentTable
import slick.jdbc.GetResult
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future


@Singleton
class AdjustmentRepository() extends BaseCrudRepository[Adjustment] {
  val adjustments = TableQuery[AdjustmentTable]
  implicit val getResult = GetResult(r => Adjustment(r.nextLong(), r.nextLong(), r.nextLong(), r.nextLong()))

  override def findAll(): Future[List[Adjustment]] = ???

  override def findById(id: Long): Future[Option[Adjustment]] = ???

  override def save(entity: Adjustment): Future[Adjustment] ={
    val insertAction = (adjustments returning adjustments) += entity
    db.run(insertAction)
  }

  override def saveAll(entities: List[Adjustment]): Future[List[Adjustment]] = ???

  override def delete(id: Long): Future[Boolean] = ???

  override def deleteAll(): Future[Boolean] = ???
}
