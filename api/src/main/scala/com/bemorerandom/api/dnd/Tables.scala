package com.bemorerandom.api.dnd

import slick.profile.SqlDriver

class Tables(val driver: SqlDriver) {
  import driver.api._

  implicit val sexTypeMapper = MappedColumnType.base[Sex, String](Sex.stringRepresentation, Sex.modelRepresentation)

  implicit val raceTypeMapper = MappedColumnType.base[Race, String](_.name, Race.apply)

  class Races(tag: Tag) extends Table[String](tag, "dnd_races") {
    def name = column[String]("name", O.PrimaryKey)

    def * = name
  }
  val races = TableQuery[Races]

  class NpcFirstNames(tag: Tag) extends Table[(Int, String, Sex, String)](tag, "dnd_npc_first_names") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def sex = column[Sex]("sex")
    def raceName = column[String]("race")
    def * = (id, name, sex, name)

    def race = foreignKey("", raceName, races)(_.name)
  }
  val npcFirstNames = TableQuery[NpcFirstNames]
  val npcFirstNamesForInsert = npcFirstNames.map(table => (table.name, table.sex, table.raceName))

  class NpcLastNames(tag: Tag) extends Table[(Int, String, String)](tag, "dnd_npc_last_names") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def raceName = column[String]("race")
    def * = (id, name, name)

    def race = foreignKey("", raceName, races)(_.name)
  }
  val npcLastNames = TableQuery[NpcLastNames]
  val npcLastNamesForInsert = npcLastNames.map(table => (table.name, table.raceName))
}
