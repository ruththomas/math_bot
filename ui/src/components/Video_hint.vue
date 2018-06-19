<template>
  <div class="video-hint">
    <iframe class="embeded" :src="hintShowing.videoURL" scrolling="no" frameborder="0" allowfullscreen="true"></iframe>
  </div>
</template>

<script>
export default {
  name: 'Video_hint',
  computed: {
    hintShowing () {
      return this.$store.getters.getHintShowing
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

  .embeded {
    height: 100%;
    width: 100%;
  }
}
</style>
