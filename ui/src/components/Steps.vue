<template>
  <div class="col-4 steps">
    <div class="row">
      <h2 class="header">{{`${planetName}`}}. {{ parseCamelCase(level) }}</h2>
    </div>
    <div class="row steps-container">
      <div class="btn-group-vertical">
        <button
          v-for="(step, value) in steps"
          type="button"
          class="btn btn-dark btn-lg btn-block"
          @click="step.active ? goToRobot(level, step.name) : ''"
          :key="step + ':' + value"
        >
          <div class="col-6">
            <div class="step-info-text">Level {{ value + 1 }}</div>
          </div>
          <div class="col-6">
            <stars :level="level" :step="step.name" :step-stats="step" :star-group="'star-cluster'"></stars>
          </div>
        </button>
        <!--<button-->
          <!--v-if="steps[steps.length - 1].wins > 0 && nextLevel !== 'None'"-->
          <!--type="button"-->
          <!--class="btn btn-outline-dark"-->
          <!--@click="goToRobot(nextLevel.name, nextLevel.firstStep)">-->
          <!--<div class="step-info-text-container">-->
            <!--<div class="step-info-text">-->
              <!--<span class="step-info-new-level">Next planet!</span>-->
              <!--{{parseCamelCase(nextLevel.name)}}-->
            <!--</div>-->
          <!--</div>-->
          <!--<div class="step-info-image-container">-->
            <!--<img-->
              <!--class="step-image step-image-planet"-->
              <!--:class="`step-${nextLevel.planetClass}`"-->
              <!--:src="permanentImages.planets[nextLevel.planet]"-->
              <!--alt="Image of a planet"-->
            <!--/>-->
          <!--</div>-->
        <!--</button>-->
      </div>
    </div>
  </div>
</template>

<script>
import utils from '../services/utils'
import api from '../services/api'
import Stars from './Stars'

export default {
  name: 'steps',
  computed: {
    planetName () {
      const planets = {
        BasicProgramming: '1',
        Counting: '2',
        Numbers: '3',
        Recursion: '4',
        Conditionals: '5'
      }
      return planets[this.level]
    },
    level () {
      return this.$store.getters.getLevel
    },
    levels () {
      return this.$store.getters.getLevels
    },
    steps () {
      return this.$store.getters.getSteps
    },
    nextLevel () {
      const name = this.steps[this.steps.length - 1].nextLevel
      if (name === 'None') {
        return 'None'
      } else {
        let planetNumber = 0
        const nextLevelData = {}
        nextLevelData.name = name
        nextLevelData.firstStep = this.findFirstStep(this.levels.find((level, index) => {
          planetNumber = index + 1
          return level.name === name
        }))
        nextLevelData.planet = `planet${planetNumber}`
        nextLevelData.planetClass = `planet-${planetNumber}`
        return nextLevelData
      }
    },
    tokenId () {
      return this.$store.getters.getTokenId
    }
  },
  methods: {
    parseCamelCase: utils.parseCamelCase,
    findFirstStep (level) {
      const levelSteps = utils.orderEm(level.level)[0].name
      return levelSteps
    },
    goToRobot (level, step) {
      api.switchLevel({tokenId: this.tokenId, level: level, step: step}, (res) => {
        this.$store.dispatch('updateStats', res.body)
        this.$router.push({path: '/robot'})
      })
    }
  },
  components: {
    Stars
  },
  props: ['permanentImages']
}
</script>

