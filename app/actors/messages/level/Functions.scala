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

  private def indexEm(functions: List[FuncToken]): List[FuncToken] =
    functions.zipWithIndex.map(fNi => fNi._1.copy(index = Some(fNi._2)))

  def apply(tokenId: TokenId, lambdas: Lambdas): Functions = { // Legacy account, swap from lambdas to functions
    val transferred: List[Function] = convertFuncList(
      indexEm(List(lambdas.main.copy(`type` = Some(Categories.main))))
      ++ indexEm(
        (lambdas.activeFuncs ++ lambdas.inactiveActives.getOrElse(List.empty[FuncToken]))
          .map(v => v.copy(`type` = Some(Categories.function)))
      )
      ++ indexEm(
        (lambdas.cmds ++ lambdas.inactiveCmds.getOrElse(List.empty[FuncToken]))
          .map(v => v.copy(`type` = Some(Categories.command)))
      )
      ++ indexEm(
        (lambdas.stagedFuncs ++ lambdas.inactiveStaged.getOrElse(List.empty[FuncToken]))
          .map(v => v.copy(`type` = Some(Categories.staged)))
      )
    )
    new Functions(
      tokenId = tokenId,
      list = transferred.map(f => (f.created_id, f)).toMap
    )
  }

  def apply( // new account
      tokenId: TokenId
  ): Functions = new Functions(tokenId)

  def apply( // remapping listed functions
            tokenId: TokenId,
            listed: List[Function]): Functions = new Functions(tokenId, listed.map(f => f.created_id -> f).toMap)
}

case class Functions(
    tokenId: TokenId,
    list: Map[String, Function] =
      (main :: cmds ::: funcs).zipWithIndex.map(f => f._1.copy(index = f._2)).map(f => (f.created_id, f)).toMap
) {
  def listed: List[Function] = this.list.values.toList
  def main: Function = this.list.values.toList.filter(_.category == Categories.main).head
  def staged: List[Function] = this.list.values.toList.filter(_.category == Categories.staged)
  def actives: List[Function] = this.list.values.toList.filter(_.category == Categories.function)
  def commands: List[Function] = this.list.values.toList.filter(_.category == Categories.command)
}
