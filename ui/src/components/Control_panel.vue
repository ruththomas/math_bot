<template>
  <div class="row control-panel">
    <!--<div class="" style="padding: 0;">-->
      <!--<img :src="permanentImages.instructionsRobot" class="instructions-robot">-->
    <!--</div>-->

    <div
      class="btn button-effect help-button"
      @click="videoHint.getHint()"
    >
      <stars
        :star-group="'star-spread'"
        :level="level"
        :step="step"
        :step-stats="stepStats"></stars>
    </div>

    <div
      @click="goToProfile()"
      class="return-to-profile"
      data-toggle="tooltip" title="Return to profile"
    >
      <img :src="handlePicture(userProfile.picture)" />
    </div>
  </div>
</template>

<script>
import RobotCarrying from './Robot_carrying'
import Stars from './Stars'
import VideoHint from '../services/VideoHint'

export default {
  name: 'control-panel',
  computed: {
    userProfile () {
      return JSON.parse(localStorage.getItem('profile'))
    },
    tryAgainShowing () {
      return this.$store.getters.getTryAgainShowing
    },
    congratsShowing () {
      return this.$store.getters.getCongratsShowing
    },
    currentStepData () {
      return this.$store.getters.getStepData
    },
    permanentImages () {
      return this.$store.getters.getPermanentImages
    },
    description () {
      return this.currentStepData.description
    },
    steps () {
      return this.$store.getters.getSteps
    },
    step () {
      return this.$store.getters.getStep
    },
    level () {
      return this.$store.getters.getLevel
    },
    stepStats () {
      const stepName = this.step
      return this.steps.find(s => s.name === stepName)
    }
  },
  data () {
    return {
      speechBubbleShowing: true,
      getTime: false,
      videoHint: new VideoHint(this)
    }
  },
  methods: {
    handlePicture (picture) {
      if (!picture || picture.match(/gravatar/)) {
        return this.permanentImages.gravatar
      } else {
        return picture
      }
    },
    goToProfile () {
      this.$store.dispatch('toggleHintShowing', {showing: false, videoURL: ''})
      this.$store.dispatch('deleteMessages')
      this.$router.push({path: 'profile'})
    }
  },
  components: {
    RobotCarrying,
    Stars
  }
}
</script>

<style scoped lang="scss">
  $click-color: #B8E986;
  $instructions-robot-size: 13vmin;
  $grid-space-size: 9vmin;

  .control-panel {
    display: flex;
    align-items: flex-end;
    justify-content: space-between;
    position: relative;
    width: calc(#{$grid-space-size} * 10 + 2px);

    .help-button {
      border-bottom-left-radius: 0;
      border-bottom-right-radius: 0;
      border: 1px solid $click-color;
    }
  }

  .instructions {
    display: flex;
    align-items: flex-end;
    width: 100%;
  }

  .instructions-robot-container {
    height: 150px;
    display: flex;
    align-items: flex-end;
  }

  .instructions-robot {
    height: $instructions-robot-size;
    margin-left: calc(#{$grid-space-size} / 3);
  }

  .instructions-filler-left {
    width: 120px;
  }

  .return-to-profile {
    cursor: pointer;
    height: 12vmin;
    width: 12vmin;
    display: flex;
    align-items: center;
    justify-content: center;
    border-radius: 50%;

    img {
      border-radius: 50%;
      height: 80%;
      width: 80%;
      box-shadow: 0 0 100px 2vmin rgba(0,0,0,1);
    }
  }
</style>
