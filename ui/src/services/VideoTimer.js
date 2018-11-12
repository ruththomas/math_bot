/*
* VideoTimer - used to create a timer for all level/steps that have watched a video
* After 60 minutes all stars are reset
* */
class VideoTimer {
  constructor (remainingTime, count, stars, state) {
    this.state = state
    this.count = count
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
    this.state.videoHintControl.requestHintsTaken()
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
