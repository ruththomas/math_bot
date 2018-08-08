package actors

import actors.GoogleApiHelpers.GoogleTokens
import actors.messages.auth.{ GithubTokensFromCodeSuccess, GoogleTokensFromCodeSuccess, RequestTokensFromCode, TokensFromCodeFailure }
import akka.actor.Actor
import akka.pattern.pipe
import akka.http.scaladsl.{ Http, HttpExt }
import akka.http.scaladsl.model.HttpMethods.POST
import akka.http.scaladsl.model.headers
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import com.google.inject.{ Inject, Singleton }
import configuration.{ GithubApiConfig, GoogleApiConfig }
import loggers.{ AkkaSemanticLog, SemanticLog }
import models.GithubTokens
import utils.{ AkkaToPlayMarshaller, JwtTokenParser, SecureIdentifier }

import scala.concurrent.{ ExecutionContext, Future }
import scala.util.Failure

@Singleton
class GithubOAuth @Inject()(
                             val config : GithubApiConfig
                           ) extends Actor  {

  import HttpMethods._
  import GithubTokens._

  implicit val executionContext : ExecutionContext = context.dispatcher
  implicit val materializer : akka.stream.Materializer = ActorMaterializer()

  val http : HttpExt = Http(context.system)

  val log = new AkkaSemanticLog(context.system, this)

  override def aroundReceive(receive : Receive, msg : Any) : Unit = {
    log.info(Seq(SemanticLog.tags.description(msg.toString)))
    super.aroundReceive(receive, msg)
  }

  def retrieveTokens(sessionId : SecureIdentifier, code : String) : Future[Either[GithubTokens, String]] = {
    for {
      response <- http.singleRequest(
        HttpRequest(
          method = POST,
          uri = config.authTokenUrl,
          headers = List(headers.Accept(MediaRange(MediaTypes.`application/json`))),
          entity = FormData(
            "code" -> code,
            "client_id" -> config.clientId,
            "client_secret" -> config.clientSecret,
            "redirect_uri" -> config.authRedirectUri.toString(),
            "state" -> sessionId.toString
          ).toEntity
        )
      )
      tokensOrError <- AkkaToPlayMarshaller.unmarshalToPlayJson(response)
    } yield {
      tokensOrError match {
        case Left(Some(tokens)) =>
            Left(tokens.as[GithubTokens])
        case Left(None) => Right("Unable to verity jwt")
        case Right(reason) => Right(s"Status: ${reason._1} reason: ${reason._2}")
      }
    }

  }

  override def receive : Receive = {
    case RequestTokensFromCode(sessionId, code) =>
      retrieveTokens(sessionId, code) map {
        case Left(tokens) =>
          GithubTokensFromCodeSuccess(sessionId, tokens)
        case Right(reason) =>
          TokensFromCodeFailure(sessionId, config.oauthUrl, reason)
      } pipeTo sender() onComplete {
        // In practice this is usually a connectivity error. Revisit in production to see if there is a triage.
        case Failure(e) => sender() ! TokensFromCodeFailure(sessionId, config.oauthUrl, e.toString)
        case _ => // Success handled by the pipeTo
      }
  }
}