package actors.messages.level

import DefaultFunctions._
import actors.messages.AssignedFunction
import models.deprecatedPlayerToken.{FuncToken, Lambdas}
import play.api.libs.json.{Json, OFormat}
import types.TokenId

object Functions {
  implicit val format: OFormat[Functions] = Json.format[Functions]

  private def updateCreatedIds(lambdas: Lambdas, assignedFunctions: List[AssignedFunction]): Lambdas = {
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
      category = ft.`type`.getOrElse(""), // The only functions that should have None for category should be nested
      commandId = ft.commandId.getOrElse(""),
      sizeLimit = ft.sizeLimit.getOrElse(-1)
    )
  }

  private def indexEm(functions: List[FuncToken]): List[FuncToken] =
    functions.zipWithIndex.map(fNi => fNi._1.copy(index = Some(fNi._2)))

  def apply(tokenId: TokenId, l: Lambdas, assigned: List[AssignedFunction]): Functions = { // Legacy account, swap from lambdas to functions
    val lambdas = updateCreatedIds(l, assigned)
    val main = convertFuncList(indexEm(List(lambdas.main.copy(`type` = Some(Categories.main)))))
    val actives = convertFuncList(
      indexEm(
        (lambdas.activeFuncs ::: lambdas.inactiveActives.getOrElse(List.empty[FuncToken]))
          .map(v => v.copy(`type` = Some(Categories.function)))
      )
    )
    val cmds = convertFuncList(
      indexEm(
        (lambdas.cmds ::: lambdas.inactiveCmds.getOrElse(List.empty[FuncToken]))
          .map(v => v.copy(`type` = Some(Categories.command)))
      )
    )
    val staged = convertFuncList(
      indexEm(
        (lambdas.stagedFuncs ::: lambdas.inactiveStaged.getOrElse(List.empty[FuncToken]))
          .map(v => v.copy(`type` = Some(Categories.staged)))
      )
    )
    val transferred: List[Function] = main ++ actives ++ cmds ++ staged
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
