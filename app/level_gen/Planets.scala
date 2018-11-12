package level_gen
import level_gen.models.CelestialSystem

trait Planets extends Continents with LevelGenTags {
  val programming = List(
    CelestialSystem(
      name = "BasicProgramming",
      kind = planet,
      children = basicProgramming
    ),
    CelestialSystem(
      name = "Counting",
      kind = planet,
      children = counting
    ),
    CelestialSystem(
      name = "Numbers",
      kind = planet,
      children = numbers
    ),
    CelestialSystem(
      name = "Recursion",
      kind = planet,
      children = recursion
    ),
    CelestialSystem(
      name = "Conditionals",
      kind = planet,
      children = conditionals
    )
  )

  val arithmetic = List(
    CelestialSystem(
      name = "Addition",
      kind = planet,
      children = addition
    ),
    CelestialSystem(
      name = "Subtraction",
      kind = planet,
      children = subtraction
    ),
    CelestialSystem(
      name = "Multiplication",
      kind = planet,
      children = multiplication
    ),
    CelestialSystem(
      name = "Division",
      kind = planet,
      children = division
    )
  )
}
