package level_gen.planets
import actors.messages.AssignedFunction
import level_gen.LevelGenTags
import level_gen.models.{CelestialSystem, ContinentStruct}

trait Recursion extends LevelGenTags {
  val recursion = List(
    CelestialSystem(
      name = "RecurForward",
      kind = continent,
      continentStruct = Some(
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
    CelestialSystem(
      name = "WalkAround",
      kind = continent,
      continentStruct = Some(
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
    CelestialSystem(
      name = "SmallSpiral",
      kind = continent,
      continentStruct = Some(
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
    CelestialSystem(
      name = "BigSpiral",
      kind = continent,
      continentStruct = Some(
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
    CelestialSystem(
      name = "Stairs",
      kind = continent,
      continentStruct = Some(
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
    CelestialSystem(
      name = "LongStairs",
      kind = continent,
      continentStruct = Some(
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
    CelestialSystem(
      name = "Snake",
      kind = continent,
      continentStruct = Some(
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
    CelestialSystem(
      name = "ZigZag",
      kind = continent,
      continentStruct = Some(
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
    CelestialSystem(
      name = "CleanUp",
      kind = continent,
      continentStruct = Some(
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
    CelestialSystem(
      name = "CleanUpMore",
      kind = continent,
      continentStruct = Some(
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
    CelestialSystem(
      name = "PickupPile",
      kind = continent,
      continentStruct = Some(
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
    CelestialSystem(
      name = "CleanComb",
      kind = continent,
      continentStruct = Some(
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
    CelestialSystem(
      name = "CleanComb2",
      kind = continent,
      continentStruct = Some(
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
    CelestialSystem(
      name = "CleanComb3",
      kind = continent,
      continentStruct = Some(
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
    CelestialSystem(
      name = "CleanZigZags",
      kind = continent,
      continentStruct = Some(
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
}
