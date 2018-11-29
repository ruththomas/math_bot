<template>
<div class="control-bar bar noDrag" v-if="Object.keys(robot).length">

  <img
    id="main-delete-function"
    class="trash noDrag dialog-button function-control"
    :src="permanentImages.buttons.trashButton"
    @click="animateVulnerable"
  />
  <b-popover
    v-if="levelControl.functions.main.func.length"
    target="main-delete-function"
    placement="top"
    triggers="click"
  >
    <img class="dialog-button close-popover"
         :src="permanentImages.buttons.xButton"
         @click="[animateVulnerable(), closePopover('main-delete-function')]"
    />
    <div class="button-effect trash-confirm"  @click="[animateVulnerable(), wipeFunction(), closePopover('main-delete-function')]">
      <img class="dialog-button btn-trash" :src="permanentImages.openTrashCan" />
    </div>
  </b-popover>

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
    alt="Play button" @click="[start(), closeHint()]"
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
    class="reset play button noDrag dialog-button pulse"
    :src="permanentImages.buttons.resetButton"
    alt="Reset button" @click="runCompiled.reset"
    data-toggle="tooltip" title="Reset program"/>
</div>
</template>

<script>
import utils from '../services/utils'

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
    closePopover: utils.closePopover,
    animateVulnerable () {
      const $functions = $('.editMain-drop-zone > .piece:not(.placeholder-piece)')
      const animationClass = 'piece-shake'
      $functions.each(function () {
        const $ele = $(this)
        if ($ele.hasClass(animationClass)) {
          $ele.removeClass(animationClass)
        } else {
          $ele.addClass(animationClass)
        }
      })
    },
    adjustSpeed () {
      this.$store.dispatch('changeRobotSpeed')
    },
    closeHint () {
      this.$store.dispatch('toggleHintShowing', {showing: false, videoURL: ''})
    }
  },
  props: ['wipeFunction', 'start']
}
</script>

<style scoped lang="scss">
$bar-height: 1px;
$click-color: #B8E986;
$dialog-button-size: 3.5vmin;
$danger-color: #F25C5C;

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

.trash-confirm {

  animation: shake 0.8s;
  animation-iteration-count: infinite;
  margin: 3vmin;
}
.close-popover {
  float: right;
  display: flex;
  position: absolute;
  bottom: calc(100% - #{$dialog-button-size} / 2);
  right:  calc(#{-$dialog-button-size} / 2);
  z-index: 10001;
  cursor: pointer;
}

.btn-trash {

  background-color: $danger-color;
  box-shadow: 0 2px 10px 0 $danger-color;
}
</style>
