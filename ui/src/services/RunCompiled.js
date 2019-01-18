import GridAnimator from './GridAnimator'
import $store from '../store/store'
import $router from '../router'
import { $root } from '../main'

class RunCompiled extends GridAnimator {
  constructor () {
    super()
    this.currentFrame = {index: -1}
    this.direction = 1
    this.robotFrames = []
    this.levelControl = $store.state.levelControl
    this.compilerControl = $store.state.compilerControl
    this.robot = $store.state.levelControl.robot
    this.stepData = $store.state.levelControl.continent
    this.toolList = this.stepData.toolList
    this.programCreate = true

    this._askCompiler = this._askCompiler.bind(this)
    this._processFrames = this._processFrames.bind(this)
    this._initializeStep = this._initializeStep.bind(this)
    this.start = this.start.bind(this)
    this.pause = this.pause.bind(this)
    this.stop = this.stop.bind(this)
    this.reset = this.reset.bind(this)
    this.setDirection = this.setDirection.bind(this)
    this._resetStep = this._resetStep.bind(this)
    this._waitForFrames = this._waitForFrames.bind(this)
    this.initializeNextStep = this.initializeNextStep.bind(this)
    this.resetIfFailure = this.resetIfFailure.bind(this)
    this.clearMbl = this.clearMbl.bind(this)
    this._updateGalaxyData = this._updateGalaxyData.bind(this)
    this._deleteAllMessages = this._deleteAllMessages.bind(this)
  }

  lastFrame = null
  failure = false

  resetIfFailure () {
    if (this.robot.state === 'failure') {
      this.reset()
    }
  }

  clearMbl () {
    $store.state.levelControl.mbl = ''
  }

  start () {
    if (this.levelControl.functions.main.func.length) {
      const normalMode = $store.state.levelControl.mode === 'normal'
      if (this.robot.state !== 'paused') {
        this.robotFrames = []
        this._deleteAllMessages()
        this.robot.setState('running')
        this._askCompiler(Object.assign({create: true, startRunning: this._processFrames}, !normalMode ? {mbl: $store.state.levelControl.mbl} : {}))
      } else {
        this.robot.setState('running')
        this._processFrames()
      }
    }
  }

  pause () {
    this.direction = 1
    this.robot.setSpeed(400)
    this.robot.setState(this.robot.state === 'running' ? 'paused' : 'home')
  }

  stop () {
    this._stopMessage()
    this.robot.setState('stopped')
    this.reset()
  }

  reset () {
    this.robot.setState('home')
    this._stopRobot()
  }

  initializeNextStep () {
    this._initializeStep()
  }

  quit () {
    this.initializeNextStep()
    $router.push({path: '/profile'})
  }

  stayOnLevel () {
    this._stopRobot()
    this._hideCongrats()
  }

  _hideCongrats () {
    this._hideLevelCongrats()
    this._hideStepCongrats()
  }

  _showStepCongrats = () => $root.$emit('bv::show::modal', 'step-congrats-modal')

  _hideStepCongrats = () => $root.$emit('bv::hide::modal', 'step-congrats-modal')

  _showLevelCongrats = () => $root.$emit('bv::show::modal', 'level-congrats-modal')

  _hideLevelCongrats = () => $root.$emit('bv::hide::modal', 'level-congrats-modal')

  _showFreeHint (url) {
    $store.state.videoHintControl.showFreeHint(url)
  }

