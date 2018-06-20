package utils

import akka.http.scaladsl.model.Uri

case class CompilerConfiguration(maxProgramSteps : Int)

case class WebClientConfig(signinSuccess : Uri, signinFail : Uri)

case class GoogleApiConfig(
                            oauthTokenUri : Uri,
                            peopleMeUri : Uri,
                            revokeTokenUri : Uri,
                            authRedirectUri : Uri,
                            clientId : String,
                            clientSecret : String,
                            scopes : Seq[String]
                          )
