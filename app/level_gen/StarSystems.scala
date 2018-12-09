package level_gen
import level_gen.models.{CelestialSystem, StarSystemStruct}

trait StarSystems extends Planets with LevelGenTags {
  val galaxy1Children = List(
    CelestialSystem(
      name = "Code",
      kind = starSystem,
      children = programmingChildren
    ),
    CelestialSystem(
      name = "1 Digit",
      kind = starSystem,
      children = arithmeticChildren
    ),
    CelestialSystem(
      name = "Just Fun",
      kind = starSystem,
      children = sandboxChildren,
      starSystemStruct = Some(StarSystemStruct("Practice your skills", freePlay = true))
    )
  )
}
