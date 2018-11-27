package level_gen
import level_gen.models.{CelestialSystem, StarSystemStruct}

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
    ),
    CelestialSystem(
      name = "Sandbox",
      kind = starSystem,
      children = sandboxChildren,
      starSystemStruct = Some(StarSystemStruct("Practice your skills", freePlay = true))
    )
  )
}
