package level_gen
import level_gen.models.Planet

trait Planets extends Continents {
  val programming = List(
    Planet(
      name = "BasicProgramming",
      continents = basicProgramming
    ),
    Planet(
      name = "Counting",
      continents = counting
    ),
    Planet(
      name = "Numbers",
      continents = numbers
    ),
    Planet(
      name = "Recursion",
      continents = recursion
    ),
    Planet(
      name = "Conditionals",
      continents = conditionals
    )
  )
}
