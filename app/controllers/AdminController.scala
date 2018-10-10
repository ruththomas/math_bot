package controllers

import actors.{ActorTags, AdminActor}
import actors.convert_flow.{AdminRequestConvertFlow, AdminResponseConvertFlow}
import actors.messages.auth.SessionAuthorized
import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.util
import akka.http.scaladsl.util.FastFuture
import akka.stream.Materializer
import com.google.inject.Inject
import configuration.{AdminConfig, LocalAuthConfig}
import com.google.inject.name.Named
import daos._
import email.AdminVerificationEmail
import models.{AdminAuth, PlayerAccount}
import play.api.Environment
import play.api.libs.json.{JsValue, Json}
import play.api.libs.streams.ActorFlow
import play.api.libs.ws._
import play.api.mvc._
import utils.SecureIdentifier

import scala.concurrent.ExecutionContext

class AdminController @Inject()(
    implicit val mat: Materializer,
    val localCredentialDAO: LocalCredentialDao,
    val playerAccountDAO: PlayerAccountDAO,
    val adminAuthDAO: AdminAuthDAO,
    val sessionDAO: SessionDAO,
    @Named(ActorTags.sendGrid) val sendGrid: ActorRef,
    implicit val system: ActorSystem,
    implicit val conf: play.api.Configuration,
    playerTokenDAO: PlayerTokenDAO,
    ws: WSClient,
    environment: Environment,
    adminConfig: AdminConfig,
    implicit val ec: ExecutionContext
) extends Controller {
  import actors.VideoHintActor.{calculateRemainingTime, generateTimestamp}
  import actors.messages.auth.AuthFormatters._

  private type WSMessage = JsValue

  def adminSocket: WebSocket = WebSocket.acceptOrResult[WSMessage, WSMessage] { request =>
    request.cookies.get("player-session").map(c => Json.parse(c.value).as[SessionAuthorized]) match {
      case Some(sessionAuthorized) =>
        playerAccountDAO.find(sessionAuthorized.sub).map {
          case Some(vPlayerAccount) if vPlayerAccount.isAdmin =>
            Right(
              AdminRequestConvertFlow()
                .via(
                  ActorFlow.actorRef { out =>
                    AdminActor.props(out, playerTokenDAO, ws, environment)
                  }
                )
                .via(AdminResponseConvertFlow())
            )
          case _ =>
            Left(Unauthorized("No admin privileges"))
        }
      case _ =>
        FastFuture.successful(Left(Unauthorized("Cookie missing")))
    }
  }

  private val requestExpiration: Int = 2.592e+9.toInt // 30 days

  def requestAdmin(): Action[AnyContent] = Action.async { implicit request =>
    request.cookies.get("player-session").map(_.value) match {
      case Some(sessionId) =>
        sessionDAO.find(SecureIdentifier(sessionId)).flatMap {
          case Some(jwt) =>
            adminAuthDAO.findByTokenId(jwt.playerTokenId).flatMap {
              case Some(adminAuth) if calculateRemainingTime(adminAuth.createdAt, requestExpiration) > 0 =>
                FastFuture.successful(Ok("There is already a request submitted, you will be emailed if accepted"))
              case _ =>
                playerAccountDAO.find(jwt.playerTokenId).map {
                  case Some(playerAccount) if playerAccount.isAdmin =>
                    Ok("Already is an admin")
                  case _ =>
                    val adminAuthId = SecureIdentifier(adminConfig.authIdByteWidth)
                    val adminAuth = AdminAuth(jwt.sub, adminAuthId.toString, generateTimestamp)
                    val verificationEmail = AdminVerificationEmail(jwt.email, adminAuthId, adminConfig)
                    adminAuthDAO.insert(adminAuth)
                    sendGrid ! verificationEmail
                    Ok("Request sent successfully, you will receive an email if you are accepted")
                }
            }
          case None =>
            FastFuture.successful(BadRequest("No session found"))
        }
      case None =>
        FastFuture.successful(BadRequest("No cookie"))
    }
  }

//  def acceptAdmin(): Action[AnyContent] = Action.async { implicit request =>
//    }
}
