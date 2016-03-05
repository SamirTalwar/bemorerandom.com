package com.bemorerandom.api

import java.net.URI

package object common {
  case class RandomThing[T](random: T, attribution: Attribution)

  case class Attribution(name: String, uri: URI)
}
