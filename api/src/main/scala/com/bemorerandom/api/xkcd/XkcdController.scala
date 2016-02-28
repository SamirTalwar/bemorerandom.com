package com.bemorerandom.api.xkcd

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

class XkcdController extends Controller {
  get("/xkcd") { request: Request =>
    response.ok.json(Xkcd.xkcd)
  }
}
