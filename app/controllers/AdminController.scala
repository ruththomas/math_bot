package controllers

import actors.{ActorTags, AdminActor}
import actors.convert_flow.{AdminRequestConvertFlow, AdminResponseConvertFlow}
import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.util
import akka.http.scaladsl.util.FastFuture
import akka.stream.Materializer
import com.google.inject.Inject
import configuration.AdminConfig
import com.google.inject.name.Named
import daos.{LocalCredentialDao, PlayerTokenDAO}
import email.{AdminApprovedEmail, AdminVerificationEmail}
import models.UsernameAndPassword
import play.api.Environment
import play.api.libs.json.{JsValue, Json}
import play.api.libs.streams.ActorFlow
import play.api.libs.ws._
import play.api.mvc.{Action, AnyContent, Controller, WebSocket}
import utils.SecureIdentifier
import scalatags.Text.all._

import scala.concurrent.{ExecutionContext, Future}

class AdminController @Inject()(
    implicit val mat: Materializer,
    val localCredentialDAO: LocalCredentialDao,
    @Named(ActorTags.sendGrid) val sendGrid: ActorRef,
    implicit val system: ActorSystem,
    implicit val conf: play.api.Configuration,
    playerTokenDAO: PlayerTokenDAO,
    ws: WSClient,
    environment: Environment,
    adminConfig: AdminConfig,
    implicit val ec: ExecutionContext
) extends Controller {

  import AuthController._

  private type WSMessage = JsValue

  def adminSocket: WebSocket = WebSocket.acceptOrResult[WSMessage, WSMessage] { request =>
    request.getQueryString("tokenId") match {
      case Some(tokenId) =>
        localCredentialDAO.findByAccountId(tokenId).map {
          case Some(localCredential) =>
            if (localCredential.admin.getOrElse(false)) {
              Right(
                AdminRequestConvertFlow()
                  .via(
                    ActorFlow.actorRef { out =>
                      AdminActor.props(out, playerTokenDAO, ws, environment)
                    }
                  )
                  .via(AdminResponseConvertFlow())
              )
            } else {
              Left(Unauthorized("Not an admin user."))
            }

          case None => Left(Unauthorized("No session found."))
        }
      case None => FastFuture.successful(Left(BadRequest("Missing account-id header field.")))
    }
  }

  def authenticate(): Action[AnyContent] = Action.async { implicit request =>
    val usernameAndPasswordOpt = for {
      json <- request.body.asJson
      signUp <- json.validate[UsernameAndPassword].asOpt
    } yield signUp

    usernameAndPasswordOpt match {
      case Some(usernameAndPassword) =>
        localCredentialDAO.find(usernameAndPassword.username).flatMap {
          case Some(localCredential) =>
            val hash = hashCredential(usernameAndPassword,
                                      localCredential.salt,
                                      localCredential.iterations,
                                      localCredential.blockSize,
                                      localCredential.hashSize)
            val authenticated = compareWithConstantTime(localCredential.hash, hash)
            if (authenticated) {
              val adminAuthId = SecureIdentifier(adminConfig.authIdByteWidth)
              localCredentialDAO
                .insertOrUpdate(localCredential.accountId, localCredential.copy(adminAuthId = Some(adminAuthId))) map {
                _ =>
                  sendGrid ! AdminVerificationEmail(usernameAndPassword.username, adminAuthId, adminConfig)
                  Ok(
                    s"Your request is under review, an email will be sent to ${usernameAndPassword.username} if you are approved."
                  )
              }
            } else {
              FastFuture.successful(Unauthorized("Password did not match"))
            }
          case None =>
            FastFuture.successful(
              Forbidden(
                "Credential was not found in the system. If you signed for Mathbot using a social provider please create an account with email and password in order to use the admin console."
              )
            )
        }
      case None =>
        FastFuture.successful(
          BadRequest(
            s"Json input should look like ${Json.toJson(UsernameAndPassword("your-mathbot@address", "your-mathbot-password"))}."
          )
        )
    }
  }

  def approved(): Action[AnyContent] = Action.async { implicit request =>
    request.getQueryString("authenticationId").map(SecureIdentifier(_)) match {
      case Some(authenticationId) =>
        localCredentialDAO.approveAdmin(authenticationId.toString).map {
          case Some(localCredential) =>
            val response = s"${localCredential.username} is approved as an admin user and can access the admin console."
            sendGrid ! AdminApprovedEmail(localCredential.username)
            Ok(response)
          case None => BadRequest("Authentication id is no longer valid")
        }
      case _ => FastFuture.successful(BadRequest("Invalid query parameters"))
    }
  }

  def rejected(): Action[AnyContent] = Action.async { implicit request =>
    request.getQueryString("authenticationId").map(SecureIdentifier(_)) match {
      case Some(authenticationId) =>
        localCredentialDAO.rejectAdmin(authenticationId.toString).map {
          case Some(localCredential) =>
            val response =
              s"${localCredential.username} has been rejected and will not be able to access the admin console."
            Ok(response) // don't send email to requester in case someone is fishing
          case None => BadRequest("Authentication id is no longer valid")
        }
      case _ => FastFuture.successful(BadRequest("Invalid query parameters"))
    }
  }

  def revoke(): Action[AnyContent] = Action.async { implicit request =>
    request.getQueryString("username") match {
      case Some(username) =>
        localCredentialDAO.revokeAdmin(username).map {
          case Some(_) =>
            Ok("Users admin privileges have been revoked")
          case None => BadRequest("No one with that username has admin access")
        }
      case None => FastFuture.successful(BadRequest("Query string missing username field"))
    }
  }
}
