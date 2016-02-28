package com.bemorerandom.api

import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.routing.HttpRouter

class ApiServer extends HttpServer {
  override protected def configureHttp(router: HttpRouter): Unit = {
  }
}
