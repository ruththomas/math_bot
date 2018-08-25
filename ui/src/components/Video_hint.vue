<template>
  <div class="video-hint">
    <img class="close-video-edit dialog-button" @click="closeHint" :src="permanentImages.buttons.xButton">
    <iframe class="embedded" :src="hintShowing.videoURL" scrolling="no" frameborder="0"></iframe>
  </div>
</template>

<script>
export default {
  name: 'Video_hint',
  computed: {
    hintShowing () {
      return this.$store.getters.getHintShowing
    },
    permanentImages () {
      return this.$store.getters.getPermanentImages
    }
  },
  methods: {
    closeHint () {
      this.$store.dispatch('toggleHintShowing', {showing: false, videoURL: ''})
    }
  }
}
</script>

<style scoped lang="scss">
$grid-space-size: 9.5vmin;
$video-hint-shadow: inset 0 0 200px #000;
$video-hint-close-top: -20px;
$video-hint-close-right: -20px;
.video-hint {
  height: calc(#{$grid-space-size} * 5);
  width: calc(#{$grid-space-size} * 10);
  border: 1px solid transparent;
  margin: 0 auto;
  border-radius: 5px;
  box-shadow: $video-hint-shadow;
  position: relative;

  .embedded {
    height: 100%;
    width: 100%;
  }
}

.close-video-edit {
  position: absolute;
  top: $video-hint-close-top;
  right: $video-hint-close-right;
}
</style>
