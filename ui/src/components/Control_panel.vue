<template>
  <div class="control-panel">

    <img @click="goToProfile()"
         class="return-to-profile"
         :src="permanentImages.returnToProfile"
         data-toggle="tooltip" title="Return to profile"
    />

    <div class="instructions" :style="congratsShowing || tryAgainShowing ? {opacity: 0} : {}">
      <div class="instructions-filler-left"></div>
      <div class="instructions-robot-container">
        <img :src="permanentImages.instructionsRobot" class="instructions-robot" data-toggle="tooltip" title="Toggle speech bubble">
      </div>
      <speech-bubble :html="description" :showing="speechBubbleShowing" :step="step"></speech-bubble>
    </div>
  </div>
</template>

<script>
import SpeechBubble from './Speech_bubble'

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
    SpeechBubble
  }
}
</script>

<style scoped lang="scss">
  .control-panel {
  }

  .instructions {
    display: flex;
    width: 100%;
  }

  .instructions-robot-container {
    height: 150px;
    display: flex;
    align-items: flex-end;
    margin-right: 20px;
  }

  .instructions-robot {
    height: 100%;
    cursor: pointer;
  }

  .instructions-filler-left {
    width: 100px;
  }

  .return-to-profile {
    position: fixed;
    left: 0;
    top: 0;
    cursor: pointer;
  }

  .fade-in-speech {
    opacity: 1;
    transition: all 0.4s ease-out;
  }

  .fade-out-speech {
    opacity: 0;
    transition: all 0.4s ease-in;
  }

  @media only screen and (max-width : 1280px) {
    .instructions-robot-container {
      height: 90px;
    }

    .return-to-profile {
      height: 120px;
    }

    .instructions-filler-left {
      width: 250px;
    }
  }

  /* Medium Devices, Desktops */
  @media only screen and (max-width : 992px) {
    .instructions-robot-container {
      height: 75px;
    }

    .return-to-profile {
      height: 65px;
    }

    .instructions-filler-left {
      width: 120px;
    }
  }

  /* Small Devices */
  @media only screen and (max-width : 667px) {
    .instructions-robot-container {
      height: 50px;
    }

    .instructions-filler-left {
      width: 170px;
    }
  }

  @media only screen and (max-width: 569px) {
    .instructions-filler-left {
      width: 75px;
    }
  }

  /* Extra Small Devices, Phones */
  @media only screen and (max-width : 480px) {
    .return-to-profile {
      height: 60px;
    }

    .instructions-robot-container {
      height: 50px;
    }

    .instructions-filler-left {
      width: 10px;
    }
  }

  /* Custom, iPhone 5 Retina */
  @media only screen and (max-width : 320px) {

  }

  /* iPad */
  @media all and (device-width: 768px) and (device-height: 1024px) and (orientation:portrait) {
    .instructions-robot-container {
      height: 150px;
    }

    .return-to-profile {
      height: 100px;
    }

    .instructions-filler-left {
      width: 75px;
    }
  }

  @media all and (device-width: 768px) and (device-height: 1024px) and (orientation:landscape) {
    .return-to-profile {
      height: 150px;
    }

    .instructions-filler-left {
      width: 180px;
    }
  }

</style>
