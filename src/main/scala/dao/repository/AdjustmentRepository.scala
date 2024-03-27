package dao.repository

import com.google.inject.{Inject, Singleton}
import config.DatabaseConfig
import dao.entities.Adjustment
import dao.table.AdjustmentTable
import slick.jdbc.GetResult

import scala.concurrent.Future
import slick.jdbc.PostgresProfile.api._


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
