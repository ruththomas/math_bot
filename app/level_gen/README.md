# Level Generation

### Game Structure

---

Mathbot's structure is a nested data structure based on the universe. Each celestial 
system is represented by `/level_gen/models/CelestialSystem.scala`

```$xslt
SuperClusters
    |                               
    |-> Galaxies
        |
        |-> StarSystems
            |
            |-> Planets
                |
                |-> Continents
```

### Creating new levels

---

Anytime a new level is created or a level is removed the stats will be automatically 
updated to match.

#### Adding a SuperCluster

Currently the system is only built to handle a single SuperCluster. SuperClusters are
created in `/level_gen/SuperClusers.scala` which is the base for all the systems.

#### Adding a Galaxy

Galaxies are located inside `/level_gen/Galaxies.scala`.

```
trait Galaxies extends StarSystems with LevelGenTags {
  val superCluster1: List[CelestialSystem] = List(
    // Add new Galaxy here, notice children is a variable imported from `StarSystems.scala`
    CelestialSystem(
      name = "ExampleGalaxy",
      kind = galaxy,
      children = exampleGalaxyChildren // checkout `Adding a StarSystem` for relationship 
    )
  )
}
```

#### Adding a StarSystem

StarSystems are located in `/level_gen/StarSystems.scala`.

``` 
trait StarSystems extends Planets with LevelGenTags {
  // Add new StarSystems here, notice children is a variable imported from `Planets.scala`
  val exampleGalaxyChildren = (
    CelestialSystem(
      name = "ExampleStarSystem",
      kind = starSystem,
      children = exampleStarSystemChildren // checkout `Adding a Planet` section for relationship
    )
  )
}
```

#### Adding a Planet

Planets are located in `/level_gen/Planets.scala`

``` 
trait Planets extends Continents with LevelGenTags {
  // Add new Planets here, notice children is a variable imported from `Continents.scala`
  val exampleStarSystemChildren = List(
    CelestialSystem(
      name = "ExamplePlanet",
      kind = planet,
      children = exampleContinentChildren // checkout `Adding a Continent` for relationship
    )
  )
}
```

#### Adding a Continent

Continents are located in `/level_gen/Continents.scala`

``` 
trait Continents extends LevelGenTags {
  // Add new Continents here
  val exampleContinentChildren = List(
      CelestialSystem(
        name = "1",
        kind = continent,
        continentStruct = Some( // Checkout `Build a Continent Struct` section
          ContinentStruct(
            gridMap = List(
              "|E| |E| |E| |E| |E| |E| |E|  |E|   |E|  |E|",
              "|E| |E| |E| |E| |E| |E| |E|  |E|   |E|  |E|",
              "|E| |E| |E| |E| (R) ($) |E|  |E|   |E|  |E|",
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
            allowedActives = Some(List.empty[String]),
            cmdsAvailable = List(
              "moveRobotForwardOneSpot",
              "changeRobotDirection",
              "pickUpItem",
              "setItemDown"
            ),
            specialParameters = List.empty[String],
            problem = "0",
            clearMain = false,
            initFocus = List("Walk"),
            videoHints = List.empty[String],
            freeHint = None
          )
        )
      )
  )
}
```

### Build a Continent Struct

---

```$xslt
case class ContinentStruct(
    gridMap: List[String], // see `Creating a Grid Map` section
    description: String, // see `Creating a description` section (this feature is not currently being displayed)
    mainMax: Int, // Set to -1 for infinity otherwise main will have of a max of this properties value
    robotOrientation: Int, // Staring orientation of robot, must be set to 0, 90, 180 or 270
    stagedEnabled: Boolean, // If set to false staged functions will be locked shut
    activeEnabled: Boolean, // If set to false all active functions will be hidden @deprecated
    stagedQty: Int, // Maximum ammount of staged functions, not including assignedStaged
    assignedStaged: List[AssignedFunction], // see `Adding PreBuiltActives and AssignedStaged Functions` section
    preBuiltActive: List[AssignedFunction], // see `Adding PreBuiltActives and AssignedStaged Functions` section
    allowedActives: Option[List[String]] = None, // list of created_id's of allowed actives, these will be the only functions available for continent
    cmdsAvailable: List[String], // List of command names (see `Command Names` section) allowed for this continent
    specialParameters: List[String], // see `Special Parameters` section
    problem: String, // see `Building a Problem` section
    clearMain: Boolean, // If set to true main function will be cleared each time continent is reset @deprecated
    initFocus: List[String], // see `Init Focue` section @deprecated
    evalEachFrame: Option[Boolean] = None, // If set to true each frame will be evaluated as the program runs. Circumvents `Program must be completely finished running` rule
    videoHints: List[String], // List of YouTube video ids, max length 3. If empty list a message will indicate continent has no hints
    freeHint: Option[String] = None // A single YouTube video id that will be displayed when user navigates to continent
)
```

