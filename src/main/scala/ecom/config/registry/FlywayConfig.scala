package ecom.config.registry

import org.flywaydb.core.Flyway

object FlywayConfig {
  def getFlyway(): Flyway = {
    Flyway.configure()
      .dataSource("jdbc:postgresql://localhost:5432/transaction_db", "admin", "password")
      .locations("classpath:db")
      .schemas("public")
      .encoding("UTF-8")
      .failOnMissingLocations(false)
      .cleanDisabled(true)
      .baselineVersion("0.0")
      .baselineOnMigrate(true)
      .load()
  }
}
