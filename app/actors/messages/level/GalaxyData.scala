package actors.messages.level
import play.api.libs.json.{Json, OFormat}
import types.GalaxyId

object GalaxyData {
  implicit val format: OFormat[GalaxyData] = Json.format[GalaxyData]

  def apply(stats: Stats, path: String): GalaxyData = {
    val key = path.take(2)
    val galaxy = Map(key -> stats.list(key))
    val starSystem = stats.list.filterKeys(l => l.length == 3 && l.take(key.length).contains(key))
    new GalaxyData(
      id = galaxy.head._1,
      starSystems = starSystem.toList.sortBy(_._1.substring(2)).map(s => StarSystemData(stats, s._1))
    )
  }
}

case class GalaxyData(id: GalaxyId, starSystems: List[StarSystemData])
