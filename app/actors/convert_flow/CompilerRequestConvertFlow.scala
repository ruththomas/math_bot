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
          case JsSuccess(CompilerRequest(_, _, Some(true), _, _), _) =>
            CompilerHalt()
          case JsSuccess(CompilerRequest(Some(steps), Some(problem), _, None, _), _) =>
            CompilerExecute(steps, Problem(encryptedProblem = problem))
          case JsSuccess(CompilerRequest(Some(steps), Some(problem), _, Some(true), None), _) =>
            CompilerCreate(steps, Problem(encryptedProblem = problem))
          case JsSuccess(CompilerRequest(Some(steps), Some(problem), _, Some(true), Some(mbl)), _) =>
            CompilerCreateMbl(steps, Problem(encryptedProblem = problem), mbl)
          case JsSuccess(CompilerRequest(Some(steps), _, _, Some(false), _), _) =>
            CompilerContinue(steps)
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
