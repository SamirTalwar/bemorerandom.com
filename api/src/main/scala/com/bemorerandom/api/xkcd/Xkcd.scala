package com.bemorerandom.api.xkcd

case class Xkcd(random: Xkcd.Random)

object Xkcd {
  val xkcd = Xkcd(
    Random(number = 4)
  )

  case class Random(number: Int)
}
