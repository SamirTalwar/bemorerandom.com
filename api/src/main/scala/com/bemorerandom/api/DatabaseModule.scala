package com.bemorerandom.api

import com.google.inject.{Binder, Module}
import slick.jdbc.JdbcBackend

class DatabaseModule(config: Config.Database) extends Module {
  override def configure(binder: Binder): Unit = {
    val database = new DatabaseConnector(config)
    database.migrate()
    val db = database.connect()

    binder.bind(classOf[JdbcBackend.DatabaseDef]).toInstance(db)
    binder.bind(classOf[dnd.Tables]).toInstance(new dnd.Tables(config.driver))
  }
}
