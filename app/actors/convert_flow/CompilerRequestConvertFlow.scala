package actors.convert_flow

import actors.messages._
import actors.messages.level.Problem
import akka.NotUsed
import akka.stream.scaladsl.Flow
import play.api.libs.json._

import scala.util.{Failure, Success, Try}

object CompilerRequestConvertFlow extends SocketRequestConvertFlow {
  import actors.messages.CompilerRequest._

  def jsonToCommand(msg: JsValue): Any = {
    Try(Json.fromJson[CompilerRequest](msg)) match {
      case Success(cr) =>
        cr match {
          case JsSuccess(CompilerRequest(_, _, Some(true), _, _, _), _) =>
            CompilerHalt()
          case JsSuccess(CompilerRequest(Some(steps), Some(problem), _, None, _, None), _) =>
            CompilerExecute(steps, Problem(encryptedProblem = problem)) // Should no longer be sent by client
          case JsSuccess(CompilerRequest(Some(steps), Some(problem), _, Some(true), None, None), _) =>
            CompilerCreate(steps, Problem(encryptedProblem = problem))
          case JsSuccess(CompilerRequest(Some(steps), Some(problem), _, Some(true), Some(mbl), None), _) =>
            CompilerCreateMbl(steps, Problem(encryptedProblem = problem), mbl)
          case JsSuccess(CompilerRequest(Some(steps), _, _, Some(false), _, None), _) =>
            CompilerContinue(steps)
          case JsSuccess(CompilerRequest(None, Some(problem), _, Some(true), None, Some(frameRequest)), _) if frameRequest.direction != 0 && frameRequest.count > 0 =>
            ProcessorCreate(
              ProcessorFrameSelector(frameRequest.previous, frameRequest.index, frameRequest.count, frameRequest.direction),
              Problem(encryptedProblem = problem)
            )
          case JsSuccess(CompilerRequest(None, Some(problem), _, Some(true), Some(mbl), Some(frameRequest)), _) if frameRequest.direction != 0 && frameRequest.count > 0 =>
            ProcessorCreateMbl(
              ProcessorFrameSelector(frameRequest.index, frameRequest.count, frameRequest.direction, frameRequest.previous),
              Problem(encryptedProblem = problem), mbl)
          case JsSuccess(CompilerRequest(None, _, _, createOpt, _, Some(frameRequest)), _) if frameRequest.direction != 0 && frameRequest.count > 0 && !createOpt.getOrElse(false) =>
            ProcessorContinue(
              ProcessorFrameSelector(frameRequest.previous, frameRequest.index, frameRequest.count, frameRequest.direction)
            )
          case _ =>
            ActorFailed("Invalid socket request json.")
        }
      case Failure(t) =>
        ActorFailed(t.getMessage)
    }
  }

  def apply(): Flow[JsValue, Any, NotUsed] = {
    Flow[JsValue].map(jsonToCommand)
  }
}
