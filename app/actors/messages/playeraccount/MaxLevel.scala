package actors.messages.playeraccount

import play.api.libs.json._

case class MaxLevelId(maxLevel: String)

case class MaxLevel(_id: String, count: Int)

object MaxLevel {

  implicit val accountsPerDay: OFormat[MaxLevel] = Json.format[MaxLevel]
}
