package level_gen
import level_gen.models.CelestialSystem

object SuperClusters extends Galaxies with LevelGenTags {
  def getCluster(name: String): CelestialSystem = name match {
    case "SuperCluster1" => SuperCluster1
    case _ => throw new Exception(s"$name not a SuperCluster")
  }

  private val SuperCluster1 = CelestialSystem(
    name = "SuperCluster1",
    kind = superCluster,
    children = superCluster1
  )
}
