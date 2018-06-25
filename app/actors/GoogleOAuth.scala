package dataentry.actors

import akka.actor.Actor
import akka.pattern.pipe
import akka.http.scaladsl.model.Uri.Query
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers.Date
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.http.scaladsl.{ Http, HttpExt }
import akka.stream.ActorMaterializer
import dataentry.actors.messages._
import dataentry.actors.models.GoogleApiHelpers
import dataentry.actors.models.GoogleApiHelpers.GoogleTokens
import utils.{ AkkaSemanticLog, SecureIdentifier, SemanticLog }
import utils.GoogleApiConfig

import scala.concurrent.ExecutionContext
import scala.util.Failure

class GoogleOAuth(
    override val config : GoogleApiConfig
) extends Actor with GoogleApiHelpers {

  import HttpMethods._

  implicit override val executionContext : ExecutionContext = context.dispatcher
  implicit override val materializer : akka.stream.Materializer = ActorMaterializer()

  override val http : HttpExt = Http(context.system)

  val log = new AkkaSemanticLog(context.system, this)

  override def aroundReceive(receive : Receive, msg : Any) : Unit = {
    log.info(SemanticLog.tags.description(msg.toString))
    super.aroundReceive(receive, msg)
  }

  def retrieveTokens(code : String) = {
    for {
      response <- http.singleRequest(
        HttpRequest(
          method = POST,
          uri = config.oauthTokenUri,
          entity = FormData(
            "code" -> code,
            "client_id" -> config.clientId,
            "client_secret" -> config.clientSecret,
            "redirect_uri" -> config.authRedirectUri.toString(),
            "grant_type" -> "authorization_code"
          ).toEntity
        )
      )
      tokensOrError <- unmarshal[GoogleTokens](response, googleTokensFormat)
    } yield (tokensOrError, response.getHeader(classOf[Date]))

  }

  private def revokeTokens(sessionId : SecureIdentifier, access_token : String) : Unit = {
    for {
      response <- http.singleRequest(
        HttpRequest(
          method = GET,
          uri = config.revokeTokenUri.withQuery(Query(("token", access_token)))
        )
      )
      body <- Unmarshal(response.entity).to[String]
    } yield {
      if (response.status.isFailure()) {
        log.warning(SemanticLog.tags.outboundHttp(sessionId, config.revokeTokenUri, response.status, body))
      }
    }
  }

  override def receive : Receive = {
    case RequestTokensFromCode(sessionId, code) =>
      retrieveTokens(code) map {
        case (Left(tokens), created) =>
          TokensFromCodeSuccess(sessionId, tokens.copy(expires_on = created.optionInstant))
        case (Right((statusCode, body)), _) =>
          TokensFromCodeFailure(sessionId, config.oauthTokenUri, statusCode, body)
      } pipeTo sender() onComplete {
        // In practice this is usually a connectivity error. Revisit in production to see if there is a triage.
        case Failure(e) => sender() ! TokensFromCodeFailure(sessionId, config.oauthTokenUri, StatusCodes.InternalServerError, e.toString)
        case _ => // Success handled by the pipeTo
      }
    case RevokeTokens(sessionId, access_token) =>
      revokeTokens(sessionId, access_token)
  }
}
