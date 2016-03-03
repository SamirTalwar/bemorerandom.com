package com.bemorerandom.api.dnd

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import com.google.inject.Inject
import slick.jdbc.JdbcBackend.DatabaseDef

class NpcGenerator @Inject() (tables: Tables, database: DatabaseDef) {
  import tables.driver.api._
  import tables._

  val random = SimpleFunction.nullary[Double]("RANDOM")

  def generate(sex: Sex, race: Race): Future[Npc] = {
    database.run(
        tables.npcFirstNames.join(tables.npcLastNames)
          .on { case (first, last) => first.raceName === last.raceName }
          .filter { case (first, last) => first.sex === sex }
          .filter { case (first, last) => first.raceName === race.name }
          .map { case (first, last) => (first.name, last.name) }
          .sortBy(_ => random)
          .take(1)
          .result)
        .map(_.head)
      .map { case (firstName, lastName) => Npc(s"$firstName $lastName", sex, race) }
  }
}
