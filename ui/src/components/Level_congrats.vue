<template>
  <b-modal
    id="level-congrats-modal"
    ref="level-congrats-modal"
    @hidden="closeCongrats"
    v-if="stepStats"
  >
    <div slot="modal-header">
      <img
        class="dialog-button close-congrats"
        @click="quit" :src="permanentImages.buttons.xButton" data-toggle="tooltip" title="Close">
    </div>
    <div v-if="stepStats.nextLevel !== 'None'" class="text-minor">
      <div>You unlocked planet</div>
      <div>{{parseCamelCase(stepStats.nextLevel)}}!</div>
    </div>
    <div v-else class="text-minor">
      <div>You beat all the levels!</div>
    </div>
    <div v-if="stepStats.nextLevel !== 'None'" class="congrats-icon">
      <img :class="`planet-${levelNumber(stepStats.nextLevel)}`" :src="permanentImages.planets[`planet${levelNumber(stepStats.nextLevel)}`]">
    </div>
    <div v-else class="congrats-icon-robot">
      <img :src="permanentImages.instructionsRobot">
    </div>
    <div v-if="stepStats.nextLevel === 'None'" class="text-minor">
      <div>More coming soon!</div>
    </div>
    <stars :level="level" :step="step" :step-stats="stepStats" :star-group="'congrats-spread'"></stars>
    <div class="text-minor">
      <div>Tell your friends!</div>
    </div>
    <social-sharing :message="socialMessage" :size="'3rem'"></social-sharing>
    <div slot="modal-footer" class="row" style="width: 100%; display: flex; justify-content: space-between;">
      <b-btn
        v-if="stepStats.nextLevel !== 'None'"
        size="md"
        style="width: 40%;"
        class="float-right"
        variant="primary"
        @click="quit"
      >
        Quit
      </b-btn>
      <b-btn
        v-if="stepStats.nextLevel !== 'None'"
        size="md"
        style="width: 40%"
        class="float-right"
        variant="primary"
        @click="next"
      >
        Next level
      </b-btn>
    </div>
  </b-modal>
</template>

<script>
import Stars from './Stars'
import SocialSharing from './Social_sharing'
import utils from '../services/utils'
export default {
  computed: {
    levels () {
      return this.$store.getters.getLevels
    },
    runCompiled () {
      return this.$store.getters.getRunCompiled
    },
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
    },
    socialMessage () {
      return `
      I beat planet ${this.level} step ${utils.findStepInd(this.steps, this.step)} on mathbot.com!
    `
    }
  },
  data () {
    return {
      show: true
    }
  },
  methods: {
    parseCamelCase: utils.parseCamelCase,
    levelNumber (levelName) {
      return this.levels.reduce((num, l, i) => {
        if (levelName === l.name) num = i + 1
        return num
      }, 1)
    },
    quit () {
      this.runCompiled.quit()
    },
    next () {
      this.runCompiled.initializeNextStep()
    },
    closeCongrats () {
      this.runCompiled.stayOnLevel()
    }
  },
  components: {
    Stars,
    SocialSharing
  }
}
</script>

