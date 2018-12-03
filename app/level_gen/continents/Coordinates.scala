package level_gen.continents
import actors.messages.AssignedFunction
import level_gen.LevelGenTags
import level_gen.models.{CelestialSystem, ContinentStruct}

trait Coordinates extends LevelGenTags {
  val coordinatesChildren = List(
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
          initFocus = List.empty[String],
          videoHints = List("7HsTgJq7Ir0", "qBB6_IS3THU", "sgfjPCRt33g"),
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
            "|E| |E| (R) |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "($) |E| |E| |E| |E| [1] [10] [100] [1g] [10g]"
          ),
          description = "Place the correct amount on the portal",
          mainMax = 1,
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
          problem = "0",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("aFkFxgIhIhI"),
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
            "|E| |E| |E| |E| |E| |E| |E|  (R)   |E|  |E|",
            "|E| |E| |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| |E| ($) |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| [1] [10] [100] [1g] [10g]"
          ),
          description = "Place the correct amount on the portal",
          mainMax = 1,
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
          problem = "0",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("-b8Ii57XREA"),
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
            "(R) |E| |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| ($) [10] [100] [1g] [10g]"
          ),
          description = "Place the correct amount on the portal",
          mainMax = 1,
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
          problem = "0",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("FOp1-UPtQEc"),
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
            "|E| |E| |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| ($) |E|  |E|   (R)  |E|",
            "|E| |E| |E| |E| |E| [1] [10] [100] [1g] [10g]"
          ),
          description = "Place the correct amount on the portal",
          mainMax = 1,
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
          problem = "0",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("nAYeSfUjLj8"),
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
            "|E| |E| |E| |E| (R) |E| |E|  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| ($) |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| [1] [10] [100] [1g] [10g]"
          ),
          description = "Place the correct amount on the portal",
          mainMax = 1,
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
          problem = "0",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("wcVNnhnqaCc"),
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
            "|E| |E| |E| |E| |E| |E| (R)  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| ($) |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| [1] [10] [100] [1g] [10g]"
          ),
          description = "Place the correct amount on the portal",
          mainMax = 1,
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
          problem = "0",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("uwR5lrMaUSE"),
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
          mainMax = 1,
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
          problem = "0",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("G_zAgyv8C48"),
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
            "(R) |E| |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| ($) [10] [100] [1g] [10g]"
          ),
          description = "Place the correct amount on the portal",
          mainMax = 1,
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
          problem = "0",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("FOp1-UPtQEc"),
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
            "|E| |E| |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| ($) |E|  |E|   (R)  |E|",
            "|E| |E| |E| |E| |E| [1] [10] [100] [1g] [10g]"
          ),
          description = "Place the correct amount on the portal",
          mainMax = 1,
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
          problem = "0",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("nAYeSfUjLj8"),
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
            "|E| |E| |E| |E| (R) |E| |E|  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| ($) |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| [1] [10] [100] [1g] [10g]"
          ),
          description = "Place the correct amount on the portal",
          mainMax = 1,
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
          problem = "0",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("wcVNnhnqaCc"),
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
            "|E| |E| |E| |E| |E| |E| |E|  (R)   |E|  |E|",
            "|E| |E| |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| |E| ($) |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| [1] [10] [100] [1g] [10g]"
          ),
          description = "Place the correct amount on the portal",
          mainMax = 1,
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
          problem = "0",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("-b8Ii57XREA"),
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
            "|E| |E| (R) |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "($) |E| |E| |E| |E| [1] [10] [100] [1g] [10g]"
          ),
          description = "Place the correct amount on the portal",
          mainMax = 1,
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
          problem = "0",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("aFkFxgIhIhI"),
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
            "|E| |E| |E| |E| |E| |E| (R)  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| ($) |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| [1] [10] [100] [1g] [10g]"
          ),
          description = "Place the correct amount on the portal",
          mainMax = 1,
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
          problem = "0",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("uwR5lrMaUSE"),
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
            "|E| |E| |E| |E| (R) |E| |E|  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| ($) |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| [1] [10] [100] [1g] [10g]"
          ),
          description = "Place the correct amount on the portal",
          mainMax = 1,
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
          problem = "0",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("wcVNnhnqaCc"),
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
            "|E| |E| |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| ($) |E|  |E|   (R)  |E|",
            "|E| |E| |E| |E| |E| [1] [10] [100] [1g] [10g]"
          ),
          description = "Place the correct amount on the portal",
          mainMax = 1,
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
          problem = "0",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("nAYeSfUjLj8"),
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
          mainMax = 1,
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
          problem = "0",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("G_zAgyv8C48"),
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
            "|E| |E| |E| (R) |E| |E| |E|  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| |E| ($) |E| |E| |E| |E|  |E|   |E|  |E|",
            "|E| |E| |E| |E| |E| [1] [10] [100] [1g] [10g]"
          ),
          description = "Place the correct amount on the portal",
          mainMax = 1,
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
          problem = "0",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("nYlx6ogp9RA"),
          freeHint = None
        )
      )
    )
  )
}
