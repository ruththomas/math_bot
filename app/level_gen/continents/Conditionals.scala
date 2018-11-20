package level_gen.continents
import actors.messages.AssignedFunction
import level_gen.LevelGenTags
import level_gen.models.{CelestialSystem, ContinentStruct}

trait Conditionals extends LevelGenTags {
  val conditionalsChildren = List(
    CelestialSystem(
      name = "1",
      kind = continent,
      continentStruct = Some(
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
    CelestialSystem(
      name = "2",
      kind = continent,
      continentStruct = Some(
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
    CelestialSystem(
      name = "3",
      kind = continent,
      continentStruct = Some(
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
    CelestialSystem(
      name = "4",
      kind = continent,
      continentStruct = Some(
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
    CelestialSystem(
      name = "5",
      kind = continent,
      continentStruct = Some(
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
    CelestialSystem(
      name = "6",
      kind = continent,
      continentStruct = Some(
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
    CelestialSystem(
      name = "7",
      kind = continent,
      continentStruct = Some(
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
    CelestialSystem(
      name = "8",
      kind = continent,
      continentStruct = Some(
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
    CelestialSystem(
      name = "9",
      kind = continent,
      continentStruct = Some(
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
    CelestialSystem(
      name = "10",
      kind = continent,
      continentStruct = Some(
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
    CelestialSystem(
      name = "11",
      kind = continent,
      continentStruct = Some(
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
    CelestialSystem(
      name = "12",
      kind = continent,
      continentStruct = Some(
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
    CelestialSystem(
      name = "13",
      kind = continent,
      continentStruct = Some(
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
    CelestialSystem(
      name = "14",
      kind = continent,
      continentStruct = Some(
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
    CelestialSystem(
      name = "15",
      kind = continent,
      continentStruct = Some(
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
    CelestialSystem(
      name = "16",
      kind = continent,
      continentStruct = Some(
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
