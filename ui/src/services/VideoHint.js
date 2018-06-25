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

  getHint () {
    this.socket.getHint(res => {
      this.$store.dispatch('addVideoTimer', res.remainingTime)
      this._startVideo(res.videoURL)
    })
  }
}

export default VideoHint
