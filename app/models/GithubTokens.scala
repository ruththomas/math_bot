package models

case class GithubTokens(
                       access_token : String,
                       scope: Seq[String],
                       token_type : String,
                       id_token : JwtToken
                       )

object GithubTokens {
  import play.api.libs.json._

  implicit val jwtTokenFormatter : OFormat[JwtToken] = Json.format[JwtToken]

  implicit val githubTokenFormat : OFormat[GithubTokens] = Json.format[GithubTokens]
}