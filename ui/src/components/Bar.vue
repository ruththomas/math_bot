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
    ></mascot>
    <div class="end-cap"></div>
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
        $endCap: $('.end-cap'),
        animationClass: 'piece-shake'
      }
    },
    animateVulnerable (animationMethod) {
      const elements = this.animationElements()
      elements.$bar[animationMethod]('red-bar')
      elements.$endCap[animationMethod]('red-bar')
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
    },
    openConfirmDelete () {
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
  left: 0;
  right: 0;
  top: calc(50%);
  height: $bar-height;
  background-color: $bar-color;
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: -1;

  .bar-controller {
    position: absolute;
    cursor: pointer;
    display: flex;
  }

  .confirm-delete {
    position: absolute;
    padding: 0.5rem;
    transform: translateY(calc(-5% - #{$bar-height}));
    left: calc(-#{$mascot-size});
    z-index: 101;
  }

  .mascot {
    width: $mascot-size;
    left: calc(-#{$mascot-size} / 1.6);
    transform: translateY(calc(-15% - #{$bar-height}));
    height: auto;
    z-index: 100;
    cursor: pointer;
  }

  .end-cap {
    position: absolute;
    height: 3vmin;
    width: 1px;
    left: 100%;
    background-color: $bar-color;
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
