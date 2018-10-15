package controllers

import java.net.URLDecoder

import actors.{ActorTags, LevelActor, LevelGenerationActor}
import actors.LevelGenerationActor.{GetLevel, GetStep}
import actors.convert_flow.{LevelRequestConvertFlow, LevelResponseConvertFlow}
import actors.messages.{ActorFailed, PreparedStepData, RawLevelData}
import akka.actor.{ActorRef, ActorSystem}
import akka.pattern.ask
import akka.stream.Materializer
import akka.util.Timeout
import com.google.inject.Inject
import com.google.inject.name.Named
import configuration.AdminConfig
import daos._
import loggers.MathBotLogger
import play.api.Environment
import play.api.libs.json.{JsValue, Json}
import play.api.libs.streams.ActorFlow
import play.api.libs.ws.WSClient
import play.api.mvc.{Action, Controller, WebSocket}
import types.{LevelName, TokenId}

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration._

class LevelController @Inject()(
    implicit val mat: Materializer,
    val localCredentialDAO: LocalCredentialDao,
    val playerAccountDAO: PlayerAccountDAO,
    val adminAuthDAO: AdminAuthDAO,
    val sessionDAO: SessionDAO,
    val playerTokenDAO: PlayerTokenDAO,
    val statsDAO: StatsDAO,
    val lambdasDAO: LambdasDAO,
    @Named(ActorTags.sendGrid) val sendGrid: ActorRef,
    implicit val system: ActorSystem,
    implicit val conf: play.api.Configuration,
    ws: WSClient,
    adminConfig: AdminConfig,
    implicit val ec: ExecutionContext,
    logger: MathBotLogger,
    environment: Environment
) extends Controller {

  implicit val timeout: Timeout = 5000.minutes
  type WSMessage = JsValue

  def levelSocket: WebSocket = WebSocket.acceptOrResult[WSMessage, WSMessage] { implicit request =>
    Future { // todo - secure after finished
      Right(
        LevelRequestConvertFlow()
          .via(
            ActorFlow.actorRef { out =>
              LevelActor.props(out, statsDAO, lambdasDAO, playerTokenDAO, ws, environment)
            }
          )
          .via(LevelResponseConvertFlow())
      )
    }
  }

  val levelActor: ActorRef =
    system.actorOf(LevelGenerationActor.props(playerTokenDAO, logger, environment), "level-actor")

  def getStep(level: LevelName, step: LevelName, encodedTokenId: Option[TokenId]) = Action.async { implicit request =>
    (levelActor ? GetStep(level, step, encodedTokenId.map(URLDecoder.decode(_, "UTF-8"))))
      .mapTo[Either[PreparedStepData, ActorFailed]]
      .map {
        case Left(preparedStepData) => Ok(Json.toJson(preparedStepData))
        case Right(invalidJson) => BadRequest(invalidJson.msg)
      }
  }

  def getLevel(level: LevelName, encodedTokenId: Option[TokenId]) = Action.async { implicit request =>
    (levelActor ? GetLevel(level, encodedTokenId.map(URLDecoder.decode(_, "UTF-8"))))
      .mapTo[Either[RawLevelData, ActorFailed]]
      .map {
        case Left(rawLevelData) => Ok(Json.toJson(rawLevelData))
        case Right(invalidJson) => BadRequest(invalidJson.msg)
      }
  }
}
