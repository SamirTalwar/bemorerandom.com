package com.bemorerandom.api

import com.bemorerandom.api.xkcd.{XkcdController, XkcdJsonModule}
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.finatra.json.modules.FinatraJacksonModule

object ApiServerMain extends ApiServer

class ApiServer extends HttpServer {
  override val defaultFinatraHttpPort: String = ":8080"

  override def jacksonModule = ApiJacksonModule

  override protected def configureHttp(router: HttpRouter): Unit = {
    router.add[XkcdController]
  }
}

object ApiJacksonModule extends FinatraJacksonModule {
  override val additionalJacksonModules = Seq(
    new XkcdJsonModule
  )
}

