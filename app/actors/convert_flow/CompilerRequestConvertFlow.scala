package actors.convert_flow

import actors.messages._
import akka.NotUsed
import akka.stream.scaladsl.Flow
import models.Problem
import play.api.libs.json._

object CompilerRequestConvertFlow extends SocketRequestConvertFlow {

  def jsonToCompilerCommand(msg: JsValue): Any = {
    Json.fromJson[CompilerRequest](msg).asOpt match {
      case Some(CompilerRequest(_, _, Some(true), _)) =>
        CompilerHalt()
      case Some(CompilerRequest(Some(steps), Some(problem), _, None)) =>
        CompilerExecute(steps, Problem(encryptedProblem = problem))
      case Some(CompilerRequest(Some(steps), Some(problem), _, Some(true))) =>
        CompilerCreate(steps, Problem(encryptedProblem = problem))
      case Some(CompilerRequest(Some(steps), _, _, Some(false))) => CompilerContinue(steps)
      case _ => ActorFailed("Invalid socket request json.")
    }
  }

  def apply(): Flow[JsValue, Any, NotUsed] = {
    Flow[JsValue].map(jsonToCompilerCommand)
  }
}
