import Ws from './Ws'

class CompilerControl extends Ws {
  constructor () {
    super('compiler')
    this._compilerTake = 10

    this._openSocket()
  }

  haltProgram (cb) {
    this._wsOnMessage(cb)
    this.send(0, '0', false, true)
  }

  send (problem, halt, create) {
    this._send(JSON.stringify({steps: this._compilerTake, problem: problem, halt: halt, create: create}))
  }
}

export default CompilerControl
