package com.bemorerandom.api.xkcd

import com.bemorerandom.api.ApiServer
import com.twitter.finagle.http.Status
import com.twitter.finatra.http.test.EmbeddedHttpServer
import com.twitter.inject.server.FeatureTest

class XkcdFeatureSpec extends FeatureTest {
  override protected def server: EmbeddedHttpServer = new EmbeddedHttpServer(new ApiServer)

  "/xkcd" should {
    "choose a random number by fair dice roll" in {
      server.httpGet(
        path = "/xkcd",
        andExpect = Status.Ok,
        withJsonBody =
          """{
            |    "random": {
            |        "number": 4
            |    },
            |    "attribution": {
            |        "name": "xkcd",
            |        "uri": "https://xkcd.com/221/"
            |    }
            |}
          """.stripMargin
      )
    }
  }
}
