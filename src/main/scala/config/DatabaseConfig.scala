package config
import com.google.inject.Singleton
import slick.jdbc.PostgresProfile.api._

@Singleton
class DatabaseConfig {
  def getDb(): Database = {
    Database.forConfig("mydb");
  }
}
