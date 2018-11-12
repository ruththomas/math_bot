package level_gen.planets
import actors.messages.AssignedFunction
import level_gen.LevelGenTags
import level_gen.models.{CelestialSystem, ContinentStruct}

trait BasicProgramming extends LevelGenTags {
  val basicProgramming = List(
    CelestialSystem(
      name = "WalkOne",
      kind = continent,
      continentStruct = Some(
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
    CelestialSystem(
      name = "WalkThree",
      kind = continent,
      continentStruct = Some(
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
    CelestialSystem(
      name = "WalkFour",
      kind = continent,
      continentStruct = Some(
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
    CelestialSystem(
      name = "WalkFourWithFunction",
      kind = continent,
      continentStruct = Some(
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
    CelestialSystem(
      name = "EditFirstFunction",
      kind = continent,
      continentStruct = Some(
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
    CelestialSystem(
      name = "TurnRight",
      kind = continent,
      continentStruct = Some(
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
    CelestialSystem(
      name = "CreateNewFunction",
      kind = continent,
      continentStruct = Some(
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
    CelestialSystem(
      name = "TurnLeft",
      kind = continent,
      continentStruct = Some(
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
    CelestialSystem(
      name = "WalkAndTurn",
      kind = continent,
      continentStruct = Some(
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
    CelestialSystem(
      name = "WalkAndTurnWithFunctions",
      kind = continent,
      continentStruct = Some(
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
    CelestialSystem(
      name = "Maze1",
      kind = continent,
      continentStruct = Some(
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
          specialParameters = List.empty[String],
          problem = "0",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("rHfsl5BNEkQ", "pJdlVLrZH7M", "wPOC7wsw_z8")
        )
      )
    ),
    CelestialSystem(
      name = "Maze2",
      kind = continent,
      continentStruct = Some(
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
          specialParameters = List.empty[String],
          problem = "0",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("zTo5nxCvUtI", "AEMDZOOlSBA", "ODEMhj9BZ94")
        )
      )
    ),
    CelestialSystem(
      name = "Maze3",
      kind = continent,
      continentStruct = Some(
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
          specialParameters = List.empty[String],
          problem = "0",
          clearMain = false,
          initFocus = List.empty[String],
          videoHints = List("yJ68pORn3_A", "cpd6QRuzwfc", "9kRdp-D59_Q")
        )
      )
    )
  )
}
