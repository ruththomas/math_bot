<template>
  <div class="video-hint">
    <div class="embedded">
      <img class="close-video-edit dialog-button" @click="closeHint" :src="permanentImages.buttons.xButton">
      <div class="hint-spinner">
        <splash-screen></splash-screen>
      </div>
      <youtube
        class="player"
        :video-id="videoId"
        :player-vars="{ autoplay: 1 }"
        :player-height="'100%'"
        :player-width="'100%'"
        @ended="closeHint"
      ></youtube>
    </div>
  </div>
</template>

<script>
import SplashScreen from './Splash_screen'
import { getIdFromURL } from 'vue-youtube-embed'

export default {
  name: 'Video_hint',
  mounted () {
    setTimeout(() => {
      this.splashScreenShowing = false
    }, 80)
  },
  computed: {
    videoId () {
      return getIdFromURL(this.hintShowing.videoURL)
    },
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
  },
  components: {
    SplashScreen
  }
}
</script>

<style scoped lang="scss">
$grid-space-size: 9vmin;
$video-hint-close-top: 0;
$video-hint-close-left: 100%;
$dialog-button-size: 3.5vmin;
$click-color: #B8E986;
$embedded-background: #1b1e21;

.video-hint {
  border: 1px solid transparent;
  border-radius: 5px;
  position: fixed;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #000000;
  z-index: 1000;

  .embedded {
    height: calc(#{$grid-space-size} * 5);
    width: calc(#{$grid-space-size} * 10);
    position: relative;
    display: flex;
    align-items: center;
    justify-content: center;
    background: $embedded-background;

    .player {
      height: 100%;
      width: 100%;
      z-index: 1;
    }

    .hint-spinner {
      position: absolute;
      *:first-child {
        background: $embedded-background!important;
      }
    }
  }
}

.close-video-edit {
  position: absolute;
  top: calc(-#{$dialog-button-size} / 2);
  left: calc(#{$video-hint-close-left} - #{$dialog-button-size} / 2);
  z-index: 2;
}
</style>