<style lang="scss">
  $click-color: #B8E986;
  $background-color: rgba(0, 0, 0, 1);
  $grid-border-radius: 4px;
  $icon-size: 50%;
  $text-minor-font-size: 4vmin;
  $grid-space-size: 9vmin;
  $font-size: 2.5rem;
  $star-size: 3rem;
  $dialog-button-size: 2rem;
  $share-btn-size: 2.5rem;
  $gradient-size: 50%;
  $outer-shadow-blur: 100px;
  $outer-shadow-size: 5px;
  $planet-1-color: rgba(202, 122, 255, 1);
  $planet-2-color: rgba(242, 92, 92, 1);
  $planet-3-color: rgba(74, 144, 226, 1);
  $planet-4-color: rgba(255, 152, 177, 1);
  $planet-5-color: rgba(80, 227, 194, 1);
  $planet-6-color: rgba(184, 233, 134, 1);
  $inactive-color: rgba(104, 104, 104, 1);
  $planet-1-size: 23vmin;
  $planet-2-size: 16vmin;
  $planet-3-size: 20vmin;
  $planet-4-size: 14vmin;
  $planet-5-size: 12vmin;
  $planet-gradient: rgba(0, 0, 0, 1);

  #level-congrats-modal {
    height: 100%;
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    color: #ffffff;

    .text-minor {
      font-size: $font-size;
      *:first-child {
        font-size: calc(#{$font-size} / 2);
      }
    }

    .modal-dialog .modal-content {
      background-color: transparent;
      .modal-body {
        background-color: $background-color;
        .stars .star-spread .star {
          height: $star-size;
          width: $star-size;
          svg image {
            display: none;
          }
        }

        .congrats-icon-robot {
          background-color: white;
          display: inline-block;
          border-radius: 50%;
          padding: 2rem;
        }

        .congrats-icon {
          background-color: transparent;
          display: inline-block;
          border-radius: 50%;

          img {
            border-radius: 50%;
          }

          .planet-1 {
            background: radial-gradient(circle at $gradient-size $gradient-size, $planet-1-color, $planet-gradient);
            height: $planet-1-size;
            width: $planet-1-size;
            top: 12%;
            left: 7%;
            /*box-shadow: inset 0 0 120px #CA7AFF*/
          }

          .planet-2 {
            background: radial-gradient(circle at $gradient-size $gradient-size, $planet-2-color, $planet-gradient);
            height: $planet-2-size;
            width: $planet-2-size;
            top: 2%;
            left: 50%;
            /*box-shadow: inset 0 0 120px #F25C5C;*/
          }

          .planet-3 {
            background: radial-gradient(circle at $gradient-size $gradient-size, $planet-3-color, $planet-gradient);
            height: $planet-3-size;
            width: $planet-3-size;
            top: 33%;
            left: 60%;
            /*box-shadow: inset 0 0 120px #4A90E2;*/
          }

          .planet-4 {
            background: radial-gradient(circle at $gradient-size $gradient-size, $planet-4-color, $planet-gradient);
            height: $planet-4-size;
            width: $planet-4-size;
            top: 65%;
            left: 50%;
            /*box-shadow: inset 0 0 120px #FF98B1;*/
          }

          .planet-5 {
            background: radial-gradient(circle at $gradient-size $gradient-size, $planet-5-color, $planet-gradient);
            height: $planet-5-size;
            width: $planet-5-size;
            top: 80%;
            left: 25%;
            /*box-shadow: inset 0 0 120px #50E3C2;*/
          }

          .planet-5 {
            background: radial-gradient(circle at $gradient-size $gradient-size, $planet-5-color, $planet-gradient);
            height: 140px;
            width: 140px;
            top: 60%;
            left: 20%;
            /*box-shadow: inset 0 0 120px #50E3C2;*/
          }

          .planet-6 {
            background: radial-gradient(circle at $gradient-size $gradient-size, $planet-6-color, $planet-gradient);
            /*box-shadow: inset 0 0 120px #B8E986;*/
          }

          .planet-1 {
            box-shadow: 0 0 $outer-shadow-blur $outer-shadow-size $planet-1-color;
            /*box-shadow: inset 0 0 120px #CA7AFF, 0 0 120px #CA7AFF;*/
          }

          .planet-2 {
            box-shadow: 0 0 $outer-shadow-blur $outer-shadow-size $planet-2-color;
            /*box-shadow: inset 0 0 120px #F25C5C, 0 0 120px #F25C5C;*/
          }

          .planet-3 {
            box-shadow: 0 0 $outer-shadow-blur $outer-shadow-size $planet-3-color;
            /*box-shadow: inset 0 0 120px #4A90E2, 0 0 120px #4A90E2;*/
          }

          .planet-4 {
            box-shadow: 0 0 $outer-shadow-blur $outer-shadow-size $planet-4-color;
            /*box-shadow: inset 0 0 120px #FF98B1, 0 0 120px #FF98B1;*/
          }

          .planet-5 {
            box-shadow: 0 0 $outer-shadow-blur $outer-shadow-size $planet-5-color;
            /*box-shadow: inset 0 0 120px #50E3C2, 0 0 120px #50E3C2;*/
          }

          .planet-6 {
            box-shadow: 0 0 $outer-shadow-blur $outer-shadow-size $planet-6-color;
            /*box-shadow: inset 0 0 120px #B8E986, 0 0 120px #B8E986;*/
          }
        }

        .social-sharing {
          .social-links {
            .share-button {
              height: $share-btn-size;
              width: $share-btn-size;

              i {
                font-size: calc(#{$share-btn-size} - 1rem);
              }
            }
          }
        }
      }

      .modal-header {
        background-color: $background-color;
        border: none;
        position: relative;
        .close-congrats {
          position: absolute;
          height: $dialog-button-size;
          width: $dialog-button-size;
          top: 0;
          right: 0;
        }
      }

      .modal-footer {
        background-color: $background-color;
        border-top: 1px solid $click-color;
        .row {
          margin: 0;
          .btn {
            background-color: $click-color;
            color: #000000;
            border: none;
          }
        }
      }
    }
  }
</style>
