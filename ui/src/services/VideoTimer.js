import api from './api'

class VideoTimer {
  constructor ({state, level, step, remainingTime}) {
    this.state = state
    this.level = level
    this.step = step

    this._startTimer = this._startTimer.bind(this)
    this._setTime = this._setTime.bind(this)

    // console.log('INSTANT.', level, step, remainingTime)

    this._timerSpeed = 60000
    this.remainingTime = remainingTime

    this._startTimer()
  }

  _resetStats () {
    api.getStats({tokenId: this.state.auth.userToken.token_id}, stats => {
      this.state.auth.userToken.stats = stats
    })
  }

  _getTime () {
    api.videoHintSocket.getTime(this.level, this.step, res => {
      if (res.remainingTime === 0) {
        this._resetStats()
      } else {
        this.remainingTime = res.remainingTime
        this._startTimer()
      }
    })
  }

  _setTime () {
    --this.remainingTime
  }

  _startTimer () {
    if (this.remainingTime === 0) {
      this._getTime()
      return
    }
    setTimeout(() => {
      this._setTime()
      this._startTimer()
    }, this._timerSpeed)
  }
}

export default VideoTimer
