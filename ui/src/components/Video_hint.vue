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
$video-hint-close-top: -20px;
$video-hint-close-right: -20px;
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
  top: $video-hint-close-top;
  right: $video-hint-close-right;
}

@media only screen and (max-width : 1280px) and (max-height: 900px) {
}

@media only screen and (max-width: 1280px) {
  $video-hint-height: 350px;
  $video-hint-width: 100%;
  $video-hint-max-width: 700px;

  .video-hint {
    height: $video-hint-height;
    width: $video-hint-width;
    max-width: $video-hint-max-width;
  }

  .close-video-edit {
  }
}

@media only screen and (max-width : 992px) {
  $video-hint-height: 175px;
  $video-hint-width: 100%;
  $video-hint-max-width: 350px;
  $video-hint-close-top: -10px;
  $video-hint-close-right: -10px;

  .video-hint {
    height: $video-hint-height;
    width: $video-hint-width;
    max-width: $video-hint-max-width;
  }

  .close-video-edit {
    top: $video-hint-close-top;
    right: $video-hint-close-right;
    z-index: 1000;
  }
}

/* Small Devices */
@media only screen and (max-width : 736px) {
}

/* Small Devices */
@media only screen and (max-width : 667px) {
  $video-hint-height: 150px;
  $video-hint-width: 100%;
  $video-hint-max-width: 300px;
  $video-hint-close-top: -10px;
  $video-hint-close-right: -10px;

  .video-hint {
    height: $video-hint-height;
    width: $video-hint-width;
    max-width: $video-hint-max-width;
  }

  .close-video-edit {
    top: $video-hint-close-top;
    right: $video-hint-close-right;
  }
}

@media only screen and (max-width: 568px) {
}

/* Extra Small Devices, Phones */
@media only screen and (max-width : 480px) {
}

/* iPad */
@media all and (device-width: 768px) and (device-height: 1024px) and (orientation:portrait) {
  $video-hint-height: 350px;
  $video-hint-width: 100%;
  $video-hint-max-width: 700px;

  .video-hint {
    height: $video-hint-height;
    width: $video-hint-width;
    max-width: $video-hint-max-width;
  }

  .close-video-edit {
  }
}

@media all and (device-width: 768px) and (device-height: 1024px) and (orientation:landscape) {
}
</style>
