package level_gen
import level_gen.models.CelestialSystem

trait Galaxies extends StarSystems with LevelGenTags {
  val superCluster1Children: List[CelestialSystem] = List(
    CelestialSystem(
      name = "Galaxy1",
      kind = galaxy,
      children = galaxy1Children
    )
  )
}
