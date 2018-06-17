<template>
  <div class="speech-bubble" :class="!showing ? 'hide-speech-bubble' : ''">
    <div class="bubble-text">
      <div v-html="html"></div>
      <stars :star-group="'star-spread'" :step="step"></stars>
    </div>
  </div>
</template>

<script>
import Stars from './Stars'
export default {
  name: 'speech_bubble',
  components: {Stars},
  props: ['html', 'showing', 'step']
}
</script>

<style scoped src="../css/scoped/speechBubble.scss" lang="scss"></style>
<style src="../css/global/speechBubble.css"></style>
