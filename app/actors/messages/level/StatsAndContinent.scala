package actors.messages.level
import play.api.libs.json.{Json, OFormat}

object StatsAndContinent {
  implicit val format: OFormat[StatsAndContinent] = Json.format[StatsAndContinent]
}

case class StatsAndContinent(stats: Stats, builtContinent: BuiltContinent)
