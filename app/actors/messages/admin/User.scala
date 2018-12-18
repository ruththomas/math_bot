package actors.messages.admin
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import play.api.libs.functional.syntax._
import play.api.libs.json._

object User {

  val dateFormat = "yyyy-mm-dd"

  val jodaDateReads = Reads[DateTime](
    js => js.validate[String].map[DateTime](dtString => DateTime.parse(dtString, DateTimeFormat.forPattern(dateFormat)))
  )

  val jodaDateWrites: Writes[DateTime] = new Writes[DateTime] {
    def writes(d: DateTime): JsValue = JsString(d.toString())
  }

  val userReads: Reads[User] = (
    (JsPath \ "name").read[String] and
    (JsPath \ "created").read[DateTime](jodaDateReads)
  )(User.apply _)

  val userWrites: Writes[User] = (
    (JsPath \ "name").write[String] and
    (JsPath \ "created").write[DateTime](jodaDateWrites)
  )(unlift(User.unapply))

  implicit val userFormat: Format[User] = Format(userReads, userWrites)
}

case class User(name: String, created: DateTime)
