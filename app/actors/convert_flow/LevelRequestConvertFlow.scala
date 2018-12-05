package actors.convert_flow
import actors.LevelActor._
import actors.messages.ActorFailed
import akka.NotUsed
import akka.stream.scaladsl.Flow
import play.api.libs.json.{JsValue, Json, OFormat}
import actors.messages.level.Function

object LevelRequestConvertFlow extends SocketRequestConvertFlow {
  implicit val requestFormat: OFormat[LevelRequest] = Json.format[LevelRequest]
  final case class LevelRequest(
      action: String,
      function: Option[Function] = None,
      path: Option[String] = None,
      boolean: Option[Boolean] = None,
      id: Option[String] = None
  )

  override def jsonToCommand(msg: JsValue): Any = {
    Json.fromJson[LevelRequest](msg).asOpt match {
      case Some(LevelRequest(action, _, _, _, _))
          if action == "get-raw-super-cluster" => // was used for testing, is probably garbage
        GetSuperCluster("SuperCluster1")
      case Some(LevelRequest(action, _, _, _, _)) if action == "get-stats" =>
        GetStats() // todo - tokenId needs to come from cookie
      case Some(LevelRequest(action, _, pathOpt, _, _)) if action == "get-galaxy" =>
        GetGalaxyData(pathOpt)
      case Some(LevelRequest(action, _, pathOpt, _, _)) if action == "get-star-system" =>
        GetStarSystemData(pathOpt)
      case Some(LevelRequest(action, _, pathOpt, _, _)) if action == "get-continent" =>
        GetContinentData(pathOpt)
      case Some(LevelRequest(action, Some(function), _, _, _)) if action == "update-function" =>
        UpdateFunction(function)
      case Some(LevelRequest(action, Some(function), _, _, _)) if action == "update-function-properties" =>
        UpdateFunctionProperties(function)
      case Some(LevelRequest(action, _, _, Some(boolean), _)) if action == "advance-stats" =>
        RunWon(boolean)
      case Some(LevelRequest(action, _, _, _, _)) if action == "get-path" =>
        GetPath()
      case Some(LevelRequest(action, _, _, _, _)) if action == "update-stats" =>
        UpdateStats()
      case Some(LevelRequest(action, _, _, _, _)) if action == "init" =>
        Init()
      case Some(LevelRequest(action, _, pathOpt, _, Some(id))) if action == "watched-video" =>
        WatchedVideo(id, pathOpt)
      case Some(LevelRequest(action, _, Some(path), _, Some(id))) if action == "reset-videos" =>
        ResetVideos(id, path)
      case Some(LevelRequest(action, _, Some(path), _, _)) if action == "update-path" =>
        UpdatePath(path)
      case Some(LevelRequest(action, _, _, _, _)) if action == "unlock" =>
        Unlock()
      case Some(LevelRequest(action, Some(function), _, _, _))
          if action == "activate-function" || action == "deactivate-function" =>
        ActivateDeactivateFunction(function)
      case Some(LevelRequest(action, Some(function), _, _, _)) if action == "update-function-color" =>
        ChangeFunctionColor(function)
      case _ => ActorFailed("Bad json input")
    }
  }
  override def apply(): Flow[JsValue, Any, NotUsed] = {
    Flow[JsValue].map(jsonToCommand)
  }
}
