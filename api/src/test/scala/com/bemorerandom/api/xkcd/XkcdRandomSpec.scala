package com.bemorerandom.api.xkcd

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FunSpec, Matchers}

@RunWith(classOf[JUnitRunner])
class XkcdRandomSpec extends FunSpec with Matchers {
  describe("the XKCD random number generator") {
    it("chooses a number by fair dice roll") {
      Xkcd.xkcd.random.number should be(4)
    }
  }
}
