package models

case class JwtToken(iss: String,
                    sub: String,
                    email: String,
                    email_verified: Boolean,
                    name: String,
                    picture: String,
                    given_name: String,
                    family_name: String) {
  def getIssuerShortName: String = {
    iss match {
      case "https://accounts.google.com" => "google-oauth2"
    }
  }
}
