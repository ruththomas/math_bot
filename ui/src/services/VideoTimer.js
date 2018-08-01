import api from './api'
/*
* VideoTimer - used to create a timer for all level/steps that have watched a video
* After 60 minutes all stars are reset
* */
class VideoTimer {
  constructor ({state, level, step, stars, remainingTime}) {
    this.state = state
    this.level = level
    this.step = step
    this.stars = stars

    this._startTimer = this._startTimer.bind(this)
    this._setTime = this._setTime.bind(this)

    this._timerSpeed = 1000
    this.remainingTime = remainingTime * 60 // converted to seconds

    this._startTimer()
  }

  _setTime () {
    --this.remainingTime
  }

  _resetVideos () {
    api.videoHintSocket.requestHintsTaken(res => {
      const timers = {}
      res.remainingTimes.forEach(rt => {
        timers[`${rt.level}/${rt.step}`] = new VideoTimer({state: this.state, level: rt.level, step: rt.step, stars: rt.stars, remainingTime: rt.remainingTime})
      })
      this.state.videoTimers = timers
    })
  }

  _startTimer () {
    if (this.remainingTime === 0) return this._resetVideos()

    setTimeout(() => {
      this._setTime()
      this._startTimer()
    }, this._timerSpeed)
  }
}

export default VideoTimer
