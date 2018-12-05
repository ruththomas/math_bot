package actors.messages.admin
import play.api.libs.json._

case class CurrentPath(_id: String, count: Int)

object CurrentPath {

  implicit val lsR: OFormat[CurrentPath] = Json.format[CurrentPath]

}
