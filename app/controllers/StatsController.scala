package controllers

import java.net.URLDecoder

import actors.CurrentStatsActor
import actors.CurrentStatsActor._
import actors.messages.ActorFailed
import akka.actor.ActorSystem
import akka.pattern.ask
import akka.util.Timeout
import javax.inject.Inject
import loggers.MathBotLogger
import daos.PlayerTokenDAO
import models.CurrentStats
import play.api.libs.json._
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

class StatsController @Inject()(system: ActorSystem, playerTokenDAO: PlayerTokenDAO, logger: MathBotLogger)
    extends Controller {

  implicit val timeout: Timeout = 5000.minutes

  val statsActor = system.actorOf(CurrentStatsActor.props(system, playerTokenDAO, logger), "stats-actor")

  def advanceStats(encodedTokenId: String, success: Option[String]) = Action.async {
    implicit request: Request[AnyContent] =>
      (statsActor ? UpdateStats(success.contains("true"), URLDecoder.decode(encodedTokenId, "UTF-8")))
        .mapTo[Either[CurrentStats, ActorFailed]]
        .map {
          case Left(stats) => Ok(Json.prettyPrint(Json.toJson(stats)))
          case Right(invalidJson) => BadRequest(invalidJson.msg)
        }
  }

  def getStats(encodedTokenId: String) = Action.async { implicit request =>
    (statsActor ? GetStats(URLDecoder.decode(encodedTokenId, "UTF-8")))
      .mapTo[Either[CurrentStats, ActorFailed]]
      .map {
        case Left(stats) => Ok(Json.toJson(stats))
        case Right(invalidJson) => BadRequest(invalidJson.msg)
      }
  }

  def changeLevel(encodedTokenId: String, level: String, step: String) = Action.async { implicit request =>
    (statsActor ? ChangeLevel(URLDecoder.decode(encodedTokenId, "UTF-8"), level, step))
      .mapTo[Either[CurrentStats, ActorFailed]]
      .map {
        case Left(stats) => Ok(Json.toJson(stats))
        case Right(invalidJson) => BadRequest(invalidJson.msg)
      }
  }

  def unlock(encodedTokenId: String) = Action.async { implicit request: Request[AnyContent] =>
    (statsActor ? Unlock(URLDecoder.decode(encodedTokenId, "UTF-8")))
      .mapTo[Either[CurrentStats, ActorFailed]]
      .map {
        case Left(stats) => Ok(Json.toJson(stats))
        case Right(invalidJson) => BadRequest(invalidJson.msg)
      }
  }

  def reset(encodedTokenId: String) = Action.async { implicit request: Request[AnyContent] =>
    (statsActor ? Reset(URLDecoder.decode(encodedTokenId, "UTF-8"))).mapTo[Either[CurrentStats, ActorFailed]].map {
      case Left(stats) => Ok(Json.prettyPrint(Json.toJson(stats)))
      case Right(invalidJson) => BadRequest(invalidJson.msg)
    }
  }
}
