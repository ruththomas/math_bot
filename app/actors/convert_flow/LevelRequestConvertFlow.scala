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
      path: Option[String] = None
  )

  final val tempId = "mathbot|xa5skmltsyyqsRGgW3JA6A==" // temporary until a secure this socket
  final val anotherId = "somecrazymofo"

  override def jsonToCommand(msg: JsValue): Any = {
    Json.fromJson[LevelRequest](msg).asOpt match {
      case Some(LevelRequest(action, _, _))
          if action == "get-raw-super-cluster" => // was used for testing, is probably garbage
        GetSuperCluster("SuperCluster1")
      case Some(LevelRequest(action, _, _)) if action == "get-stats" =>
        GetStats(anotherId) // todo - tokenId needs to come from cookie
      case Some(LevelRequest(action, _, Some(path))) if action == "get-galaxy" =>
        GetGalaxyData(tempId, path)
      case Some(LevelRequest(action, _, Some(path))) if action == "get-star-system" =>
        GetStarSystemData(tempId, path)
      case Some(LevelRequest(action, _, Some(path))) if action == "get-continent" =>
        GetContinentData(tempId, path)
      case Some(LevelRequest(action, Some(function), _)) if action == "update-function" =>
        UpdateFunction(tempId, function)
      case Some(LevelRequest(action, _, _)) if action == "update-stats" =>
        RunWon(anotherId)
      case _ => ActorFailed("Bad json input")
    }
  }
  override def apply(): Flow[JsValue, Any, NotUsed] = {
    Flow[JsValue].map(jsonToCommand)
  }
}
