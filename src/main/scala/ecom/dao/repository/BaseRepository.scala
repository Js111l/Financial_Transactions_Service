package ecom.dao.repository

import slick.jdbc.PostgresProfile.api._

class BaseRepository {
  protected val db = Database.forConfig("mydb")

}
