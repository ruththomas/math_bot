package actors.convert_flow

import actors.AdminActor.{ActiveUserCount, Last7DaysLogins, SignupsPerDay, UserCount}
import actors.messages.ActorFailed
import actors.messages.playeraccount.UserAccountSignups
import akka.NotUsed
import akka.stream.scaladsl.Flow
import play.api.libs.json.{JsValue, Json, OWrites}

object AdminResponseConvertFlow extends SocketResponseConvertFlow {

  final case class AdminResponse(
                                  status: String,
                                  userCount: Option[Long] = None,
                                  message: Option[String] = None,
                                  userAccountSignups: Option[Seq[UserAccountSignups]] = None,
                                  last7DaysLoginCount: Option[Long] = None,
                                  activeUserCount: Option[Long] = None
                                )

  implicit val adminResponseWrites: OWrites[AdminResponse] = Json.format[AdminResponse]

  final val success = "success"
  final val failed = "failed"

  override def responseToJson(msg: Any): JsValue = {
    Json.toJson[AdminResponse](msg match {
      case Last7DaysLogins(logins) => AdminResponse(success, None, None, None, Some(logins))
      case ActiveUserCount(count) => AdminResponse(success, None, None, None, None, Some(count))
      case UserCount(count) => AdminResponse(success, userCount = Some(count))
      case SignupsPerDay(signups) => AdminResponse(success, None, None, Some(signups))
      case ActorFailed(message) => AdminResponse(failed, message = Some(message))
      case _ => AdminResponse(failed)
    })
  }

  override def apply(): Flow[Any, JsValue, NotUsed] = Flow[Any].map(responseToJson)
}
