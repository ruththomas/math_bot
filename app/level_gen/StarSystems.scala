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
    )
    // 2 Digit
    // 5 Digit
    // 1 Decimal
    // 2 Decimals
  )
}
