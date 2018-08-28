<template>
  <div class="row control-panel">
    <img @click="goToProfile()"
         class="return-to-profile"
         :src="permanentImages.returnToProfile"
         data-toggle="tooltip" title="Return to profile"
    />
    <div class="col" style="padding: 0;">
      <img :src="permanentImages.instructionsRobot" class="instructions-robot">
    </div>
    <div class="col" style="padding: 0;">
      <speech-bubble :html="description" :showing="speechBubbleShowing"></speech-bubble>
    </div>
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
  $instructions-robot-size: 13vmin;
  $grid-space-size: 9vmin;

  .control-panel {
    display: flex;
    align-items: flex-end;
    position: relative;
    right: calc(#{$grid-space-size} * 0.75);
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
  }

  .instructions-filler-left {
    width: 120px;
  }

  .return-to-profile {
    position: fixed;
    left: 0;
    top: 0;
    cursor: pointer;
    height: 15vmin;
    width: 15vmin;
  }

  @media only screen and (orientation: portrait) {
    .return-to-profile {
      left: auto;
      right: 0;
    }
  }

  @media only screen and (max-width: 567px) {
    .return-to-profile {
      left: auto;
      right: 0;
    }
  }
</style>
