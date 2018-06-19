<template>
  <div class="speech-bubble" :class="!showing ? 'hide-speech-bubble' : ''">
    <div class="bubble-text" v-html="html"></div>
    <video-hint></video-hint>
  </div>
</template>

<script>
import VideoHint from './Video_hint_controls'
export default {
  name: 'speech_bubble',
  components: {VideoHint},
  props: ['html', 'showing', 'step']
}
</script>

<style scoped src="../css/scoped/speechBubble.scss" lang="scss"></style>
<style src="../css/global/speechBubble.css"></style>
