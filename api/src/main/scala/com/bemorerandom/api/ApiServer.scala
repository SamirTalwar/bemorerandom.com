package com.bemorerandom.api

import com.bemorerandom.api.dnd.{DndController, DndJacksonModule}
import com.bemorerandom.api.xkcd.XkcdController
import com.fasterxml.jackson.databind
import com.google.inject.Module
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.finatra.json.modules.FinatraJacksonModule

object ApiServerMain extends ApiServer

class ApiServer extends HttpServer {
  override protected val defaultFinatraHttpPort: String = ":8080"

  private val config = Config.fromEnvironmentVariables()

  override protected def modules: Seq[Module] = Seq(
    new DatabaseModule(config.database)
  )

  override protected def jacksonModule: Module = new FinatraJacksonModule {
    override protected def additionalJacksonModules: Seq[databind.Module] = Seq(
      new SymbolTupleJacksonModule,
      new DndJacksonModule
    )
  }

  override protected def configureHttp(router: HttpRouter): Unit = {
    router.add[DndController]
    router.add[XkcdController]
  }
}
