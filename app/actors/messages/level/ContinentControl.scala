package actors.messages.level
import compiler.{ CellType, Grid }
object ContinentControl {
  private def isFinalSpot(grid: Grid) =
    grid.currentCell().cellType == CellType.FinalAnswer

  private def totalDropped(grid: Grid, problem: Problem) = {
    val spotSum: BigDecimal = grid.currentCell().contents.map(_.valueToBigDec).sum
    val solution: BigDecimal = BigDecimal(Problem.evalProblem(problem))
    spotSum == solution
  }

  private def discoverRecursion(main: Function,
                                actives: List[Function],
                                visited: Set[Function] = Set.empty[Function]): Boolean = {
    visited.contains(main) match {
      case true =>
        true
      case false =>
        main.func.exists(
          _.flatMap(func => actives.find(_.created_id == func.created_id))
            .exists(func => discoverRecursion(func, actives, visited + main))
        )
    }
  }

  def checkParams(parameters: List[String], functions: List[Function]): Boolean = {
    val main: Function = functions.filter(_.category == Categories.main).head
    val actives: List[Function] = functions.filter(_.category == Categories.function)
    parameters.foldLeft(true) { (bool, param) =>
      if (bool) {
        param match {
          case "functionRequired" =>
            main.func.forall(_.exists { ft =>
              actives.find(f => f.created_id == ft.created_id) match {
                case Some(funcToken) => funcToken.category.contains(Categories.function)
                case None => false
              }
            })
          case "recursionRequired" =>
            discoverRecursion(main, actives)
          case _ => true
        }
      } else bool
    }
  }
}

class ContinentControl(
    parameters: List[String],
    description: String,
    functions: Functions
) {
  import ContinentControl._
  val listedFunctions: List[Function] = functions.list.values.toList

  def success(grid : Grid, problem : Problem): Boolean = {
    if (parameters.contains("sandbox")) false
    else
      isFinalSpot(grid) && totalDropped(grid, problem) && checkParams(parameters, listedFunctions)
  }
}
