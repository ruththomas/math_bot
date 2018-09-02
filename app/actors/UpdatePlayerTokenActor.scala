package actors

import actors.PlayerActor.UpdatePlayerToken
import actors.messages.AssignedFunction
import akka.actor.{Actor, ActorSystem, Props}
import akka.http.scaladsl.util.FastFuture
import akka.pattern.pipe
import loggers.MathBotLogger
import daos.DefaultCommands
import models._
import play.api.Environment

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object UpdatePlayerTokenActor {
  final case class ApplyPolyfills(playerToken: PlayerToken)

  final case class AddDefaultFuncsField(playerToken: PlayerToken)

  final case class AddNamesToCommands(playerToken: PlayerToken)

  final case class DedupFunctions(playerToken: PlayerToken)

  final case class PolyfillsApplied(playerToken: PlayerToken)

  final case class ImplementWins(playerToken: PlayerToken)

  final case class EnsureCorrectCreatedId(playerToken: PlayerToken)

  final case class AdjustFunctionLimits(playerToken: PlayerToken)

  def dedup(lambdas: Lambdas): Lambdas = {
    def dedupList(funcList: List[FuncToken]): List[FuncToken] = funcList match {
      case Nil => Nil
      case ft :: rest if rest.exists(_.created_id == ft.created_id) =>
        ft :: dedupList(rest.filterNot(_.created_id == ft.created_id))
      case ft :: rest => ft :: dedupList(rest)
    }
    val dedupedActives = dedupList(lambdas.activeFuncs)
    val dedupedInactiveActives = dedupList(lambdas.inactiveActives.getOrElse(List.empty[FuncToken]))
    val dedupedStaged = dedupList(lambdas.stagedFuncs)
    val dedupedInactiveStaged = dedupList(lambdas.inactiveStaged.getOrElse(List.empty[FuncToken]))
    val filteredMainFunc = lambdas.main.func.getOrElse(List.empty[FuncToken]).filter { ft =>
      (dedupedActives ++ dedupedInactiveActives ++ (lambdas.cmds ++ lambdas.inactiveCmds
        .getOrElse(List.empty[FuncToken])))
        .exists(_.created_id == ft.created_id)
    }
    lambdas.copy(
      main = lambdas.main.copy(func = Some(filteredMainFunc)),
      activeFuncs = dedupedActives,
      inactiveActives = Some(dedupedInactiveActives),
      stagedFuncs = dedupedStaged,
      inactiveStaged = Some(dedupedInactiveStaged)
    )
  }

  def props(system: ActorSystem, logger: MathBotLogger, environment: Environment) =
    Props(new UpdatePlayerTokenActor()(system, logger, environment))
}

/**
 * Actor used to update the player token with changes to its structure
 * @define Actor
 * @param system
 * @param logger
 * @param environment
 */
class UpdatePlayerTokenActor()(system: ActorSystem, logger: MathBotLogger, environment: Environment) extends Actor {
  import UpdatePlayerTokenActor._
  private val className = "PolyfillActor"
  val levelGenerator: LevelGenerator = new LevelGenerator(environment)
  val assignedFunctions: List[AssignedFunction] = levelGenerator.getAssignedFunctions

  def updateCreatedIds(lambdas: Lambdas): Lambdas = {
    def updateFunctions(funcList: List[FuncToken]): List[FuncToken] = funcList.map { ft =>
      val createdId = assignedFunctions.find(_.image == ft.image.getOrElse("")) match {
        case Some(assignedFunction) =>
          assignedFunction.createdId
        case None =>
          ft.created_id
      }
      ft.copy(created_id = createdId, func = Some(updateFunctions(ft.func.getOrElse(List.empty[FuncToken]))))
    }

    lambdas.copy(
      main = lambdas.main.copy(func = Some(updateFunctions(lambdas.main.func.getOrElse(List.empty[FuncToken])))),
      stagedFuncs = updateFunctions(lambdas.stagedFuncs),
      inactiveStaged = Some(updateFunctions(lambdas.inactiveStaged.getOrElse(List.empty[FuncToken]))),
      activeFuncs = updateFunctions(lambdas.activeFuncs),
      inactiveActives = Some(updateFunctions(lambdas.inactiveActives.getOrElse(List.empty[FuncToken])))
    )
  }

