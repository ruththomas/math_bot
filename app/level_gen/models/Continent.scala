package level_gen.models
import actors.messages.level.LayerStatistic
import play.api.libs.json.{Json, OFormat}
object Continent {
  implicit val format: OFormat[Continent] = Json.format[Continent]
}
case class Continent(
    name: String,
    stats: Option[LayerStatistic] = None,
    struct: Option[ContinentStruct]
)
