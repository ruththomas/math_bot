package actors

import java.security.MessageDigest

import actors.messages.PreparedStepData._
import actors.messages._
import akka.actor.{Actor, Props}
import akka.pattern.pipe
import daos.{DefaultCommands, PlayerTokenDAO}
import loggers.MathBotLogger
import models._
import play.api.Environment
import types.{LevelName, StepName, TokenId}

import scala.concurrent.Future

object LevelGenerationActor {
  case class GetStepControl(level: LevelName, step: StepName)

  case class GetPlayerToken(tokenId: TokenId, level: LevelName, step: StepName)

  case class PlayerTokenReceived(playerToken: PlayerToken, level: LevelName, stepName: StepName)

  case class UpdateDb(playerToken: PlayerToken, rawStepData: RawStepData)

  case class PrepareStepData(playerToken: PlayerToken, rawStepData: RawStepData)

  case class GetGridMap(playerToken: Option[PlayerToken])

  case class StepData(rawStepData: Option[RawStepData], preparedStepData: Option[PreparedStepData])

  case class ProcessStepData(playerToken: PlayerToken)

  case class GetLevel(level: LevelName, tokenId: Option[TokenId])

  case class ResetStagedFunctions(playerToken: PlayerToken, rawStepData: RawStepData)

  case class GetStep(level: LevelName, step: StepName, tokenId: Option[TokenId] = None)

  def makeQtyUnlimited(qty: Int): Int = if (qty < 0) 10000 else qty

  def props(playerTokenDAO: PlayerTokenDAO, logger: MathBotLogger, environment: Environment) =
    Props(new LevelGenerationActor()(playerTokenDAO, logger, environment))
}