<style scoped lang="scss">
  $btn-font-size: 1.5rem;
  $gradient-size: 100px;
  $outer-shadow-blur: 50px;
  $outer-shadow-size: 1px;
  $planet-1-color: rgba(202, 122, 255, 1);
  $planet-2-color: rgba(242, 92, 92, 1);
  $planet-3-color: rgba(74, 144, 226, 1);
  $planet-4-color: rgba(255, 152, 177, 1);
  $planet-5-color: rgba(80, 227, 194, 1);
  $planet-6-color: rgba(184, 233, 134, 1);
  $gradient-size: 100px;
  $font-color: #ffffff;

  .steps {
    border: 1px solid maroon;
    color: $font-color;
    height: 100%;
    margin: 8% 0 8% 0;

    .header {
      padding: 2% 0;
    }

    .btn {
      color: $font-color;
      display: flex;
      margin: 0.15em 0 0.15em 0;
      border-radius: 0.25rem!important;
      background-color: #000000;
      font-size: $btn-font-size;
      padding: 8% 0;
    }

    .steps-container {
      overflow: auto;
      -webkit-overflow-scrolling: touch;
      height: 100%;

      .btn-group-vertical {
        min-height: min-content;
      }
    }
  }

  .step-planet-1 {
    background: radial-gradient(circle at $gradient-size $gradient-size, $planet-1-color, #000);
    box-shadow: 0 0 $outer-shadow-blur $outer-shadow-size $planet-1-color;
  }

  .step-planet-2 {
    background: radial-gradient(circle at $gradient-size $gradient-size, $planet-2-color, #000);
    box-shadow: 0 0 $outer-shadow-blur $outer-shadow-size $planet-2-color;
  }

  .step-planet-3 {
    background: radial-gradient(circle at $gradient-size $gradient-size, $planet-3-color, #000);
    box-shadow: 0 0 $outer-shadow-blur $outer-shadow-size $planet-3-color;
  }

  .step-planet-4 {
    background: radial-gradient(circle at $gradient-size $gradient-size, $planet-4-color, #000);
    box-shadow: 0 0 $outer-shadow-blur $outer-shadow-size $planet-4-color;
  }

  .step-planet-5 {
    background: radial-gradient(circle at $gradient-size $gradient-size, $planet-5-color, #000);
    box-shadow: 0 0 $outer-shadow-blur $outer-shadow-size $planet-5-color;
  }

  .step-image-stars {
    height: 100%;
  }

  .step-image-lock {
    height: 51px;
    width: 42px;
  }

  .step-active {
    opacity: 1;
  }

  .step-disables {
    opacity: 0.8;
  }

  /*
  // ipad pro Portrait
  @media only screen
  and (min-device-width: 1024px) and (max-device-width: 1024px)
  and (min-device-height: 1366px) and (max-device-height: 1366px)
  and (min-width: 1024px) and (max-width: 1024px)
  and (orientation: portrait)
  and (-webkit-min-device-pixel-ratio: 1.5) {
    $steps-height: 100%;
    $steps-width: 380px;
    $steps-header-font-size: 38px;
    $steps-header-line-height: 42px;
    $step-info-text-font-size: 26px;
    $step-width: 350px;
    $step-image-width: 120px;

    .steps-header {
      font-size: $steps-header-font-size;
      line-height: $steps-header-line-height;
    }

    .steps {
      width: $steps-width;
    }

    .steps-navigator-item {
      width: $step-width;
      height: 98px;
    }

    .step-info-text {
      font-size: $step-info-text-font-size;
    }

    .step-info-image-container {
      width: $step-image-width;
    }

    .step-image-lock {}
  }

  // ipad landscape
  @media only screen
  and (min-device-width : 768px)
  and (max-device-height : 1024px)
  and (orientation : landscape) {
    $steps-height: 100%;
    $steps-width: 280px;
    $steps-header-font-size: 28px;
    $steps-header-line-height: 32px;
    $step-info-text-font-size: 20px;
    $step-width: 250px;
    $step-image-width: 120px;

    .steps-header {
      font-size: $steps-header-font-size;
      line-height: $steps-header-line-height;
    }

    .steps {
      width: $steps-width;
    }

    .steps-navigator-item {
      width: $step-width;
      height: 98px;
    }

    .step-info-text {
      font-size: $step-info-text-font-size;
    }

    .step-info-image-container {
      width: $step-image-width;
    }

    .step-image-lock {}
  }

  // ipad portrait
  @media only screen
  and (min-device-width : 768px)
  and (max-device-height : 1024px)
  and (orientation : portrait)  {
    $steps-height: 100%;
    $steps-width: 280px;
    $steps-header-font-size: 28px;
    $steps-header-line-height: 32px;
    $step-info-text-font-size: 20px;
    $step-width: 250px;
    $step-image-width: 120px;

    .steps-header {
      font-size: $steps-header-font-size;
      line-height: $steps-header-line-height;
    }

    .steps {
      width: $steps-width;
    }

    .steps-navigator-item {
      width: $step-width;
    height: 98px;
    }

    .step-info-text {
      font-size: $step-info-text-font-size;
    }

    .step-info-image-container {
      width: $step-image-width;
    }

    .step-image-lock {}
  }

  // Medium Devices, Desktops
  @media only screen and (max-width : 823px) and (orientation: landscape) {
    $steps-height: 300px;
    $steps-width: 250px;
    $steps-header-font-size: 18px;
    $steps-header-line-height: 22px;
    $step-info-text-font-size: 12px;
    $step-width: 225px;
    $step-image-width: 120px;

    .steps-header {
      font-size: $steps-header-font-size;
      line-height: $steps-header-line-height;
    }

    .steps {
      width: $steps-width;
    }

    .steps-navigator-item {
      width: $step-width;
      height: 50px;
    }

    .step-info-text {
      font-size: $step-info-text-font-size;
    }

    .step-info-image-container {
      width: $step-image-width;
    }

    .step-image-lock {}
  }

  @media only screen and (max-width : 736px) and (orientation: landscape) {
    $steps-height: 300px;
    $steps-width: 250px;
    $steps-header-font-size: 18px;
    $steps-header-line-height: 22px;
    $step-info-text-font-size: 12px;
    $step-width: 225px;
    $step-image-width: 120px;

    .steps-header {
      font-size: $steps-header-font-size;
      line-height: $steps-header-line-height;
    }

    .steps {
      width: $steps-width;
    }

    .steps-navigator-item {
      width: $step-width;
      height: 50px;
    }

    .step-info-text {
      font-size: $step-info-text-font-size;
    }

    .step-info-image-container {
      width: $step-image-width;
    }

    .step-image-lock {}
  }

  // Small Devices
  @media only screen and (max-width : 667px) and (orientation: landscape) {
    $steps-height: 300px;
    $steps-width: 150px;
    $steps-header-font-size: 18px;
    $steps-header-line-height: 22px;
    $step-info-text-font-size: 12px;
    $step-width: 125px;
    $step-image-width: 80px;

    .steps-header {
      font-size: $steps-header-font-size;
      line-height: $steps-header-line-height;
    }

    .steps {
      width: $steps-width;
    }

    .steps-navigator-item {
      width: $step-width;
      height: 50px;
    }

    .step-info-text {
      font-size: $step-info-text-font-size;
    }

    .step-info-image-container {
      width: $step-image-width;
    }

    .step-image-lock {}
  }

  @media only screen and (max-width: 568px) and (orientation: landscape) {
    $steps-height: 300px;
    $steps-width: 150px;
    $steps-header-font-size: 18px;
    $steps-header-line-height: 22px;
    $step-info-text-font-size: 12px;
    $step-width: 125px;
    $step-image-width: 80px;

    .steps-header {
      font-size: $steps-header-font-size;
      line-height: $steps-header-line-height;
    }

    .steps {
      height: $steps-height;
      width: $steps-width;
    }

    .steps-navigator-item {
      width: $step-width;
      height: 50px;
    }

    .step-info-text {
      font-size: $step-info-text-font-size;
    }

    .step-info-image-container {
      width: $step-image-width;
    }

    .step-image-lock {}
  }

  @media only screen and (max-width: 414px) {
    $steps-height: 550px;
    $steps-width: 150px;
    $steps-header-font-size: 18px;
    $steps-header-line-height: 22px;
    $step-info-text-font-size: 12px;
    $step-width: 125px;
    $step-image-width: 80px;

    .steps-header {
      font-size: $steps-header-font-size;
      line-height: $steps-header-line-height;
    }

    .steps {
      height: $steps-height;
      width: $steps-width;
    }

    .steps-navigator-item {
      width: $step-width;
      height: 50px;
    }

    .step-info-text {
      font-size: $step-info-text-font-size;
    }

    .step-info-image-container {
      width: $step-image-width;
    }

    .step-image-lock {}
  }

  @media only screen and (max-width : 375px) {
    $steps-height: 550px;
    $steps-width: 150px;
    $steps-header-font-size: 18px;
    $steps-header-line-height: 22px;
    $step-info-text-font-size: 12px;
    $step-width: 125px;
    $step-image-width: 80px;

    .steps-header {
      font-size: $steps-header-font-size;
      line-height: $steps-header-line-height;
    }

    .steps {
      height: $steps-height;
      width: $steps-width;
    }

    .steps-navigator-item {
      width: $step-width;
      height: 50px;
    }

    .step-info-text {
      font-size: $step-info-text-font-size;
    }

    .step-info-image-container {
      width: $step-image-width;
    }

    .step-image-lock {}
  }

  // Custom, iPhone Retina
  @media only screen and (max-width : 320px) {
    $steps-height: 400px;
    $steps-width: 120px;
    $steps-header-font-size: 18px;
    $steps-header-line-height: 22px;
    $step-info-text-font-size: 12px;

    .steps-header {
      font-size: $steps-header-font-size;
      line-height: $steps-header-line-height;
    }

    .steps {
      height: $steps-height;
      width: $steps-width;
    }

    .steps-navigator-item {
      width: 90px;
      height: 50px;
    }

    .step-info-text {
      font-size: $step-info-text-font-size;
    }

    .step-info-image-container {
      display: none;
    }

    .step-image-lock {}
  }
  */
</style>
