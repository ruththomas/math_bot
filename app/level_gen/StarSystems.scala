package level_gen
import level_gen.models.{Planet, StarSystem}

trait StarSystems extends Planets {
  val galaxy1 = List(
    StarSystem(
      name = "Programming",
      planets = programming
    )
  )
}
