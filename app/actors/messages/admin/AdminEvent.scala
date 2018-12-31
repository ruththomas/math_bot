package actors.messages.admin
import java.util.{Date, UUID}

import play.api.libs.json._

object AdminEvent {

  val titleLabel = "title"
  val dateLabel = "date"
  val descriptionLabel = "description"
  val linksLabel = "links"
  // val _idLabel = "_id"
  val idLabel = "id"
  implicit val eventFormat: OFormat[AdminEvent] = Json.format[AdminEvent]

  def apply(_date: Long,
            title: String,
            description: String,
            links: String,
            id: String = UUID.randomUUID().toString): AdminEvent = {

    AdminEvent(new Date(_date), title, description, links, id)

  }

}

case class AdminEvent(date: Date, title: String, description: String, links: String, id: String)
