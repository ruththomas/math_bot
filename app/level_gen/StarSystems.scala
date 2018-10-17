package level_gen
import level_gen.models.CelestialSystem

trait StarSystems extends Planets with LevelGenTags {
  val galaxy1 = List(
    CelestialSystem(
      name = "Programming",
      kind = starSystem,
      children = programming
    )
  )
}
