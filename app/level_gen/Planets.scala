package level_gen
import level_gen.models.CelestialSystem

trait Planets extends Continents with LevelGenTags {
  val programmingChildren = List(
    CelestialSystem(
      name = "BasicProgramming",
      kind = planet,
      children = basicProgrammingChildren
    ),
    CelestialSystem(
      name = "Counting",
      kind = planet,
      children = countingChildren
    ),
    CelestialSystem(
      name = "Numbers",
      kind = planet,
      children = numbersChildren
    ),
    CelestialSystem(
      name = "Recursion",
      kind = planet,
      children = recursionChildren
    ),
    CelestialSystem(
      name = "Conditionals",
      kind = planet,
      children = conditionalsChildren
    )
  )

  val arithmeticChildren = List(
    CelestialSystem(
      name = "Addition",
      kind = planet,
      children = additionChildren
    ),
    CelestialSystem(
      name = "Subtraction",
      kind = planet,
      children = subtractionChildren
    ),
    CelestialSystem(
      name = "Multiplication",
      kind = planet,
      children = multiplicationChildren
    ),
    CelestialSystem(
      name = "Division",
      kind = planet,
      children = divisionChildren
    )
  )
}
