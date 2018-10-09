package controllers

import actors.{ActorTags, AdminActor}
import actors.convert_flow.{AdminRequestConvertFlow, AdminResponseConvertFlow}
import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.util
import akka.http.scaladsl.util.FastFuture
import akka.stream.Materializer
import com.google.inject.Inject
import configuration.{AdminConfig, LocalAuthConfig}
import com.google.inject.name.Named
import daos.{LocalCredentialDao, PlayerAccountDAO, PlayerTokenDAO}
import models.PlayerAccount
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
    @Named(ActorTags.sendGrid) val sendGrid: ActorRef,
    implicit val system: ActorSystem,
    implicit val conf: play.api.Configuration,
    playerTokenDAO: PlayerTokenDAO,
    ws: WSClient,
    environment: Environment,
    adminConfig: AdminConfig,
    mathbotConfig: LocalAuthConfig,
    implicit val ec: ExecutionContext
) extends Controller {

  private type WSMessage = JsValue

  def adminSocket: WebSocket = WebSocket.acceptOrResult[WSMessage, WSMessage] { request =>
    request.cookies.get("player-account").map(c => Json.parse(c.value).as[PlayerAccount]) match {
      case Some(playerAccount) =>
        playerAccountDAO.find(playerAccount.tokenId).map {
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
}
