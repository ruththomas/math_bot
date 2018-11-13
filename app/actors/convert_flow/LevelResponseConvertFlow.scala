package actors.convert_flow
import actors.messages.ActorFailed
import actors.messages.level._
import akka.NotUsed
import akka.stream.scaladsl.Flow
import level_gen.models.CelestialSystem
import play.api.libs.json.{JsValue, Json, OWrites}

object LevelResponseConvertFlow extends SocketResponseConvertFlow {
  implicit val levelResponseWrites: OWrites[LevelResponse] = Json.format[LevelResponse]
  final case class LevelResponse(
      status: String,
      message: Option[String] = None,
      celestialSystem: Option[CelestialSystem] = None,
      stats: Option[Stats] = None,
      layerStatistic: Option[LayerStatistic] = None,
      starSystemData: Option[StarSystemData] = None,
      galaxyData: Option[GalaxyData] = None,
      builtContinent: Option[BuiltContinent] = None,
      function: Option[Function] = None,
      pathAndContinent: Option[PathAndContinent] = None,
      path: Option[String] = None
  )

  final val success = "success"
  final val failed = "failed"

  override def responseToJson(msg: Any): JsValue = {
    Json.toJson[LevelResponse](msg match {
      case celestialSystem: CelestialSystem => LevelResponse(success, celestialSystem = Some(celestialSystem))
      case stats: Stats => LevelResponse(success, stats = Some(stats))
      case layerStatistic: LayerStatistic => LevelResponse(success, layerStatistic = Some(layerStatistic))
      case starSystemData: StarSystemData => LevelResponse(success, starSystemData = Some(starSystemData))
      case galaxyData: GalaxyData => LevelResponse(success, galaxyData = Some(galaxyData))
      case builtContinent: BuiltContinent => LevelResponse(success, builtContinent = Some(builtContinent))
      case function: Function => LevelResponse(success, function = Some(function))
      case pathAndContinent: PathAndContinent => LevelResponse(success, pathAndContinent = Some(pathAndContinent))
      case path: String => LevelResponse(success, path = Some(path))
      case ActorFailed(message) => LevelResponse(failed, message = Some(message))
      case _ => LevelResponse(failed, message = Some("Malformed Json output"))
    })
  }
  override def apply(): Flow[Any, JsValue, NotUsed] = Flow[Any].map(responseToJson)
}
