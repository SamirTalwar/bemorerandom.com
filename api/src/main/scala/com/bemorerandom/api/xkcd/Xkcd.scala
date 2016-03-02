package com.bemorerandom.api.xkcd

import java.net.URI
import com.bemorerandom.api.common.Attribution

case class Xkcd(random: Xkcd.Random, attribution: Attribution)

object Xkcd {
  val xkcd = Xkcd(
    Random(number = 4),
    Attribution(name = "xkcd", uri = new URI("https://xkcd.com/221/"))
  )

  case class Random(number: Int)
}
