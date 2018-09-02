import api from './api'
import Robot from './RobotState'
import GridAnimator from './GridAnimator'

class RunCompiled extends GridAnimator {
  constructor (context) {
    super()
    if (context) {
      this.context = context
      this.robotFrames = []
      this.$store = this.context.$store
      this.$router = this.context.$router
      this.tokenId = this.$store.getters.getTokenId
      this.stats = this.$store.getters.getStats
      this.robot = this.$store.getters.getRobot
      this.stepData = this.$store.getters.getStepData
      this.toolList = this.stepData.toolList
      this.programCreate = true

      this._askCompiler = this._askCompiler.bind(this)
      this._processFrames = this._processFrames.bind(this)
      this._initializeStep = this._initializeStep.bind(this)
      this._showBridgeScreen = this._showBridgeScreen.bind(this)
      this.start = this.start.bind(this)
      this.pause = this.pause.bind(this)
      this.stop = this.stop.bind(this)
      this._resetStep = this._resetStep.bind(this)
      this._waitForFrames = this._waitForFrames.bind(this)
    }
  }

  _reconstructFunctions (_funcToken) {
    const func = !_funcToken ? Object.assign({}, this.$store.getters.getMainFunction) : Object.assign({}, _funcToken)
    const funcList = func.func
    const activeFuncs = this.$store.getters.getActiveFunctions
    const commands = this.$store.getters.getCommands
    return funcList
      .map(f => {
        const funcToken = activeFuncs.find(af => af.created_id === f.created_id)
        if (funcToken) return Object.assign({}, funcToken)
        else return commands.find(cf => cf.created_id === f.created_id)
      })
      .map(f => {
        if (func.created_id !== f.created_id) {
          f.func = this._reconstructFunctions(f)
        } else {
          f.func = []
          f.recursive = true
        }
        return f
      })
  }

  _flattenFunctions (funcs, _acc) {
    _acc = !_acc ? [] : _acc
    for (let i = 0; i < funcs.length; i++) {
      const current = funcs[i]
      _acc.push(current)
      this._flattenFunctions(current.func, _acc)
    }
    return _acc
  }

  _isCommand (func) {
    const createdId = Number(func.created_id)
    return createdId < 2000 && createdId > 999
  }

  _unexecutableTest () {
    const funcs = this._flattenFunctions(this._reconstructFunctions())
    return funcs.reduce((bool, f) => {
      if (!this._isCommand(f) && !f.recursive && !f.func.length) {
        bool = false
      }
      return bool
    }, true)
  }

  start () {
    // console.log('start ~ ', this.robotFrames.slice())
    const isExecutable = this.$store.getters.getMainFunction.func.length ? this._unexecutableTest() : false

    if (!isExecutable) {
      this._unexecutableFunctionMessage()
    } else if (this.robot.state !== 'paused') {
      this.robotFrames = []
      this.robot.setState('running')
      this._askCompiler(this._processFrames)
    } else {
      this.robot.setState('running')
      this._processFrames()
    }
  }

  pause () {
    this._pausedMessage()
    this.robot.setState('paused')
  }

  stop () {
    this._stopMessage()
    this.robot.setState('stopped')
  }

  _stopMessage () {
    const messageBuilder = {
      type: 'warn',
      msg: 'Program stopped'
    }

    this._addMessage(messageBuilder)
  }

  _pausedMessage () {
    const messageBuilder = {
      type: 'success',
      msg: 'Program paused'
    }

    this._addMessage(messageBuilder)
  }

  _unexecutableFunctionMessage () {
    const messageBuilder = {
      type: 'warn',
      msg: 'One of your functions is empty',
      handlers () {
        const $bar = $('.bar')

        return {
          runBeforeAppend () {
            $bar.addClass('red-bar')
          },
          runOnDelete () {
            $bar.removeClass('red-bar')
          }
        }
      }
    }

    this._addMessage(messageBuilder)
  }

  _addMessage (messageBuilder) {
    this.$store.dispatch('addMessage', messageBuilder)
  }

  _initializeStep (stepData) {
    this.$store.dispatch('updateStepData', stepData)
    this.$store.dispatch('updateLambdas', stepData.lambdas)
    stepData.initialRobotState.context = this.context
    const robot = new Robot(stepData.initialRobotState)
    this.$store.dispatch('updateRobot', robot)
    this.constructor(this.context)
  }

  _updateStats (stats) {
    this.$store.dispatch('updateStats', stats)
  }

  _initializeOnLastFrame (frame) {
    this._updateStats(frame.stats)
    this._initializeStep(frame.stepData)
  }

  _resetStep () {
    api.getStep({tokenId: this.tokenId, level: this.stats.level, step: this.stats.step}, stepData => {
      this._initializeStep(stepData)
    })
  }

  _stopRobot () {
    api.compilerWebSocket.haltProgram(() => {})
    this._resetStep()
  }

  _toggleBridge = (which, bool) => this.$store.dispatch(`toggle${which}`, bool)

  _showBridgeScreen (frame) {
    return new Promise(resolve => {
      if (frame.programState === 'failure') this._toggleBridge('TryAgain', true)
      else this._toggleBridge('Congrats', true)
      setTimeout(() => {
        this._toggleBridge('Congrats', false)
        this._toggleBridge('TryAgain', false)
        resolve()
      }, 3000)
    })
  }

  _success (frame) {
    return this.initializeAnimation(this.$store, frame, async () => {
      // console.log('[last frame grid]', JSON.parse(JSON.stringify(frame.robotState.grid)))
      // console.log('[grid]', JSON.parse(JSON.stringify(this.grid)))
      await this._showBridgeScreen(frame)
      this._initializeOnLastFrame(frame)
      this._stopRobot()
    })
  }

  _failure (frame) {
    // console.log(JSON.parse(JSON.stringify(frame)))
    return this.initializeAnimation(this.$store, frame, async () => {
      await this._showBridgeScreen(frame)
      this._initializeOnLastFrame(frame)
      this._stopRobot()
    })
  }

  /*
  * In case program state is still running but server has not responded yet
  * */
  _waitForFrames (waitTime) {
    if (this.robotFrames.length) return this._processFrames()
    else if (waitTime === 0) return this._stopRobot()
    setTimeout(() => this._waitForFrames(waitTime - 1), 50)
  }

  _running (frame) {
    return this.initializeAnimation(this.$store, frame, () => {
      if (this.robot.state === 'running') {
        if (this.robotFrames.length) {
          this._processFrames()
        } else {
          this._waitForFrames(50)
        }
      } else if (this.robot.state === 'stopped') {
        this._stopRobot()
      }
    })
  }

  async _processFrames (_) {
    // console.log('frames ~ ', this.robotFrames.slice())
    const current = this.robotFrames.shift()
    this._controlAsk()
    const run = await this[`_${current.programState}`](current)
    run(current)
  }

  _controlAsk () {
    if (this.robotFrames.length > 0 && this.robotFrames.length < 20) {
      const last = this.robotFrames[this.robotFrames.length - 1]
      if (this.robotFrames.length < 20 && last.programState === 'running') {
        this._askCompiler()
      }
    }
  }

  _askCompiler (startRunning) {
    api.compilerWebSocket.compileWs({problem: this.stepData.problem.encryptedProblem}, (compiled) => {
      // console.log(compiled)
      this.robotFrames = this.robotFrames.concat(compiled.frames)
      if (startRunning) startRunning()
    })
  }
}

export default RunCompiled
