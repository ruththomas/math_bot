package actors.messages.level

object CommandIds extends Enumeration {
  val changeRobotDirection = "changeRobotDirection"
  val moveRobotForwardOneSpot = "moveRobotForwardOneSpot"
  val setItemDown = "setItemDown"
  val pickUpItem = "pickUpItem"
  val function = "function"
  val mainFunction = "main-function"
}

object Categories extends Enumeration {
  val main = "main"
  val command = "command"
  val function = "function"
  val staged = "staged"
}

object DefaultFunctions {
  def main: Function = Function(
    created_id = "100001",
    name = Categories.main,
    func = Some(List.empty[Function]),
    image = Categories.main,
    index = 0,
    category = Categories.main,
    commandId = CommandIds.mainFunction
  )

  def cmds: List[Function] = List(
    Function(
      created_id = "1001",
      name = "Turn Right",
      func = Some(List.empty[Nothing]),
      image = "turn_right",
      index = 0,
      category = Categories.command,
      commandId = CommandIds.changeRobotDirection
    ),
    Function(
      created_id = "1002",
      name = "Walk",
      func = Some(List.empty[Nothing]),
      image = "move_forward",
      index = 1,
      category = Categories.command,
      commandId = CommandIds.moveRobotForwardOneSpot
    ),
    Function(
      created_id = "1003",
      name = "Put Down",
      func = Some(List.empty[Nothing]),
      image = "drop_item",
      index = 2,
      category = Categories.command,
      commandId = CommandIds.setItemDown
    ),
    Function(
      created_id = "1004",
      name = "Pick Up",
      func = Some(List.empty[Nothing]),
      image = "pick_up",
      index = 3,
      category = Categories.command,
      commandId = CommandIds.pickUpItem
    )
  )

  def funcs: List[Function] =
    (1 to 50).toList.map(
      s =>
        Function(
          created_id = s"200000${s.toString}",
          func = Some(List.empty[Function]),
          name = "",
          image = s.toString,
          index = s,
          category = Categories.staged,
          commandId = CommandIds.function,
          sizeLimit = -1
      )
    )
}
