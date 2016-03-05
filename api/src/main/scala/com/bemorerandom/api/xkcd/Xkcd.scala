package com.bemorerandom.api.xkcd

import java.net.URI
import com.bemorerandom.api.common.{Attribution, RandomThing}

object Xkcd {
  val xkcd = RandomThing(
    RandomNumber(number = 4),
    Attribution(name = "xkcd", uri = new URI("https://xkcd.com/221/"))
  )

  case class RandomNumber(number: Int)
}
