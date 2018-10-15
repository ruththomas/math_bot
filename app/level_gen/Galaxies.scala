package level_gen
import level_gen.models.{Galaxy, StarSystem}

trait Galaxies extends StarSystems {
  val superCluster1: List[Galaxy] = List(
    Galaxy(
      name = "Galaxy1",
      starSystems = galaxy1
    )
  )
}
