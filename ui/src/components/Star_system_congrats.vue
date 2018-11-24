<template>
<b-modal
  id="star-system-congrats-modal"
  ref="star-system-congrats-modal"
  v-if="nextStarSystem"
>
  <div slot="modal-header">
    <img
      class="dialog-button close-congrats"
      @click="quit" :src="permanentImages.buttons.xButton" data-toggle="tooltip" title="Close">
  </div>
  <div class="text-minor">
    <div>You unlocked star system</div>
    <div>{{parseCamelCase(nextStarSystem.stats.name)}}!</div>
  </div>
  <div class="congrats-icon-robot">
    <img :class="nextStarSystem.stats.name" :src="permanentImages.instructionsRobot">
  </div>
  <stars :continent-id="congratsData.path"></stars>
  <div class="text-minor">
    <div>Tell your friends!</div>
  </div>
  <social-sharing :size="'3rem'"></social-sharing>
  <div slot="modal-footer" class="row" style="width: 100%; display: flex; justify-content: space-between;">
    <b-btn
      size="md"
      style="width: 40%;"
      class="float-right"
      variant="primary"
      @click="quit"
    >
      Quit
    </b-btn>
    <b-btn
      size="md"
      style="width: 40%"
      class="float-right"
      variant="primary"
      :show="getCongratsData"
      @click="next"
    >
      Next level
    </b-btn>
  </div>
</b-modal>
</template>

<script>
import utils from '../services/utils'
import Stars from './Stars'
import SocialSharing from './Social_sharing'
export default {
  name: 'Star_system_congrats',

  computed: {
    levelControl () {
      return this.$store.getters.getLevelControl
    },
    nextStarSystem () {
      return this.levelControl.getNextStarSystem()
    },
    permanentImages () {
      return this.$store.getters.getPermanentImages
    },
    runCompiled () {
      return this.levelControl.runCompiled
    }
  },
  data () {
    return {
      congratsData: {
        path: '00000',
        builtContinent: {
          name: ''
        }
      }
    }
  },
  methods: {
    parseCamelCase: utils.parseCamelCase,
    quit () {
      this.runCompiled.quit()
    },
    next () {
      this.runCompiled.initializeNextStep()
      this.$router.push({path: '/robot'})
    },
    closeCongrats () {
      this.runCompiled.stayOnLevel()
    },
    getCongratsData () {
      const data = this.$route.query
      this.congratsData = data.pathAndContinent
      this.$router.push({query: {}})
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
$BasicProgramming-color: rgba(202, 122, 255, 1);
$Counting-color: rgba(242, 92, 92, 1);
$Numbers-color: rgba(74, 144, 226, 1);
$Recursion-color: rgba(255, 152, 177, 1);
$Conditionals-color: rgba(80, 227, 194, 1);
$Coordinates-color: rgba(185, 80, 86, 1);
$Addition-color: rgba(41, 254, 28, 1);
$Subtraction-color: rgba(253, 254, 137, 1);
$Multiplication-color: rgba(254, 151, 78, 1);
$Division-color: rgba(64, 169, 254, 1);
$Exponents-color: rgba(185, 62, 167, 1);
$Roots-color: rgba(54, 63, 185, 1);
$planet-1-size: 23vmin;
$planet-2-size: 16vmin;
$planet-3-size: 20vmin;
$planet-4-size: 14vmin;
$planet-5-size: 12vmin;
$planet-gradient: rgba(0, 0, 0, 1);

#star-system-congrats-modal {
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
