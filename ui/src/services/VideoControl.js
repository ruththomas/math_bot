import Ws from './Ws'
import VideoTimer from './VideoTimer'

class VideoControl extends Ws {
  constructor (state) {
    super('videohint')
    this.state = state

    this.requestHintsTaken = this.requestHintsTaken.bind(this)
    this._startTimers = this._startTimers.bind(this)

    this._openSocket(this.requestHintsTaken)
  }

  _requestHint () {
    this._openSocket(() => {
      this._send(JSON.stringify({action: 'get-hint', tokenId: this.tokenId}))
    })
  }

  _startTimers (remainingTimes) {
    let timers = {}
    remainingTimes.forEach(rt => {
      timers[rt.continentId] = new VideoTimer(rt.remainingTime, rt.videoCount, rt.stars, this.state)
    })
    this.state.videoTimers = timers
  }

  requestHintsTaken () {
    this._wsOnMessage((hints) => {
      this._startTimers(hints.remainingTimes)
    }, this._handleErr)
    this._send(JSON.stringify({action: 'get-hints-taken'}))
  }

  getHint (cb) {
    this._wsOnMessage(cb)
    this._requestHint()
  }
}

export default VideoControl
