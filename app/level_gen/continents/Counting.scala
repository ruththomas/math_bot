package level_gen.continents
import actors.messages.AssignedFunction
import level_gen.LevelGenTags
import level_gen.models.{CelestialSystem, ContinentStruct}

trait Counting extends LevelGenTags {
  val countingChildren = List(
    CelestialSystem(
      name = "Pickup",
      kind = continent,
      continentStruct = Some(
        ContinentStruct(
          gridMap = List(
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| (R) [TS(1)] ($) |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|"
          ),
          description = "Place the kitty on the portal.",
          mainMax = -1,
          robotOrientation = 90,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = -1,
          assignedStaged = List.empty[AssignedFunction],
          preBuiltActive = List.empty[AssignedFunction],
          cmdsAvailable = List(
            "moveRobotForwardOneSpot",
            "changeRobotDirection",
            "pickUpItem",
            "setItemDown"
          ),
          specialParameters = List.empty[String],
          problem = "1",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("l_CnNj5aO6E", "lnCPjFhMU-s", "BSVWStDirZg")
        )
      )
    ),
    CelestialSystem(
      name = "Pickup3",
      kind = continent,
      continentStruct = Some(
        ContinentStruct(
          gridMap = List(
            "|E| |E| |E| |E|             |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E|             |E| |E| |E| |E| |E| |E|",
            "|E| |E| (R) [TS(1,1,1)]     ($) |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E|             |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E|             |E| |E| |E| |E| |E| |E|"
          ),
          description = "Place 3 kitties on the portal.",
          mainMax = -1,
          robotOrientation = 90,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = -1,
          assignedStaged = List.empty[AssignedFunction],
          preBuiltActive = List.empty[AssignedFunction],
          cmdsAvailable = List(
            "moveRobotForwardOneSpot",
            "changeRobotDirection",
            "pickUpItem",
            "setItemDown"
          ),
          specialParameters = List.empty[String],
          problem = "3",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("l24v6ot_fYw", "Ty-eyXkXW4o", "UtPNfChYedM")
        )
      )
    ),
    CelestialSystem(
      name = "Pickup5",
      kind = continent,
      continentStruct = Some(
        ContinentStruct(
          gridMap = List(
            "[1] |E| ($) |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| (R) |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|"
          ),
          description = "Place 5 kitties on the portal.",
          mainMax = -1,
          robotOrientation = 0,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = -1,
          assignedStaged = List.empty[AssignedFunction],
          preBuiltActive = List.empty[AssignedFunction],
          cmdsAvailable = List(
            "moveRobotForwardOneSpot",
            "changeRobotDirection",
            "pickUpItem",
            "setItemDown"
          ),
          specialParameters = List.empty[String],
          problem = "5",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("aC3-fCaFErk", "jjYQlsgXvyE", "RGgNlFSKErg")
        )
      )
    ),
    CelestialSystem(
      name = "Pickup4",
      kind = continent,
      continentStruct = Some(
        ContinentStruct(
          gridMap = List(
            "[1] |E| ($) |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| (R) |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|"
          ),
          description = "Place 4 kitties on the portal.",
          mainMax = -1,
          robotOrientation = 0,
          stagedEnabled = true,
          activeEnabled = true,
          assignedStaged = List.empty[AssignedFunction],
          stagedQty = -1,
          preBuiltActive = List.empty[AssignedFunction],
          cmdsAvailable = List(
            "moveRobotForwardOneSpot",
            "changeRobotDirection",
            "pickUpItem",
            "setItemDown"
          ),
          specialParameters = List.empty[String],
          problem = "4",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("F5ZWSTBwZNU", "OGLNVzup_TM")
        )
      )
    ),
    CelestialSystem(
      name = "Navigate",
      kind = continent,
      continentStruct = Some(
        ContinentStruct(
          gridMap = List(
            "($) |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| (R) |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|"
          ),
          description = "Create a function to get to the portal.",
          mainMax = 1,
          robotOrientation = 0,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = -1,
          assignedStaged = List.empty[AssignedFunction],
          preBuiltActive = List.empty[AssignedFunction],
          cmdsAvailable = List(
            "moveRobotForwardOneSpot",
            "changeRobotDirection",
            "pickUpItem",
            "setItemDown"
          ),
          specialParameters = List("functionRequired"),
          problem = "0",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("jePF1-i3tck", "7GYsnAFv_qk")
        )
      )
    ),
    CelestialSystem(
      name = "Pickup2WithFunction",
      kind = continent,
      continentStruct = Some(
        ContinentStruct(
          gridMap = List(
            "[1] |E| ($) |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| (R) |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|"
          ),
          description = "Use your function to get to the kitties and put 2 on the portal.",
          mainMax = 9,
          robotOrientation = 0,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = -1,
          assignedStaged = List.empty[AssignedFunction],
          preBuiltActive = List.empty[AssignedFunction],
          cmdsAvailable = List(
            "moveRobotForwardOneSpot",
            "changeRobotDirection",
            "pickUpItem",
            "setItemDown"
          ),
          specialParameters = List("functionRequired"),
          problem = "2",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("92eDgAbBF9w", "6hTzddZVJ4Q")
        )
      )
    ),
    CelestialSystem(
      name = "Pickup1WithFunction",
      kind = continent,
      continentStruct = Some(
        ContinentStruct(
          gridMap = List(
            "[1] |E| ($) |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| (R) |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|"
          ),
          description = "Use your function to get to the kitties and put 1 on the portal.",
          mainMax = 7,
          robotOrientation = 0,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = -1,
          assignedStaged = List.empty[AssignedFunction],
          preBuiltActive = List.empty[AssignedFunction],
          cmdsAvailable = List(
            "moveRobotForwardOneSpot",
            "changeRobotDirection",
            "pickUpItem",
            "setItemDown"
          ),
          specialParameters = List("functionRequired"),
          problem = "1",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("lsZo8ZpnrBo", "IiYfP6Fb3Cw")
        )
      )
    ),
    CelestialSystem(
      name = "Pickup5WithFunction",
      kind = continent,
      continentStruct = Some(
        ContinentStruct(
          gridMap = List(
            "[1] |E| ($) |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| (R) |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|"
          ),
          description = "Use your function to get to the kitties and put 5 on the portal.",
          mainMax = 15,
          robotOrientation = 0,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = -1,
          assignedStaged = List.empty[AssignedFunction],
          preBuiltActive = List.empty[AssignedFunction],
          cmdsAvailable = List(
            "moveRobotForwardOneSpot",
            "changeRobotDirection",
            "pickUpItem",
            "setItemDown"
          ),
          specialParameters = List("functionRequired"),
          problem = "5",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("s8bVR65tR_U", "cqiaefo9zXY")
        )
      )
    ),
    CelestialSystem(
      name = "Pickup9WithFunction",
      kind = continent,
      continentStruct = Some(
        ContinentStruct(
          gridMap = List(
            "[1] |E| ($) |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| (R) |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|"
          ),
          description = "Use your function to get to the kitties and put 9 on the portal.",
          mainMax = 23,
          robotOrientation = 0,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = -1,
          assignedStaged = List.empty[AssignedFunction],
          preBuiltActive = List.empty[AssignedFunction],
          cmdsAvailable = List(
            "moveRobotForwardOneSpot",
            "changeRobotDirection",
            "pickUpItem",
            "setItemDown"
          ),
          specialParameters = List("functionRequired"),
          problem = "9",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("rpO3fqlm33A", "o06S52Fsuvw")
        )
      )
    ),
    CelestialSystem(
      name = "Pickup4WithFunction",
      kind = continent,
      continentStruct = Some(
        ContinentStruct(
          gridMap = List(
            "[1] |E| ($) |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| (R) |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|"
          ),
          description = "Use your function to get to the kitties and put 4 on the portal.",
          mainMax = 13,
          robotOrientation = 0,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = -1,
          assignedStaged = List.empty[AssignedFunction],
          preBuiltActive = List.empty[AssignedFunction],
          cmdsAvailable = List(
            "moveRobotForwardOneSpot",
            "changeRobotDirection",
            "pickUpItem",
            "setItemDown"
          ),
          specialParameters = List("functionRequired"),
          problem = "4",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("tXzR4s9Ptns", "rAp3CisZJYM")
        )
      )
    ),
    CelestialSystem(
      name = "Pickup6WithFunction",
      kind = continent,
      continentStruct = Some(
        ContinentStruct(
          gridMap = List(
            "[1] |E| ($) |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| (R) |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|"
          ),
          description = "Use your function to get to the kitties and put 6 on the portal.",
          mainMax = 17,
          robotOrientation = 0,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = -1,
          assignedStaged = List.empty[AssignedFunction],
          preBuiltActive = List.empty[AssignedFunction],
          cmdsAvailable = List(
            "moveRobotForwardOneSpot",
            "changeRobotDirection",
            "pickUpItem",
            "setItemDown"
          ),
          specialParameters = List("functionRequired"),
          problem = "6",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("--p0EUiWF1I")
        )
      )
    ),
    CelestialSystem(
      name = "Pickup8WithFunction",
      kind = continent,
      continentStruct = Some(
        ContinentStruct(
          gridMap = List(
            "[1] |E| ($) |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| (R) |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|"
          ),
          description = "Use your function to get to the kitties and put 8 on the portal.",
          mainMax = 21,
          robotOrientation = 0,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = -1,
          assignedStaged = List.empty[AssignedFunction],
          preBuiltActive = List.empty[AssignedFunction],
          cmdsAvailable = List(
            "moveRobotForwardOneSpot",
            "changeRobotDirection",
            "pickUpItem",
            "setItemDown"
          ),
          specialParameters = List("functionRequired"),
          problem = "8",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("V3EKeXdQDBM")
        )
      )
    ),
    CelestialSystem(
      name = "Pickup3WithFunction",
      kind = continent,
      continentStruct = Some(
        ContinentStruct(
          gridMap = List(
            "[1] |E| ($) |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| (R) |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|"
          ),
          description = "Use your function to get to the kitties and put 3 on the portal.",
          mainMax = 11,
          robotOrientation = 0,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = -1,
          assignedStaged = List.empty[AssignedFunction],
          preBuiltActive = List.empty[AssignedFunction],
          cmdsAvailable = List(
            "moveRobotForwardOneSpot",
            "changeRobotDirection",
            "pickUpItem",
            "setItemDown"
          ),
          specialParameters = List("functionRequired"),
          problem = "3",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("zWLznLyDVPU")
        )
      )
    )
  )
}
