import api from './api'
import { getIdFromURL } from 'vue-youtube-embed'
/*
* VideoHintControls - controls video hints
* */
class VideoHint {
  constructor (context) {
    this.context = context
    this.$store = this.context.$store
    this.socket = api.videoHintSocket
  }

  currentVideo = null

  showVideo (videoURL) {
    if (videoURL) this.setCurrentVideo(videoURL)
    this.context.$root.$emit('bv::show::modal', 'video-modal')
  }

  hideVideo () {
    this.currentVideo = null
    this.context.$root.$emit('bv::hide::modal', 'video-modal')
  }

  setCurrentVideo (url) {
    this.currentVideo = getIdFromURL(url)
  }

  startVideo () {
    if (this.currentVideo === null) {
      this.socket.getHint(res => {
        if (res.status !== 'no-videos') {
          this.$store.dispatch('addVideoTimer', res.remainingTime)
          this.setCurrentVideo(res.videoURL)
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
}

export default VideoHint
