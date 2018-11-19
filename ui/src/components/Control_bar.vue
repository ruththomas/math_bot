<template>
<div class="control-bar bar noDrag" v-if="Object.keys(robot).length">
  <img class="trash noDrag dialog-button" :src="permanentImages.buttons.trashButton"  @click="wipeFunction" data-toggle="tooltip" title="Clear main" />
  <div
    v-if="robot.state !== 'failure'"
    class="speed dialog-button"
    @click="adjustSpeed"
    data-toggle="tooltip"
    title="Adjust speed">
    <i v-if="robotSpeedDisplay === 'lightning'" class="fa fa-bolt"></i>
    <span v-else>
        {{ robotSpeedDisplay }}
      </span>
  </div>

  <img
    v-if="robot.state === 'home' || robot.state === 'paused'"
    class="play noDrag dialog-button"
    :src="permanentImages.buttons.playButton"
    alt="Play button" @click="[start(), togglePut(true), closeHint()]"
    data-toggle="tooltip" title="Run program" />

  <img
    v-else-if="robot.state === 'running'"
    class="play noDrag dialog-button"
    :src="permanentImages.buttons.pauseButton"
    alt="Pause button" @click="runCompiled.pause"
    data-toggle="tooltip" title="Pause program" />

  <img
    v-if="robot.state === 'running'"
    class="stop button noDrag dialog-button"
    :src="permanentImages.buttons.stopButton"
    alt="Stop button" @click="runCompiled.stop"
    data-toggle="tooltip" title="Stop program"/>

  <img
    v-if="robot.state === 'failure'"
    class="reset play button noDrag dialog-button"
    :src="permanentImages.buttons.resetButton"
    alt="Reset button" @click="runCompiled.reset"
    data-toggle="tooltip" title="Reset program"/>
</div>
</template>

<script>
export default {
  name: 'Control_bar',
  computed: {
    levelControl () {
      return this.$store.getters.getLevelControl
    },
    robot () {
      return this.levelControl.robot
    },
    runCompiled () {
      return this.levelControl.runCompiled
    },
    permanentImages () {
      return this.$store.getters.getPermanentImages
    },
    robotSpeedDisplay () {
      return this.robot.robotSpeed.display
    }
  },
  methods: {
    adjustSpeed () {
      this.$store.dispatch('changeRobotSpeed')
    },
    closeHint () {
      this.$store.dispatch('toggleHintShowing', {showing: false, videoURL: ''})
    }
  },
  props: ['wipeFunction', 'togglePut', 'start']
}
</script>

<style scoped lang="scss">
$bar-height: 1px;
.bar {
  position: absolute;
  left: 0;
  right: 0;
  top: calc(50%);
  height: $bar-height;
  background-color: #B8E986;
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: -1;
}

.red-bar {
  background-color: #FF0000;
}

.dialog-button {
  z-index: 2010;
  display: flex;
  cursor: pointer;
  position: absolute;
  float: right;
}

.play {
  border-radius: 50%;
  right: 0;
}

.reset {
}

.play-border {
  border: 3px solid rgb(135, 206, 250);
}

.stop {
  right: 10vmin;
}

.trash {
  left: 0;
}

.speed {
  right: 5vmin;
  background-color: #B8E986;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: black;
  font-size: 2vmin;
  font-weight: 500;
}

.x {
  float: left;
}
</style>
