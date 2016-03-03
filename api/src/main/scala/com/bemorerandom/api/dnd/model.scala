package com.bemorerandom.api.dnd

sealed trait Sex
case object Female extends Sex
case object Male extends Sex

object Sex {
  val modelRepresentation = Map[String, Sex](
    "female" -> Female,
    "male" -> Male
  )
  val stringRepresentation = Map[Sex, String](
    Female -> "female",
    Male -> "male"
  )
}

case class Race(name: String) extends AnyVal

case class Npc(name: String, sex: Sex, race: Race)
