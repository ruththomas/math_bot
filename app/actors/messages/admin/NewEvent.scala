package actors.messages.admin
import play.api.libs.json.{Json, OFormat}

object NewEvent {

  implicit val reventFormat: OFormat[NewEvent] = Json.format[NewEvent]
}

case class NewEvent(date: Long, title: String, description: String, links: String)
