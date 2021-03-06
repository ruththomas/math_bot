package actors.messages.level

import actors.VideoHintActor.embedURL
import daos.FunctionsDAO
import level_gen.models.{CelestialSystem, ContinentStruct}
import play.api.libs.functional.syntax._
import play.api.libs.json._
import compiler.ElementKinds._

object BuiltContinent {
  def apply(functions: Functions, continent: CelestialSystem, functionsDAO: Option[FunctionsDAO]): BuiltContinent = {
    val continentStruct = continent.continentStruct.get
    val builtGrid = buildGrid(continentStruct.gridMap)
    new BuiltContinent(
      name = continent.name,
      gridMap = builtGrid,
      description = makeDescription(continentStruct),
      mainMax = continentStruct.maxMain,
      initialRobotState = setInitialRobot(continentStruct),
      stagedEnabled = continentStruct.stagedEnabled,
      activeEnabled = continentStruct.activeEnabled,
      lambdas = PreparedFunctions(functions, continentStruct, functionsDAO.get),
      toolList = gatherTools(builtGrid),
      specialParameters = continentStruct.specialParameters,
      problem = problemGen(continentStruct.problem),
      initFocus = createInitFocus(continentStruct.initFocus),
      freeHint = freeHintUrl(continentStruct.freeHint),
      evalEachFrame = continentStruct.evalEachFrame.getOrElse(false),
      stepControl = new ContinentControl(continentStruct.specialParameters, continentStruct.description, functions)
    )
  }

  case class InitialRobotState(location: Map[String, Int], orientation: String, holding: List[String])

  import Problem._
  import daos.DefaultCommands._

  def gatherTools(gridMap: List[List[GridPart]]): List[ClientElement] = {
    import compiler.ElementKinds._
    val tools = gridMap.flatten.filter(_.tools.nonEmpty).flatMap(_.tools).distinct.map(t => t.name -> t).toMap
    if (tools.nonEmpty) {
      listedControlElements.map(ClientElement.apply) ::: listedElements.flatMap(e => tools.get(e.name))
    } else listedControlElements.filter(_.name == "white").map(ClientElement.apply)
  }

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
    (JsPath \ "functions").read[Functions] and
    (JsPath \ "continent").read[CelestialSystem]
  )(BuiltContinent(_, _, None))

  val stepDataWrites: Writes[BuiltContinent] = (
    (JsPath \ "name").write[String] and
    (JsPath \ "gridMap").write[List[List[GridPart]]] and
    (JsPath \ "description").write[String] and
    (JsPath \ "mainMax").write[Int] and
    (JsPath \ "initialRobotState").write[InitialRobotState] and
    (JsPath \ "stagedEnabled").write[Boolean] and
    (JsPath \ "activeEnabled").write[Boolean] and
    (JsPath \ "lambdas").write[PreparedFunctions] and
    (JsPath \ "toolList").write[List[ClientElement]] and
    (JsPath \ "specialParameters").write[List[String]] and
    (JsPath \ "problem").write[Problem] and
    (JsPath \ "initFocus").write[List[String]] and
    (JsPath \ "freeHint").writeNullable[String] and
    (JsPath \ "evalEachFrame").write[Boolean] and
    OWrites[ContinentControl](_ => Json.obj())
  )(unlift(BuiltContinent.unapply))

  implicit val stepDataFormat: Format[BuiltContinent] =
    Format(stepDataReads, stepDataWrites)
}

case class BuiltContinent(
    name: String,
    gridMap: List[List[GridPart]],
    description: String,
    mainMax: Int,
    initialRobotState: BuiltContinent.InitialRobotState,
    stagedEnabled: Boolean,
    activeEnabled: Boolean,
    lambdas: PreparedFunctions,
    toolList: List[ClientElement],
    specialParameters: List[String],
    problem: Problem,
    initFocus: List[String],
    freeHint: Option[String],
    evalEachFrame: Boolean,
    stepControl: ContinentControl
)
