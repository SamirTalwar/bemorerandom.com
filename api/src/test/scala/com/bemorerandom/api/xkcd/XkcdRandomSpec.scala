package com.bemorerandom.api.xkcd

import java.net.URI
import com.bemorerandom.api.common.Attribution
import com.bemorerandom.api.xkcd.Xkcd.xkcd
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FunSpec, Matchers}

@RunWith(classOf[JUnitRunner])
class XkcdRandomSpec extends FunSpec with Matchers {
  describe("the XKCD random number generator") {
    it("chooses a number by fair dice roll") {
      xkcd.random.number should be(4)
    }

    it("is attributed to xkcd.com") {
      xkcd.attribution should be(Attribution(name = "xkcd", uri = new URI("https://xkcd.com/221/")))
    }
  }
}
