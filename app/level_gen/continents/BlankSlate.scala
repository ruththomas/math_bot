package level_gen.continents
import actors.messages.AssignedFunction
import level_gen.LevelGenTags
import level_gen.models.{CelestialSystem, ContinentStruct}

trait BlankSlate extends LevelGenTags {
  val blankSlateChildren = List(
    CelestialSystem(
      name = "1",
      kind = continent,
      continentStruct = Some(
        ContinentStruct(
          gridMap = List(
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| (R) |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|"
          ),
          description = "Have fun with the robot on a blank slate!",
          mainMax = -1,
          robotOrientation = 0,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = -1,
          assignedStaged = List.empty[AssignedFunction],
          preBuiltActive = List.empty[AssignedFunction],
          allowedActives = None,
          cmdsAvailable = List(
            "moveRobotForwardOneSpot",
            "changeRobotDirection",
            "pickUpItem",
            "setItemDown"
          ),
          specialParameters = List("sandbox"),
          problem = "0",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List.empty[String],
          freeHint = None
        )
      )
    )
  )
}
