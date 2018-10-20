package actors.convert_flow
import actors.LevelActor._
import actors.messages.ActorFailed
import akka.NotUsed
import akka.stream.scaladsl.Flow
import play.api.libs.json.{JsValue, Json, OFormat}
import actors.messages.level.Function

object LevelRequestConvertFlow extends SocketRequestConvertFlow {
  implicit val requestFormat: OFormat[LevelRequest] = Json.format[LevelRequest]
  final case class LevelRequest(
      action: String,
      continentRequest: Option[List[Int]] = None,
      function: Option[Function] = None
  )
  override def jsonToCommand(msg: JsValue): Any = {
    Json.fromJson[LevelRequest](msg).asOpt match {
      case Some(LevelRequest(action, _, _))
          if action == "get-raw-super-cluster" => // was used for testing, is probably garbage
        GetSuperCluster("SuperCluster1")
      case Some(LevelRequest(action, _, _)) if action == "get-stats" =>
        GetStats("mathbot|xa5skmltsyyqsRGgW3JA6A==") // todo - tokenId needs to come from cookie
//        GetStats("somecrazymofo")
      case Some(LevelRequest(action, _, _)) if action == "get-super-cluster" =>
        GetSuperClusterData("somecrazymofo")
      case Some(LevelRequest(action, _, _)) if action == "get-galaxy" =>
        GetGalaxyData("somecrazymofo")
      case Some(LevelRequest(action, _, _)) if action == "get-star-system" =>
        GetStarSystemData("somecrazymofo", "000")
      case Some(LevelRequest(action, _, _)) if action == "get-planet" =>
        GetPlanetData("somecrazymofo")
      case Some(LevelRequest(action, Some(List(starSystem, planet, continent)), _)) if action == "get-continent" =>
        GetContinentData("mathbot|xa5skmltsyyqsRGgW3JA6A==", s"0$starSystem$planet$continent")
      case Some(LevelRequest(action, _, Some(function))) if action == "update-function" =>
        UpdateFunction("mathbot|xa5skmltsyyqsRGgW3JA6A==", function)
      case Some(LevelRequest(action, Some(List(starSystem, planet, continent)), _)) if action == "change-level" =>
        ChangeLevel("somecrazymofo", starSystem, planet, continent)
      case _ => ActorFailed("Bad json input")
    }
  }
  override def apply(): Flow[JsValue, Any, NotUsed] = {
    Flow[JsValue].map(jsonToCommand)
  }
}
