import Ws from './Ws'
import Robot from './RobotState'
import RunCompiled from './RunCompiled'
import _ from 'underscore'
import circular from 'circular-json'
import $store from '../store/store'

class LevelControl extends Ws {
  constructor () {
    super('level')
    this._setPath = this._setPath.bind(this)
    this._init = this._init.bind(this)
    this._setStarSystem = this._setStarSystem.bind(this)
    this._setGalaxy = this._setGalaxy.bind(this)
    this._setContinent = this._setContinent.bind(this)
    this.getPath = this.getPath.bind(this)
    this.getStarSystem = this.getStarSystem.bind(this)
    this.getGalaxyData = this.getGalaxyData.bind(this)
    this.getContinent = this.getContinent.bind(this)
    this.updateFunction = this.updateFunction.bind(this)
    this.updatePlanet = this.updatePlanet.bind(this)
    this.updateFunctionProperties = this.updateFunctionProperties.bind(this)
    this._resetContinent = this._resetContinent.bind(this)
    this._prepFunc = this._prepFunc.bind(this)
    this._setFunctions = this._setFunctions.bind(this)

    this._openSocket(this._init)
  }

  mbl = ''
  path = '00000'
  galaxy = null
  continent = null
  robot = null
  functions = null
  gridMap = null
  runCompiled = null

  _setPath ({path}) {
    this.path = path
  }

  _setGalaxy ({galaxyData}) {
    this.galaxy = galaxyData
  }

  _setStarSystem ({starSystemData}) {
    this.starSystem = starSystemData
  }

  _setContinent ({pathAndContinent: {path, builtContinent}}, dontShowHint) {
    this.continent = builtContinent
    this.path = path
    const robotState = this.continent.initialRobotState
    const robotSpeed = this.robot === null ? 0 : this.robot.robotSpeed
    this.robot = new Robot(Object.assign(robotState, {robotSpeed: robotSpeed}))
    this.functions = this.continent.lambdas
    this.gridMap = this.continent.gridMap
    this.runCompiled = new RunCompiled()
    if (!dontShowHint) {
      $store.state.videoHintControl.showFreeHint(this.continent.freeHint)
    }
  }

  _resetContinent ({pathAndContinent: {path, builtContinent}}) {
    this.continent = builtContinent
    this.functions = this.continent.lambdas
  }

  _updatePath () {
    this._wsOnMessage(() => {})
    this._send(JSON.stringify({action: 'update-path', path: this.path}))
  }

  _setFunctions (functions) {
    this.functions = functions
  }

  activateFunction (func) {
    this._wsOnMessage((updated) => {
      this._setFunctions(updated.preparedFunctions)
    })
    this._send(JSON.stringify({action: 'activate-function', 'function': func}))
  }

  deactivateFunction (func) {
    this._wsOnMessage((updated) => {
      this._setFunctions(updated.preparedFunctions)
    })
    this._send(JSON.stringify({action: 'deactivate-function', 'function': func}))
  }

  /*
  * removes func contents from nested functions then stringifies function
  * deals with circular reference issue with recursive functions
  * to be used with update function
  * */
  _prepFunc (func) {
    const copied = circular.stringify(func)
    const parsed = circular.parse(copied)
    parsed.func = parsed.func.map((f) => {
      return _.omit(f, 'func')
    })
    return parsed
  }

  updateFunction (func) {
    this._wsOnMessage(() => {}) // doing nothing with response for now
    this._send(JSON.stringify({action: 'update-function', 'function': this._prepFunc(func)}))
  }

  updateFunctionProperties (func) {
    this._wsOnMessage(this._resetContinent)
    this._send(JSON.stringify({action: 'update-function-properties', 'function': this._prepFunc(func)}))
  }

  getPath () {
    this._wsOnMessage(this._setPath)
    this._send(JSON.stringify({action: 'get-path'}))
  }

  getGalaxyData () {
    this._wsOnMessage(this._setGalaxy)
    this._send(JSON.stringify({action: 'get-galaxy'}))
  }

  getStarSystem (id) {
    this._wsOnMessage(this._setStarSystem)
    this._send(JSON.stringify({action: 'get-star-system', path: id}))
  }

  getContinent (path, cb) {
    this._wsOnMessage(({pathAndContinent}) => {
      this._setContinent({pathAndContinent})
      if (cb) cb(pathAndContinent)
    })
    this._send(JSON.stringify({action: 'get-continent', path: path || undefined}))
  }

  deleteMain () {
    this.functions.main.func = []
    this.updateFunction(this.functions.main)
  }

  deleteFunction (func) {
    func.func = []
    this.updateFunction(func)
  }

  isLastContinent (pathToCheck) {
    const path = pathToCheck || this.path
    return this.galaxy.starSystems[path[2]].planets[path[3]].continents.length - 1 === Number(path.slice(4))
  }

  isEndOfGame (pathToCheck) {
    const path = pathToCheck || this.path
    return this.galaxy.starSystems.length - 1 === Number(path[2])
  }

  updateStarSystem (ind) {
    this.path = this.path.substr(0, 2) + ind + '00'
    this._updatePath()
  }

  updatePlanet (ind) {
    this.path = this.path.substr(0, 3) + ind + '0'
    this._updatePath()
  }

  getNextStarSystem () {
    return this.galaxy.starSystems[Number(this.path[2]) + 1]
  }

  getNextPlanet () {
    return this.galaxy.starSystems[this.path[2]].planets[Number(this.path[3]) + 1]
  }

  getPlanetStats () {
    return this.galaxy.starSystems[this.path[2]].planets[this.path[3]].stats
  }

  _init () {
    this._wsOnMessage((res) => {
      switch (Object.keys(res).filter((key) => key !== 'status')[0]) {
        case 'path':
          this._setPath(res)
          break
        case 'galaxyData':
          this._setGalaxy(res)
          break
        case 'pathAndContinent':
          this._setContinent(res, true)
          break
        default:
          console.error(res.status, res.message)
      }
    })
    this._send(JSON.stringify({action: 'init'}))
  }
}

export default LevelControl
