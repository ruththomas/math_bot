package compiler

import compiler.mbl._
import compiler.operations._
import daos.CommandIds
import models.{ FuncToken, GridMap, GridPart, Problem }
import play.api.libs.json._

object Compiler {

  private type TokenUserFunctions = Map[String, UserFunction]
  private type NamedUserFunctions = Map[String, UserFunctionNamed]
  private type LambdaUserFunctions = Map[Int, UserFunctionLambda]
  type ErrorMessage = String

  private def cellTypeFromName(name: String) = name match {
    case "empty space floor" => CellType.EmptySpace
    case "empty space" => CellType.EmptySpace
    case "final answer" => CellType.FinalAnswer
    case "wall" => CellType.Wall
  }

  def processBoard(grid: GridMap): Option[Grid] = {
    var robotLocation = (0, 0)

    val convertedToElements = grid.gMap
      .zip(Stream.from(0))
      .map(v => (v._2, v._1.zip(Stream.from(0))))
      .flatMap(
        v =>
          v._2.map(
            u =>
              (v._1, u._2) -> (
                u._1 match {
                  case GridPart(name, _, _) if name == "final answer" => Some(Cell(CellType.FinalAnswer))
                  case GridPart(name, robotSpot, tools) if name == "empty space" && !robotSpot && tools.isEmpty => None
                  case GridPart(_, robotSpot, _) if robotSpot =>
                    robotLocation = (v._1, u._2) // Just for the side effect
                    None
                  case GridPart(name, _, tools) if name == "empty space" && tools.nonEmpty =>
                    Some(
                      Cell(
                        cellTypeFromName(name),
                        tools.map(tool => Element(tool.original, tool.name, tool.image, tool.color, tool.value))
                      )
                    )
                  case GridPart(name, _, _) if name == "wall" => Some(Cell(CellType.Wall))
                }
            )
        )
      )
      .flatMap(cl => cl._2.map(c => cl._1 -> c))

    Some(
      Grid(
        Point(robotLocation),
        grid.robotOrientation,
        convertedToElements.toMap
      )
    )
  }

  def processBoard(jBoard: JsArray): Option[Grid] = {
    val convertedToElements = jBoard.value map { row =>
      row.asInstanceOf[JsArray].value map { cell =>
        val f = cell.asInstanceOf[JsObject].value
        (
          f.get("robotSpot").exists(_.asInstanceOf[JsBoolean].value),
          f.get("tools").map(_.asInstanceOf[JsArray].value) map { tools =>
            tools.map(_.asInstanceOf[JsObject]) map { v =>
              Element(
                v.value.get("original").exists(_.asInstanceOf[JsBoolean].value),
                v.value.get("name").map(_.asInstanceOf[JsString].value).get,
                v.value.get("image").map(_.asInstanceOf[JsString].value).get,
                v.value.get("color").map(_.asInstanceOf[JsString].value).get,
                v.value.get("value").map(_.asInstanceOf[JsNumber].value.toInt).get
              )
            }
          },
          f.get("name").map(_.asInstanceOf[JsString].value).getOrElse("empty space")
        )
      } zip Stream.from(0)
    } zip Stream.from(0) flatMap (v => v._1.map(u => (v._2, u._2) -> u._1))

    def isNonEmptyCell(cell: ((Int, Int), (Boolean, Option[Seq[Element]], String))): Boolean = cell match {
      case ((_, _), (_, Some(_ :: _), _)) => true
      case ((_, _), (true, _, _)) => true
      case ((_, _), (_, _, "final answer")) => true
      case _ => false
    }

    convertedToElements.find(_._2._1).map(_._1) map { robotLocation =>
      Grid(
        Point(robotLocation),
        "0",
        convertedToElements
          .filter(isNonEmptyCell)
          .map(v => v._1 -> Cell(cellType = cellTypeFromName(v._2._3), contents = v._2._2.get.toList))
          .toMap
      )
    }
  }

  def compile(main: FuncToken,
              funcs: List[FuncToken],
              commands: List[FuncToken],
              grid: GridMap,
              problem: Problem): Option[GridAndProgram] = {
    val funcTokens = funcs.map(token => token.created_id -> token).toMap + (main.created_id -> main)
    val firstPass = convertToOps(main, funcTokens, Map.empty[String, UserFunction], commands)
    val program = firstPass._1.asInstanceOf[UserFunction]
    val byCreatedId = firstPass._2
    fixReferences(byCreatedId.values.toSeq :+ program,
                  lookUpReference(byCreatedId, Map.empty[String, UserFunction], Map.empty[Int, UserFunction]))
    processBoard(grid).map(g => GridAndProgram(g, program, problem))
  }

  private def lookUpReference(byCreatedId: Map[String, UserFunction],
                              byName: Map[String, UserFunction],
                              byLambda: Map[Int, UserFunction])(ref: UserFunctionRef) =
    ref match {
      case UserFunctionRefById(created_id) =>
        byCreatedId.get(created_id)
      case UserFunctionRefByName(name) =>
        byName.get(name)
      case UserFunctionRefByLambdaId(lambdaId) =>
        byLambda.get(lambdaId)
    }

