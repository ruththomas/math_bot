package level_gen.continents
import actors.messages.AssignedFunction
import level_gen.LevelGenTags
import level_gen.models.{CelestialSystem, ContinentStruct}

trait Numbers extends LevelGenTags {
  val numbersChildren = List(
    CelestialSystem(
      name = "Number7",
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
          description = "Create a function to put 7 kitties on the portal.",
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
          problem = "7",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("AxPKFXxcFFY", "zcHdtTmMddM")
        )
      )
    ),
    CelestialSystem(
      name = "Number8",
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
          description = "Create a function to put 8 kitties on the portal.",
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
          problem = "8",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("clFSrrU-h3k")
        )
      )
    ),
    CelestialSystem(
      name = "SevenAgain",
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
          description = "Reuse your function to put 7 kitties on the portal.",
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
          problem = "7",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("HOm0_YM1V_A")
        )
      )
    ),
    CelestialSystem(
      name = "Number2",
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
          description = "Create a function to put 2 kitties on the portal.",
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
          problem = "2",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("URxnxjxRz1w")
        )
      )
    ),
    CelestialSystem(
      name = "SevenAgainAgain",
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
          description = "Reuse your function to put 7 kitties on the portal.",
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
          problem = "7",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("GXO_6gDCU7s")
        )
      )
    ),
    CelestialSystem(
      name = "Number3",
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
          description = "Create a function to put 3 kitties on the portal.",
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
          problem = "3",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("vtg0vTHLG9o")
        )
      )
    ),
    CelestialSystem(
      name = "Reuse7",
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
          description = "Reuse your function to put 7 kitties on the portal.",
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
          problem = "7",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("LZ9zgvQOE_8")
        )
      )
    ),
    CelestialSystem(
      name = "Number5",
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
          description = "Create a function to put 5 kitties on the portal.",
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
          problem = "5",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("UpxGarD5nn4")
        )
      )
    ),
    CelestialSystem(
      name = "ReuseCode",
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
          description = "You know what to do. No more repetitive tasks for you!",
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
          problem = "7",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("UZZpDk8SpUI")
        )
      )
    )
  )
}
