package ecom.config

import com.typesafe.config.{ConfigFactory, ConfigList}

class AppConfig {
  val config = ConfigFactory.load()

  def getPaymentMethods(): ConfigList = {
    this.config.getList("payment-methods")
  }

  def getApiKey(): String = {
    this.config.getString("stripe-api-key")
  }
}
