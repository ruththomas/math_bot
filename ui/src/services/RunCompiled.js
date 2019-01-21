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
      if (this.robot.state === 'home') {
        this.robotFrames = []
        this._deleteAllMessages()
        this.robot.setState('running')
        this._askCompiler(Object.assign({create: true, startRunning: this._processFrames}, !normalMode ? {mbl: $store.state.levelControl.mbl} : {}))
      } else if (this.robot.state === 'paused') {
        this.robot.setState('running')
        this._processFrames()
      }
    }
  }

  pause () {
    this.robot.setSpeed(400)
    this.robot.setState('paused')
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

  /**
   * @description runs when programState of success is returned from the compiler
   * @param frame {Object}
   * @returns {Promise<*>}
   * @private
   */
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

  /**
   * @description runs when programState of failure is returned from compiler
   * @param frame {Object}
   * @returns {Promise<*>}
   * @private
   */
  _failure (frame) {
    return this.initializeAnimation(frame, true, async () => {
      this.lastFrame = frame
      this.failure = true
      this.robot.setState('paused')
      this._failedMessage()
      this._updateGalaxyData()
    })
  }

  /**
   * @description This is a safety feature in case there is some latency from the compiler
   * @param waitTime {Number}
   * @returns {*}
   * @private
   */
  _waitForFrames (waitTime) {
    if (this.robotFrames.length) return this._processFrames()
    else if (waitTime === 0) return this._stopRobot()
    setTimeout(() => this._waitForFrames(waitTime - 1), 50)
  }

  /**
   * @description runs when frame has programState of running
   * @param frame {Object}
   * @returns {Promise<*>}
   * @private
   */
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

  /**
   * @description Sets robot direction and restarts robot
   * @param sliderValue {Number}
   * @param speed {Number}
   */
  setDirection (sliderValue, speed) {
    // todo -> after testing in prod run this through a function to skip frames, if needed.
    const newDirection = sliderValue > 50 ? 1 : -1

    // If direction changes reduce robotFrames to a single frame so _controlAsk is forced
    // to ask for more frames immediately
    if (this.direction !== newDirection) {
      this.robotFrames = this.robotFrames.slice(0, 1)
    }

    // Change direction of robot
    this.direction = newDirection
    // Set robot speed. Speed is calculated in Grid_controls.vue based on slider value
    this.levelControl.robot.setSpeed(speed)

    // Ensure robot direction is forward if robot is home
    if (this.robot.state === 'home') this.direction = 1

    // Start robot
    this.start()
  }

  /**
   * @description Asynchronously processes frames from robotFrames
   * @returns {Promise<void>}
   * @private
   */
  async _processFrames () {
    // Call control ask to get more frames
    // this must be async to ensure the robotFrames are correct in the case of a direction change
    await this._controlAsk()
    // After frames are updated set current frame. Current frame cannot be empty
    this.currentFrame = this.robotFrames.length === 1 ? this.robotFrames[0] : this.robotFrames.shift()
    // Run appropriately named function based on programState from compiler
    // this._running
    // this._success
    // this._failure
    const run = await this[`_${this.currentFrame.programState}`](this.currentFrame)
    // After animation is finished run cb
    run(this.currentFrame)
  }

  _lastFrame () {
    return this.robotFrames[this.robotFrames.length - 1] || {index: 0}
  }

  _askBuffer = 5

  /**
   * @description Calls for more frames if robot frames is less than _askBuffer
   * @returns {Promise} - this returns a promise to guarantee frames have been replaced in the case of a direction change
   * @private
   */
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

  /**
   * @description Calls compiled for more frames
   * @param create {Boolean} - Should only be true for first call of a program
   * @param startRunning {function} - Cb that can be used after frames are updated
   * @param mbl {String} - Mathbot lang text if in advanced mode
   * @private
   */
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
