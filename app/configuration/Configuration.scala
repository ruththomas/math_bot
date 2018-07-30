package configuration

import akka.http.scaladsl.model.Uri
import akka.util.Timeout

case class CompilerConfiguration(maxProgramSteps : Int)

case class GoogleApiConfig(
                            oauthTokenUri : Uri,
                            authRedirectUri : Uri,
                            clientId : String,
                            clientSecret : String,
                            scopes : Seq[String],
                            oauthPemUri : Uri
                          )

case class ActorConfig(
                      timeout : Timeout
                      )

case class MongoConfig(
                      name : String,
                      url : String
                      )

