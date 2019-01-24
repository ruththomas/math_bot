<template>
  <div class="control-bar bar noDrag" v-if="Object.keys(levelControl.robot).length">
    <clear-function-confirm
      v-if="confirmDeleteOpen"
      :close-method="closeConfirmDelete"
      :trash-method="wipeFunction"
      :animation-method="startAnimation"
      :de-animation-method="stopAnimation"
    ></clear-function-confirm>
    <mascot
      v-else
      :id="'main-delete-function'"
      class="bar-controller"
      @click.native="levelControl.functions.main.func.length ? [openConfirmDelete()] : []"
      :color="gridRobotColor"
      :door-handle-color="'#B8E986'"
      :eye-color="'#4A90E2'"
    ></mascot>
    <svg class="bar-graphic" xmlns="http://www.w3.org/2000/svg" id="Layer_1" data-name="Layer 1" height="100%" width="100%" viewBox="0 0 997.51 50">
      <title>bar</title>
      <path :fill="!confirmDeleteOpen ? '#B8E986' : '#F25C5C'" d="M0,172.44" transform="translate(-0.99 -2.39)"/>
      <path :stroke="!confirmDeleteOpen ? '#B8E986' : '#F25C5C'" fill="none" d="M95,26c316.82,0,604.62.08,903,0" transform="translate(-0.99 -2.39)"/>
      <line :stroke="!confirmDeleteOpen ? '#B8E986' : '#F25C5C'" fill="none" x1="997.01" y1="47.22" x2="997.01"/>
      <path :stroke="!confirmDeleteOpen ? '#B8E986' : '#F25C5C'" fill="none" d="M1.07,26c1.47-.25,3.93.41,6.59,1.32,7.22,2.47,7.93,10.57,11.86,11,5.9.61,9.31-17.13,15.37-17.13,7.07,0,9.88,24.11,16.25,24.15,7.24,0,10.07-31.14,19.32-31.62C81,12.9,83,26,95,26" transform="translate(-0.99 -2.39)"/>
    </svg>
  </div>
</template>

<script>
import utils from '../services/utils'
import Mascot from './Mascot'
import ClearFunctionConfirm from './Clear_function_confirm'
export default {
  name: 'Bar',
  computed: {
    levelControl () {
      return this.$store.getters.getLevelControl
    },
    permanentImages () {
      return this.$store.getters.getPermanentImages
    },
    isLastFrame () {
      const robotFrames = this.levelControl.runCompiled.robotFrames
      return robotFrames.length && this.levelControl.runCompiled.currentFrame === robotFrames.length - 1
    },
    gridRobotColor () {
      if (this.isLastFrame) {
        return '#F25C5C'
      } else if (this.levelControl.robot.state === 'running' || this.levelControl.robot.state === 'paused') {
        return '#B8E986'
      } else {
        return '#ffffff'
      }
    }
  },
  data () {
    return {
      confirmDeleteOpen: false
    }
  },
  methods: {
    closePopover: utils.closePopover,
    wipeFunction () {
      this.levelControl.deleteMain()
      this.closeConfirmDelete()
      this.setPut(true)
    },
    animationElements () {
      return {
        $functions: $('.editMain-drop-zone > .piece:not(.placeholder-piece)'),
        $bar: $('.bar'),
        animationClass: 'piece-shake'
      }
    },
    animateVulnerable (animationMethod) {
      const elements = this.animationElements()
      // elements.$bar[animationMethod]('red-bar')
      // elements.$endCap[animationMethod]('red-bar')
      elements.$functions.each(function () {
        const $ele = $(this)
        $ele[animationMethod](elements.animationClass)
      })
    },
    startAnimation () {
      this.animateVulnerable('addClass')
    },
    stopAnimation () {
      this.animateVulnerable('removeClass')
    },
    closeConfirmDelete () {
      this.confirmDeleteOpen = false
      setTimeout(this.levelControl._positionBar, 10)
    },
    openConfirmDelete () {
      setTimeout(this.levelControl._positionBar, 10)
      this.confirmDeleteOpen = true
    }
  },
  components: {
    Mascot,
    ClearFunctionConfirm
  },
  props: ['setPut']
}
</script>

<style scoped lang="scss">
$mascot-size: 9vmin;
$bar-height: 1px;
$bar-color: #B8E986;
$padding-left: calc(#{$mascot-size} / 1.2);
$danger-color: #F25C5C;
$dialog-button-size: 3.5vmin;
.bar {
  position: absolute;
  left: 0px;
  right: 0px;
  top: 0px;
  bottom: 0px;
  /*
  height: $bar-height;
  background-color: $bar-color;
  display: flex;
  align-items: center;
  justify-content: center;

  */
  z-index: -1;
  .cls-1 {
    fill:$bar-color;
  }
  .cls-2 {
    fill:none;
    stroke:$bar-color;
  }
  .bar-graphic {
    right:0px;
    position: absolute;
    width:95.2%;
    height:auto;
  }
  .bar-controller {
    position: absolute;
    cursor: pointer;
    display: flex;
  }

  .confirm-delete {
    position: absolute;
    padding: 0.5rem;
    transform: translateY(calc(-5% - #{$bar-height}));
    left: -1vmin;
    z-index: 101;
  }

  .mascot {
    width: $mascot-size;
    left: -2vmin;
    transform: translateY(calc(-15% - #{$bar-height}));
    height: auto;
    z-index: 100;
    cursor: pointer;
  }
}

.hidden-bar {
  background-color: transparent;
}

.red-bar {
  background-color: #FF0000!important;
}

.trash-main {
  margin: 0.5vmin;
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

.deactivate-edit-main {
  opacity: 0;
}
</style>
