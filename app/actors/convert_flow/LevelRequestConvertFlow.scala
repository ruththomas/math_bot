package actors.convert_flow
import actors.LevelActor._
import actors.messages.ActorFailed
import akka.NotUsed
import akka.stream.scaladsl.Flow
import play.api.libs.json.{JsValue, Json, OFormat}

object LevelRequestConvertFlow extends SocketRequestConvertFlow {
  implicit val levelUpdateRequestFormat: OFormat[LevelUpdateRequest] = Json.format[LevelUpdateRequest]
  implicit val adminRequestFormat: OFormat[LevelRequest] = Json.format[LevelRequest]
  final case class LevelUpdateRequest(starSystem: Int, planet: Int, continent: Int)
  final case class LevelRequest(
      action: String,
      level: Option[LevelUpdateRequest] = None
  )
  override def jsonToCommand(msg: JsValue): Any = {
    Json.fromJson[LevelRequest](msg).asOpt match {
      case Some(LevelRequest(action, None))
          if action == "get-raw-super-cluster" => // was used for testing, is probably garbage
        GetSuperCluster("SuperCluster1")
      case Some(LevelRequest(action, None)) if action == "get-stats" =>
        GetStats("mathbot|xa5skmltsyyqsRGgW3JA6A==") // todo - tokenId needs to come from cookie
//        GetStats("somecrazymofo")
      case Some(LevelRequest(action, None)) if action == "get-super-cluster" =>
        GetSuperClusterData("somecrazymofo")
      case Some(LevelRequest(action, None)) if action == "get-galaxy" =>
        GetGalaxyData("somecrazymofo")
      case Some(LevelRequest(action, None)) if action == "get-star-system" =>
        GetStarSystemData("somecrazymofo", "000")
      case Some(LevelRequest(action, None)) if action == "get-planet" =>
        GetPlanetData("somecrazymofo")
      case Some(LevelRequest(action, None)) if action == "get-continent" =>
        GetContinentData("mathbot|xa5skmltsyyqsRGgW3JA6A==", "00000")
      case Some(LevelRequest(action, Some(LevelUpdateRequest(starSystem, planet, continent))))
          if action == "change-level" =>
        ChangeLevel("somecrazymofo", starSystem, planet, continent)
      case _ => ActorFailed("Bad json input")
    }
  }
  override def apply(): Flow[JsValue, Any, NotUsed] = {
    Flow[JsValue].map(jsonToCommand)
  }
}
