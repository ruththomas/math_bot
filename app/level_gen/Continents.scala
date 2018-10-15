package level_gen
import actors.messages.AssignedFunction
import level_gen.models.{Continent, ContinentStruct}

trait Continents {
  val basicProgramming = List(
    Continent(
      name = "WalkOne",
      struct = Some(
        ContinentStruct(
          gridMap = List(
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| ($) |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| (R) |E| |E| |E| |E|"
          ),
          description = "Walk to the portal using the walk command.",
          mainMax = 1,
          robotOrientation = 0,
          stagedEnabled = false,
          activeEnabled = true,
          stagedQty = 0,
          assignedStaged = List.empty[AssignedFunction],
          preBuiltActive = List.empty[AssignedFunction],
          allowedActives = Some(List.empty[String]),
          cmdsAvailable = List(
            "moveRobotForwardOneSpot"
          ),
          specialParameters = List.empty[String],
          problem = "0",
          clearMain = false,
          initFocus = List("Walk"),
          videoHints = List("u5BqiML0q70"),
          freeHint = Some("q9V-TnU-0Yw")
        )
      )
    ),
    Continent(
      name = "WalkThree",
      struct = Some(
        ContinentStruct(
          gridMap = List(
            "|E| |E| |E| |E| |E| ($) |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| (R) |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|"
          ),
          description = "Walk to the portal using the walk command 3 times.",
          mainMax = 3,
          robotOrientation = 0,
          stagedEnabled = false,
          activeEnabled = true,
          stagedQty = 0,
          assignedStaged = List.empty[AssignedFunction],
          preBuiltActive = List.empty[AssignedFunction],
          allowedActives = Some(List.empty[String]),
          cmdsAvailable = List(
            "moveRobotForwardOneSpot"
          ),
          specialParameters = List.empty[String],
          problem = "0",
          clearMain = false,
          initFocus = List("Walk"),
          videoHints = List("aGRc1MR91Fk", "hAOSQnzbVLY", "CUbBgS7llOI"),
          freeHint = Some("RUxCBeJx-Bg")
        )
      )
    ),
    Continent(
      name = "WalkThree",
      struct = Some(
        ContinentStruct(
          gridMap = List(
            "|E| |E| |E| |E| |E| ($) |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| (R) |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|"
          ),
          description = "Walk to the portal using the walk command 3 times.",
          mainMax = 3,
          robotOrientation = 0,
          stagedEnabled = false,
          activeEnabled = true,
          stagedQty = 0,
          assignedStaged = List.empty[AssignedFunction],
          preBuiltActive = List.empty[AssignedFunction],
          allowedActives = Some(List.empty[String]),
          cmdsAvailable = List(
            "moveRobotForwardOneSpot"
          ),
          specialParameters = List.empty[String],
          problem = "0",
          clearMain = false,
          initFocus = List("Walk"),
          videoHints = List("aGRc1MR91Fk", "hAOSQnzbVLY", "CUbBgS7llOI"),
          freeHint = Some("RUxCBeJx-Bg")
        )
      )
    ),
    Continent(
      name = "WalkFour",
      struct = Some(
        ContinentStruct(
          gridMap = List(
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| (R) |E| |E| |E| ($) |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|"
          ),
          description = "Walk to the portal using the walk command 4 times.",
          mainMax = 4,
          robotOrientation = 90,
          stagedEnabled = false,
          activeEnabled = true,
          stagedQty = 0,
          assignedStaged = List.empty[AssignedFunction],
          preBuiltActive = List.empty[AssignedFunction],
          allowedActives = Some(List.empty[String]),
          cmdsAvailable = List(
            "moveRobotForwardOneSpot"
          ),
          specialParameters = List.empty[String],
          problem = "0",
          clearMain = false,
          initFocus = List("Walk"),
          videoHints = List("9p4DJYmFL6o", "52Qjuc9AU-Y", "pwNEp_Yoi8w"),
          freeHint = None
        )
      )
    ),
    Continent(
      name = "WalkFourWithFunction",
      struct = Some(
        ContinentStruct(
          gridMap = List(
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| (R) |E| |E| |E| ($) |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|"
          ),
          description = "Walk to the portal using the rocket function.",
          mainMax = 1,
          robotOrientation = 90,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = 0,
          assignedStaged = List.empty[AssignedFunction],
          preBuiltActive = List(
            AssignedFunction(
              createdId = "50246629508",
              name = "Rocket",
              image = "rocket",
              sizeLimit = -1,
              func = List(
                "moveRobotForwardOneSpot",
                "moveRobotForwardOneSpot",
                "moveRobotForwardOneSpot",
                "moveRobotForwardOneSpot"
              )
            )
          ),
          allowedActives = Some(List("50246629508")),
          cmdsAvailable = List(
            "moveRobotForwardOneSpot"
          ),
          specialParameters = List(
            "functionRequired"
          ),
          problem = "0",
          clearMain = false,
          initFocus = List("rocket"),
          videoHints = List("NvEIoXrjc0A"),
          freeHint = Some("WBMNlpX_VqE")
        )
      )
    ),
    Continent(
      name = "EditFirstFunction",
      struct = Some(
        ContinentStruct(
          gridMap = List(
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| (R) |E| |E| |E| |E| ($)",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|"
          ),
          description = "Add another walk command to the rocket function.",
          mainMax = 1,
          robotOrientation = 90,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = 0,
          assignedStaged = List.empty[AssignedFunction],
          preBuiltActive = List.empty[AssignedFunction],
          allowedActives = Some(List("50246629508")),
          cmdsAvailable = List(
            "moveRobotForwardOneSpot"
          ),
          specialParameters = List(
            "functionRequired"
          ),
          problem = "0",
          clearMain = false,
          initFocus = List("rocket"),
          videoHints = List("Vlu_7mILe1g", "V5vqJcPBTI0")
        )
      )
    ),
    Continent(
      name = "TurnRight",
      struct = Some(
        ContinentStruct(
          gridMap = List(
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| (R) |E| |E| |E| |E| ($)",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|"
          ),
          description = "Turn right and walk to the portal using rocket.",
          mainMax = 2,
          robotOrientation = 0,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = 0,
          assignedStaged = List.empty[AssignedFunction],
          preBuiltActive = List.empty[AssignedFunction],
          allowedActives = Some(List("50246629508")),
          cmdsAvailable = List(
            "moveRobotForwardOneSpot",
            "changeRobotDirection"
          ),
          specialParameters = List.empty[String],
          problem = "0",
          clearMain = false,
          initFocus = List("Turn Right"),
          videoHints = List("qi-PCVruzZs", "lwc-qpKeRAA")
        )
      )
    ),
    Continent(
      name = "CreateNewFunction",
      struct = Some(
        ContinentStruct(
          gridMap = List(
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| ($) |E| |E| |E| |E| (R)",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|"
          ),
          description = "Create a new u-turn function using 2 right turns.",
          mainMax = 2,
          robotOrientation = 90,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = 0,
          assignedStaged = List(
            AssignedFunction(
              createdId = "58071679485",
              name = "U Turn",
              image = "uTurn",
              sizeLimit = 2,
              func = List.empty[String]
            )
          ),
          preBuiltActive = List.empty[AssignedFunction],
          allowedActives = Some(List("50246629508", "58071679485")),
          cmdsAvailable = List(
            "moveRobotForwardOneSpot",
            "changeRobotDirection"
          ),
          specialParameters = List(
            "functionRequired"
          ),
          problem = "0",
          clearMain = false,
          initFocus = List("open-staged"),
          videoHints = List("377dFh51QzM", "OGV9_vN5qoU", "vExSvJwCLU4")
        )
      )
    ),
    Continent(
      name = "TurnLeft",
      struct = Some(
        ContinentStruct(
          gridMap = List(
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| (R) |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| ($) |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|"
          ),
          description = "Create a turn left function.",
          mainMax = 2,
          robotOrientation = 270,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = 0,
          assignedStaged = List(
            AssignedFunction(
              createdId = "53367904610",
              name = "Turn Left",
              image = "turnLeft",
              sizeLimit = 3,
              func = List.empty[String]
            )
          ),
          preBuiltActive = List.empty[AssignedFunction],
          allowedActives = Some(List("53367904610")),
          cmdsAvailable = List(
            "moveRobotForwardOneSpot",
            "changeRobotDirection"
          ),
          specialParameters = List(
            "functionRequired"
          ),
          problem = "0",
          clearMain = false,
          initFocus = List("open-staged"),
          videoHints = List("C0bpqUxtep0", "zHVg48iPvCg", "JTVWGig1h9o")
        )
      )
    ),
    Continent(
      name = "WalkAndTurn",
      struct = Some(
        ContinentStruct(
          gridMap = List(
            "|E| |E| |E| |E| |E| ($) |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| (R) |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|"
          ),
          description = "Get to the portal.",
          mainMax = -1,
          robotOrientation = 0,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = 0,
          assignedStaged = List.empty[AssignedFunction],
          preBuiltActive = List.empty[AssignedFunction],
          allowedActives = Some(List.empty[String]),
          cmdsAvailable = List(
            "moveRobotForwardOneSpot",
            "changeRobotDirection"
          ),
          specialParameters = List.empty[String],
          problem = "0",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("VO_IA7AZvjQ", "iAxdJdNOjp0", "9BQxnQ1ScJs")
        )
      )
    ),
    Continent(
      name = "WalkAndTurnWithFunctions",
      struct = Some(
        ContinentStruct(
          gridMap = List(
            "|E| |E| |E| |E| |E| ($) |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| (R) |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|"
          ),
          description = "Get to the portal using only 3 commands.",
          mainMax = 3,
          robotOrientation = 0,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = 0,
          assignedStaged = List.empty[AssignedFunction],
          preBuiltActive = List.empty[AssignedFunction],
          allowedActives = Some(List("50246629508")),
          cmdsAvailable = List(
            "moveRobotForwardOneSpot",
            "changeRobotDirection"
          ),
          specialParameters = List(
            "functionRequired"
          ),
          problem = "0",
          clearMain = false,
          initFocus = List("rocket"),
          videoHints = List("1bDucaHrFwo", "swF5vuJHLF0")
        )
      )
    ),
    Continent(
      name = "Maze1",
      struct = Some(
        ContinentStruct(
          gridMap = List(
            "|E| |E| |E| |E| |E| |W| |E| |W| |E| |E|",
            "|E| |E| |E| |E| |E| |W| ($) |W| |E| |E|",
            "|W| |W| |W| |W| |W| |W| |E| |W| |E| |E|",
            "|E| |E| |E| (R) |E| |E| |E| |W| |E| |E|",
            "|W| |W| |W| |W| |W| |W| |W| |W| |E| |E|"
          ),
          description = "Get to the portal. Using functions is more fun.",
          mainMax = -1,
          robotOrientation = 90,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = -1,
          assignedStaged = List.empty[AssignedFunction],
          preBuiltActive = List.empty[AssignedFunction],
          cmdsAvailable = List(
            "moveRobotForwardOneSpot",
            "changeRobotDirection"
          ),
          specialParameters = List(
            "functionRequired"
          ),
          problem = "0",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("rHfsl5BNEkQ", "pJdlVLrZH7M", "wPOC7wsw_z8")
        )
      )
    ),
    Continent(
      name = "Maze2",
      struct = Some(
        ContinentStruct(
          gridMap = List(
            "|W| |W| |W| |W| |W| |W| |W| |W| |E| |E|",
            "|E| |E| |E| |E| |E| |E| (R) |W| |E| |E|",
            "|E| |W| |W| |E| |W| |W| |W| |W| |E| |E|",
            "|E| ($) |E| |E| |W| |E| |E| |E| |E| |E|",
            "|W| |W| |W| |W| |W| |E| |E| |E| |E| |E|"
          ),
          description = "Get to the portal. Using functions is more fun.",
          mainMax = -1,
          robotOrientation = 270,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = -1,
          assignedStaged = List.empty[AssignedFunction],
          preBuiltActive = List.empty[AssignedFunction],
          cmdsAvailable = List(
            "moveRobotForwardOneSpot",
            "changeRobotDirection"
          ),
          specialParameters = List(
            "functionRequired"
          ),
          problem = "0",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("zTo5nxCvUtI", "AEMDZOOlSBA", "ODEMhj9BZ94")
        )
      )
    ),
    Continent(
      name = "Maze3",
      struct = Some(
        ContinentStruct(
          gridMap = List(
            "|W| |W| |W| |W| |W| |W| |W| |W| |W| |W|",
            "(R) |E| |W| |E| |E| |E| |E| |E| |E| |W|",
            "|W| |E| |E| |E| |E| |E| |W| |W| |E| |W|",
            "|W| |E| |W| |W| |W| |E| |E| |E| |E| |W|",
            "|E| |E| |W| ($) |E| |E| |W| |W| |W| |W|"
          ),
          description = "Get to the portal. Using functions is more fun.",
          mainMax = -1,
          robotOrientation = 90,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = -1,
          assignedStaged = List.empty[AssignedFunction],
          preBuiltActive = List.empty[AssignedFunction],
          cmdsAvailable = List(
            "moveRobotForwardOneSpot",
            "changeRobotDirection"
          ),
          specialParameters = List(
            "functionRequired"
          ),
          problem = "0",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("yJ68pORn3_A", "cpd6QRuzwfc", "9kRdp-D59_Q")
        )
      )
    )
  )

