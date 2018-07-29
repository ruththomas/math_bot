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
      <speech-bubble :html="description" :showing="speechBubbleShowing"></speech-bubble>
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

  @media only screen and (max-width: 823px) and (orientation: landscape) {
    .instructions-robot-container {
      height: 55px;
    }

    .return-to-profile {
      height: 95px;
    }

    .instructions-filler-left {
      width: 120px;
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
      width: 110px;
    }
  }

  @media only screen and (max-width: 667px) and (orientation: landscape) {
    .instructions-filler-left {
      width: 80px;
    }

    .return-to-profile {
      height: 95px;
    }

    .instructions-robot-container {
      height: 55px;
    }
  }

  @media only screen and (max-width: 667px) and (orientation: landscape) {
    .instructions-filler-left {
      width: 80px;
    }

    .instructions-robot-container {
      height: 75px;
    }

    .instructions-robot-container {
      height: 55px;
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
      width: 40px;
    }

    .return-to-profile {
      height: 90px;
    }

    .instructions-robot-container {
      height: 55px;
    }
  }

  @media only screen and (max-width: 375px) {
    .instructions-filler-left {
      width: 20px;
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
      width: 10px;
    }

    .return-to-profile {
      height: 60px;
    }

    .instructions-robot-container {
      height: 45px;
    }
  }
</style>
