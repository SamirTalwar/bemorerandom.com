package com.bemorerandom.api

import com.twitter.finatra.http.test.EmbeddedHttpServer
import com.twitter.inject.server.FeatureTest

class XkcdFeatureSpec extends FeatureTest {
  override protected def server: EmbeddedHttpServer = new EmbeddedHttpServer(new ApiServer)
}
