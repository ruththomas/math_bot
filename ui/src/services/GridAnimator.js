import $store from '../store/store'

class GridAnimator {
  constructor () {
    this._updateGrid = this._updateGrid.bind(this)
  }

  $store = null
  robot = null
  grid = null
  robotState = null
  toolList = null
  robotSpeed = null
  animations = {
    bumped: 'robot-shake'
  }
  $robot = null

  _animate () {
    const animation = this.animations[this.robotState.animation] || 'walk'
    this.$robot.addClass(animation)
  }

  _removeAnimations () {
    Object.keys(this.animations).forEach(key => {
      this.$robot.removeClass(this.animations[key])
    })
  }

  _updateGrid () {
    // console.log('[cells]', JSON.parse(JSON.stringify(this.robotState.grid.cells)))
    this.robotState.grid.cells.forEach(cell => {
      if (cell.location.x === 2) {
        // console.log(`[x: ${cell.location.x}, y: ${cell.location.y}]`, this.grid[cell.location.x][cell.location.y].tools)
      }
      this.grid[cell.location.x][cell.location.y].tools = cell.items.map((name, tInd) => {
        const originalTool = this.grid[cell.location.x][cell.location.y].tools[tInd]
        if (!originalTool || (originalTool && !originalTool.original)) {
          const newTool = JSON.parse(JSON.stringify(this.toolList[name]))
          newTool.original = false
          return newTool
        } else {
          return originalTool
        }
      })
    })
  }

  _moveRobot () {
    return new Promise(resolve => {
      this._animate()
      this.robot.updateRobot(this.robotState)
      this._updateGrid()
      setTimeout(() => {
        this._removeAnimations()
        resolve()
      }, this.robotSpeed.speed)
    })
  }

  async initializeAnimation (frame, done) {
    this.frame = frame
    this.robotState = frame.robotState
    this.robot = $store.state.levelControl.robot
    this.grid = $store.state.levelControl.gridMap
    this.toolList = $store.state.levelControl.continent.toolList
    this.robotSpeed = this.robot.robotSpeed
    this.$robot = $('.robot')

    await this._moveRobot()
    await this._updateGrid()
    return new Promise(resolve => {
      resolve(done)
    })
  }
}

export default GridAnimator
