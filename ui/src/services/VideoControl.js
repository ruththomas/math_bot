import Ws from './Ws'
import VideoTimer from './VideoTimer'
import { $root } from '../main'
import { getIdFromURL } from 'vue-youtube-embed'
import $store from '../store/store'

class VideoControl extends Ws {
  constructor (state) {
    super('videohint')
    this.state = state

    this.requestHintsTaken = this.requestHintsTaken.bind(this)
    this._startTimers = this._startTimers.bind(this)
    this.getHint = this.getHint.bind(this)
    this.showHint = this.showHint.bind(this)

    this._openSocket(this.requestHintsTaken)
  }

  freeHintsShown = []
  currentVideo = null

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
    this._send(JSON.stringify({action: 'get-hint'}))
  }

  showFreeHint (url) {
    this.freeHintsShown.push(url)
    this.setCurrentVideo(url)
    this.showVideo()
  }

  showHint () {
    this.getHint((res) => {
      if (res.status !== 'no-videos') {
        $store.dispatch('addVideoTimer', res.remainingTime)
        this.setCurrentVideo(res.videoURL)
        this.showVideo()
      } else {
        const messageBuilder = {
          type: 'warn',
          msg: 'No hints available'
        }
        $store.dispatch('addMessage', messageBuilder)
      }
    })
  }

  showVideo () {
    $root.$emit('bv::show::modal', 'video-modal')
  }

  hideVideo () {
    this.currentVideo = null
    $root.$emit('bv::hide::modal', 'video-modal')
  }

  setCurrentVideo (url) {
    this.currentVideo = getIdFromURL(url)
  }
}

export default VideoControl
