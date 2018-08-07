package actors.messages

// Adds a "action" field to case classes that get converted into json so
// the json can be recognized more easily
trait MessageAction {
  val action: Symbol
}