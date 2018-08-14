package controllers

import actors.ActorTags
import actors.messages.auth._
import akka.actor.ActorRef
import akka.http.scaladsl.model.Uri.Query
import akka.http.scaladsl.util.FastFuture
import akka.pattern.ask
import akka.util.Timeout
import com.google.inject.name.Named
import configuration.{ActorConfig, GithubApiConfig, GoogleApiConfig}
import daos.{SessionCache, SessionDAO}
import javax.inject.Inject
import loggers.SemanticLog
import models.JwtToken
import play.api.libs.json.{JsString, Json}
import play.api.mvc.{Action, AnyContent, Controller}
import utils.{JwtTokenParser, SecureIdentifier}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

class AuthController @Inject()(
    val sessionDAO: SessionDAO,
    val sessionCache: SessionCache,
    @Named(ActorTags.googleOAuth) val googleOauth: ActorRef,
    @Named(ActorTags.githubOAuth) val githubOAuth: ActorRef,
    val jwtParser: JwtTokenParser,
    val googleConfig: GoogleApiConfig,
    val githubConfig: GithubApiConfig,
    val actorConfig: ActorConfig,
    val logger: SemanticLog
)(implicit ec: ExecutionContext)
    extends Controller {

  private def generateNeedsAuthorization(sessionId: SecureIdentifier) = {
    NeedsAuthorization(
      sessionId,
      Seq(
        AuthUrl(
          "google",
          googleConfig.oauthUrl
            .withQuery(
              Query(
                "client_id" -> googleConfig.clientId,
                "scope" -> googleConfig.scopes.mkString(" "),
                "redirect_uri" -> googleConfig.authRedirectUri.toString(),
                "state" -> sessionId.toString,
                "response_type" -> "code"
              )
            )
            .toString
        ),
        AuthUrl(
          "github",
          githubConfig.oauthUrl
            .withQuery(
              Query(
                "client_id" -> githubConfig.clientId,
                "scope" -> githubConfig.scopes.mkString(":"),
                "redirect_uri" -> githubConfig.authRedirectUri.toString(),
                "state" -> sessionId.toString
              )
            )
            .toString
        )
      )
    )
  }

  private def generateSessionAuthorized(sessionId: SecureIdentifier, idToken: JwtToken) =
    SessionAuthorized(sessionId,
                      idToken.name,
                      idToken.picture,
                      s"${idToken.getIssuerShortName}|${idToken.sub}",
                      idToken.email)

  private implicit val timeout: Timeout = actorConfig.timeout

  import actors.messages.auth.AuthFormatters._

  def requestSession(): Action[AnyContent] = Action.async { implicit request =>
    val sid = SecureIdentifier(32)
    sessionCache.put(sid, None)

    Future { Ok(Json.toJson(generateNeedsAuthorization(sid))) }
  }

  def resumeSession(): Action[AnyContent] = Action.async { implicit request =>
    request.body.asJson.flatMap(_.asOpt[ResumeSession]) match {
      case Some(ResumeSession(sessionId, _)) =>
        for {
          token <- sessionDAO.find(sessionId)
        } yield
          token match {
            case Some(idToken) =>
              Ok(
                Json
                  .toJson(generateSessionAuthorized(sessionId, idToken))
              )
            case _ => Ok(Json.toJson(generateNeedsAuthorization(sessionId)))
          }
      case _ =>
        Future(BadRequest(JsString("Invalid or missing resume session json")))
    }
  }

  def authorizeGoogle(): Action[AnyContent] = Action.async { implicit request =>
    (for {
      code <- request.getQueryString("code")
      state <- request.getQueryString("state")
    } yield {
      val sessionId = SecureIdentifier(state)
      for {
        sessionValid <- FastFuture {
          Try {
            sessionCache.get(sessionId).isDefined
          }
        }
        tokenResult <- sessionValid match {
          case true => googleOauth ? RequestTokensFromCode(sessionId, code)
          case false => FastFuture.successful(SessionNotAuthorized(sessionId, "Invalid sessionId in state/guard code"))
        }
        storedResult <- tokenResult match {
          case GoogleTokensFromCodeSuccess(aSessionId, tokens) =>
            sessionDAO.insertOrUpdate(aSessionId, tokens.id_token) map { _ =>
              Left[JwtToken, String](tokens.id_token)
            }
          case TokensFromCodeFailure(_, _, reason) =>
            FastFuture.successful(
              Right[JwtToken, String](s"Could not verify authorization because of '$reason'")
            )
          case SessionNotAuthorized(_, reason, _) =>
            FastFuture.successful {
              Right[JwtToken, String](s"Unable to authorize session $sessionId because of $reason")
            }
          case msg: Any =>
            logger.error(SemanticLog.tags.message(msg))
            FastFuture.successful(
              Right[JwtToken, String](s"Unexpected message from google oauth actor")
            )
        }
      } yield
        storedResult match {
          case Left(idToken) =>
            Ok(
              Json
                .toJson(generateSessionAuthorized(sessionId, idToken))
            )
          case Right(reason) => Unauthorized(JsString(reason))
        }
    }).getOrElse(FastFuture.successful(BadRequest("One or more query parameters are missing")))
  }

  def authorizeGithub(): Action[AnyContent] = Action.async { implicit request =>
    (for {
      code <- request.getQueryString("code")
      state <- request.getQueryString("state")
    } yield {
      val sessionId = SecureIdentifier(state)
      for {
        sessionValid <- FastFuture {
          Try {
            sessionCache.get(sessionId).isDefined
          }
        }
        tokenResult <- sessionValid match {
          case true => githubOAuth ? RequestTokensFromCode(sessionId, code)
          case false => FastFuture.successful(SessionNotAuthorized(sessionId, "Invalid sessionId in state/guard code"))
        }
        storedResult <- tokenResult match {
          case GithubTokensFromCodeSuccess(_, tokens) =>
            FastFuture.successful(
              Left[JwtToken, String](tokens.id_token)
            )
          case TokensFromCodeFailure(_, _, reason) =>
            FastFuture.successful(
              Right[JwtToken, String](s"Could not verify authorization because of '$reason'")
            )
          case SessionNotAuthorized(_, reason, _) =>
            FastFuture.successful {
              Right[JwtToken, String](s"Unable to authorize session $sessionId because of $reason")
            }
          case msg: Any =>
            logger.error(SemanticLog.tags.message(msg))
            FastFuture.successful(
              Right[JwtToken, String](s"Unexpected message from github oauth actor")
            )
        }
      } yield
        storedResult match {
          case Left(idToken) =>
            Ok(
              Json
                .toJson(generateSessionAuthorized(sessionId, idToken))
            )
          case Right(reason) => Unauthorized(JsString(reason.toString))
        }
    }).getOrElse(FastFuture.successful(BadRequest("One or more query parameters are missing")))
  }

}
