package actors.convert_flow
import actors.messages.ActorFailed
import actors.messages.level.Stats
import akka.NotUsed
import akka.stream.scaladsl.Flow
import level_gen.models.{Continent, SuperCluster}
import play.api.libs.json.{JsValue, Json, OWrites}

object LevelResponseConvertFlow extends SocketResponseConvertFlow {
  implicit val levelResponseWrites: OWrites[LevelResponse] = Json.format[LevelResponse]
  final case class LevelResponse(
      status: String,
      message: Option[String] = None,
      superCluster: Option[SuperCluster] = None,
      stats: Option[Stats] = None,
      continent: Option[Continent] = None
  )

  final val success = "success"
  final val failed = "failed"

  override def responseToJson(msg: Any): JsValue = {
    Json.toJson[LevelResponse](msg match {
      case superCluster: SuperCluster => LevelResponse(success, superCluster = Some(superCluster))
      case stats: Stats => LevelResponse(success, stats = Some(stats))
      case continent: Continent => LevelResponse(success, continent = Some(continent))
      case ActorFailed(message) => LevelResponse(failed, message = Some(message))
      case _ => LevelResponse(failed, message = Some("Malformed Json output"))
    })
  }
  override def apply(): Flow[Any, JsValue, NotUsed] = Flow[Any].map(responseToJson)
}
