import api from './api'
/*
* VideoHintControls - controls video hints
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

  startFreeHint (videoURL) {
    this._startVideo(videoURL)
  }

  getHint () {
    this.socket.getHint(res => {
      if (res.status !== 'no-videos') {
        this.$store.dispatch('addVideoTimer', res.remainingTime)
        this._startVideo(res.videoURL)
      } else {
        const messageBuilder = {
          type: 'warn',
          msg: 'No hints available'
        }
        this.$store.dispatch('addMessage', messageBuilder)
      }
    })
  }
}

export default VideoHint
