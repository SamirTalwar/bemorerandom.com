package com.bemorerandom.api.xkcd

trait XkcdRandom {
  val number: Int
}

case object XkcdRandom extends XkcdRandom {
  val number: Int = 4
}
