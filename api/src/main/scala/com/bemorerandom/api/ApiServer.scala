package com.bemorerandom.api

import com.bemorerandom.api.xkcd.XkcdController
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.routing.HttpRouter
import org.flywaydb.core.Flyway

object ApiServerMain extends ApiServer

class ApiServer extends HttpServer {
  override val defaultFinatraHttpPort: String = ":8080"

  override def appMain(): Unit = {
    val config = Config.fromEnvironmentVariables()
    migrateDatabase(config.database)
  }

  override protected def configureHttp(router: HttpRouter): Unit = {
    router.add[XkcdController]
  }

  private def migrateDatabase(databaseConfig: Config.Database): Unit = {
    val migrator = new Flyway
    migrator.setDataSource(databaseConfig.url, databaseConfig.user, databaseConfig.password)
    migrator.migrate()
  }
}
