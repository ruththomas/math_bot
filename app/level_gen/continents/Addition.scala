package level_gen.continents
import actors.messages.AssignedFunction
import level_gen.LevelGenTags
import level_gen.models.{CelestialSystem, ContinentStruct}

trait Addition extends LevelGenTags {
  val additionChildren = List(
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
          problem = "1 + 2",
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
          problem = "2 + 5",
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
          problem = "4 + 3",
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
          problem = "8 + 1",
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
          problem = "6 + 2",
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
          problem = "5 + 4",
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
          problem = "7 + 2",
          clearMain = false,
          initFocus = List("Walk"),
          videoHints = List.empty[String],
          freeHint = None
        )
      )
    ),
    CelestialSystem(
      name = "8",
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
          problem = "4 + 4",
          clearMain = false,
          initFocus = List("Walk"),
          videoHints = List.empty[String],
          freeHint = None
        )
      )
    ),
    CelestialSystem(
      name = "9",
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
          problem = "3 + 6",
          clearMain = false,
          initFocus = List("Walk"),
          videoHints = List.empty[String],
          freeHint = None
        )
      )
    ),
    CelestialSystem(
      name = "10",
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
          problem = "4 + 5",
          clearMain = false,
          initFocus = List("Walk"),
          videoHints = List.empty[String],
          freeHint = None
        )
      )
    ),
    CelestialSystem(
      name = "11",
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
          problem = "2 + 2",
          clearMain = false,
          initFocus = List("Walk"),
          videoHints = List.empty[String],
          freeHint = None
        )
      )
    ),
    CelestialSystem(
      name = "12",
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
          problem = "2 + 4",
          clearMain = false,
          initFocus = List("Walk"),
          videoHints = List.empty[String],
          freeHint = None
        )
      )
    ),
    CelestialSystem(
      name = "13",
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
          problem = "1 + 1",
          clearMain = false,
          initFocus = List("Walk"),
          videoHints = List.empty[String],
          freeHint = None
        )
      )
    ),
    CelestialSystem(
      name = "14",
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
          problem = "3 + 3",
          clearMain = false,
          initFocus = List("Walk"),
          videoHints = List.empty[String],
          freeHint = None
        )
      )
    ),
    CelestialSystem(
      name = "15",
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
          problem = "2 + 7",
          clearMain = false,
          initFocus = List("Walk"),
          videoHints = List.empty[String],
          freeHint = None
        )
      )
    ),
    CelestialSystem(
      name = "16",
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
          problem = "6 + 3",
          clearMain = false,
          initFocus = List("Walk"),
          videoHints = List.empty[String],
          freeHint = None
        )
      )
    ),
    CelestialSystem(
      name = "17",
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
          problem = "5 + 1",
          clearMain = false,
          initFocus = List("Walk"),
          videoHints = List.empty[String],
          freeHint = None
        )
      )
    ),
    CelestialSystem(
      name = "18",
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
          problem = "4 + 2",
          clearMain = false,
          initFocus = List("Walk"),
          videoHints = List.empty[String],
          freeHint = None
        )
      )
    )
  )
}