  _initializeStep () {
    const freeHint = this.lastFrame.pathAndContinent.builtContinent.freeHint
    if (this.lastFrame === null) {
      this._showFreeHint(freeHint)
    }
    this.levelControl._setContinent(this.lastFrame)
    this._hideCongrats()
    this.constructor(this.context)
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

  _closeMessageRobotHome () {
    return () => {
      return this.robot.state === 'home'
    }
  }

  _failedMessage () {
    const dis = this
    const failedMessage1 = {
      type: 'success',
      msg: '',
      handlers () {
        const $helpButton = $('.help-button')
        const $reset = $('.reset.dialog-button')
        return {
          runBeforeAppend () {
            $helpButton.addClass('background-alert')
            $helpButton.addClass('animated flash')
            $reset.addClass('animated flash')
          },
          runOnDelete () {
            $helpButton.removeClass('flash')
            $reset.removeClass('flash')
            $helpButton.removeClass('background-alert')
          },
          closeControl: dis._closeMessageRobotHome()
        }
      }
    }
    this._addMessage(failedMessage1)
  }

  _mainEmptyMessage (emptyFuncs) {
    const messageBuilder = {
      type: 'warn',
      msg: '',
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

  _deleteAllMessages () {
    $store.dispatch('deleteAllMessages')
  }

  _addMessage (messageBuilder) {
    $store.dispatch('addMessage', messageBuilder)
  }

  _resetStep () {
    this.levelControl.resetContinent()
  }

  _stopRobot () {
    this.compilerControl.haltProgram(() => {})
    this._resetStep()
  }

  _toggleBridge = (which, bool) => this.$store.dispatch(`toggle${which}`, bool)

  _updateGalaxyData () {
    $store.state.levelControl.getGalaxyData()
  }

  _success (frame) {
    return this.initializeAnimation(frame, true, async () => {
      this.lastFrame = frame
      this.robot.setState('success')
      const isLastPlanet = $store.state.levelControl.isLastPlanet()
      const isLastContinent = $store.state.levelControl.isLastContinent()
      $root.$emit('bv::hide::popover')
      if (isLastContinent && isLastPlanet) {
        $router.push({path: '/profile', query: Object.assign({congratsShow: 'star-system-congrats'}, this.lastFrame)})
      } else if (isLastContinent) {
        $router.push({path: '/profile', query: Object.assign({congratsShow: 'planet-congrats'}, this.lastFrame)})
      } else {
        this._showStepCongrats()
      }
      this.compilerControl.haltProgram(() => {})
      setTimeout(this._updateGalaxyData, 50)
    })
  }

  _failure (frame) {
    return this.initializeAnimation(frame, true, async () => {
      this.lastFrame = frame
      this.failure = true
      this.robot.setState('paused')
      this._failedMessage()
      this._updateGalaxyData()
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
    return this.initializeAnimation(frame, this.currentFrame.index === 0, () => {
      if (this.robot.state === 'running') {
        if (this.robotFrames.length) {
          this._processFrames()
        } else {
          this._waitForFrames(50)
        }
      }
    })
  }

  setDirection (sliderValue, speed) {
    const newDirection = sliderValue > 50 ? 1 : -1
    if (this.direction !== newDirection) {
      this.robotFrames = this.robotFrames.slice(0, 1)
    }
    this.direction = newDirection
    this.levelControl.robot.setSpeed(speed)
    if (this.levelControl.robot.state === 'paused' || this.levelControl.robot.state === 'home') {
      if (this.levelControl.robot.state === 'home' && this.direction < 0) {
        this.direction = 1
      } else {
        this.start()
      }
    } else {
      this.levelControl.robot.setState('running')
    }
  }

  async _processFrames (_) {
    // console.log('frames ~ ', this.robotFrames.slice())
    await this._controlAsk()
    this.currentFrame = this.robotFrames.length === 1 ? this.robotFrames[0] : this.robotFrames.shift()
    const run = await this[`_${this.currentFrame.programState}`](this.currentFrame)
    run(this.currentFrame)
  }

  _lastFrame () {
    return this.robotFrames[this.robotFrames.length - 1] || {index: 0}
  }

  _askBuffer = 5

  _controlAsk () {
    return new Promise(resolve => {
      if (this._lastFrame().programState === 'running' && this.robotFrames.length < this._askBuffer) {
        this._askCompiler({startRunning: resolve})
      } else {
        resolve()
      }
    })
  }

  _mblError (error) {
    const dis = this
    const messageBuilder = {
      type: 'warn',
      msg: error,
      handlers () {
        return {
          closeControl: dis._closeMessageRobotHome()
        }
      }
    }

    this._addMessage(messageBuilder)
  }

  _generateFrames () {
    return {
      index: this.currentFrame.index + this.direction,
      count: 10,
      direction: this.direction
    }
  }

  _askCompiler ({create = false, startRunning = undefined, mbl = undefined}) {
    this.compilerControl._wsOnMessage((compiled) => {
      if (compiled.hasOwnProperty('error')) {
        this._mblError(compiled.error)
        this.robot.setState('failure')
      } else {
        this.robotFrames = compiled.frames
        if (startRunning) startRunning()
      }
    })

    this.compilerControl.send(Object.assign({
      problem: this.levelControl.continent.problem.encryptedProblem,
      create: create,
      frames: this._generateFrames()
    }, mbl ? {mbl} : {}))
  }
}

export default RunCompiled