  override def receive: Receive = {
    case ApplyPolyfills(playerToken) =>
      Future { playerToken.lambdas.get.inactiveStaged }
        .map {
          case Some(_) => AddNamesToCommands(playerToken)
          case None => AddDefaultFuncsField(playerToken)
        }
        .pipeTo(self)(sender)
    case AddDefaultFuncsField(playerToken) => // Polyfill to ensure field added to existing users
      for {
        lambdas <- playerToken.lambdas
        updatedLambdas = lambdas.copy(stagedFuncs = List.empty[FuncToken], inactiveStaged = Some(DefaultCommands.funcs))
      } yield
        Future { playerToken.copy(lambdas = Some(updatedLambdas)) }
          .map { AddNamesToCommands.apply }
          .pipeTo(self)(sender)
    case AddNamesToCommands(playerToken) => // Polyfill to ensure commands include names for existing users
      for {
        lambdas <- playerToken.lambdas
        updatedLambdas = Some(lambdas.copy(cmds = DefaultCommands.cmds))
      } yield
        Future { playerToken.copy(lambdas = updatedLambdas) }
          .map { EnsureCorrectCreatedId.apply }
          .pipeTo(self)(sender)
    case EnsureCorrectCreatedId(playerToken) => // converts a users existing system generated functions created_id to match what is in the levels assigned staged and pre built active
      val updatedLambdas = updateCreatedIds(playerToken.lambdas.getOrElse(Lambdas()))
      Future { playerToken.copy(lambdas = Some(updatedLambdas)) }
        .map { DedupFunctions }
        .pipeTo(self)(sender)
    case DedupFunctions(playerToken) => // ensures user only has one instance of pre-built active or assigned staged?
      for {
        lambdas <- playerToken.lambdas
        deduped = dedup(lambdas)
      } yield
        Future { playerToken.copy(lambdas = Some(deduped)) }
          .map { ImplementWins.apply }
          .pipeTo(self)(sender)
    case ImplementWins(playerToken) => // adds stars to stats
      for {
        stats <- playerToken.stats
        levels = stats.levels
      } yield
        Future {
          levels.head._2.head._2.wins match {
            case Some(_) => playerToken
            case None =>
              playerToken.copy(stats = Some(stats.copy(levels = levels.map { l =>
                l._1 -> l._2.map { st =>
                  st._1 -> st._2.copy(wins = Some(st._2.stars), stars = 3)
                }
              })))
          }
        }.map { AdjustFunctionLimits.apply }.pipeTo(self)(sender)
    case AdjustFunctionLimits(playerToken) => // for adjusting size limits on generated functions
      val onesToAdjust: Map[String, Int] = Map(
        "59033416266" -> 30
      )
      Future {
        val lambdas = playerToken.lambdas.getOrElse(Lambdas())
        val updatedLambdas = lambdas.copy(
          inactiveActives = Some(lambdas.inactiveActives.getOrElse(List.empty[FuncToken]).map {
            case ft if onesToAdjust isDefinedAt ft.created_id => ft.copy(sizeLimit = Some(onesToAdjust(ft.created_id)))
            case ft => ft
          }),
          activeFuncs = lambdas.activeFuncs.map {
            case ft if onesToAdjust isDefinedAt ft.created_id => ft.copy(sizeLimit = Some(onesToAdjust(ft.created_id)))
            case ft => ft
          }
        )
        playerToken.copy(lambdas = Some(updatedLambdas))
      }.map { PolyfillsApplied.apply }.pipeTo(self)(sender)
    case PolyfillsApplied(playerToken) =>
      sender ! UpdatePlayerToken(playerToken)
  }
}
