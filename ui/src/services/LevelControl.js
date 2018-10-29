import Ws from './Ws'
import $router from '../router'
import Robot from './RobotState'
import RunCompiled from './RunCompiled'

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

    this._openSocket(this._init)
  }

  path = null
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

  _setContinent ({pathAndContinent: {path, builtContinent}}) {
    this.continent = builtContinent
    this.path = path
    const robotState = this.continent.initialRobotState
    const robotSpeed = this.robot === null ? 0 : this.robot.robotSpeed
    this.robot = new Robot(Object.assign(robotState, {robotSpeed: robotSpeed}))
    this.functions = this.continent.lambdas
    this.gridMap = this.continent.gridMap
    this.runCompiled = new RunCompiled()
    console.log(this.functions)
    // console.log(JSON.stringify(this.functions, null, 2))
  }

  updateFunction (func) {
    this._wsOnMessage(() => {}) // doing nothing with response for now
    this._send(JSON.stringify({action: 'update-function', 'function': func}))
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
          this._setContinent(res)
          break
        default:
          console.error(res.status, res.message)
      }
    })
    this._send(JSON.stringify({action: 'init'}))
    $router.push({path: '/profile'})
  }
}

export default LevelControl
