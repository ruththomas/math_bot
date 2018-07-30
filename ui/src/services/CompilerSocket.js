import Ws from './Ws'
import urlEncode from 'urlencode'

class CompilerSocket extends Ws {
  constructor (tokenId) {
    super()
    this.tokenId = tokenId
    this._compilerTake = 4

    this._openCompilerWs()
  }

  /*
  * openCompilerWs - opens socket connection for compiler
  * */
  _openCompilerWs (cb) {
    this._getWsPath(urlEncode(this.tokenId), 'compiler', path => {
      this._ws = new WebSocket(path)
      // console.log(path)
      // console.log(this.compilerWs)
      this._ws.onopen = () => {
        console.log(`COMPILER WS OPEN`)
        if (cb) cb()
      }
      this._ws.onerror = (err) => { console.error('COMPILER WS FAILED', err) }
      this._ws.onclose = () => { console.log('COMPILER WS CLOSED') }
    })
  }

  /*
  * compilerWs - call to compiler for compilation
  * @param context - Vue instance context (this)
  * @param problem - Encrypted problem for step
  * */
  compileWs (problem, cb) {
    // console.log('problem ~>', problem)
    if (this._ws === null || this._ws.readyState !== 1) { // if socket closed open new connection
      this._openCompilerWs(() => { // open connection
        this._wsOnMessage(cb) // make connection on message the callback
        this._compilerSend(problem.problem, false)
      })
    } else { // else just update this connections on message method
      this._wsOnMessage(cb) // make connection on message the callback
      this._compilerSend(problem.problem, false)
    }
  }

  haltProgram (cb) {
    this._wsOnMessage(cb)
    this._compilerSend('0', true, false)
  }

  _compilerSend (problem, halt, create) {
    this._send(JSON.stringify({steps: this._compilerTake, problem: problem, halt: halt, create: create}))
  }
}

export default CompilerSocket
