package models

import daos.DefaultCommands.{cmds, funcs, main}
import play.api.libs.functional.syntax._
import play.api.libs.json.{JsPath, Json, Reads}
/*
 * Lambdas - user functions
 * @param main {FuncToken} - Main function
 * @param inactiveStaged {Option[List[FuncToken]]} - Bucket for inactive staged functions
 * @param stagedFuncs {List[FuncToken]} - Staged functions usable by the user
 * @param cmds {List[FuncToken]} - Primitive functions
 * @param inactiveCmds {List[FuncToken]} - Bucket for inactive primitives
 * @param inactiveFuncs {Option[List[FuncToken]]} - Bucket for inactive active functions
 * @param activeFuncs {List[FuncToken]} - Active functions usable by user
 * */
case class Lambdas(
    main: FuncToken = main,
    inactiveStaged: Option[List[FuncToken]] = Some(funcs),
    stagedFuncs: List[FuncToken] = List.empty[FuncToken],
    cmds: List[FuncToken] = cmds,
    inactiveCmds: Option[List[FuncToken]] = None,
    inactiveActives: Option[List[FuncToken]] = None,
    activeFuncs: List[FuncToken] = List.empty[FuncToken]
)

object Lambdas {

  implicit val jsonFormat = Json.format[Lambdas]

  implicit val commandReads: Reads[Lambdas] = (
    (JsPath \ "main").read[FuncToken] and
    (JsPath \ "defaultFuncs").readNullable[List[FuncToken]] and
    (JsPath \ "stagedFuncs").read[List[FuncToken]] and
    (JsPath \ "cmds").read[List[FuncToken]] and
    (JsPath \ "inactiveCmds").readNullable[List[FuncToken]] and
    (JsPath \ "inactiveActives").readNullable[List[FuncToken]] and
    (JsPath \ "activeFuncs").read[List[FuncToken]]
  )(Lambdas.apply _)
}
