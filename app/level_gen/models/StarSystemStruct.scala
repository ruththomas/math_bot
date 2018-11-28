package level_gen.models
import play.api.libs.json.{Json, OFormat}

object StarSystemStruct {
  implicit val format: OFormat[StarSystemStruct] = Json.format[StarSystemStruct]
}

case class StarSystemStruct(
    description: String,
    freePlay: Boolean
)
