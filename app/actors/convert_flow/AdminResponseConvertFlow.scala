package actors.convert_flow

import actors.AdminActor._
import actors.messages.ActorFailed
import actors.messages.admin.{AdminEvent, CurrentPath, LevelStats}
import actors.messages.playeraccount.{MaxLevel, UserAccountSignups}
import akka.NotUsed
import akka.stream.scaladsl.Flow
import play.api.libs.json.{JsValue, Json, OWrites}

object AdminResponseConvertFlow extends SocketResponseConvertFlow {

  final case class AdminResponse(
      status: String,
      userCount: Option[Long] = None,
      message: Option[String] = None,
      userAccountSignups: Option[Seq[UserAccountSignups]] = None,
      lastXDaysLoginCount: Option[Long] = None,
      currentPath: Option[Seq[CurrentPath]] = None,
      levelStats: Option[Seq[LevelStats]] = None,
      maxLevel: Option[Seq[MaxLevel]] = None,
      events: Option[Seq[AdminEvent]] = None,
      event: Option[AdminEvent] = None
  )

  implicit val adminResponseWrites: OWrites[AdminResponse] = Json.format[AdminResponse]

  final val success = "success"
  final val failed = "failed"

  override def responseToJson(msg: Any): JsValue = {
    Json.toJson[AdminResponse](msg match {
      case LastXDaysLogins(logins) =>
        AdminResponse(success, None, None, None, Some(logins))
      case UserCount(count) =>
        AdminResponse(success, userCount = Some(count))
      case SignupsPerDay(signups) =>
        AdminResponse(success, None, None, Some(signups))
      case UserMaxLevel(maxLevel) =>
        AdminResponse(success, None, None, None, None, None, None, Some(maxLevel))
      case LevelStatsResult(levelStats) =>
        AdminResponse(success, None, None, None, None, None, Some(levelStats))
      case Events(events) =>
        AdminResponse(success, None, None, None, None, None, None, None, Some(events))
      case Event(event) =>
        AdminResponse(success, None, None, None, None, None, None, None, None, Some(event))
      case DeleteEventResult(message) =>
        AdminResponse(success, None, Some(message))
      case PutEventResult(message) =>
        AdminResponse(success, None, Some(message))
      case PostEventResult(message) =>
        AdminResponse(success, None, Some(message))
      case ActorFailed(message) =>
        AdminResponse(failed, message = Some(message))
      case _ => AdminResponse(failed)
    })
  }

  override def apply(): Flow[Any, JsValue, NotUsed] = Flow[Any].map(responseToJson)
}
