package level_gen
import level_gen.models.SuperCluster

object SuperClusters extends Galaxies {
  def getCluster(name: String): SuperCluster = name match {
    case "SuperCluster1" => SuperCluster1
    case _ => throw new Exception(s"$name not a SuperCluster")
  }

  private val SuperCluster1 = SuperCluster(
    name = "SuperCluster1",
    galaxies = superCluster1
  )
}
