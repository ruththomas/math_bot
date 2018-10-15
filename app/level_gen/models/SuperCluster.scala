package level_gen.models
import java.util.Date
import play.api.libs.json.{Json, OFormat}

object SuperCluster {
  implicit val format: OFormat[SuperCluster] = Json.format[SuperCluster]
}

case class SuperCluster(name: String = "Mathbot", galaxies: List[Galaxy])