  val counting = List(
    Continent(
      name = "Pickup",
      struct = Some(
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
    Continent(
      name = "Pickup3",
      struct = Some(
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
    Continent(
      name = "Pickup5",
      struct = Some(
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
    Continent(
      name = "Pickup4",
      struct = Some(
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
    Continent(
      name = "Navigate",
      struct = Some(
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
    Continent(
      name = "Pickup2WithFunction",
      struct = Some(
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
    Continent(
      name = "Pickup1WithFunction",
      struct = Some(
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
    Continent(
      name = "Pickup5WithFunction",
      struct = Some(
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
    Continent(
      name = "Pickup9WithFunction",
      struct = Some(
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
    Continent(
      name = "Pickup4WithFunction",
      struct = Some(
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
    Continent(
      name = "Pickup6WithFunction",
      struct = Some(
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
    Continent(
      name = "Pickup8WithFunction",
      struct = Some(
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
    Continent(
      name = "Pickup3WithFunction",
      struct = Some(
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

  val numbers = List(
    Continent(
      name = "Number7",
      struct = Some(
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
    Continent(
      name = "Number8",
      struct = Some(
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
    Continent(
      name = "SevenAgain",
      struct = Some(
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
    Continent(
      name = "Number2",
      struct = Some(
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
    Continent(
      name = "SevenAgainAgain",
      struct = Some(
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
    Continent(
      name = "Number3",
      struct = Some(
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
    Continent(
      name = "Reuse7",
      struct = Some(
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
    Continent(
      name = "Number5",
      struct = Some(
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
    Continent(
      name = "ReuseCode",
      struct = Some(
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

  val recursion = List(
    Continent(
      name = "RecurForward",
      struct = Some(
        ContinentStruct(
          gridMap = List(
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "(R) |E| |E| [E] |E| |E| |E| |E| |E| ($)",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|"
          ),
          description = "Walk to the portal.",
          mainMax = 1,
          robotOrientation = 90,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = 0,
          assignedStaged = List(
            AssignedFunction(
              createdId = "54709789996",
              name = "Walk Forever",
              image = "walkForever",
              sizeLimit = 2,
              func = List.empty[String]
            )
          ),
          preBuiltActive = List.empty[AssignedFunction],
          allowedActives = Some(List("54709789996")),
          cmdsAvailable = List(
            "moveRobotForwardOneSpot",
            "changeRobotDirection",
            "pickUpItem",
            "setItemDown"
          ),
          specialParameters = List("recursionRequired"),
          problem = "0",
          clearMain = true,
          initFocus = List.empty[String],
          evalEachFrame = Some(true),
          videoHints = List("ro0V_ov9xPg", "8wYuS7APWKQ")
        )
      )
    ),
    Continent(
      name = "WalkAround",
      struct = Some(
        ContinentStruct(
          gridMap = List(
            "|W| |W| |W| |E| |E| |E| |E| |E| |W| |W|",
            "|W| |W| |W| |E| |W| |W| |W| |E| |W| |W|",
            "|W| |W| |W| |E| |W| |W| |W| ($) |W| |W|",
            "|W| |W| |W| |E| |W| |W| |W| |W| |W| |W|",
            "|W| |W| |W| |E| |E| |E| |E| (R) |W| |W|"
          ),
          description = "Walk to the portal.",
          mainMax = 1,
          robotOrientation = 270,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = 0,
          assignedStaged = List(
            AssignedFunction(
              createdId = "52128941453",
              name = "Spiral",
              image = "spiral",
              sizeLimit = 11,
              func = List.empty[String]
            )
          ),
          preBuiltActive = List.empty[AssignedFunction],
          allowedActives = Some(List("52128941453")),
          cmdsAvailable = List(
            "moveRobotForwardOneSpot",
            "changeRobotDirection",
            "pickUpItem",
            "setItemDown"
          ),
          specialParameters = List("recursionRequired"),
          problem = "0",
          clearMain = false,
          initFocus = List.empty[String],
          evalEachFrame = Some(true),
          videoHints = List("a8PdMGZgeRY", "VupAbRPKvoc", "L-EMZv5YBRk")
        )
      )
    ),
    Continent(
      name = "SmallSpiral",
      struct = Some(
        ContinentStruct(
          gridMap = List(
            "|W| |W| |W| |W| (R) |E| |E| |E| |W| |W|",
            "|W| |W| |W| |W| |W| |W| |W| |E| |W| |W|",
            "|W| |W| |W| |E| |E| ($) |W| |E| |W| |W|",
            "|W| |W| |W| |E| |W| |W| |W| |E| |W| |W|",
            "|W| |W| |W| |E| |E| |E| |E| |E| |W| |W|"
          ),
          description = "Walk to the portal.",
          mainMax = 1,
          robotOrientation = 90,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = 0,
          assignedStaged = List.empty[AssignedFunction],
          preBuiltActive = List.empty[AssignedFunction],
          allowedActives = Some(List("52128941453")),
          cmdsAvailable = List(
            "moveRobotForwardOneSpot",
            "changeRobotDirection",
            "pickUpItem",
            "setItemDown"
          ),
          specialParameters = List("recursionRequired"),
          problem = "0",
          clearMain = false,
          initFocus = List.empty[String],
          evalEachFrame = Some(true),
          videoHints = List("46p_GJz8vDU")
        )
      )
    ),
    Continent(
      name = "BigSpiral",
      struct = Some(
        ContinentStruct(
          gridMap = List(
            "(R) |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|W| |W| |W| |W| |W| |W| |W| |W| |W| |E|",
            "|E| |E| |E| |E| |E| ($) |W| |W| |W| |E|",
            "|E| |W| |W| |W| |W| |W| |W| |W| |W| |E|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|"
          ),
          description = "Walk to the portal.",
          mainMax = 1,
          robotOrientation = 90,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = 0,
          assignedStaged = List.empty[AssignedFunction],
          preBuiltActive = List.empty[AssignedFunction],
          allowedActives = Some(List("52128941453")),
          cmdsAvailable = List(
            "moveRobotForwardOneSpot",
            "changeRobotDirection",
            "pickUpItem",
            "setItemDown"
          ),
          specialParameters = List("recursionRequired"),
          problem = "0",
          clearMain = false,
          initFocus = List.empty[String],
          evalEachFrame = Some(true),
          videoHints = List("yGMEdd_UoUQ")
        )
      )
    ),
    Continent(
      name = "Stairs",
      struct = Some(
        ContinentStruct(
          gridMap = List(
            "(R) |W| |W| |W| |W| |W| |W| |W| |W| |W|",
            "|E| |E| |W| |W| |W| |W| |W| |W| |W| |W|",
            "|W| |E| |E| |W| |W| |W| |W| |W| |W| |W|",
            "|W| |W| |E| |E| |W| |W| |W| |W| |W| |W|",
            "|W| |W| |W| ($) |W| |W| |W| |W| |W| |W|"
          ),
          description = "Walk to the portal.",
          mainMax = 1,
          robotOrientation = 180,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = 0,
          assignedStaged = List(
            AssignedFunction(
              createdId = "51974949138",
              name = "Stairs",
              image = "stairs",
              sizeLimit = 6,
              func = List.empty[String]
            )
          ),
          preBuiltActive = List.empty[AssignedFunction],
          allowedActives = Some(List("51974949138", "53367904610")),
          cmdsAvailable = List(
            "moveRobotForwardOneSpot",
            "changeRobotDirection",
            "pickUpItem",
            "setItemDown"
          ),
          specialParameters = List("recursionRequired"),
          problem = "0",
          clearMain = false,
          initFocus = List.empty[String],
          evalEachFrame = Some(true),
          videoHints = List("OmTwwYGLvkc", "FrwGNObfH00")
        )
      )
    ),
    Continent(
      name = "LongStairs",
      struct = Some(
        ContinentStruct(
          gridMap = List(
            "(R) |E| |E| |W| |W| |W| |W| |W| |W| |W|",
            "|W| |W| |E| |E| |E| |W| |W| |W| |W| |W|",
            "|W| |W| |W| |W| |E| |E| |E| |W| |W| |W|",
            "|W| |W| |W| |W| |W| |W| |E| |E| ($) |W|",
            "|W| |W| |W| |W| |W| |W| |W| |W| |W| |W|"
          ),
          description = "Walk to the portal.",
          mainMax = 1,
          robotOrientation = 90,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = 0,
          assignedStaged = List.empty[AssignedFunction],
          preBuiltActive = List.empty[AssignedFunction],
          allowedActives = Some(List("51974949138", "53367904610")),
          cmdsAvailable = List(
            "moveRobotForwardOneSpot",
            "changeRobotDirection",
            "pickUpItem",
            "setItemDown"
          ),
          specialParameters = List("recursionRequired"),
          problem = "0",
          clearMain = false,
          initFocus = List.empty[String],
          evalEachFrame = Some(true),
          videoHints = List("4X9m0M39Ruw", "_OHKFB54kds")
        )
      )
    ),
    Continent(
      name = "Snake",
      struct = Some(
        ContinentStruct(
          gridMap = List(
            "|E| |E| |E| |W| |E| |E| |E| |W| |E| |E|",
            "|E| |W| |E| |W| |E| |W| |E| |W| |E| |W|",
            "|E| |W| |E| |W| |E| |W| |E| |W| |E| |W|",
            "|E| |W| |E| |W| |E| |W| |E| |W| |E| |W|",
            "(R) |W| |E| |E| |E| |W| |E| ($) |E| |W|"
          ),
          description = "Walk to the portal.",
          mainMax = 1,
          robotOrientation = 0,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = 0,
          assignedStaged = List(
            AssignedFunction(
              createdId = "57634311012",
              name = "Snake",
              image = "snake",
              sizeLimit = 17,
              func = List.empty[String]
            )
          ),
          preBuiltActive = List.empty[AssignedFunction],
          allowedActives = Some(List("57634311012", "53367904610")),
          cmdsAvailable = List(
            "moveRobotForwardOneSpot",
            "changeRobotDirection",
            "pickUpItem",
            "setItemDown"
          ),
          specialParameters = List("recursionRequired"),
          problem = "0",
          clearMain = false,
          initFocus = List.empty[String],
          evalEachFrame = Some(true),
          videoHints = List("oq_yTn0c8HY", "dSK-wHfY5I4", "hufLnseGBeE")
        )
      )
    ),
    Continent(
      name = "ZigZag",
      struct = Some(
        ContinentStruct(
          gridMap = List(
            "|E| |E| |E| |W| |E| |E| |E| |W| |E| ($)",
            "|E| |W| |E| |W| |E| |W| |E| |W| |E| |W|",
            "|E| |W| |E| |W| |E| |W| |E| |W| |E| |W|",
            "|E| |W| |E| |W| |E| |W| |E| |W| |E| |W|",
            "(R) |W| |E| |E| |E| |W| |E| |E| |E| |W|"
          ),
          description = "Walk to the portal.",
          mainMax = 1,
          robotOrientation = 0,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = 0,
          assignedStaged = List.empty[AssignedFunction],
          preBuiltActive = List.empty[AssignedFunction],
          allowedActives = Some(List("57634311012", "53367904610")),
          cmdsAvailable = List(
            "moveRobotForwardOneSpot",
            "changeRobotDirection",
            "pickUpItem",
            "setItemDown"
          ),
          specialParameters = List("recursionRequired"),
          problem = "0",
          clearMain = false,
          initFocus = List.empty[String],
          evalEachFrame = Some(true),
          videoHints = List("hHoG4K25CGg")
        )
      )
    ),
    Continent(
      name = "CleanUp",
      struct = Some(
        ContinentStruct(
          gridMap = List(
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|W| |W| |W| |W| |W| |W| |W| |W| |W| |W|",
            "(R) [TS(1)] [TS(1)] [TS(1)] [TS(1)] [TS(1)] [TS(1)] [TS(1)] [TS(1)] ($)",
            "|W| |W| |W| |W| |W| |W| |W| |W| |W| |W|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|"
          ),
          description = "Pickup all kitties and place them on the portal.",
          mainMax = 1,
          robotOrientation = 90,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = 0,
          assignedStaged = List(
            AssignedFunction(
              createdId = "50555483574",
              name = "Cleanup",
              image = "cleanup",
              sizeLimit = 18,
              func = List.empty[String]
            )
          ),
          preBuiltActive = List.empty[AssignedFunction],
          allowedActives = Some(List("50555483574")),
          cmdsAvailable = List(
            "moveRobotForwardOneSpot",
            "changeRobotDirection",
            "pickUpItem",
            "setItemDown"
          ),
          specialParameters = List("recursionRequired"),
          problem = "8",
          clearMain = false,
          initFocus = List.empty[String],
          evalEachFrame = Some(true),
          videoHints = List("LLV9JS7LYKE", "u-Zq0mpxq3E", "hvp6pvnlqZY")
        )
      )
    ),
    Continent(
      name = "CleanUpMore",
      struct = Some(
        ContinentStruct(
          gridMap = List(
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|W| |W| |W| |W| |W| |W| |W| |W| |W| |W|",
            "(R) |E| [TS(1)] [TS(1)] |E| [TS(1)] |E| [TS(1)] |E| ($)",
            "|W| |W| |W| |W| |W| |W| |W| |W| |W| |W|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|"
          ),
          description = "Pickup all kitties and place them on the portal.",
          mainMax = 1,
          robotOrientation = 90,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = 0,
          assignedStaged = List(
            AssignedFunction(
              createdId = "53165523148",
              name = "Cleanup More",
              image = "cleanupMore",
              sizeLimit = 4,
              func = List.empty[String]
            ),
            AssignedFunction(
              createdId = "57688072155",
              name = "PickupN",
              image = "pickupN",
              sizeLimit = 9,
              func = List.empty[String]
            ),
            AssignedFunction(
              createdId = "54191328403",
              name = "DropN",
              image = "dropN",
              sizeLimit = 9,
              func = List.empty[String]
            )
          ),
          preBuiltActive = List.empty[AssignedFunction],
          allowedActives = Some(List("53165523148", "57688072155", "54191328403")),
          cmdsAvailable = List(
            "moveRobotForwardOneSpot",
            "changeRobotDirection",
            "pickUpItem",
            "setItemDown"
          ),
          specialParameters = List("recursionRequired"),
          problem = "4",
          clearMain = false,
          initFocus = List.empty[String],
          evalEachFrame = Some(true),
          videoHints = List("JMvBt7WWqQ4")
        )
      )
    ),
    Continent(
      name = "PickupPile",
      struct = Some(
        ContinentStruct(
          gridMap = List(
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|W| |W| |W| |W| |W| |W| |W| |W| |W| |W|",
            "(R) |E| [TS(1,1,1,1,1)] |E| |E| |E| |E| |E| |E| ($)",
            "|W| |W| |W| |W| |W| |W| |W| |W| |W| |W|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|"
          ),
          description = "Pickup stack of kitties and place them on the portal.",
          mainMax = 1,
          robotOrientation = 90,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = 0,
          assignedStaged = List.empty[AssignedFunction],
          preBuiltActive = List.empty[AssignedFunction],
          allowedActives = Some(List("53165523148", "57688072155", "54191328403")),
          cmdsAvailable = List(
            "moveRobotForwardOneSpot",
            "changeRobotDirection",
            "pickUpItem",
            "setItemDown"
          ),
          specialParameters = List("recursionRequired"),
          problem = "5",
          clearMain = false,
          initFocus = List.empty[String],
          evalEachFrame = Some(true),
          videoHints = List("9kiV6T4Oxrc")
        )
      )
    ),
    Continent(
      name = "CleanComb",
      struct = Some(
        ContinentStruct(
          gridMap = List(
            "|W| [TS(1)] |W| [TS(1)] |W| [TS(1)] |W| [TS(1)] |W| ($)",
            "|W| |E| |W| |E| |W| |E| |W| |E| |W| |E|",
            "|W| |E| |W| |E| |W| |E| |W| |E| |W| |E|",
            "|W| |E| |W| |E| |W| |E| |W| |E| |W| |E|",
            "(R) |E| |E| |E| |E| |E| |E| |E| |E| |E|"
          ),
          description = "Pickup all kitties and place them on the portal.",
          mainMax = 1,
          robotOrientation = 90,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = 0,
          assignedStaged = List(
            AssignedFunction(
              createdId = "59033416266",
              name = "Clean Comb",
              image = "cleanComb",
              sizeLimit = 30,
              func = List.empty[String]
            )
          ),
          preBuiltActive = List.empty[AssignedFunction],
          allowedActives = Some(List("59033416266", "53367904610", "58071679485", "57688072155", "54191328403")),
          cmdsAvailable = List(
            "moveRobotForwardOneSpot",
            "changeRobotDirection",
            "pickUpItem",
            "setItemDown"
          ),
          specialParameters = List("recursionRequired"),
          problem = "4",
          clearMain = false,
          initFocus = List.empty[String],
          evalEachFrame = Some(true),
          videoHints = List("XittjRWOp7M", "m3aFQypd8FA")
        )
      )
    ),
    Continent(
      name = "CleanComb2",
      struct = Some(
        ContinentStruct(
          gridMap = List(
            "|W| |E| [TS(1)] |W| |E| [TS(1)] |W| |E| [TS(1)] |W|",
            "|W| |E| |W| |W| |E| |W| |W| |E| |W| |W|",
            "|W| |E| |W| |W| |E| |W| |W| |E| |W| |W|",
            "|W| |E| |W| |W| |E| |W| |W| |E| |W| |W|",
            "(R) |E| |E| |E| |E| |E| |E| |E| |E| ($)"
          ),
          description = "Pickup all kitties and place them on the portal.",
          mainMax = 1,
          robotOrientation = 90,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = 0,
          assignedStaged = List.empty[AssignedFunction],
          preBuiltActive = List.empty[AssignedFunction],
          allowedActives = Some(List("59033416266", "53367904610", "58071679485", "57688072155", "54191328403")),
          cmdsAvailable = List(
            "moveRobotForwardOneSpot",
            "changeRobotDirection",
            "pickUpItem",
            "setItemDown"
          ),
          specialParameters = List("recursionRequired"),
          problem = "3",
          clearMain = false,
          initFocus = List.empty[String],
          evalEachFrame = Some(true),
          videoHints = List("-wPiyMf5Fe4", "8EdQe8izE3c", "3VzjyIq-2EY")
        )
      )
    ),
    Continent(
      name = "CleanComb3",
      struct = Some(
        ContinentStruct(
          gridMap = List(
            "|W| |E| |E| |E|     |W| |W| |E| |E| |E|     |W|",
            "|W| |E| |W| [TS(1)] |W| |W| |E| |W| [TS(1)] |W|",
            "|W| |E| |W| |W|     |W| |W| |E| |W| |W|     |W|",
            "|W| |E| |W| |W|     |W| |W| |E| |W| |W|     |W|",
            "(R) |E| |E| |E|     |E| |E| |E| |E|     |E| ($)"
          ),
          description = "Pickup all kitties and place them on the portal.",
          mainMax = 1,
          robotOrientation = 90,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = 0,
          assignedStaged = List.empty[AssignedFunction],
          preBuiltActive = List.empty[AssignedFunction],
          allowedActives = Some(List("59033416266", "53367904610", "58071679485", "57688072155", "54191328403")),
          cmdsAvailable = List(
            "moveRobotForwardOneSpot",
            "changeRobotDirection",
            "pickUpItem",
            "setItemDown"
          ),
          specialParameters = List("recursionRequired"),
          problem = "2",
          clearMain = false,
          initFocus = List.empty[String],
          evalEachFrame = Some(true),
          videoHints = List("Y8DCXlnBhRo", "CntzuVR13mM")
        )
      )
    ),
    Continent(
      name = "CleanZigZags",
      struct = Some(
        ContinentStruct(
          gridMap = List(
            "|W| |W| |W| |W| |W| |W| |W| |W| |W| |W|",
            "|W| |W| [TS(1)] |W| |W| [TS(1)] |W| |W| [TS(1)] |W|",
            "|W| |E| |E| |W| |E| |E| |W| |E| |E| |W|",
            "|W| |E| |W| |W| |E| |W| |W| |E| |W| |W|",
            "(R) |E| |E| |E| |E| |E| |E| |E| |E| ($)"
          ),
          description = "Pickup all kitties and place them on the portal.",
          mainMax = 1,
          robotOrientation = 90,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = 0,
          assignedStaged = List.empty[AssignedFunction],
          preBuiltActive = List.empty[AssignedFunction],
          allowedActives = Some(List("59033416266", "53367904610", "58071679485", "57688072155", "54191328403")),
          cmdsAvailable = List(
            "moveRobotForwardOneSpot",
            "changeRobotDirection",
            "pickUpItem",
            "setItemDown"
          ),
          specialParameters = List("recursionRequired"),
          problem = "3",
          clearMain = false,
          initFocus = List.empty[String],
          evalEachFrame = Some(true),
          videoHints = List("Hy5Yj8Bzjzs")
        )
      )
    )
  )

  val conditionals = List(
    Continent(
      name = "1",
      struct = Some(
        ContinentStruct(
          gridMap = List(
            "|W| |W| |W| |W| |W| |W| |W| |W| ($) |W|",
            "|W| |W| |W| |W| |W| |W| |W| |W| |E| |W|",
            "|W| |W| |W| |W| |W| |W| |W| |W| |E| |W|",
            "(R) |E| |E| |E| |E| |E| |E| |E| [TS(1)] |W|",
            "|W| |W| |W| |W| |W| |W| |W| |W| |W| |W|"
          ),
          description = "Walk to the portal.",
          mainMax = 1,
          robotOrientation = 90,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = 0,
          assignedStaged = List(
            AssignedFunction(
              createdId = "54771309360",
              name = "Left If",
              image = "leftIf",
              sizeLimit = 2,
              func = List.empty[String]
            ),
            AssignedFunction(
              createdId = "50425126605",
              name = "Walk Turn",
              image = "walkTurn",
              sizeLimit = 8,
              func = List.empty[String]
            )
          ),
          preBuiltActive = List.empty[AssignedFunction],
          allowedActives = Some(
            List(
              "53367904610",
              "50425126605",
              "54771309360"
            )
          ),
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
          evalEachFrame = Some(true),
          videoHints = List("w01mjed8ld0"),
          freeHint = Some("w01mjed8ld0")
        )
      )
    ),
    Continent(
      name = "2",
      struct = Some(
        ContinentStruct(
          gridMap = List(
            "|W| |W| |W| |W| ($) |W| |W| |W| |W| |W|",
            "|W| |W| |W| |W| |E| |W| |W| |W| |W| |W|",
            "|W| |W| |W| |W| |E| |W| |W| |W| |W| |W|",
            "|W| |W| |W| |W| |E| |W| |W| |W| |W| |W|",
            "(R) |E| |E| |E| [TS(10)] |W| |W| |W| |W| |W|"
          ),
          description = "Walk to the portal.",
          mainMax = 1,
          robotOrientation = 90,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = 0,
          assignedStaged = List.empty[AssignedFunction],
          preBuiltActive = List.empty[AssignedFunction],
          allowedActives = Some(
            List(
              "53367904610",
              "50425126605",
              "54771309360"
            )
          ),
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
          evalEachFrame = Some(true),
          videoHints = List("Ft0cKD6XjQU")
        )
      )
    ),
    Continent(
      name = "3",
      struct = Some(
        ContinentStruct(
          gridMap = List(
            "($) |E| |E| |E| |E| |E| |E| |E| |E| [TS(1g)]",
            "|W| |W| |W| |W| |W| |W| |W| |W| |W| |E|",
            "[TS(1g)] |E| |E| |E| |E| (R) |W| |W| |W| |E|",
            "|E| |W| |W| |W| |W| |W| |W| |W| |W| |E|",
            "[TS(1g)] |E| |E| |E| |E| |E| |E| |E| |E| [TS(1g)]"
          ),
          description = "Walk to the portal.",
          mainMax = 1,
          robotOrientation = 270,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = 0,
          assignedStaged = List.empty[AssignedFunction],
          preBuiltActive = List.empty[AssignedFunction],
          allowedActives = Some(
            List(
              "53367904610",
              "50425126605",
              "54771309360"
            )
          ),
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
          evalEachFrame = Some(true),
          videoHints = List("7MyeHJBZvF4")
        )
      )
    ),
    Continent(
      name = "4",
      struct = Some(
        ContinentStruct(
          gridMap = List(
            "|W| |W| |W| |W| (R) |W| |W| |W| |W| |W|",
            "|W| |W| |W| |W| |E| |W| |W| |W| |W| |W|",
            "|W| |W| |W| |W| |E| |W| |W| |W| |W| |W|",
            "|W| |W| |W| |W| |E| |W| |W| |W| |W| |W|",
            "($) |E| |E| |E| [TS(10g)] |W| |W| |W| |W| |W|"
          ),
          description = "Walk to the portal.",
          mainMax = 1,
          robotOrientation = 180,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = 0,
          assignedStaged = List(
            AssignedFunction(
              createdId = "52594280153",
              name = "Right If",
              image = "rightIf",
              sizeLimit = 2,
              func = List.empty[String]
            )
          ),
          preBuiltActive = List.empty[AssignedFunction],
          allowedActives = Some(
            List(
              "50425126605",
              "52594280153"
            )
          ),
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
          evalEachFrame = Some(true),
          videoHints = List("9Qnm16ny8dU")
        )
      )
    ),
    Continent(
      name = "5",
      struct = Some(
        ContinentStruct(
          gridMap = List(
            "(R) |E| |E| |E| |E| |E| |E| |E| |E| [TS(10)]",
            "|W| |W| |W| |W| |W| |W| |W| |W| |W| |E|",
            "[TS(10)] |E| |E| |E| |E| ($) |W| |W| |W| |E|",
            "|E| |W| |W| |W| |W| |W| |W| |W| |W| |E|",
            "[TS(10)] |E| |E| |E| |E| |E| |E| |E| |E| [TS(10]"
          ),
          description = "Walk to the portal.",
          mainMax = 1,
          robotOrientation = 90,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = 0,
          assignedStaged = List.empty[AssignedFunction],
          preBuiltActive = List.empty[AssignedFunction],
          allowedActives = Some(
            List(
              "50425126605",
              "52594280153"
            )
          ),
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
          evalEachFrame = Some(true),
          videoHints = List("hYNX1y1R6NI")
        )
      )
    ),
    Continent(
      name = "6",
      struct = Some(
        ContinentStruct(
          gridMap = List(
            "(R) |E| |E| |E| [TS(1)] |E| |E| |E| |E| [TS(10g)]",
            "|W| |W| |W| |W| |W| |W| |W| |W| |W| |E|",
            "[TS(10g)] |E| |E| |E| |E| ($) |W| |W| |W| [TS(1)]",
            "|E| |W| |W| |W| |W| |W| |W| |W| |W| |E|",
            "[TS(10g)] |E| [TS(1)] |E| |E| |E| |E| |E| |E| [TS(10g]"
          ),
          description = "Walk to the portal.",
          mainMax = 1,
          robotOrientation = 90,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = 0,
          assignedStaged = List.empty[AssignedFunction],
          preBuiltActive = List.empty[AssignedFunction],
          allowedActives = Some(
            List(
              "50425126605",
              "52594280153"
            )
          ),
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
          evalEachFrame = Some(true),
          videoHints = List("oy5nczAt17Q")
        )
      )
    ),
    Continent(
      name = "7",
      struct = Some(
        ContinentStruct(
          gridMap = List(
            "($) |E| [TS(10)] |E| [TS(1g)] [TS(10g)] [TS(1)] |E| |E| [TS(100)]",
            "|W| |W| |W| |W| |W| |W| |W| |W| |W| [TS(1g)]",
            "[TS(100)] [TS(10) [TS(1)] |E| [TS(10)] (R) |W| |W| |W| |E|",
            "[TS(10g)] |W| |W| |W| |W| |W| |W| |W| |W| [TS(10)]",
            "[TS(100)] |E| [TS(10) |E| [TS(1)] [TS(1)] |E| [TS(10g)] [TS(1g)] [TS(100]"
          ),
          description = "Walk to the portal.",
          mainMax = 1,
          robotOrientation = 270,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = 0,
          assignedStaged = List.empty[AssignedFunction],
          preBuiltActive = List.empty[AssignedFunction],
          allowedActives = Some(
            List(
              "53367904610",
              "50425126605",
              "54771309360"
            )
          ),
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
          evalEachFrame = Some(true),
          videoHints = List("ho-zM7OQ8bA")
        )
      )
    ),
    Continent(
      name = "8",
      struct = Some(
        ContinentStruct(
          gridMap = List(
            "|W| |W| |W| |W| |W| |W| |W| |W| |W| |W|",
            "|W| |W| |W| |W| |W| [TS(100)] |E| |E| |E| ($)",
            "|W| |W| |W| |W| |W| |E| |W| |W| |W| |W|",
            "(R) |E| |E| |E| |E| [TS(1)] |W| |W| |W| |W|",
            "|W| |W| |W| |W| |W| |W| |W| |W| |W| |W|"
          ),
          description = "Walk to the portal.",
          mainMax = 1,
          robotOrientation = 90,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = 0,
          assignedStaged = List.empty[AssignedFunction],
          preBuiltActive = List.empty[AssignedFunction],
          allowedActives = Some(
            List(
              "53367904610",
              "50425126605",
              "54771309360",
              "52594280153"
            )
          ),
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
          evalEachFrame = Some(true),
          videoHints = List("iNfg2Tgs70o")
        )
      )
    ),
    Continent(
      name = "9",
      struct = Some(
        ContinentStruct(
          gridMap = List(
            "|W| |W| |W| |W| |W| |W| |W| |W| |W| |W|",
            "|W| |W| |W| |W| |W| [TS(100)] |E| |E| |E| [TS(100)]",
            "|W| |W| |W| |W| |W| |E| |W| |W| |W| |E|",
            "(R) |E| |E| |E| |E| [TS(1)] |W| |W| |W| |E|",
            "|W| |W| |W| |W| |W| |W| |W| ($) |E| [TS(100)]"
          ),
          description = "Walk to the portal.",
          mainMax = 1,
          robotOrientation = 90,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = 0,
          assignedStaged = List.empty[AssignedFunction],
          preBuiltActive = List.empty[AssignedFunction],
          allowedActives = Some(
            List(
              "53367904610",
              "50425126605",
              "54771309360",
              "52594280153"
            )
          ),
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
          evalEachFrame = Some(true),
          videoHints = List("FqSjGHTxuBQ")
        )
      )
    ),
    Continent(
      name = "10",
      struct = Some(
        ContinentStruct(
          gridMap = List(
            "|W| [TS(100) |E| [TS(10g)] |W| ($)     |W| |W| |W| |W|",
            "|W| |E|      |W| |E|       |W| |E|     |W| |W| |W| |W|",
            "|W| |E|      |W| |E|       |W| |E|     |W| |W| |W| |W|",
            "|W| |E|      |W| |E|       |W| |E|     |W| |W| |W| |W|",
            "|W| (R)      |W| [TS(1)]   |E| [TS(10)] |W| |W| |W| |W|"
          ),
          description = "Walk to the portal.",
          mainMax = 1,
          robotOrientation = 0,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = 0,
          assignedStaged = List(
            AssignedFunction(
              createdId = "50870843406",
              name = "Right If 2",
              image = "rightIf2",
              sizeLimit = 2,
              func = List.empty[String]
            ),
            AssignedFunction(
              createdId = "57442038480",
              name = "Left If 2",
              image = "leftIf2",
              sizeLimit = 2,
              func = List.empty[String]
            )
          ),
          preBuiltActive = List.empty[AssignedFunction],
          allowedActives = Some(
            List(
              "53367904610",
              "50425126605",
              "54771309360",
              "52594280153",
              "50870843406",
              "57442038480"
            )
          ),
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
          evalEachFrame = Some(true),
          videoHints = List("g-pR5BFxNh0")
        )
      )
    ),
    Continent(
      name = "11",
      struct = Some(
        ContinentStruct(
          gridMap = List(
            "|W|     |W| |W|       [TS(1)] |E|       [TS(10)]  |W| |W|      ($) [TS(100)]",
            "|W|     |W| |W|       |E|     |W|       [TS(100)] |E| [TS(10)] |W| |E|",
            "|W|     |W| |W|       [TS(1)] [TS(1g)]  |W|       |W| |E|      |W| |E|",
            "[TS(1)] |E| [TS(10)]  |W|     |E|       |W|       |W| |E|      |W| |E|",
            "(R)     |W| [TS(10g)] |E|     [TS(10g)] |W|       |W| [TS(1g)] |E| [TS(1g)]"
          ),
          description = "Walk to the portal.",
          mainMax = 1,
          robotOrientation = 0,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = 0,
          assignedStaged = List(
            AssignedFunction(
              createdId = "57049032471",
              name = "Left If 3",
              image = "leftIf3",
              sizeLimit = 2,
              func = List.empty[String]
            )
          ),
          preBuiltActive = List.empty[AssignedFunction],
          allowedActives = Some(
            List(
              "53367904610",
              "50425126605",
              "54771309360",
              "52594280153",
              "50870843406",
              "57442038480",
              "57049032471"
            )
          ),
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
          evalEachFrame = Some(true),
          videoHints = List("NwWjP2HpaIM")
        )
      )
    ),
    Continent(
      name = "12",
      struct = Some(
        ContinentStruct(
          gridMap = List(
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|W| |W| |W| |W| |W| |W| |W| |W| |W| |W|",
            "(R) [TS(10g)] [TS(10)] [TS(10g)] [TS(10g)] [TS(10)] [TS(10g)] [TS(10)] [TS(10g)] ($)",
            "|W| |W| |W| |W| |W| |W| |W| |W| |W| |W|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|"
          ),
          description = "Walk to the portal.",
          mainMax = 1,
          robotOrientation = 90,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = 0,
          assignedStaged = List(
            AssignedFunction(
              createdId = "56021486035",
              name = "Walk Ahead",
              image = "walkAhead",
              sizeLimit = 7,
              func = List.empty[String]
            ),
            AssignedFunction(
              createdId = "57492275363",
              name = "Drop If",
              image = "dropIf",
              sizeLimit = 1,
              func = List.empty[String]
            )
          ),
          preBuiltActive = List.empty[AssignedFunction],
          allowedActives = Some(
            List(
              "56021486035",
              "57492275363",
              "54191328403",
              "57688072155"
            )
          ),
          cmdsAvailable = List(
            "moveRobotForwardOneSpot",
            "changeRobotDirection",
            "pickUpItem",
            "setItemDown"
          ),
          specialParameters = List.empty[String],
          problem = "30",
          clearMain = false,
          initFocus = List.empty[String],
          evalEachFrame = Some(true),
          videoHints = List("Idwzh8xdNZo")
        )
      )
    ),
    Continent(
      name = "13",
      struct = Some(
        ContinentStruct(
          gridMap = List(
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|W| |W| |W| |W| |W| |W| |W| |W| |W| |W|",
            "(R) [TS(1g)] [TS(1)] [TS(1)] [TS(10)] [TS(100)] [TS(10)] [TS(1)] [TS(1g)] ($)",
            "|W| |W| |W| |W| |W| |W| |W| |W| |W| |W|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|"
          ),
          description = "Walk to the portal.",
          mainMax = 1,
          robotOrientation = 90,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = 0,
          assignedStaged = List(
            AssignedFunction(
              createdId = "56699295345",
              name = "Drop If 2",
              image = "dropIf2",
              sizeLimit = 1,
              func = List.empty[String]
            ),
            AssignedFunction(
              createdId = "58036514341",
              name = "Drop If 3",
              image = "dropIf3",
              sizeLimit = 1,
              func = List.empty[String]
            )
          ),
          preBuiltActive = List.empty[AssignedFunction],
          allowedActives = Some(
            List(
              "56021486035",
              "56699295345",
              "58036514341",
              "57492275363",
              "54191328403",
              "57688072155"
            )
          ),
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
          evalEachFrame = Some(true),
          videoHints = List("zo-uFEGbdog")
        )
      )
    ),
    Continent(
      name = "14",
      struct = Some(
        ContinentStruct(
          gridMap = List(
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|W| |W| |W| |W| |W| |W| |W| |W| |W| |W|",
            "(R) |E| [TS(10)] |E| [TS(10g)] [TS(100)] |E| |E| [TS(10g)] ($)",
            "|W| |W| |W| |W| |W| |W| |W| |W| |W| |W|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|"
          ),
          description = "Walk to the portal.",
          mainMax = 1,
          robotOrientation = 90,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = 0,
          assignedStaged = List.empty[AssignedFunction],
          preBuiltActive = List.empty[AssignedFunction],
          allowedActives = Some(
            List(
              "56021486035",
              "56699295345",
              "58036514341",
              "57492275363",
              "54191328403",
              "57688072155"
            )
          ),
          cmdsAvailable = List(
            "moveRobotForwardOneSpot",
            "changeRobotDirection",
            "pickUpItem",
            "setItemDown"
          ),
          specialParameters = List.empty[String],
          problem = "100",
          clearMain = false,
          initFocus = List.empty[String],
          evalEachFrame = Some(true),
          videoHints = List("6Pa379ZbSHo")
        )
      )
    ),
    Continent(
      name = "15",
      struct = Some(
        ContinentStruct(
          gridMap = List(
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|W| |W| |W| |W| |W| |W| |W| |W| |W| |W|",
            "(R) [TS(10)] [TS(10)] [TS(10)] [TS(10)] [TS(10)] [TS(10)] [TS(10)] [TS(10)] ($)",
            "|W| |W| |W| |W| |W| |W| |W| |W| |W| |W|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|"
          ),
          description = "Walk to the portal.",
          mainMax = 1,
          robotOrientation = 90,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = 0,
          assignedStaged = List.empty[AssignedFunction],
          preBuiltActive = List.empty[AssignedFunction],
          allowedActives = Some(
            List(
              "56021486035",
              "56699295345",
              "58036514341",
              "57492275363",
              "54191328403",
              "57688072155"
            )
          ),
          cmdsAvailable = List(
            "moveRobotForwardOneSpot",
            "changeRobotDirection",
            "pickUpItem",
            "setItemDown"
          ),
          specialParameters = List.empty[String],
          problem = "80",
          clearMain = false,
          initFocus = List.empty[String],
          evalEachFrame = Some(true),
          videoHints = List("qHd7WlbsptQ")
        )
      )
    ),
    Continent(
      name = "16",
      struct = Some(
        ContinentStruct(
          gridMap = List(
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|",
            "|W| |W| |W| |W| |W| |W| |W| |W| |W| |W|",
            "(R) [TS(10)] [TS(100)] [TS(100)] [TS(10)] [TS(1)] [TS(100)] [TS(100)] [TS(1)] ($)",
            "|W| |W| |W| |W| |W| |W| |W| |W| |W| |W|",
            "|E| |E| |E| |E| |E| |E| |E| |E| |E| |E|"
          ),
          description = "Walk to the portal.",
          mainMax = 1,
          robotOrientation = 90,
          stagedEnabled = true,
          activeEnabled = true,
          stagedQty = 0,
          assignedStaged = List.empty[AssignedFunction],
          preBuiltActive = List.empty[AssignedFunction],
          allowedActives = Some(
            List(
              "56021486035",
              "56699295345",
              "58036514341",
              "57492275363",
              "54191328403",
              "57688072155"
            )
          ),
          cmdsAvailable = List(
            "moveRobotForwardOneSpot",
            "changeRobotDirection",
            "pickUpItem",
            "setItemDown"
          ),
          specialParameters = List.empty[String],
          problem = "22",
          clearMain = false,
          initFocus = List.empty[String],
          evalEachFrame = Some(true),
          videoHints = List("CAE9eMId6j4")
        )
      )
    )
  )
}
