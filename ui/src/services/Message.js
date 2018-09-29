import uid from 'uid'
/*
* Message - adds messages to message list and removes self after 3 seconds
* */
class Message {
  constructor (state, messageBuilder) {
    this.state = state

    this.id = uid(7)
    this.timeOutCounter = 3000

    this.type = messageBuilder.type
    this.msg = `${messageBuilder.msg}`
    this.handlers = messageBuilder.handlers ? messageBuilder.handlers() : {}

    this.runBeforeAppend = this.handlers.runBeforeAppend || function () {}
    this.runOnDelete = this.handlers.runOnDelete || function () {}

    this._removeSelf = this._removeSelf.bind(this)
    this._timedDelete = this._timedDelete.bind(this)
  }

  _removeSelf () {
    this.state.messageList = this.state.messageList.filter(m => m.id !== this.id)
    this.runOnDelete()
  }

  _timedDelete () {
    if (!this.timeOutCounter) return this._removeSelf()
    this.timeOutCounter -= 10
    setTimeout(this._timedDelete, 10)
  }

  add () {
    this.runBeforeAppend()
    this.state.messageList.unshift(this)
    this.state.messageList = this.state.messageList.slice(0, 3)
    this._timedDelete()
  }

  delete () {
    this.timeOutCounter = 0
  }
}

export default Message
