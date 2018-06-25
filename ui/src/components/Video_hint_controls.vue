<template>
    <div class="video-hint" @click="videoHint.getHint()" v-if="videoHint !== null">
      <stars
        :star-group="'star-spread'"
        :level="level"
        :step="step"
        :step-stats="stepStats"></stars>
    </div>
</template>

<script>
import Stars from './Stars'
import VideoHint from '../services/VideoHint'

export default {
  name: 'VideoHint',
  computed: {
    level () {
      return this.$store.getters.getLevel
    },
    step () {
      return this.$store.getters.getStep
    },
    steps () {
      return this.$store.getters.getSteps
    },
    stepStats () {
      const stepName = this.step
      return this.steps.find(s => s.name === stepName)
    }
  },
  data () {
    return {
      getTime: false,
      videoHint: new VideoHint(this)
    }
  },
  components: {
    Stars
  }
}
</script>

<style scoped lang="scss">
.video-hint {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  margin: 0 auto;
  display: flex;
  justify-content: center;
  cursor: pointer;
}
</style>
