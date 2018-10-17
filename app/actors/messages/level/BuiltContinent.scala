package actors.messages.level

import actors.VideoHintActor.embedURL
import actors.messages.ResponseLambdas
import level_gen.models.ContinentStruct
import models.Problem.makeProblem
import models._
import play.api.libs.functional.syntax._
import play.api.libs.json._

object BuiltContinent {
  def apply(lambdas: Lambdas, continentStruct: ContinentStruct): BuiltContinent =
    new BuiltContinent(
      gridMap = buildGrid(continentStruct.gridMap),
      description = makeDescription(continentStruct),
      mainMax = continentStruct.mainMax,
      initialRobotState = setInitialRobot(continentStruct),
      stagedEnabled = continentStruct.stagedEnabled,
      activeEnabled = continentStruct.activeEnabled,
      lambdas = ResponseLambdas(lambdas),
      toolList = ToolList(),
      specialParameters = continentStruct.specialParameters,
      problem = problemGen(continentStruct.problem),
      initFocus = createInitFocus(continentStruct.initFocus),
      freeHint = freeHintUrl(continentStruct.freeHint),
      stepControl = new StepControl(continentStruct.specialParameters, continentStruct.description, lambdas)
    )

  case class InitialRobotState(location: Map[String, Int], orientation: String, holding: List[String])

  import daos.DefaultCommands._

  def freeHintUrl(idOpt: Option[String]): Option[String] = idOpt match {
    case Some(id) => Some(embedURL(id))
    case None => None
  }

  def findRobotCoords(grid: List[String], coords: Map[String, Int] = Map("x" -> 0, "y" -> 0)): Map[String, Int] =
    grid match {
      case r :: _ if r contains "(R)" => Map("x" -> coords("x"), "y" -> prepRow(r).indexOf("(R)"))
      case _ :: rest => findRobotCoords(rest, Map("x" -> (coords("x") + 1), "y" -> 0))
    }

  def setInitialRobot(continentStruct: ContinentStruct): InitialRobotState =
    InitialRobotState(location = findRobotCoords(continentStruct.gridMap),
                      orientation = continentStruct.robotOrientation.toString,
                      holding = List.empty[String])

  def createInitFocus(initFocus: List[String]): List[String] = initFocus.map { a =>
    cmds.find(_.name.getOrElse("") == a) match {
      case Some(token) => token.created_id
      case None =>
        a match {
          case id if id == "open-staged" => "open-staged"
          case id => id
        }
    }
  }

  def problemGen(rawProblem: String): Problem = makeProblem(rawProblem)

  def parseCamelCase(camelStr: String): String = camelStr.toList match {
    case Nil => camelStr.toString
    case l :: rest =>
      if (l.isUpper) " " + l.toString + parseCamelCase(rest.mkString(""))
      else l.toString + parseCamelCase(rest.mkString(""))
  }

  def makeDescription(continentStruct: ContinentStruct): String = {
    "<p>" +
    s"${continentStruct.description
      .split("!!")
      .map {
        case a if a == "[P]" => problemGen(continentStruct.problem).problem.getOrElse("0")
        case a if a contains "[img]" =>
          a.replace("[img]", "<img ") + " />"
        case a => a
      }
      .mkString(" ")
      .split("\n")
      .mkString(" <br /> ")}" +
    s"</p>"
  }

  private def prepRow(row: String): List[String] = row.split(" ").toList.filterNot(_ == "")

  def buildGrid(gridMap: List[String]): List[List[GridPart]] = {
    gridMap map { row =>
      prepRow(row) map { key =>
        GridPart.apply(key)
      }
    }
  }

  implicit val initialRobotStateWrites: Writes[InitialRobotState] = (
    (JsPath \ "location").write[Map[String, Int]] and
    (JsPath \ "orientation").write[String] and
    (JsPath \ "holding").write[List[String]]
  )(unlift(InitialRobotState.unapply))

  val stepDataReads: Reads[BuiltContinent] = (
    (JsPath \ "lambdas").read[Lambdas] and
    (JsPath \ "continentStruct").read[ContinentStruct]
  )(BuiltContinent(_, _))

  val stepDataWrites: Writes[BuiltContinent] = (
    (JsPath \ "gridMap").write[List[List[GridPart]]] and
    (JsPath \ "description").write[String] and
    (JsPath \ "mainMax").write[Int] and
    (JsPath \ "initialRobotState").write[InitialRobotState] and
    (JsPath \ "stagedEnabled").write[Boolean] and
    (JsPath \ "activeEnabled").write[Boolean] and
    (JsPath \ "lambdas").write[ResponseLambdas] and
    (JsPath \ "toolList").write[ToolList] and
    (JsPath \ "specialParameters").write[List[String]] and
    (JsPath \ "problem").write[Problem] and
    (JsPath \ "initFocus").write[List[String]] and
    (JsPath \ "freeHint").writeNullable[String] and
    OWrites[StepControl](_ => Json.obj())
  )(unlift(BuiltContinent.unapply))

  implicit val stepDataFormat: Format[BuiltContinent] =
    Format(stepDataReads, stepDataWrites)
}

case class BuiltContinent(
    gridMap: List[List[GridPart]],
    description: String,
    mainMax: Int,
    initialRobotState: BuiltContinent.InitialRobotState,
    stagedEnabled: Boolean,
    activeEnabled: Boolean,
    lambdas: ResponseLambdas,
    toolList: ToolList,
    specialParameters: List[String],
    problem: Problem,
    initFocus: List[String],
    freeHint: Option[String],
    stepControl: StepControl
)
