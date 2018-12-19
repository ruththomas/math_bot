package actors.convert_flow

import actors.AdminActor._
import actors.messages.ActorFailed
import actors.messages.admin.{AdminEvent, NewEvent}
import akka.NotUsed
import akka.stream.scaladsl.Flow
import play.api.libs.json.{JsValue, Json, OFormat}

object AdminRequestConvertFlow extends SocketRequestConvertFlow {
  final case class AdminRequest(
      action: String,
      level: Option[String],
      event: Option[AdminEvent],
      newEvent: Option[NewEvent]
  )
  implicit val adminRequestConvertFlow: OFormat[AdminRequest] = Json.format[AdminRequest]

  def jsonToCommand(msg: JsValue): Any = {
    Json.fromJson[AdminRequest](msg).asOpt match {
      case Some(AdminRequest(action, _, _, _)) if action == "user-count" => GetUserCount()
      case Some(AdminRequest(action, _, _, _)) if action == "active-user-count" => GetActiveUserCount()
      case Some(AdminRequest(action, _, _, _)) if action == "signups" => GetSignupsPerDay()
      case Some(AdminRequest(action, _, _, _)) if action == "logins-last-week" => GetLoginsLast7Days()
      case Some(AdminRequest(action, id, _, _)) if action == "level-stats" => GetLevelStats(id)
      case Some(AdminRequest(action, _, _, _)) if action == "current-path" => GetCurrentPath()
      case Some(AdminRequest(action, _, _, _)) if action == "max-level" => GetMaxLevel()
      case Some(AdminRequest(action, _, _, _)) if action == "events-get" => GetEvents()
      case Some(AdminRequest(action, _, event, _)) if action == "events-put" => PutEvent(event)
      case Some(AdminRequest(action, _, _, event)) if action == "events-post" => PostEvent(event)
      case Some(AdminRequest(action, _, event, _)) if action == "events-delete" => DeleteEvent(event)
      case _ => ActorFailed("Bad json input")
    }
  }

  override def apply(): Flow[JsValue, Any, NotUsed] = {
    Flow[JsValue].map(jsonToCommand)
  }
}
