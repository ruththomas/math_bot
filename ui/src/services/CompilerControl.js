import Ws from './Ws'

class CompilerControl extends Ws {
  constructor () {
    super('compiler')
    this._compilerTake = 10

    this._openSocket()
  }

  haltProgram (cb) {
    this._wsOnMessage(cb)
    this.send({halt: true})
  }

  send ({problem, halt, mbl, create}) {
    const prepReq = {
      steps: this._compilerTake,
      problem: problem,
      halt: halt,
      create: create
    }
    if (mbl) prepReq.mbl = mbl
    this._send(JSON.stringify(prepReq))
  }
}

export default CompilerControl
