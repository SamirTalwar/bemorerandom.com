package com.bemorerandom.api.dnd

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.io.Source

import com.bemorerandom.api.{ApiServer, Config, DatabaseConnector}
import com.twitter.finagle.http.Status
import com.twitter.finatra.http.EmbeddedHttpServer
import com.twitter.inject.server.FeatureTest
import org.json4s._
import org.json4s.native.JsonMethods
import org.scalatest.BeforeAndAfter

class DndFeatureSpec extends FeatureTest with BeforeAndAfter {
  override protected def server: EmbeddedHttpServer = new EmbeddedHttpServer(new ApiServer)

  implicit val formats = DefaultFormats

  val databaseConfig = Config.fromEnvironmentVariables().database
  import databaseConfig.driver.api._

  before {
    val connector = new DatabaseConnector(databaseConfig)
    connector.clean()
    connector.migrate()

    val database = connector.connect()
    val sql = sqlu"#${Source.fromInputStream(getClass.getClassLoader.getResourceAsStream("db/static/dnd-npc-test-names.sql")).mkString}"
    Await.result(database.run(sql), atMost = 1.second)
  }

  "/dnd/npc" should {
    "generate a non-player character name" in {
      val response = server.httpGet(
        path = "/dnd/npc/female/human",
        andExpect = Status.Ok)

      val body = response.withReader(JsonMethods.parse(_))
      val Diff(changed, added, deleted) = body.diff(JObject(
        "random" -> JObject(
          "dnd" -> JObject(
            "npc" -> JObject(
              "sex" -> JString("female"),
              "race" -> JString("human")
            )
          )
        ),
        "attribution" -> JObject(
          "name" -> JString("Chris Perkins"),
          "uri" -> JString("http://brandondraga.tumblr.com/post/66804468075/chris-perkins-npc-name-list")
        )
      ))

      changed should be (JNothing)
      added should be (JNothing)

      val name = (deleted \ "random" \ "dnd" \ "npc" \ "name").extract[String]
      name should fullyMatch regex "[A-Z][a-z]+ [A-Z][a-z]+"
    }
  }
}
