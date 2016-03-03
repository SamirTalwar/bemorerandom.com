package com.bemorerandom.api.dnd

import java.sql.SQLException
import scala.concurrent.Await
import scala.concurrent.duration._
import com.bemorerandom.api.{Config, DatabaseConnector}
import org.junit.runner.RunWith
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.junit.JUnitRunner
import org.scalatest.{BeforeAndAfter, FunSpec, Matchers}
import slick.jdbc.JdbcBackend

@RunWith(classOf[JUnitRunner])
class NpcGeneratorSpec extends FunSpec with Matchers with ScalaFutures with BeforeAndAfter {
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
    Await.result(
      database.run(DBIO.seq(
        sqlu"TRUNCATE TABLE dnd_npc_first_names",
        sqlu"TRUNCATE TABLE dnd_npc_last_names")),
      atMost = 1.second)
  }

  describe("the NPC name generator") {
    describe("given a race and sex") {
      it("generates a name") {
        try {
          Await.result(database.run(DBIO.seq(
            tables.npcFirstNamesForInsert ++= Seq(
              ("Beyla", Female, "dwarf"),
              ("Fenryl", Female, "dwarf"),
              ("Grenenzel", Female, "dwarf"),
              ("Agaro", Male, "dwarf"),
              ("Arnan", Male, "dwarf"),
              ("Auxlan", Male, "dwarf"),
              ("Aryllan", Female, "elf"),
              ("Atalya", Female, "elf"),
              ("Ayrthwil", Female, "elf")
            ),
            tables.npcLastNamesForInsert ++= Seq(
              ("Ambershard", "dwarf"),
              ("Barrelhelm", "dwarf"),
              ("Copperhearth", "dwarf")
            ))),
            atMost = 1.second)
        } catch {
          case e: SQLException => {
            e.getNextException.printStackTrace()
            throw e
          }
        }

        val generator = new NpcGenerator(tables, database)

        generator.generate(Female, dwarf).futureValue should (
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
    }
  }
}
