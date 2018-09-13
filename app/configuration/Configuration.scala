package configuration

import akka.http.scaladsl.model.Uri
import akka.util.Timeout

case class CompilerConfiguration(maxProgramSteps: Int, maxEmptyLoopCount: Int)

case class GoogleApiConfig(
    oauthUrl: Uri,
    authRedirectUri: Uri,
    authTokenUrl: Uri,
    clientId: String,
    clientSecret: String,
    scopes: Seq[String],
    oauthPemUri: Uri
)

case class ActorConfig(
    timeout: Timeout
)

case class MongoConfig(
    name: String,
    url: String
)

case class GithubApiConfig(
    oauthUrl: Uri,
    authRedirectUri: Uri,
    authTokenUrl: Uri,
    publicEmailsUrl: Uri,
    userUrl: Uri,
    clientId: String,
    clientSecret: String,
    scopes: Seq[String],
    issuer: String
)

case class LocalAuthConfig(
    signupUrl: Uri,
    authUrl: Uri,
    accountIdByteWidth: Int,
    saltByteWidth: Int,
    sessionIdByteWidth: Int,
    scryptIterationExponent: Int,
    scryptBlockSize: Int,
    hashByteSize: Int,
    recoveryIdByteWidth : Int,
    recoveryEmailUrl : Uri
)
