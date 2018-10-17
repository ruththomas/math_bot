package actors.messages.level
import play.api.libs.json.{Json, OFormat}
import types.GalaxyId

object GalaxyData {
  implicit val format: OFormat[GalaxyData] = Json.format[GalaxyData]
}

case class GalaxyData(id: GalaxyId, starSystems: List[StarSystemData])
