package com.bemorerandom.api

import org.flywaydb.core.Flyway
import slick.jdbc.JdbcBackend

class DatabaseConnector(config: Config.Database) {
  def connect() = JdbcBackend.Database.forURL(config.url, config.user, config.password)

  def migrate(): Unit = {
    migrator.migrate()
  }

  def clean(): Unit = {
    migrator.clean()
  }

  val migrator = {
    val _migrator = new Flyway
    _migrator.setDataSource(config.url, config.user, config.password)
    _migrator
  }
}
