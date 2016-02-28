package com.bemorerandom.api

import com.bemorerandom.api.xkcd.XkcdController
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.routing.HttpRouter

object ApiServerMain extends ApiServer

class ApiServer extends HttpServer {
  override val defaultFinatraHttpPort: String = ":8080"

  override protected def configureHttp(router: HttpRouter): Unit = {
    router.add[XkcdController]
  }
}
