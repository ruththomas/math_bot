package models

import play.api.libs.json.Json

case class GithubTokens(
                       access_token : String,
                       scope: Seq[String],
                       token_type : String
                       )

object GithubTokens {
  implicit val githubTokenFormat = Json.format[GithubTokens]
}