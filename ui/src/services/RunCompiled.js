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
    }
  }

  start () {
    const mainFunction = this.$store.getters.getMainFunction.func
    // console.log('start ~ ', this.robotFrames.slice())
    if (!mainFunction.length) {
      this._mainEmptyMessage()
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

  _mainEmptyMessage () {
    const messageBuilder = {
      type: 'warn',
      msg: 'Main is empty',
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
    this.$store.dispatch('updateRobot', new Robot(stepData.initialRobotState))
    this.constructor(this.context)
  }

  _updateStats (stats) {
    this.$store.dispatch('updateStats', stats)
  }

  _initializeOnLastFrame (frame) {
    this._updateStats(frame.stats)
    this._initializeStep(frame.stepData)
  }

  _resetStep (res) {
    api.getStep({tokenId: this.tokenId, level: this.stats.level, step: this.stats.step}, stepData => {
      this._initializeStep(stepData)
    })
  }

  _stopRobot () {
    api.compilerWebSocket.haltProgram(this._resetStep)
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
    // console.log(JSON.parse(JSON.stringify(frame)))
    return this.initializeAnimation(this.$store, frame, async () => {
      await this._showBridgeScreen(frame)
      this._initializeOnLastFrame(frame)
    })
  }

  _failure (frame) {
    // console.log(JSON.parse(JSON.stringify(frame)))
    return this.initializeAnimation(this.$store, frame, async () => {
      await this._showBridgeScreen(frame)
      this._initializeOnLastFrame(frame)
    })
  }

  _running (frame) {
    return this.initializeAnimation(this.$store, frame, () => {
      if (this.robot.state === 'running') {
        this._processFrames()
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
    if (this.robotFrames.length && this.robotFrames.length < 8) {
      const last = this.robotFrames[this.robotFrames.length - 1]
      if (this.robotFrames.length < 8 && last.programState === 'running') {
        this._askCompiler()
      }
    }
  }

  _askCompiler (startRunning) {
    api.compilerWebSocket.compileWs({problem: this.stepData.problem.encryptedProblem}, (compiled) => {
      this.robotFrames = this.robotFrames.concat(compiled.frames)
      if (startRunning) startRunning()
    })
  }
}

export default RunCompiled
