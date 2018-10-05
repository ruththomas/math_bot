package controllers

import actors.AdminActor
import actors.convert_flow.{AdminRequestConvertFlow, AdminResponseConvertFlow}
import akka.actor.ActorSystem
import akka.http.scaladsl.util.FastFuture
import akka.stream.Materializer
import com.google.inject.Inject
import daos.{LocalCredentialDao, PlayerTokenDAO}
import models.UsernameAndPassword
import play.api.Environment
import play.api.libs.json.{JsValue, Json}
import play.api.libs.streams.ActorFlow
import play.api.libs.ws._
import play.api.mvc.{Action, AnyContent, Controller, WebSocket}
import scala.concurrent.{ExecutionContext, Future}

class AdminController @Inject()(implicit val system: ActorSystem,
                                implicit val mat: Materializer,
                                val localCredential: LocalCredentialDao,
                                playerTokenDAO: PlayerTokenDAO,
                                ws: WSClient,
                                environment: Environment,
                                implicit val ec: ExecutionContext)
    extends Controller {

  import AuthController._

  private type WSMessage = JsValue

  def adminSocket: WebSocket = WebSocket.accept[WSMessage, WSMessage] { _ =>
    AdminRequestConvertFlow()
      .via(
        ActorFlow.actorRef { out =>
          AdminActor.props(out, playerTokenDAO, ws, environment)
        }
      )
      .via(AdminResponseConvertFlow())
  }

  def authenticate(): Action[AnyContent] = Action.async { implicit request =>
    (for {
      json <- request.body.asJson
      signUp <- json.validate[UsernameAndPassword].asOpt
    } yield signUp) match {
      case Some(credential) =>
        localCredential.find(credential.username).map {
          case Some(lc) =>
            val hash = hashCredential(credential, lc.salt, lc.iterations, lc.blockSize, lc.hashSize)
            val authenticated = compareWithConstantTime(lc.hash, hash)
            if (authenticated) {
              ???
            } else {
              Unauthorized("Password did not match")
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

    FastFuture.successful(Ok)
  }
}
