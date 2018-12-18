package actors.messages.admin
import java.text.SimpleDateFormat
import java.util.Date

import play.api.libs.json._

object Event {

  val titleLabel = "title"
  implicit val eventFormat: OFormat[Event] = Json.format[Event]

  private val format_1 = "yyyy-MM-dd HH:mm:ss"
  private val stf = new SimpleDateFormat(format_1)

  def apply(_date: Long, title: String, description: String, links: String): Event = {

    Event(new Date(_date), title, description, links)

  }

}

case class Event(date: Date, title: String, description: String, links: String)