class LevelGenerationActor()(playerTokenDAO: PlayerTokenDAO, logger: MathBotLogger, environment: Environment)
    extends Actor {
  import LevelGenerationActor._
  import context.dispatcher

  val levelGenerator: LevelGenerator = new LevelGenerator(environment)

  private val className = "LevelGenerationActor"

  override def receive: Receive = {
    case GetGridMap(playerToken) =>
      for {
        token <- playerToken
        stats <- token.stats
        rawStepData <- levelGenerator.getRawStepData(stats.level, stats.step)
      } yield {
        val preparedStepData = PreparedStepData(
          token,
          rawStepData.copy(stagedQty = makeQtyUnlimited(rawStepData.stagedQty),
                           mainMax = makeQtyUnlimited(rawStepData.mainMax))
        )

        Future {
          GridMap(
            gMap = preparedStepData.gridMap,
            robotOrientation = rawStepData.robotOrientation.toString,
            success = preparedStepData.stepControl.success,
            description = preparedStepData.description,
            problem = Problem(preparedStepData.problem.encryptedProblem),
            evalEachFrame = rawStepData.evalEachFrame.getOrElse(false)
          )
        }.map(g => g)
          .pipeTo(self)(sender)
      }
    case GetLevel(level, _) =>
      Future { levelGenerator.getJsonFromFile(s"app/assets/$level.json") }
        .map {
          case Some(rawLevelData) => rawLevelData
          case None => ActorFailed(s"No $level found in assets.")
        }
        .pipeTo(self)(sender)
    case GetStep(level, step, id) =>
      id match {
        case Some(tokenId) =>
          Future { tokenId }
            .map { GetPlayerToken(_, level, step) }
            .pipeTo(self)(sender)
        case None =>
          Future { levelGenerator.getRawStepData(level, step) }
            .map {
              case Some(rawStepData) => rawStepData
              case None => ActorFailed(s"No level $level or step $step found.")
            }
            .pipeTo(self)(sender)
      }
    case GetPlayerToken(tokenId, level, step) =>
      playerTokenDAO
        .getToken(tokenId)
        .map {
          case Some(token) =>
            val verifiedLevelAndStep = levelGenerator.verifyLevelAndStepName(level, step)
            PlayerTokenReceived(
              token.copy(
                stats = Some(token.stats.get.copy(level = verifiedLevelAndStep._1, step = verifiedLevelAndStep._2))
              ),
              verifiedLevelAndStep._1,
              verifiedLevelAndStep._2
            )
          case None => ActorFailed(s"No token found with token_id $tokenId")
        }
        .pipeTo(self)(sender)
    case PlayerTokenReceived(playerToken, level, step) =>
      Future { levelGenerator.getRawStepData(level, step) }
        .map {
          case Some(rawStepData) =>
            ResetStagedFunctions(
              playerToken,
              rawStepData.copy(stagedQty = makeQtyUnlimited(rawStepData.stagedQty),
                               mainMax = makeQtyUnlimited(rawStepData.mainMax))
            )
          case None => ActorFailed(s"No level $level or step $step found.")
        }
        .pipeTo(self)(sender)
    case ResetStagedFunctions(playerToken, rawStepData) =>
      // Resets staged to zero before UpdateDb
      // Moves all staged functions back to defaultFuncs
      Future {
        playerToken.lambdas match {
          case Some(lambdas) =>
            val updatedDefault = lambdas.inactiveStaged.get ::: lambdas.stagedFuncs
            val defaultIds = DefaultCommands.funcs.map(_.created_id)
            val filteredDefault = updatedDefault.filter(d => defaultIds.contains(d.created_id))

            val r = lambdas.copy(
              stagedFuncs = List.empty[FuncToken],
              inactiveStaged = Some(filteredDefault)
            )
            UpdateDb(playerToken.copy(lambdas = Some(r)), rawStepData)
          case None =>
        }
      }.pipeTo(self)(sender)
    case UpdateDb(playerToken, rawStepData) =>
      for {
        lambdas <- playerToken.lambdas
        activeFuncs = lambdas.activeFuncs
        inactiveActives = lambdas.inactiveActives
        activesCombined = activeFuncs ++ inactiveActives.getOrElse(List.empty[FuncToken])
      } yield {
        // Create List[FuncToken] of assigned staged
        val assignedStaged = {
          rawStepData.assignedStaged.flatMap { as =>
            if (activesCombined.exists(_.created_id == as.createdId)) None
            else {
              Some(
                FuncToken(
                  created_id = as.createdId,
                  func = Some(List.empty[FuncToken]),
                  set = Some(false),
                  name = Some(as.name),
                  image = Some(as.image),
                  index = Some(playerToken.lambdas.get.stagedFuncs.length),
                  `type` = Some("function"),
                  commandId = Some("function"),
                  sizeLimit = Some(makeQtyUnlimited(as.sizeLimit))
                )
              )
            }
          }
        }

        // Create List[FuncToken] of pre built active functions
        val preBuiltActive = {
          rawStepData.preBuiltActive.flatMap { pa =>
            if (activesCombined.exists(_.created_id == pa.createdId)) None
            else {
              Some(
                FuncToken(
                  created_id = pa.createdId,
                  func = Some(pa.func.flatMap(fn => lambdas.cmds.find(_.commandId.contains(fn)))),
                  name = Some(pa.name),
                  image = Some(pa.image),
                  index = Some(playerToken.lambdas.get.activeFuncs.length),
                  `type` = Some("function"),
                  commandId = Some("function"),
                  sizeLimit = Some(pa.sizeLimit)
                )
              )
            }
          }
        }

        // Move staged functions to inactive staged
        val stagedAndInactiveStaged: Map[String, List[FuncToken]] = {
          val inactiveStaged = lambdas.inactiveStaged.getOrElse(List.empty[FuncToken])
          val qty = rawStepData.stagedQty

          val newStaged = assignedStaged ++ inactiveStaged.take(qty)
          val newDefault = inactiveStaged.filterNot(d => newStaged.exists(_.created_id == d.created_id))

          Map("newStaged" -> newStaged, "newInActives" -> newDefault)
        }

        // Move actives to inactive actives
        val activesAndInactiveActives: Map[String, List[FuncToken]] = rawStepData.allowedActives match {
          case Some(allowed) if allowed.nonEmpty => // allowed is a non empty list
            val consolidatedFuncs = lambdas.activeFuncs ++ lambdas.inactiveActives.getOrElse(List.empty[FuncToken])
            val allowedIds = rawStepData.assignedStaged.map(_.createdId) ++ allowed
            val allowedActives = consolidatedFuncs.filter(ft => allowedIds.contains(ft.created_id))
            val inActives = consolidatedFuncs.filterNot(ft => allowedIds.contains(ft.created_id))
            Map("newActives" -> (preBuiltActive ++ allowedActives), "newInActives" -> inActives)
          case None => // allowed is None (all actives allowed)
            Map("newActives" -> (preBuiltActive ++ activesCombined), "newInActives" -> List.empty[FuncToken])
          case _ => // allowed is an empty list (no actives allowed)
            Map("newActives" -> List.empty[FuncToken], "newInActives" -> (preBuiltActive ++ activesCombined))
        }

        // Move actives to inactive commands
        val commandsAndInactiveCommands: Map[String, List[FuncToken]] = {
          val allowed = rawStepData.cmdsAvailable
          val commands = DefaultCommands.cmds
          val allowedCommands = commands.filter(cmd => allowed.contains(cmd.commandId.getOrElse("")))
          val inActives = commands.filterNot(cmd => allowed.contains(cmd.commandId.getOrElse("")))
          Map("newCommands" -> allowedCommands, "newInActives" -> inActives)
        }

        // Filter functions from main that are not allowed in level
        val filteredMainFunc: List[FuncToken] = {
          if (rawStepData.clearMain) List.empty[FuncToken]
          else
            lambdas.main.func
              .getOrElse(List.empty[FuncToken])
              .filter { ft =>
                (stagedAndInactiveStaged
                  .getOrElse("newStaged", List.empty[FuncToken]) ++ activesAndInactiveActives
                  .getOrElse("newActives", List.empty[FuncToken]) ++ commandsAndInactiveCommands
                  .getOrElse("newCommands", List.empty[FuncToken]))
                  .exists(_.created_id == ft.created_id)
              }
              .take(rawStepData.mainMax)
        }

        import PlayerActor.indexFunctions

        // Copy lambdas and update values
        val updatedLambdas = lambdas.copy(
          cmds = indexFunctions(commandsAndInactiveCommands.getOrElse("newCommands", List.empty[FuncToken])),
          inactiveCmds = commandsAndInactiveCommands.get("newInActives"),
          stagedFuncs = indexFunctions(stagedAndInactiveStaged.getOrElse("newStaged", List.empty[FuncToken])),
          inactiveStaged = stagedAndInactiveStaged.get("newInActives"),
          activeFuncs = indexFunctions(activesAndInactiveActives.getOrElse("newActives", List.empty[FuncToken])),
          inactiveActives = activesAndInactiveActives.get("newInActives"),
          main = lambdas.main.copy(func = Some(indexFunctions(filteredMainFunc)))
        )

        val updatedPlayerToken = playerToken.copy(lambdas = Some(updatedLambdas))

        // Update db with new token
        playerTokenDAO
          .updateToken(updatedPlayerToken)
          .map(_ => PreparedStepData(updatedPlayerToken, rawStepData))
          .pipeTo(self)(sender)
      }

    case gridMap: GridMap =>
      sender ! gridMap
    case preparedStepData: PreparedStepData =>
      logger.LogDebug(className, "PreparedStepData generated.")
      sender ! Left(preparedStepData)
    case rawLevelData: RawLevelData =>
      logger.LogDebug(className, "RawLevelData generated.")
      sender ! Left(rawLevelData)
    case rawStepData: RawStepData =>
      logger.LogDebug(className, "RawStepData generated.")
      sender ! rawStepData
    case actorFailed: ActorFailed =>
      logger.LogFailure(className, s"ActorFailed generated. msg:${actorFailed.msg}")
      sender ! Right(actorFailed)
    case _ =>
      logger.LogFailure(className, "Not sure what happened.")
      sender ! Right(ActorFailed("Not sure what happened. LevelGenerationActor"))
  }
}
