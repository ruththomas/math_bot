package level_gen.continents
import actors.messages.AssignedFunction
import level_gen.LevelGenTags
import level_gen.models.{CelestialSystem, ContinentStruct}

trait Multiplication extends LevelGenTags {
  val multiplicationChildren = List(
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
            "|E| |E| |E| |E| |E| [1] [10] [100] [1000] [10000]"
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
          problem = "2 * 2",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("ppkafvWniJE"),
          freeHint = Some("ppkafvWniJE")
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
            "|E| |E| |E| |E| |E| [1] [10] [100] [1000] [10000]"
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
          problem = "3 * 2",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("Tvb8xXFWsUQ"),
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
            "|E| |E| |E| |E| |E| [1] [10] [100] [1000] [10000]"
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
          problem = "4 * 2",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("7G-P2mHhpUA"),
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
            "|E| |E| |E| |E| |E| [1] [10] [100] [1000] [10000]"
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
          problem = "8 * 1",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("z4oUZIRGMFA"),
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
            "|E| |E| |E| |E| |E| [1] [10] [100] [1000] [10000]"
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
          problem = "4 * 2",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("7G-P2mHhpUA"),
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
            "|E| |E| |E| |E| |E| [1] [10] [100] [1000] [10000]"
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
          problem = "3 * 2",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("Tvb8xXFWsUQ"),
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
            "|E| |E| |E| |E| |E| [1] [10] [100] [1000] [10000]"
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
          problem = "2 * 4",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("PXvp81TuhQY", "2tuaC-SdCJw"),
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
            "|E| |E| |E| |E| |E| [1] [10] [100] [1000] [10000]"
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
          problem = "4 * 2",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("7G-P2mHhpUA"),
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
            "|E| |E| |E| |E| |E| [1] [10] [100] [1000] [10000]"
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
          problem = "2 * 3",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("8yOP_lpxA2A", "Yk3ABZWYKYQ"),
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
            "|E| |E| |E| |E| |E| [1] [10] [100] [1000] [10000]"
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
          problem = "2 * 2",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("ppkafvWniJE"),
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
            "|E| |E| |E| |E| |E| [1] [10] [100] [1000] [10000]"
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
          problem = "3 * 2",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("Tvb8xXFWsUQ"),
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
            "|E| |E| |E| |E| |E| [1] [10] [100] [1000] [10000]"
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
          problem = "4 * 2",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("7G-P2mHhpUA"),
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
            "|E| |E| |E| |E| |E| [1] [10] [100] [1000] [10000]"
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
          problem = "1 * 1",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("Sr8QUWOdh24"),
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
            "|E| |E| |E| |E| |E| [1] [10] [100] [1000] [10000]"
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
          problem = "2 * 3",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("8yOP_lpxA2A", "Yk3ABZWYKYQ"),
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
            "|E| |E| |E| |E| |E| [1] [10] [100] [1000] [10000]"
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
          problem = "4 * 2",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("7G-P2mHhpUA"),
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
            "|E| |E| |E| |E| |E| [1] [10] [100] [1000] [10000]"
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
          problem = "3 * 3",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("aoMlhKGn5yI"),
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
            "|E| |E| |E| |E| |E| [1] [10] [100] [1000] [10000]"
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
          problem = "2 * 4",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("PXvp81TuhQY", "2tuaC-SdCJw"),
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
            "|E| |E| |E| |E| |E| [1] [10] [100] [1000] [10000]"
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
          problem = "1 * 9",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("bI-JMwQrq0k", "eiYAQ8o-ZfE"),
          freeHint = None
        )
      )
    )
  )
}
