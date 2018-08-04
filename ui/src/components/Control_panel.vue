<template>
  <div v-if="!congratsShowing && !tryAgainShowing" class="control-panel">
      <img @click="goToProfile()"
           class="return-to-profile"
           :src="permanentImages.returnToProfile"
           data-toggle="tooltip" title="Return to profile"
      />

      <div class="instructions">
        <div class="instructions-filler-left"></div>
        <div class="instructions-robot-container">
          <img :src="permanentImages.instructionsRobot" class="instructions-robot">
        </div>
        <speech-bubble :html="description" :showing="speechBubbleShowing"></speech-bubble>
      </div>
      <speech-bubble :html="description" :showing="speechBubbleShowing" :step="step"></speech-bubble>
    </div>
</template>

<script>
import SpeechBubble from './Speech_bubble'
import RobotCarrying from './Robot_carrying'

export default {
  name: 'control-panel',
  computed: {
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
      const stepName = this.$store.getters.getStep
      return this.steps.find(s => s.name === stepName)
    }
  },
  data () {
    return {
      speechBubbleShowing: true
    }
  },
  methods: {
    goToProfile () {
      this.$store.dispatch('toggleHintShowing', {showing: false, videoURL: ''})
      this.$store.dispatch('deleteMessages')
      this.$router.push({path: 'profile'})
    }
  },
  components: {
    SpeechBubble,
    RobotCarrying
  }
}
</script>

<style scoped lang="scss">
  .control-panel {
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
    height: 100%;
  }

  .instructions-filler-left {
    width: 120px;
  }

  .return-to-profile {
    position: fixed;
    left: 0;
    top: 0;
    cursor: pointer;
  }

  // ipad landscape
  @media only screen
  and (min-device-width : 768px)
  and (max-device-width : 1024px)
  and (orientation : landscape) {
    .instructions-robot-container {
      height: 90px;
    }

    .return-to-profile {
      height: 120px;
    }

    .instructions-filler-left {
      width: 220px;
    }
  }

  // ipad portrait
  @media only screen
  and (min-device-width : 768px)
  and (max-device-width : 1024px)
  and (orientation : portrait) {
    .instructions-robot-container {
      height: 90px;
    }

    .return-to-profile {
      height: 120px;
    }

    .instructions-filler-left {
      width: 100px;
    }
  }

  /* ipad pro Portrait */
  @media only screen
  and (min-device-width: 1024px)
  and (max-device-width: 1366px)
  and (orientation: portrait)
  and (-webkit-min-device-pixel-ratio: 1.5) {
    .instructions-robot-container {
      height: 90px;
    }

    .return-to-profile {
      height: 120px;
    }

    .instructions-filler-left {
      width: 220px;
    }
  }

  @media only screen and (max-width: 823px) and (orientation: landscape) {
    .instructions-robot-container {
      height: 55px;
    }

    .return-to-profile {
      height: 95px;
    }

    .instructions-filler-left {
      width: 230px;
    }
  }

  @media only screen and (max-width : 736px) and (orientation: landscape) {
    .instructions-robot-container {
      height: 55px;
    }

    .return-to-profile {
      height: 95px;
    }

    .instructions-filler-left {
      width: 170px;
    }
  }

  @media only screen and (max-width: 667px) and (orientation: landscape) {
    .instructions-filler-left {
      width: 170px;
    }

    .instructions-robot-container {
      height: 75px;
    }

    .instructions-robot-container {
      height: 50px;
    }
  }

  /* iphone 5 landscape*/
  @media only screen and (max-width: 568px) and (orientation: landscape){
    .instructions-filler-left {
      width: 140px;
    }

    .instructions-robot-container {
      height: 45px;
    }
  }

  @media only screen and (max-width: 414px) {
    .instructions-filler-left {
      width: 50px;
    }

    .return-to-profile {
      height: 80px;
    }

    .instructions-robot-container {
      height: 50px;
    }
  }

  @media only screen and (max-width: 375px) {
    .instructions-filler-left {
      width: 60px;
    }

    .return-to-profile {
      height: 80px;
    }

    .instructions-robot-container {
      height: 50px;
    }
  }

  /* Custom, iPhone 5 Retina */
  @media only screen and (max-width : 320px) {
    .instructions-filler-left {
      width: 35px;
    }

    .return-to-profile {
      height: 60px;
    }

    .instructions-robot-container {
      height: 45px;
    }
  }
</style>
