package com.bemorerandom.api.dnd

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.io.Source

import com.bemorerandom.api.{Config, DatabaseConnector}
import org.junit.runner.RunWith
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.junit.JUnitRunner
import org.scalatest.{BeforeAndAfter, FunSpec, Matchers}
import slick.jdbc.JdbcBackend

@RunWith(classOf[JUnitRunner])
class NpcGeneratorSpec extends FunSpec with Matchers with ScalaFutures with BeforeAndAfter {
  override implicit val patienceConfig = PatienceConfig(timeout = 1.second, interval = 100.milliseconds)

  val dragonborn = Race("dragonborn")
  val dwarf = Race("dwarf")

  val databaseConfig = Config.fromEnvironmentVariables().database
  val tables = new Tables(databaseConfig.driver)
  var connector: DatabaseConnector = null
  var database: JdbcBackend.DatabaseDef = null

  import databaseConfig.driver.api._

  before {
    connector = new DatabaseConnector(databaseConfig)
    connector.clean()
    connector.migrate()
    database = connector.connect()
    val sql = sqlu"#${Source.fromInputStream(getClass.getClassLoader.getResourceAsStream("db/static/dnd-npc-test-names.sql")).mkString}"
    Await.result(database.run(sql), atMost = 1.second)
  }

  describe("the NPC name generator") {
    describe("given a race and sex") {
      it("generates a name") {
        val generator = new NpcGenerator(tables, database)

        generator.generate(Female, dwarf).futureValue.get should (
          be(Npc("Beyla Ambershard", Female, dwarf)) or
          be(Npc("Fenryl Ambershard", Female, dwarf)) or
          be(Npc("Grenenzel Ambershard", Female, dwarf)) or
          be(Npc("Beyla Barrelhelm", Female, dwarf)) or
          be(Npc("Fenryl Barrelhelm", Female, dwarf)) or
          be(Npc("Grenenzel Barrelhelm", Female, dwarf)) or
          be(Npc("Beyla Copperhearth", Female, dwarf)) or
          be(Npc("Fenryl Copperhearth", Female, dwarf)) or
          be(Npc("Grenenzel Copperhearth", Female, dwarf))
        )
      }

      it("generates a name even when missing surnames") {
        val generator = new NpcGenerator(tables, database)

        generator.generate(Male, dragonborn).futureValue.get should (
          be(Npc("Andujar", Male, dragonborn)) or
          be(Npc("Armagan", Male, dragonborn)) or
          be(Npc("Armek", Male, dragonborn)))
      }

      it("generates nothing if it has no data") {
        val generator = new NpcGenerator(tables, database)

        generator.generate(Female, new Race("sponge")).futureValue should be(None)
      }
    }
  }
}
