package com.bemorerandom.api

import scala.collection.JavaConverters._

case class Config(database: Config.Database)

object Config {
  private val PostgresqlDefaultPort = 5432

  case class Database(url: String, user: String, password: String) {
    val driver = slick.driver.PostgresDriver
  }

  def fromEnvironmentVariables() = {
    val env = System.getenv().asScala.toMap
    Config(database = databaseConfigFrom(env))
  }

  private def databaseConfigFrom(env: Map[String, String]): Database = {
    val host = env("DB_HOST")
    val port = env.get("DB_PORT").map(Integer.parseInt).getOrElse(PostgresqlDefaultPort)
    val url = s"jdbc:postgresql://$host:$port/${env("DB_NAME")}"
    Config.Database(url = url, user = env("DB_USER"), password = env("DB_PASSWORD"))
  }
}
