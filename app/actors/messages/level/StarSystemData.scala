package actors.messages.level
import play.api.libs.json.{Json, OFormat}

object StarSystemData {
  implicit val format: OFormat[StarSystemData] = Json.format[StarSystemData]

  def apply(stats: Stats, path: String): StarSystemData = {
    val key = path.take(3)
    val starSystem = Map(key -> stats.list(key))
    val p = stats.list.filterKeys(l => l.length == 4 && l.take(key.length).contains(key))
    val continents = stats.list.filterKeys(l => l.length > 4 && l.take(key.length).contains(key))
    val planets = p.toList.sortBy(_._1.last.toInt).map { p =>
      PlanetData(
        id = p._1,
        stats = p._2,
        continents.filterKeys(_.take(4).contains(p._1)).toList.sortBy(_._1.substring(4).toInt).map { c =>
          ContinentData(c._1, c._2)
        }
      )
    }

    new StarSystemData(
      id = starSystem.head._1,
      stats = starSystem.head._2,
      planets = Some(planets)
    )
  }
}

case class StarSystemData(
    id: String,
    stats: LayerStatistic,
    planets: Option[List[PlanetData]]
)
