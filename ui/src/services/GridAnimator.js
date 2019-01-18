import $store from '../store/store'

class GridAnimator {
  constructor () {
    this._updateGrid = this._updateGrid.bind(this)
  }

  sounds = null
  robot = null
  grid = null
  robotState = null
  toolList = null
  robotSpeed = null
  animations = {
    bumped: 'robot-shake'
  }
  $robot = null

  _makeSound () {
    if (!this.isLastOrFirst && this.$robot !== null) {
      const sound = this.sounds[this.robotState.animation] || this.sounds.walk
      sound.play()
    }
  }

  _animate () {
    if (this.$robot !== null) {
      const animation = this.animations[this.robotState.animation] || 'walk'
      this.$robot.addClass(animation)
    }
  }

  _removeAnimations () {
    if (this.$robot !== null) {
      Object.keys(this.animations).forEach(key => {
        this.$robot.removeClass(this.animations[key])
      })
    }
  }

  _updateGrid () {
    if (this.$robot !== null && this.robotState.grid) {
      // console.log('[cells]', JSON.parse(JSON.stringify(this.robotState.grid.cells)))
      this.robotState.grid.cells.forEach(cell => {
        if (cell.location.x === 2) {
          // console.log(`[x: ${cell.location.x}, y: ${cell.location.y}]`, this.grid[cell.location.x][cell.location.y].tools)
        }
        this.grid[cell.location.x][cell.location.y].tools = cell.items.map((name, tInd) => {
          const originalTool = this.grid[cell.location.x][cell.location.y].tools[tInd]
          if (!originalTool || (originalTool && !originalTool.original)) {
            const newTool = JSON.parse(JSON.stringify(this.toolList.find((t) => t.name === name)))
            newTool.original = false
            return newTool
          } else {
            return originalTool
          }
        })
      })
    }
  }

  _moveRobot () {
    if (this.$robot !== null) {
      return new Promise(resolve => {
        this._makeSound()
        this._animate()
        this.robot.updateRobot(this.robotState)
        this._updateGrid()
        setTimeout(() => {
          this._removeAnimations()
          resolve()
        }, this.robotSpeed)
      })
    }
  }

  /**
   * @description This function is temporary because the compiler is replacing holding with the animation
   * @param robotState
   * @private
   * todo -> remove this function after issue is addressed in compiler
   */
  _fixAnimation (robotState) {
    if (robotState.holding && typeof robotState.holding === 'string') {
      robotState.animation = robotState.holding
      robotState.holding = []
    }
    return robotState
  }

  async initializeAnimation (frame, isLastOrFirstFrame, done) {
    this.frame = frame
    this.isLastOrFirst = isLastOrFirstFrame
    this.robotState = this._fixAnimation(frame.robotState)
    this.robot = $store.state.levelControl.robot
    this.sounds = $store.state.soundControl.sounds
    this.grid = $store.state.levelControl.gridMap
    this.toolList = $store.state.levelControl.continent.toolList
    this.robotSpeed = this.robot.robotSpeed
    this.$robot = $('.grid-robot')
    await this._moveRobot()
    await this._updateGrid()
    return new Promise(resolve => {
      resolve(done)
    })
  }
}

export default GridAnimator