#### Command Names 
* Command names can also be found in `app/model/DefaultCommands.scala`

```$xslt
  "changeRobotDirection"
  "moveRobotForwardOneSpot"
  "setItemDown"
  "pickUpItem"
```

#### Creating a Grid Map
* Legend
  
```$xslt
(R) - Robot Spot
($) - Portal
|E|	- Robot can go
|W|	- Robot can't go
[1]	- Self replenishing - value 1
[10] - Self replenishing - value 10
[100] - Self replenishing - value 100
[1g] - Self replenishing - value 1,000
[10g] - Self replenishing - value 10,000
[TS(<TOOL>,<TOOL>)] - None self replenishing tools (comma sperated, no spaces) - e.g [TS(1,10,100,1g,10g)]
```
* Grid map creation
    * Use the above legend to fill in the grid map
    * We have only extensively tested a 5 x 10 grid however the grid can be any dimension! 
```$xslt
[
    "[1]   |E| |E| |E| |E| |E| |E| |E| |E| |E|",
    "[10]  |E| |E| |E| |E| |E| |W| |W| |W| |W|",
    "[100] |E| |E| |E| |E| |E| |W| |E| |E| |E|",
    "[1g]  |E| |E| |E| |E| ($) |W| |E| |E| |E|",
    "[10g] |E| |E| |E| |E| (R) |W| |E| |E| |E|"
]
```

#### Creating a Description
* Include the following in the description to be converted 
```$xslt
!!P!! - includes built problem in description
!!L!! - includes level name in description
!!S!! - includes step name in description
!![img]src='<IMG SRC (Must be in single quotes)>'!! - include an image in description, will be same size as font
```

#### Adding PreBuiltActives and AssignedStaged Functions

Create a `List[AssignedFunction]`

``` 
AssignedFunction(
    createdId: String, // An 11 digit string of numbers starting with 5
    name: String, // Name of function
    image: String, // Name of image found in `ui/src/assets/assets.js -> funcImages property`
    sizeLimit: Int, // Max size of function, if -1 infinity, this is permanently set for this function
    func: List[String] // List of command names see `Command Names` section. !!important!! if AssignedStaged this should be List.empty[String]
)
```

#### Special Parameters 
* These are special requirements beyond the regular requirements (see `Game Rules to Pass a Step`) to pass this step

Current Special Parameters
```$xslt
    "functionRequired" - user must use a built function
    "recursionRequired" - user must use recursion
```

#### Building a Problem
* {int} - becomes an integer with that many digits
* int - becomes self
* The math library we are using can also handle complex math
```$xslt
"{1} + {2} * ({6} - {4}) - 55" == "4 + 65 * (123456 - 4513) - 55"
```

#### Init Focus
* Elements to alert the user of when the step loads
* Elements will flash twice in the order they are found in the array
* Helpful to demonstrate what order things must happen to pass the level
* Name becomes the corresponding id on the front end either by becoming the hashed value, or literal
```$xslt
    # Commands - will be hashed into the corresponding id
        > changeRobotDirection
        > moveRobotForwardOneSpot
        > setItemDown
        > pickUpItem
    # Functions - will be hashed into the corresponding id
        > Use the functions `name`
    # Other elements - will be converted into the correct id name
        > main-placeholder (only visible when main max set)
        > staged (green + that opens staged functions)
```

