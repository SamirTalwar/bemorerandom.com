package com.bemorerandom.api.xkcd

import java.net.URI

case class Xkcd(random: Xkcd.Random, documentation: Xkcd.Documentation)

object Xkcd {
  val xkcd = Xkcd(
    Random(number = 4),
    Documentation(uri = new URI("https://xkcd.com/221/"))
  )

  case class Random(number: Int)

  case class Documentation(uri: URI)
}
