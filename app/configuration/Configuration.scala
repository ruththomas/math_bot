package configuration

import akka.http.scaladsl.model.Uri

case class CompilerConfiguration(maxProgramSteps : Int)

case class WebClientConfig(signinSuccess : Uri, signinFail : Uri)

case class GoogleApiConfig(
                            oauthTokenUri : Uri,
                            authRedirectUri : Uri,
                            clientId : String,
                            clientSecret : String,
                            scopes : Seq[String]
                          )

