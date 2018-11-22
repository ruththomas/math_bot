package level_gen
import level_gen.models.CelestialSystem

trait StarSystems extends Planets with LevelGenTags {
  val galaxy1Children = List(
    CelestialSystem(
      name = "Programming",
      kind = starSystem,
      children = programmingChildren
    ),
    CelestialSystem(
      name = "Single Digit Arithmetic",
      kind = starSystem,
      children = arithmeticChildren
    )
  )
}