  // Return a list of unmatched function references
  private def checkReferences(functions: Seq[UserFunction], lookup: UserFunctionRef => Option[UserFunction]) : Seq[UserFunctionRef] =
    functions.flatMap {
      f =>
        f.operations.flatMap {
          case funcRef: UserFunctionRef =>
            if (lookup(funcRef).isEmpty) Some(funcRef) else None
          case ifColor: IfColor =>
            ifColor.operation match {
              case funcRef: UserFunctionRefById =>
                if (lookup(funcRef).isEmpty) Some(funcRef) else None
              case _ =>
                None
            }
          case _ =>
            None
        }
    }

  // To avoid an infinite loop while processing user functions, user functions are sometimes replaced with refs.
  // Before running the code, replace the refs with the functions.  This function works by side effect because it
  // was simpler to write the fixup using mutable operations value. Unknown functions are replaced with a NoOperation
  // as they should be discovered by an earlier check.
  private def fixReferences(functions: Seq[UserFunction], lookup: UserFunctionRef => Option[UserFunction]): Unit = {

    functions.foreach { f =>
      f.operations = f.operations.map {
        case funcRef: UserFunctionRef =>
          lookup(funcRef).getOrElse(NoOperation)
        case ifColor: IfColor =>
          ifColor.operation match {
            case funcRef: UserFunctionRefById =>
              ifColor.copy(operation = lookup(funcRef).getOrElse(NoOperation))
            case _ =>
              ifColor
          }
        case other => other
      }
    }
  }

  def convertToOps(token: FuncToken,
                   funcTokens: Map[String, FuncToken],
                   userFuncs: TokenUserFunctions,
                   commands: List[FuncToken]): (Operation, Map[String, UserFunction]) = {
    token.created_id match {
      case id if commands.map(t => t.created_id).contains(id) =>
        val command = commands.find(c => c.created_id == id).map(c => c.commandId).getOrElse("unknown") match {
          case Some(CommandIds.changeRobotDirection) => ChangeRobotDirection
          case Some(CommandIds.moveRobotForwardOneSpot) => MoveRobotForwardOneSpot
          case Some(CommandIds.setItemDown) => SetItemDown
          case Some(CommandIds.pickUpItem) => PickUpItem
          case _ => NoOperation
        }
        (command, userFuncs)
      case id if funcTokens.contains(id) =>
        token.color match {
          case "default" =>
            userFuncs.get(id) match {
              case Some(uf) =>
                (uf, userFuncs)
              case None =>
                // The empty "UserFunction()" is used to prevent convertToOps from trying to convert the same function again.  It
                // doesn't really get added because the line below replaces the empty with the "updated" call
                val converted = convertFunction(token, funcTokens, userFuncs + (id -> UserFunction()), commands)
                val uf = new UserFunction(converted.operations)
                (uf, converted.userFuncs.updated(token.created_id, uf))
            }
          case color =>
            userFuncs.get(id) match {
              case Some(uf) =>
                (IfColor(color, uf), userFuncs)
              case None =>
                // The empty "UserFunction()" is used to prevent convertToOps from trying to convert the same function again.  It
                // doesn't get really added because the line below replaces the empty with the "updated" call
                val converted = convertFunction(token.copy(color = "default"),
                                                funcTokens,
                                                userFuncs + (id -> UserFunction()),
                                                commands)
                val uf = new UserFunction(converted.operations)
                (IfColor(color, uf), converted.userFuncs.updated(token.created_id, uf))
            }
        }
    }
  }

  case class Converted(operations: Seq[Operation], userFuncs: Map[String, UserFunction])

  def convertFunction(
      funcToken: FuncToken,
      funcTokens: Map[String, FuncToken],
      userFuncs: Map[String, UserFunction],
      commands: List[FuncToken]
  ): Converted = {
    val operations = funcTokens(funcToken.created_id).func.get
    operations.foldLeft[Converted](Converted(Seq.empty[Operation], userFuncs)) { (converted, token) =>
      val (op, funcs) = convertToOps(token, funcTokens, converted.userFuncs, commands)
      Converted(converted.operations :+ op, funcs)
    }
  }

  private class LambdaCounter(var count: Int = 0) {
    def inc: Int = {
      count = count + 1
      count
    }
  }

  private val opsReduce =
    new PartialFunction[List[Either[(Operation, NamedUserFunctions, LambdaUserFunctions), ErrorMessage]], (List[Operation], NamedUserFunctions, LambdaUserFunctions)] {
      def isDefinedAt(ops: List[Either[(Operation, NamedUserFunctions, LambdaUserFunctions), ErrorMessage]]) : Boolean =
        !ops.exists(_.isRight)

      def apply(
          ops: List[Either[(Operation, NamedUserFunctions, LambdaUserFunctions), ErrorMessage]]
      ): (List[Operation], NamedUserFunctions, LambdaUserFunctions) = {
        val tl = ops.map(_.left.get)
        (
          tl.map(_._1).filterNot(o => o.equals(NoOperation)), // Gather all operations into a list without nops
          tl.map(_._2).reduce(_ ++ _), // Combine all the named functions into one map
          tl.map(_._3).reduce(_ ++ _) // Combine all the lambda functions into one map
        )
      }
    }

