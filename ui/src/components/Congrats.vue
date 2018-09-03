<template>
  <div
    class="row message-template congrats"
  >
    <div v-if="congrats" class="won">
      <img class="congrats-icon won-icon" :src="permanentImages.smileyFace" />
      <stars :level="level" :step="step" :step-stats="stepStats" :star-group="'congrats-spread'"></stars>
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
  $click-color: #B8E986;
  $grid-background: rgba(0, 0, 0, 0.6);
  $grid-border-radius: 4px;
  $icon-size: 50%;
  $text-minor-font-size: 4vmin;
  $grid-space-size: 9vmin;

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
    color: #ffffff;

    * {
      height: 100%;
      width: 100%;
    }

    .text-minor {
      font-size: $text-minor-font-size;
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
</style>
