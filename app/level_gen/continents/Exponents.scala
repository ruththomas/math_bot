package level_gen.continents
import actors.messages.AssignedFunction
import level_gen.LevelGenTags
import level_gen.models.{CelestialSystem, ContinentStruct}

trait Exponents extends LevelGenTags {
  val exponentsChildren = List(
    CelestialSystem(
      name = "1",
      kind = continent,
      continentStruct = Some(
        ContinentStruct(
          gridMap = List(
            "|E| |E| |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| |E| (R) |E| |E| ($) |E|  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| [1] [10] [100] [1g] [10g]"
          ),
          description = "Place the correct amount on the portal",
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
          specialParameters = List.empty[String],
          problem = "2 ^ 2",
          clearMain = false,
          initFocus = List("Walk"),
          videoHints = List.empty[String],
          freeHint = None
        )
      )
    ),
    CelestialSystem(
      name = "2",
      kind = continent,
      continentStruct = Some(
        ContinentStruct(
          gridMap = List(
            "|E| |E| |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| |E| (R) |E| |E| ($) |E|  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| [1] [10] [100] [1g] [10g]"
          ),
          description = "Place the correct amount on the portal",
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
          specialParameters = List.empty[String],
          problem = "3 ^ 2",
          clearMain = false,
          initFocus = List("Walk"),
          videoHints = List.empty[String],
          freeHint = None
        )
      )
    ),
    CelestialSystem(
      name = "3",
      kind = continent,
      continentStruct = Some(
        ContinentStruct(
          gridMap = List(
            "|E| |E| |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| |E| (R) |E| |E| ($) |E|  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| [1] [10] [100] [1g] [10g]"
          ),
          description = "Place the correct amount on the portal",
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
          specialParameters = List.empty[String],
          problem = "1 ^ 2",
          clearMain = false,
          initFocus = List("Walk"),
          videoHints = List.empty[String],
          freeHint = None
        )
      )
    ),
    CelestialSystem(
      name = "4",
      kind = continent,
      continentStruct = Some(
        ContinentStruct(
          gridMap = List(
            "|E| |E| |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| |E| (R) |E| |E| ($) |E|  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| [1] [10] [100] [1g] [10g]"
          ),
          description = "Place the correct amount on the portal",
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
          specialParameters = List.empty[String],
          problem = "3 ^ 1",
          clearMain = false,
          initFocus = List("Walk"),
          videoHints = List.empty[String],
          freeHint = None
        )
      )
    ),
    CelestialSystem(
      name = "5",
      kind = continent,
      continentStruct = Some(
        ContinentStruct(
          gridMap = List(
            "|E| |E| |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| |E| (R) |E| |E| ($) |E|  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| [1] [10] [100] [1g] [10g]"
          ),
          description = "Place the correct amount on the portal",
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
          specialParameters = List.empty[String],
          problem = "2 ^ 2",
          clearMain = false,
          initFocus = List("Walk"),
          videoHints = List.empty[String],
          freeHint = None
        )
      )
    ),
    CelestialSystem(
      name = "6",
      kind = continent,
      continentStruct = Some(
        ContinentStruct(
          gridMap = List(
            "|E| |E| |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| |E| (R) |E| |E| ($) |E|  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| [1] [10] [100] [1g] [10g]"
          ),
          description = "Place the correct amount on the portal",
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
          specialParameters = List.empty[String],
          problem = "3 ^ 2",
          clearMain = false,
          initFocus = List("Walk"),
          videoHints = List.empty[String],
          freeHint = None
        )
      )
    ),
    CelestialSystem(
      name = "7",
      kind = continent,
      continentStruct = Some(
        ContinentStruct(
          gridMap = List(
            "|E| |E| |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| |E| (R) |E| |E| ($) |E|  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| [1] [10] [100] [1g] [10g]"
          ),
          description = "Place the correct amount on the portal",
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
          specialParameters = List.empty[String],
          problem = "1 ^ 3",
          clearMain = false,
          initFocus = List("Walk"),
          videoHints = List.empty[String],
          freeHint = None
        )
      )
    )
  )
}