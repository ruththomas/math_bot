package controllers

import java.net.URLDecoder

import javax.inject.Inject
import actors.messages.ActorFailed
import actors.{PlayerActor, PolyfillActor}
import actors.PlayerActor._
import actors.messages.ResponsePlayerToken
import akka.actor.ActorSystem
import akka.pattern.ask
import akka.util.Timeout
import loggers.MathBotLogger
import model.PlayerTokenDAO
import model.models.PlayerToken
import play.api.Environment
import play.api.mvc._
import play.api.libs.json._

import scala.concurrent.duration._
import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global

class PlayerController @Inject()(system: ActorSystem,
                                 playerTokenDAO: PlayerTokenDAO,
                                 logger: MathBotLogger,
                                 environment: Environment)
    extends Controller {

  implicit val timeout: Timeout = 5000.minutes

  val polyfillActor = system.actorOf(PolyfillActor.props(system, logger, environment), "polyfill-actor")

  val playerActor =
    system.actorOf(PlayerActor.props(system, playerTokenDAO, polyfillActor, logger, environment), "player-actor")

  def moveActiveFunc(tokenId: String, oldIndex: String, newIndex: String): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      (playerActor ? ReorginizeActiveFunctions(URLDecoder.decode(tokenId, "UTF-8"), oldIndex, newIndex))
        .mapTo[Either[PreparedLambdasToken, ActorFailed]]
        .map {
          case Left(preparedLambdasToken) => Ok(Json.toJson(preparedLambdasToken.lambdas))
          case Right(actorFailed) => BadRequest(actorFailed.msg)
        }
  }

  def addToken(): Action[JsValue] = Action.async(parse.json) { implicit request: Request[JsValue] =>
    (playerActor ? AddToken(request.body)).mapTo[Either[ResponsePlayerToken, ActorFailed]].map {
      case Left(responsePlayerToken) =>
        Ok(Json.toJson(responsePlayerToken))
      case Right(invalidJson) => BadRequest(invalidJson.msg)
    }
  }

  def editLambdas(): Action[JsValue] = Action.async(parse.json) { implicit request: Request[JsValue] =>
    (playerActor ? EditLambdas(request.body)).mapTo[Either[PreparedLambdasToken, ActorFailed]].map {
      case Left(preparedLambdasToken) => Ok(Json.toJson(preparedLambdasToken.lambdas))
      case Right(invalidJson) => BadRequest(invalidJson.msg)
    }
  }

  def activateFunction(encodedTokenId: String, stagedIndex: String, activeIndex: String): Action[AnyContent] =
    Action.async { implicit request: Request[AnyContent] =>
      (playerActor ? ActivateFunc(URLDecoder.decode(encodedTokenId, "UTF-8"), stagedIndex, activeIndex))
        .mapTo[Either[PreparedLambdasToken, ActorFailed]]
        .map {
          case Left(preparedLambdasToken) => Ok(Json.toJson(preparedLambdasToken.lambdas))
          case Right(invalidJson) => BadRequest(invalidJson.msg)
        }
    }

  def test() = Action.async(parse.json) { implicit request: Request[JsValue] =>
    request.body.validate[PlayerToken].asOpt match {
      case Some(playerToken) =>
        for {
          deleteToken <- playerTokenDAO.delete(playerToken.token_id)
          insertMutatedToken <- playerTokenDAO.insert(playerToken)
        } yield Ok(Json.toJson(playerToken))
      case None => Future { BadRequest }
    }
  }
}
