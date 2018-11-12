package actors.messages.level

import DefaultFunctions._
import models.{FuncToken, Lambdas}
import play.api.libs.json.{Json, OFormat}
import types.TokenId

object Functions {
  implicit val format: OFormat[Functions] = Json.format[Functions]

  private def convertFuncList(list: List[FuncToken], childFuncs: Boolean = false): List[Function] = list.map { ft =>
    Function(
      created_id = ft.created_id,
      color = ft.color,
      func =
        if (childFuncs) None else Some(convertFuncList(ft.func.getOrElse(List.empty[FuncToken]), childFuncs = true)),
      set = ft.set.getOrElse(false),
      name = ft.name.getOrElse(""),
      image = ft.image.getOrElse(""),
      index = ft.index.getOrElse(0),
      category = ft.`type`.get,
      commandId = ft.commandId.get,
      sizeLimit = ft.sizeLimit.getOrElse(-1)
    )
  }

  def apply(tokenId: TokenId, lambdas: Lambdas): Functions = { // Legacy account, swap from lambdas to functions
    val transferred: List[Function] = convertFuncList(
      List(lambdas.main.copy(`type` = Some(Categories.main)))
      ++ (lambdas.activeFuncs ++ lambdas.inactiveActives.getOrElse(List.empty[FuncToken]))
        .map(v => v.copy(`type` = Some(Categories.function)))
      ++ (lambdas.cmds ++ lambdas.inactiveCmds.getOrElse(List.empty[FuncToken]))
        .map(v => v.copy(`type` = Some(Categories.command)))
      ++ (lambdas.stagedFuncs ++ lambdas.inactiveStaged.getOrElse(List.empty[FuncToken]))
        .map(v => v.copy(`type` = Some(Categories.staged)))
    )
    new Functions(
      tokenId = tokenId,
      list = transferred.map(f => (f.created_id, f)).toMap
    )
  }

  def apply( // new account
      tokenId: TokenId
  ): Functions = new Functions(tokenId)
}

case class Functions(
    tokenId: TokenId,
    list: Map[String, Function] =
      (main :: cmds ::: funcs).zipWithIndex.map(f => f._1.copy(index = f._2)).map(f => (f.created_id, f)).toMap
)