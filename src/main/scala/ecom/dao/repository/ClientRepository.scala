package ecom.dao.repository

import ecom.actors.model.{Address, Client}
import ecom.dao.entities.Adjustment
import ecom.dao.table.ClientTable
import slick.jdbc.GetResult
import slick.lifted.TableQuery

import scala.concurrent.Future

class ClientRepository extends BaseCrudRepository[Client] {
  val clients = TableQuery[ClientTable]
  implicit val getResult = GetResult(r =>
    Client(r.nextLong(), r.nextString(), r.nextString(), r.nextString(), r.nextLong(),
    Address(r.nextString(), r.nextString(), r.nextString(), r.nextString(), r.nextString())))

  override def findAll(): Future[List[Client]] = ???

  override def findById(id: Long): Future[Option[Client]] = ???

  override def save(entity: Client): Future[Client] = ???

  override def saveAll(entities: List[Client]): Future[List[Client]] = ???

  override def delete(id: Long): Future[Boolean] = ???

  override def deleteAll(): Future[Boolean] = ???
}
