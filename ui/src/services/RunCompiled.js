import GridAnimator from './GridAnimator'
import _ from 'underscore'
import $store from '../store/store'
import $router from '../router'
import { $root } from '../main'

class RunCompiled extends GridAnimator {
  constructor () {
    super()
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
    this._resetStep = this._resetStep.bind(this)
    this._waitForFrames = this._waitForFrames.bind(this)
    this.initializeNextStep = this.initializeNextStep.bind(this)
    this.resetIfFailure = this.resetIfFailure.bind(this)
    this.startMbl = this.startMbl.bind(this)
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

  startMbl () {
    this.start($store.state.levelControl.mbl)
  }

  start (mbl) {
    const emptyFuncs = this._testForEmptyFunctions()

    if (emptyFuncs.length) {
      this._mainEmptyMessage(emptyFuncs)
    } else if (this.robot.state !== 'paused') {
      this.robotFrames = []
      this._deleteAllMessages()
      this.robot.setState('running')
      this._askCompiler(mbl, true, this._processFrames)
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

  reset () {
    this.robot.state = 'home'
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
    const failedMessage = {
      type: 'success',
      msg: `Not quite, a hint might help. Click on any icon to restart.`,
      handlers () {
        const $helpButton = $('.help-button')
        const $controlBar = $('.control-bar')
        const $trash = $('.trash')
        const $editMain = $('.edit-main .piece')
        return {
          runBeforeAppend () {
            $helpButton.addClass('background-alert')
            $helpButton.addClass('animated flash')
            $controlBar.addClass('hidden-bar')
            $trash.hide()
            $editMain.hide()
            setTimeout(() => {
              $controlBar.removeClass('hidden-bar')
              $trash.show()
              $editMain.show()
            }, 2005)
          },
          runOnDelete () {
            $helpButton.removeClass('flash')
            $helpButton.removeClass('background-alert')
          },
          closeControl: dis._closeMessageRobotHome()
        }
      }
    }
    this._addMessage(failedMessage)
  }

  _mainEmptyMessage (emptyFuncs) {
    const emptyCount = emptyFuncs.length

    const messageBuilder = {
      type: 'warn',
      msg: emptyFuncs.find(f => f.name === 'Main') ? 'Main cannot be empty' : `${emptyFuncs.length} of your functions ${emptyCount > 1 ? 'are' : 'is'} empty`,
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
    this.levelControl.getContinent(this.levelControl.path, (res) => {
      $store.state.levelControl._resetContinent({pathAndContinent: res})
      this.constructor(this.context)
    })
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
      this.robot.setState('failure')
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
        this.robotFrames = this.robotFrames.concat(this.robot.robotSpeed.display === 'lightning' ? compiled.frames.pop() : compiled.frames)
        if (startRunning) startRunning()
      }
    })
    this.compilerControl.send({
      problem: this.levelControl.continent.problem.encryptedProblem,
      halt: false,
      mbl: mbl,
      create: create,
      steps: this.robot.robotSpeed.display === 'lightning' ? 10100 : undefined
    })
  }
}

export default RunCompiled
