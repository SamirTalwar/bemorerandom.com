package com.bemorerandom.api.dnd

import java.net.URI
import scala.concurrent.ExecutionContext.Implicits.global
import com.bemorerandom.api.TwitterConverters._
import com.bemorerandom.api.common.Attribution
import com.google.inject.Inject
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

class DndController @Inject() (npcGenerator: NpcGenerator) extends Controller {
  get("/dnd/npc/:sex/:race") { request: Request =>
    val sex = Sex.modelRepresentation(request.params("sex"))
    val race = Race(request.params("race"))
    npcGenerator.generate(sex, race).asTwitter
      .map { npc =>
        Dnd(
          Random(npc),
          Attribution(
            name = "Chris Perkins",
            uri = new URI("http://brandondraga.tumblr.com/post/66804468075/chris-perkins-npc-name-list")))
      }
  }
}

case class Dnd(random: Random, attribution: Attribution)

case class Random(npc: Npc)
