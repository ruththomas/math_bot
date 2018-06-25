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
$video-hint-height: 500px;
$video-hint-width: 1000px;
$video-hint-shadow: inset 0 0 200px #000;
.video-hint {
  height: $video-hint-height;
  width: $video-hint-width;
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
  top: -20px;
  right: -20px;
}
</style>
