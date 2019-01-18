package level_gen.sandbox

import actors.messages.AssignedFunction
import actors.messages.level.DefaultFunctions
import level_gen.LevelGenTags
import level_gen.models.{CelestialSystem, ContinentStruct}

object Sandbox extends LevelGenTags {
  val sandbox = CelestialSystem(
    name = "1",
    kind = continent,
    continentStruct = Some(
      ContinentStruct(
        gridMap = List(
          "[-.00001] [-.0001] [-.001] [-.01] [-.1] [-1] [-10] [-100] [-1000] [-10000]",
          "[x]       |E|      |E|     |E|    |E|   |E|  |E|   |E|    |E|     |E|",
          "[y]       |E|      (R)     |E|    |E|   |E|  |E|   |E|    |E|     |E|",
          "[z]       |E|      |E|     |E|    |E|   |E|  |E|   |E|    |E|     |E|",
          "[.00001]  [.0001]  [.001]  [.01]  [.1]  [1]  [10]  [100]  [1000]  [10000]"
        ),
        description = "Practice you arithmetic functions!",
        mainMax = -1,
        robotOrientation = 0,
        stagedEnabled = true,
        activeEnabled = true,
        stagedQty = -1,
        assignedStaged = List.empty[AssignedFunction],
        preBuiltActive = List.empty[AssignedFunction],
        allowedActives = None,
        cmdsAvailable = None,
        specialParameters = List("sandbox"),
        problem = "0",
        clearMain = false,
        initFocus = List.empty[String],
        videoHints = List.empty[String],
        freeHint = Some("sZhPULEkN2k")
      )
    )
  )
}
