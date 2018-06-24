import api from './api'
/*
* VideoHint - controls video hints
* */
class VideoHint {
  constructor (context) {
    this.context = context
    this.$store = this.context.$store
    this.socket = api.videoHintSocket
  }

  _startVideo (videoURL) {
    this.$store.dispatch('toggleHintShowing', {showing: true, videoURL: videoURL})
  }

  _getTime (level, step) {
    this.socket.getTime(level, step, res => {
      this.$store.dispatch('addVideoTimer', {level, step, remainingTime: res.remainingTime})
    })
  }

  getHint () {
    this.socket.getHint(res => {
      this.$store.dispatch('updateStats', res.stats)
      this._startVideo(res.videoURL)
      this._getTime(res.stats.level, res.stats.step)
    })
  }
}

export default VideoHint
