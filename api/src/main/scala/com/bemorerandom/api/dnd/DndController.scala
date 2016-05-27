package com.bemorerandom.api.dnd

import java.net.URI

import com.bemorerandom.api.TwitterConverters._
import com.bemorerandom.api.common.{Attribution, RandomThing}
import com.google.inject.Inject
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

import scala.concurrent.ExecutionContext.Implicits.global

class DndController @Inject() (npcGenerator: NpcGenerator) extends Controller {
  get("/dnd/npc/:sex/:race") { request: Request =>
    val sex = Sex.modelRepresentation(request.params("sex"))
    val race = Race(request.params("race"))
    npcGenerator.generate(sex, race).asTwitter
      .map {
        _.map { npc =>
          RandomThing(
            'dnd -> ('npc -> npc),
            Attribution(
              name = "Chris Perkins",
              uri = new URI("http://brandondraga.tumblr.com/post/66804468075/chris-perkins-npc-name-list")))
        }
      }
  }
}
