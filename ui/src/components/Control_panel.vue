<template>
  <div class="row control-panel">
    <mascot :animate="true"></mascot>

    <div
      class="btn button-effect help-button"
      @click="videoHintControl.showHint"
    >
      <stars :continent-id="levelControl.path"></stars>
    </div>
  </div>
</template>

<script>
import RobotCarrying from './Robot_carrying'
import Stars from './Stars'
import Mascot from './Mascot'

export default {
  name: 'control-panel',
  computed: {
    levelControl () {
      return this.$store.getters.getLevelControl
    },
    gridMap () {
      return this.levelControl.continent.gridMap
    },
    robot () {
      return this.levelControl.robot
    },
    robotCarrying () {
      return this.robot.robotCarrying
    },
    problem () {
      return this.levelControl.continent.problem.problem
    },
    videoHintControl () {
      return this.$store.getters.getVideoHintControl
    },

    runCompiled () {
      return this.$store.getters.getRunCompiled
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
      counter: 45,
      max: 100
    }
  },
  components: {
    RobotCarrying,
    Stars,
    Mascot
  }
}
</script>

<style scoped lang="scss">
  $click-color: #B8E986;
  $instructions-robot-size: 13vmin;
  $grid-space-size: 9vmin;
  $grid-background: rgba(0, 0, 0, 0.6);
  $mascot-height: 20vmin;

  .control-panel {
    display: flex;
    align-items: flex-end;
    justify-content: space-between;
    position: relative;
    width: 100%;
    height: calc(#{$mascot-height} / 1.3);
    margin: 0;

    .help-button {
      border-bottom-left-radius: 0;
      border-bottom-right-radius: 0;
      background-color: $grid-background;
      display: flex;
      z-index: 100;
      position: absolute;
      right: 0;
      bottom: 0;
    }

    .mascot {
      position: relative;
      top: calc(#{$mascot-height} / 2.9);
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
</style>
