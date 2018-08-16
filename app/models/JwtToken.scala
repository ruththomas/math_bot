package models

case class JwtToken(iss: String,
                    sub: String,
                    email: String,
                    name: String,
                    picture: String) {
  def getIssuerShortName: String = {
    iss match {
      case "https://accounts.google.com" => "google-oauth2"
      case "https://github.com" => "github-oauth2" // TODO: Find the value that auth0 uses.
    }
  }
}
