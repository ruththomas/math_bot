import { robotImages } from '../assets/assets'
import $store from '../store/store'

class Robot {
  constructor ({state, holding, orientation, location, robotSpeed}) {
    this.$store = $store
    this.state = state || 'home'
    this.robotCarrying = holding || []
    this.robotFacing = orientation || 0
    this.robotLocation = location || {x: 2, y: 2}
    this.robotSpeed = 400

    this.trash = []
  }

  _robotDirections = {
    '0': robotImages.robotUp,
    '90': robotImages.robotRight,
    '180': robotImages.robotDown,
    '270': robotImages.robotLeft
  }

  _robotSpeedIndex = 0

  _updateSelf () {
    $store.state.levelControl.robot = this
  }

  setState (state) {
    this.state = state
    this._updateSelf()
  }

  setSpeed (speed) {
    this.robotSpeed = speed
  }

  /*
  * While robot is animating use this function
  * 'initialRobotState' and 'robotState' can be de-structured into this app
  * */
  updateRobot (robotState) {
    this.robotLocation = robotState.location || this.robotLocation
    this.robotCarrying = robotState.holding || this.robotCarrying
    this.robotFacing = robotState.orientation || this.robotFacing
  }
}

export default Robot
