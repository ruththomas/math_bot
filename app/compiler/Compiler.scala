package compiler

import compiler.operations._
import daos.CommandIds
import models.{ FuncToken, GridMap, GridPart, Problem }
import play.api.libs.json._

object Compiler {

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
    val firstPass = fixReferences(convertToOps(main, funcTokens, Map.empty[String, UserFunction], commands))
    processBoard(grid).map(g => GridAndProgram(g, new UserFunction(firstPass._1.asInstanceOf[UserFunction].operations), problem))
  }

  // To avoid an infinite loop while processing user functions, user functions are sometimes replaced with refs.
  // Before running the code, replace the refs with the functions
  private def fixReferences(firstPass: (Operation, Map[String, UserFunction])) = {

    val program = firstPass._1.asInstanceOf[UserFunction]

    for {
      f <- firstPass._2.values
    } yield {
      f.operations = f.operations.map {
        case funcRef: UserFunctionRef => firstPass._2(funcRef.created_id)
        case ifColor : IfColor =>
          ifColor.operation match {
            case funcRef : UserFunctionRef =>
              ifColor.copy(operation = firstPass._2(funcRef.created_id))
            case _ =>
              ifColor
          }
        case other => other
      }
    }

    program.operations = program.operations.map {
      case funcRef: UserFunctionRef => firstPass._2(funcRef.created_id)
      case ifColor : IfColor =>
        ifColor.operation match {
          case funcRef : UserFunctionRef =>
            ifColor.copy(operation = firstPass._2(funcRef.created_id))
          case _ =>
            ifColor
        }
      case other => other
    }
    firstPass
  }

  def convertToOps(
                    token      : FuncToken,
                    funcTokens : Map[String, FuncToken],
                    userFuncs  : Map[String, UserFunction],
                    commands   : List[FuncToken]) : (Operation, Map[String, UserFunction]) = {
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
                val converted = convertFunction(token, funcTokens, userFuncs + (id -> UserFunctionRef(id)), commands)
                val uf = new UserFunction(converted.operations)
                (uf, converted.userFuncs.updated(token.created_id, uf))
            }
          case color =>
            userFuncs.get(id) match {
              case Some(uf) =>
                (IfColor(color, uf), userFuncs)
              case None =>
                val converted = convertFunction(token.copy(color = "default"), funcTokens, userFuncs  + (id -> UserFunctionRef(id)), commands)
                val uf = new UserFunction(converted.operations)
                (IfColor(color, uf), converted.userFuncs.updated(token.created_id, uf))
            }
        }
    }
  }

  case class Converted(operations : Seq[Operation], userFuncs : Map[String, UserFunction])

  def convertFunction(
                       funcToken : FuncToken,
                       funcTokens: Map[String, FuncToken],
                       userFuncs  : Map[String, UserFunction],
                       commands   : List[FuncToken]
                     ) : Converted = {
    val operations = funcTokens(funcToken.created_id).func.get
    operations.foldLeft[Converted](Converted(Seq.empty[Operation], userFuncs)) {
      (converted, token) =>
        val (op, funcs) = convertToOps(token, funcTokens, converted.userFuncs, commands)
        Converted(converted.operations :+ op, funcs)
    }
  }
}
