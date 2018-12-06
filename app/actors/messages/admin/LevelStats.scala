package actors.messages.admin
import play.api.libs.json._

case class LevelStats(_id: String,
                      timesPlayed: Int,
                      timesPlayedAvg: Double,
                      timesPlayedMax: Int,
                      wins: Int,
                      winsAvg: Double,
                      winsMax: Int,
                      id: String)

object LevelStats {

  implicit val lsL: OFormat[LevelStats] = Json.format[LevelStats]

}
