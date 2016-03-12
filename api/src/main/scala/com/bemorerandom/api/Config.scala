package com.bemorerandom.api

import scala.collection.JavaConverters._

case class Config(database: Config.Database)

object Config {
  private val PostgresqlDefaultPort = 5432

  case class Database(url: String, user: String, password: String) {
    val driver = slick.driver.PostgresDriver
  }

  def fromEnvironmentVariables() = {
    val env = System.getenv().asScala
    val url = s"jdbc:postgresql://${env("DB_HOST")}:${env.getOrElse("DB_PORT", PostgresqlDefaultPort.toString)}/${env("DB_NAME")}"
    Config(
      database = Config.Database(url = url, user = env("DB_USER"), password = env("DB_PASSWORD"))
    )
  }
}
