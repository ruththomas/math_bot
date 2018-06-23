import api from './api'

class VideoHint {
  constructor (context) {
    this.context = context
    this.$store = this.context.$store
    this.socket = api.videoHintSocket
  }

  _startVideo (videoURL) {
    this.$store.dispatch('toggleHintShowing', {showing: true, videoURL: videoURL})
  }

  getHint () {
    this.socket.getHint(res => {
      this.$store.dispatch('updateStats', res.stats)
      this._startVideo(res.videoURL)
    })
  }
}

export default VideoHint
