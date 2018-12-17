import GridAnimator from './GridAnimator'
import _ from 'underscore'
import $store from '../store/store'
import $router from '../router'
import { $root } from '../main'

class RunCompiled extends GridAnimator {
  constructor () {
    super()
    this.currentFrame = -1
    this.forward = true
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

  _testForEmptyFunctions () {
    const mainFunction = $store.state.levelControl.functions.main.func
    const activeFuncs = $store.state.levelControl.functions.activeFuncs

    if (!mainFunction.length) return [{name: 'Main'}]

    return _.chain(mainFunction)
      .map(f => {
        const funcToken = activeFuncs.find(af => af.created_id === f.created_id)
        if (funcToken) return funcToken
        else return null
      })
      .filter(func => func !== null)
      .filter(func => !func.func.length)
      .value()
  }

  resetIfFailure () {
    if (this.robot.state === 'failure') {
      this.reset()
    }
  }

  clearMbl () {
    $store.state.levelControl.mbl = ''
  }

  start () {
    const emptyFuncs = this._testForEmptyFunctions()
    const normalMode = $store.state.levelControl.mode === 'normal'
    if (normalMode && emptyFuncs.length) {
      this._mainEmptyMessage(emptyFuncs)
    } else if (this.robot.state !== 'paused') {
      this.robotFrames = []
      this._deleteAllMessages()
      this.robot.setState('running')
      this._askCompiler(!normalMode ? $store.state.levelControl.mbl : false, true, this._processFrames)
    } else {
      this.robot.setState('running')
      this._processFrames()
    }
  }

  pause () {
    // this._pausedMessage()
    this.forward = true
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

  _mainEmptyMessage () {
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
    return this.initializeAnimation(frame, async () => {
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
    return this.initializeAnimation(frame, async () => {
      this.lastFrame = frame
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
    return this.initializeAnimation(frame, () => {
      if (this.robot.state === 'running') {
        if (this.robotFrames.length) {
          this._processFrames()
        } else {
          this._waitForFrames(50)
        }
      }
    })
  }

  setDirection (forward = !this.forward) {
    this.forward = forward
  }

  _nextCurrent () {
    this.currentFrame = Math.min(this.robotFrames.length - 1, this.forward ? this.currentFrame + 1 : Math.max(this.currentFrame - 1, 0))
    return this.currentFrame
  }

  async _processFrames (_) {
    // console.log('frames ~ ', this.robotFrames.slice())
    const current = this.robotFrames[this._nextCurrent()]
    this._controlAsk()
    const run = await this[`_${current.programState}`](current)
    run(current)
  }

  _lastFrame () {
    return this.robotFrames[this.robotFrames.length - 1]
  }

  _controlAsk () {
    if (this._lastFrame().programState === 'running' && this.robotFrames.length - this.currentFrame < 50) {
      this._askCompiler()
    }
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

  _askCompiler (mbl, create, startRunning) {
    this.compilerControl._wsOnMessage((compiled) => {
      if (compiled.hasOwnProperty('error')) {
        this._mblError(compiled.error)
        this.robot.setState('failure')
      } else {
        this.robotFrames = this.robotFrames.concat(compiled.frames)
        if (startRunning) startRunning()
      }
    })
    this.compilerControl.send({
      problem: this.levelControl.continent.problem.encryptedProblem,
      halt: false,
      mbl: mbl,
      create: create
    })
  }
}

export default RunCompiled
