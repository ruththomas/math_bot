package actors.messages.admin
import play.api.libs.json.{Json, OFormat}

object RawEvent {

  implicit val reventFormat: OFormat[RawEvent] = Json.format[RawEvent]
}

case class RawEvent(date: Long, title: String, description: String, links: String)
