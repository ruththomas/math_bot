package actors.messages

import daos.DefaultCommands.main
import models.{FuncToken, Lambdas}
import play.api.libs.json.Json

object ResponseLambdas {
  def apply(lambdas: Lambdas): ResponseLambdas = {
    new ResponseLambdas(main = lambdas.main,
                        stagedFuncs = lambdas.stagedFuncs,
                        cmds = lambdas.cmds,
                        activeFuncs = lambdas.activeFuncs)
  }
  implicit val jsonFormat = Json.format[ResponseLambdas]
}

case class ResponseLambdas(
    main: FuncToken = main,
    stagedFuncs: List[FuncToken],
    cmds: List[FuncToken],
    activeFuncs: List[FuncToken]
)
