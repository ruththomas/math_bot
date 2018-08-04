<template>
  <div
    class="message-template congrats"
  >
    <div v-if="congrats" class="won">
      <img class="congrats-icon won-icon" :src="permanentImages.smileyFace" />
      <stars :level="level" :step="step" :step-stats="stepStats" :star-group="'star-spread'"></stars>
      <div class="text-minor">You won!</div>
    </div>
    <div v-else class="lost">
      <img class="congrats-icon lost-icon" :src="permanentImages.thinkingFace"/>
      <div class="text-minor">Try again!</div>
    </div>
  </div>
</template>

<script>
import Stars from './Stars'
export default {
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
    },
    permanentImages () {
      return this.$store.getters.getPermanentImages
    }
  },
  props: ['congrats'],
  components: {
    Stars
  }
}
</script>

<style scoped lang="scss">
  $grid-space-size: 96px;
  $click-color: #B8E986;
  $grid-background: rgba(0, 0, 0, 0.6);
  $grid-border-radius: 4px;
  $icon-size: 150px;

  .message-template {
    border: 1px solid $click-color;
    background: $grid-background;
    border-radius: $grid-border-radius;
    position: relative;
    height: calc(#{$grid-space-size} * 5);
    width: calc(#{$grid-space-size} * 10);
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 0 auto;

    .text-minor {
      font-size: 24px;
    }

    .congrats-icon {
      height: $icon-size;
      width: $icon-size;
    }

    .won {
      .stars {
        height: calc(#{$icon-size} / 2);
        width: 25%;
        margin: 0 auto;
      }
    }
  }

  /* ipad pro Portrait */
  @media only screen
  and (min-device-width: 1024px)
  and (max-device-height: 1366px)
  and (orientation: portrait)
  and (-webkit-min-device-pixel-ratio: 1.5) {
    $grid-space-size: 56px;
    .message-template {
      height: calc(#{$grid-space-size} * 5);
      width: calc(#{$grid-space-size} * 10);
    }
  }

  // ipad landscape
  @media only screen
  and (min-device-width : 768px)
  and (max-device-width : 1024px)
  and (orientation : landscape) {
    $grid-space-size: 56px;
    .message-template {
      height: calc(#{$grid-space-size} * 5);
      width: calc(#{$grid-space-size} * 10);
    }
  }

  // ipad portrait
  @media only screen
  and (min-device-width : 768px)
  and (max-device-width : 1024px)
  and (orientation : portrait)  {
    $grid-space-size: 56px;
    .message-template {
      height: calc(#{$grid-space-size} * 5);
      width: calc(#{$grid-space-size} * 10);
    }
  }

  @media only screen and (max-width: 823px) and (orientation: landscape) {
    $grid-space-size: 32px;
    .message-template {
      height: calc(#{$grid-space-size} * 5);
      width: calc(#{$grid-space-size} * 10);
    }
  }

  @media only screen and (max-width : 736px) and (orientation: landscape) {
    $grid-space-size: 38px;
    .message-template {
      height: calc(#{$grid-space-size} * 5);
      width: calc(#{$grid-space-size} * 10);
    }
  }

  @media only screen and (max-width: 667px) and (orientation: landscape) {
    $grid-space-size: 30px;
    .message-template {
      height: calc(#{$grid-space-size} * 5);
      width: calc(#{$grid-space-size} * 10);
    }
  }

  @media only screen and (max-width: 568px) and (orientation: landscape) {
    $grid-space-size: 26px;
    .message-template {
      height: calc(#{$grid-space-size} * 5);
      width: calc(#{$grid-space-size} * 10);
    }
  }

  @media only screen and (max-width: 414px) {
    $grid-space-size: 32px;
    .message-template {
      height: calc(#{$grid-space-size} * 5);
      width: calc(#{$grid-space-size} * 10);
    }
  }

  @media only screen and (max-width: 375px) {
    $grid-space-size: 26px;
    .message-template {
      height: calc(#{$grid-space-size} * 5);
      width: calc(#{$grid-space-size} * 10);
    }
  }

  @media only screen and (max-width : 320px) {
    $grid-space-size: 24px;
    .message-template {
      height: calc(#{$grid-space-size} * 5);
      width: calc(#{$grid-space-size} * 10);
    }
  }
</style>
