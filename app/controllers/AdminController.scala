package controllers

import actors.convert_flow.{AdminRequestConvertFlow, AdminResponseConvertFlow}
import actors.messages.admin.{Event, RawEvent}
import actors.{ActorTags, AdminActor}
import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.util.FastFuture
import akka.stream.Materializer
import com.google.inject.Inject
import com.google.inject.name.Named
import configuration.AdminConfig
import daos._
import email.{AdminApprovedEmail, AdminVerificationEmail}
import models.AdminAuth
import play.api.Environment
import play.api.libs.json.{JsSuccess, JsValue, Json}
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
    val statsDAO: StatsDAO,
    val auth0LegacyDAO: Auth0LegacyDao,
    val eventsDAO: EventsDAO,
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

  private type WSMessage = JsValue

  def adminSocket: WebSocket = WebSocket.acceptOrResult[WSMessage, WSMessage] { request =>
    request.cookies.get("player-session").map(c => SecureIdentifier(c.value)) match {
      case Some(secureIdentifier) =>
        sessionDAO.find(secureIdentifier).flatMap {
          case Some(jwtToken) =>
            playerAccountDAO.find(jwtToken.playerTokenId).map {
              case Some(vPlayerAccount) if vPlayerAccount.isAdmin =>
                Right(
                  AdminRequestConvertFlow()
                    .via(
                      ActorFlow.actorRef { out =>
                        AdminActor
                          .props(out,
                                 playerAccountDAO,
                                 playerTokenDAO,
                                 auth0LegacyDAO,
                                 sessionDAO,
                                 statsDAO,
                                 ws,
                                 environment)
                      }
                    )
                    .via(AdminResponseConvertFlow())
                )
              case _ =>
                Left(Unauthorized("No admin privileges"))
            }
          case None =>
            FastFuture.successful(Left(Unauthorized("No player token found")))
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
                    val adminAuth = AdminAuth(jwt.playerTokenId, adminAuthId.toString, generateTimestamp)
                    val verificationEmail = AdminVerificationEmail(jwt.email, adminAuthId, adminConfig)
                    adminAuthDAO.insert(adminAuth)
                    sendGrid ! verificationEmail
                    Ok("Request sent successfully, you will receive an email if you are accepted")
                }
            }
          case None =>
            FastFuture.successful(BadRequest("No requestSession found"))
        }
      case None =>
        FastFuture.successful(BadRequest("No cookie"))
    }
  }

  def acceptOrDeny(isAccepted: Boolean): Action[AnyContent] = Action.async { implicit request =>
    request.getQueryString("authenticationId").map(SecureIdentifier(_)) match {
      case Some(secureIdentifier) =>
        adminAuthDAO.find(secureIdentifier.toString).flatMap {
          case Some(adminAuth) =>
            playerAccountDAO.setAdmin(Some(adminAuth.tokenId), None, isAdmin = isAccepted).flatMap {
              case Some(playerAccount) =>
                adminAuthDAO.delete(secureIdentifier.toString).map { _ =>
                  if (isAccepted) {
                    sendGrid ! AdminApprovedEmail(playerAccount.email)
                  }
                  Ok(
                    if (isAccepted) {
                      s"${playerAccount.email} now has admin privileges."
                    } else {
                      s"${playerAccount.email} has been rejected from admin status."
                    }
                  )
                }
              case _ => FastFuture.successful(BadRequest("Unable to locate player account"))
            }
          case None => FastFuture.successful(BadRequest("Unable to locate a request with that id."))
        }
      case None => FastFuture.successful(BadRequest("Missing authenticationId query parameter."))
    }
  }

  def revokeAdmin(): Action[AnyContent] = Action.async { implicit request =>
    request.getQueryString("email") match {
      case Some(email) =>
        playerAccountDAO.setAdmin(None, Some(email), isAdmin = false).map { _ =>
          Ok(s"Admin privileges have been revoked for $email.")
        }
      case None => FastFuture.successful(BadRequest("Missing email query parameter"))
    }
  }

  // todo: hook up to ws or ensure player is admin in request

  def getEvents: Action[AnyContent] = Action.async { implicit request =>
    eventsDAO.getEvents.map { p: Seq[Event] =>
      Ok(Json.toJson(p))

    }
  }

  // fixme: reference by _id

  def removeEvent(id: String): Action[AnyContent] = Action.async { implicit request =>
    eventsDAO.remove(id).flatMap { evt =>
      FastFuture.successful(Ok(Json.toJson("result" -> evt.toString)))
    }

  }

  def insertEvent: Action[WSMessage] = Action.async(parse.json) { implicit request =>
    val json: JsValue = request.body

    val m = Json.fromJson[RawEvent](json)

    m match {

      case JsSuccess(value, _) =>
        val newEvent = Event(value.date, value.title, value.description, value.links)

        eventsDAO.insert(newEvent).flatMap { _ =>
          FastFuture.successful(Ok(Json.toJson(newEvent)))

        }

      case _ => FastFuture.successful(BadRequest("invalid request"))
    }

  }

  // fixme: reference via _id

  def replaceEvent: Action[WSMessage] = Action.async(parse.json) { implicit request =>
    val json: JsValue = request.body

    val m = Json.fromJson[RawEvent](json)

    m match {

      case JsSuccess(value, _) =>
        val e = Event(value.date, value.title, value.description, value.links)

        eventsDAO.replace(e).flatMap { result =>
          FastFuture.successful(Ok(Json.toJson("result" -> result)))
        }
      case _ => FastFuture.successful(BadRequest("Invalid Request"))

    }
  }

}
