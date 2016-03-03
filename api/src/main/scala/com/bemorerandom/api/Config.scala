package com.bemorerandom.api

import scala.collection.JavaConverters._

case class Config(database: Config.Database)

object Config {
  case class Database(url: String, user: String, password: String) {
    val driver = slick.driver.PostgresDriver
  }

  def fromEnvironmentVariables() = {
    val env = System.getenv().asScala
    Config(
      database = Config.Database(url = env("DB_URL"), user = env("DB_USER"), password = env("DB_PASSWORD"))
    )
  }
}
