package actors.messages.playeraccount

import play.api.libs.json._


case class SignupDate(month: Int, day: Int, year: Int)

case class UserAccountSignups(_id: SignupDate, signups: Int)

object UserAccountSignups {


  implicit val dateRequest: OFormat[SignupDate] = Json.format[SignupDate]
  implicit val accountsPerDay: OFormat[UserAccountSignups] = Json.format[UserAccountSignups]
}
