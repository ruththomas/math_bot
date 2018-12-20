import Ws from './Ws'
import Robot from './RobotState'
import RunCompiled from './RunCompiled'
import _ from 'underscore'
import circular from 'circular-json'
import $router from '../router'

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
    this.getCurrentStarSystem = this.getCurrentStarSystem.bind(this)
    this._positionBar = this._positionBar.bind(this)
    this.getSandbox = this.getSandbox.bind(this)

    this._openSocket(this._init)
  }

  mbl = this._retreiveMbl()
  mode = 'normal'
  path = '00000'
  galaxy = null
  continent = null
  robot = null
  functions = null
  gridMap = null
  runCompiled = null

  // temporary until solved server side
  storeMbl () {
    localStorage.setItem('mbl', this.mbl)
  }

  // temporary until solved server side
  _retreiveMbl () {
    const mbl = localStorage.getItem('mbl')
    return mbl !== null ? mbl : ''
  }

  _setPath ({path}) {
    this.path = path
  }

  _setGalaxy ({galaxyData}) {
    this.galaxy = galaxyData
  }

  _setStarSystem ({starSystemData}) {
    this.starSystem = starSystemData
  }

  _setContinent ({pathAndContinent: {path, builtContinent}}) {
    this.continent = builtContinent
    this.path = path
    const robotState = this.continent.initialRobotState
    this.robot = new Robot(robotState)
    this.functions = this.continent.lambdas
    this.gridMap = this.continent.gridMap
    this.runCompiled = new RunCompiled()
    setTimeout(this._positionBar, 500)
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
    // console.log('staged length', functions.stagedFunctions.length)
    // console.log(functions.activeFuncs.map(f => {
    //   return {name: f.name, index: f.index, createdId: f.created_id}
    // }))
    this.functions = functions
  }

  _positionBar () {
    const $mainDropZone = $('.edit-main > .function-drop > .function-drop-drop-zone')
    if ($mainDropZone.length) {
      const $bar = $('.bar')
      const mainDropZoneHalf = $mainDropZone.height() / 2
      const mainDropOffsetTop = $mainDropZone.offset().top + mainDropZoneHalf
      const barOffsetTop = $bar.offset().top
      const barPosTop = $bar.position().top
      $bar.animate({top: (barPosTop + (mainDropOffsetTop - barOffsetTop) - 2) + 'px'}, 100)
    }
  }

  getColorHex (color) {
    switch (color) {
      case 'any':
        return '#696969'
      case 'empty':
        return '#353535'
      case 'white':
        return '#ffffff'
      case '1':
        return '#4A90E2'
      case '10':
        return '#CA7AFF'
      case '100':
        return '#50E3C2'
      case '1000':
        return '#FF98B1'
      case '10000':
        return '#F25C5C'
      case '.1':
        return '#CDF7F6'
      case '.01':
        return '#8FB8DE'
      case '.001':
        return '#9A94BC'
      case '.0001':
        return '#9B5094'
      case '.00001':
        return '#533A71'
      case 'x':
        return '#9CEC5B'
      case 'y':
        return '#F0F465'
      case 'z':
        return '#A3320B'
      case '-.00001':
        return '#E3BAC6'
      case '-.0001':
        return '#FFF5B2'
      case '-.001':
        return '#EC7357'
      case '-.01':
        return '#E1CE7A'
      case '-.1':
        return '#9D695A'
      case '-1':
        return '#78E0DC'
      case '-10':
        return '#A1CDF1'
      case '-100':
        return '#2D5D7B'
      case '-1000':
        return '#C2AFF0'
      case '-10000':
        return '#7D5C65'
      default:
        return '#FFD700' // color name changed in server, checkout `app/compiler/ElementKinds.scala` for new pallet
    }
  }

  activateFunction (func) {
    this._wsOnMessage((updated) => {
      this._setFunctions(updated.preparedFunctions)
    })
    this._send(JSON.stringify({action: 'activate-function', 'function': func}))
  }

  deactivateFunction (func) {
    // clear function fields
    Object.assign(func, {
      displayName: false,
      name: '',
      func: [],
      color: 'white'
    })
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

  findFunctionIndex (createdId) {
    return this.functions.activeFuncs.reduce((atIndex, func, ind) => {
      if (func.created_id === createdId) atIndex = ind
      return atIndex
    }, 0)
  }

  updateFunction (func) {
    this._wsOnMessage(this._positionBar) // doing nothing with response for now
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

  resetContinent () {
    this._wsOnMessage((res) => {
      this._setContinent({pathAndContinent: res.pathAndContinent})
      this.runCompiled = new RunCompiled()
    })
    this._send(JSON.stringify({action: 'reset-continent', path: this.path}))
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

  isLastPlanet (pathToCheck) {
    const path = pathToCheck || this.path
    return this.galaxy.starSystems[path[2]].planets.length - 1 === Number(path[3])
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
    $('.steps-container').animate({
      scrollTop: 0
    }, 1000)
  }

  getCurrentStarSystem () {
    return this.galaxy.starSystems[Number(this.path[2])]
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

  getSandbox () {
    this._wsOnMessage((res) => {
      this._setContinent({pathAndContinent: res.pathAndContinent})
      $router.push({path: '/robot'})
    })
    this._send(JSON.stringify({action: 'get-sandbox'}))
  }
}

export default LevelControl
