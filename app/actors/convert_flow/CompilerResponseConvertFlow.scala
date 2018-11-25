package actors.convert_flow

import actors.messages._
import actors.messages.level.Problem
import akka.NotUsed
import akka.stream.scaladsl.Flow
import compiler.Point
import compiler.processor.AnimationType
import controllers.MathBotCompiler._
import play.api.libs.functional.syntax._
import play.api.libs.json.{JsPath, JsString, JsValue, Writes, _}

object CompilerResponseConvertFlow extends SocketResponseConvertFlow {

  implicit val pointWrites: Writes[Point] = (
    (JsPath \ "x").write[Int] and
    (JsPath \ "y").write[Int]
  )(unlift(Point.unapply))

  implicit val clientCellWrites: Writes[ClientCell] = (
    (JsPath \ "location").write[Point] and
    (JsPath \ "items").write[List[String]]
  )(unlift(ClientCell.unapply))

  implicit val gridWrites = new Writes[ClientGrid] {
    override def writes(o: ClientGrid): JsValue = Json.obj(
      "cells" -> Json.toJson(o.cells.toList)
    )
  }

  implicit val robotStateWrite: Writes[ClientRobotState] = (
    (JsPath \ "location").write[Point] and
    (JsPath \ "orientation").write[String] and
    (JsPath \ "holding").write[List[String]] and
    (JsPath \ "animation").write[Option[AnimationType.Value]] and
    (JsPath \ "grid").write[Option[ClientGrid]]
  )(unlift(ClientRobotState.unapply))

  implicit val clientFrameWrites: OWrites[ClientFrame] = Json.writes[ClientFrame]

  implicit val problemWrites = new Writes[Problem] {
    def writes(o: Problem) = JsString(o.encryptedProblem)
  }

  implicit val compileResponseFormat: OWrites[CompilerResponse] = Json.writes[CompilerResponse]

  def responseToJson(msg: Any): JsValue = {
    val cr = msg match {
      case CompilerOutput(frames, problem) => CompilerResponse(frames, Some(problem))
      case _: CompilerHalted => CompilerResponse(halted = Some(true))
      case ActorFailed(m) => CompilerResponse(error = Some(m))
      case _ => CompilerResponse(error = Some("Unknown response from compiler"))
    }
    Json.toJson[CompilerResponse](cr)
  }

  def apply(): Flow[Any, JsValue, NotUsed] = Flow[Any].map(responseToJson)
}