  private val errReduce = new PartialFunction[List[
    Either[(Operation, NamedUserFunctions, LambdaUserFunctions), ErrorMessage]
  ], Either[(Operation, NamedUserFunctions, LambdaUserFunctions), ErrorMessage]] {
    def isDefinedAt(errors: List[Either[(Operation, NamedUserFunctions, LambdaUserFunctions), ErrorMessage]]) : Boolean =
      errors.exists(_.isRight)
    def apply(errors: List[Either[(Operation, NamedUserFunctions, LambdaUserFunctions), ErrorMessage]]) : Either[(Operation, NamedUserFunctions, LambdaUserFunctions), ErrorMessage] =
      errors.filter(_.isRight).head
  }

  // Converts parsed mbl into a program with functions and function references
  private def convertMbl(
      element: MblElement,
      lc: LambdaCounter = new LambdaCounter()
  ): Either[(Operation, NamedUserFunctions, LambdaUserFunctions), ErrorMessage] =
    element match {
      case MblList(Nil) =>
        Left((NoOperation, Map.empty[String, UserFunctionNamed], Map.empty[Int, UserFunctionLambda]))
      case MblList(MblSymbol("defun") :: MblSymbol(name) :: elements) =>
        (errReduce orElse (opsReduce andThen { c =>
          val n = UserFunctionNamed(c._1, name)
          Left((NoOperation, c._2 + (name -> n), c._3))
        }))(elements.map(e => convertMbl(e, lc)))

      case MblList(MblSymbol("if") :: MblQuoted(color) :: elements) =>
        (errReduce orElse (opsReduce andThen { c =>
          val l = UserFunctionLambda(c._1, lc.inc)
          Left(
            (
              IfColor(color, l),
              c._2,
              c._3 + (l.id -> l)
            )
          )
        }))(elements.map(e => convertMbl(e, lc)))

      case MblList(elements) => // Usually the "main" function but if its not the outermost function, and shows up anywhere else it will just be executed inline as a kind of lambda
        (errReduce orElse (opsReduce andThen { c =>
          val l = UserFunctionLambda(c._1, lc.inc)
          Left(
            (
              UserFunctionRefByLambdaId(l.id),
              c._2,
              c._3 + (l.id -> l)
            )
          )
        }))(elements.map(e => convertMbl(e, lc)))
      case MblSymbol("left") =>
        Left((ChangeRobotDirection, Map.empty[String, UserFunctionNamed], Map.empty[Int, UserFunctionLambda]))
      case MblSymbol("pickUp") =>
        Left((PickUpItem, Map.empty[String, UserFunctionNamed], Map.empty[Int, UserFunctionLambda]))
      case MblSymbol("drop") =>
        Left((SetItemDown, Map.empty[String, UserFunctionNamed], Map.empty[Int, UserFunctionLambda]))
      case MblSymbol("forward") =>
        Left((MoveRobotForwardOneSpot, Map.empty[String, UserFunctionNamed], Map.empty[Int, UserFunctionLambda]))
      case MblSymbol(functionName) =>
        Left(
          (UserFunctionRefByName(functionName),
           Map.empty[String, UserFunctionNamed],
           Map.empty[Int, UserFunctionLambda])
        )
      case unknown =>
        Right(s"Unknown element $unknown")
    }

  def compileMbl(mbl: String, grid: GridMap, problem: Problem) : Either[GridAndProgram, ErrorMessage] = {
    MblParser.parse(mbl) match {
      case Left(mblList) =>
        convertMbl(mblList) match {
          case Left((topLevelOp, byNamed, byLambdaId)) =>
            val program = topLevelOp match {
              case uf: UserFunction => uf
              case op: Operation => UserFunction(Seq(op))
            }
            checkReferences(byNamed.values.toSeq ++ byLambdaId.values.toSeq :+ program,
              lookUpReference(Map.empty[String, UserFunction], byNamed, byLambdaId)) match {
              case Nil =>
                fixReferences(byNamed.values.toSeq ++ byLambdaId.values.toSeq :+ program,
                  lookUpReference(Map.empty[String, UserFunction], byNamed, byLambdaId))
                processBoard(grid).map(g => GridAndProgram(g, program, problem)) match {
                  case Some(gp) => Left(gp)
                  case None => Right("Unable to process grid")
                }
              case unknown =>
                val printable = unknown.map {
                  case UserFunctionRefByName(n) => s"named($n)" // Most common when a user misspells a function
                  case UserFunctionRefByLambdaId(id) => s"lambda($id)" // Indicates a bug in the parser
                  case UserFunctionRefById(id) => s"icon($id)" // Won't appear until we can convert from simple to advanced editing
                }
                Right(s"Reference to unknown function${if(printable.length > 1) "" else "s"}: ${printable.mkString(", ")}")

            }
          case Right(error) =>
            Right(error)
        }
      case Right(error) =>
        Right(error)
    }
  }
}
