<template>
  <div class="edit-main" :class="functionAreaShowing === 'editMain' ? '' : 'deactivate-edit-main'">
    <function-drop
      :id="'edit-main'"
      :class="'edit-main-drop'"
      :list="mainFunctionFunc"
      :options="mainDraggableOptions"
      :change="editFunction"
      :add="add"
      :start="moving"
      :end="end"
      :origin="'editMain'"
      :size-limit="stepData.mainMax"
    ></function-drop>

    <div class="bar noDrag" v-if="Object.keys(robot).length">
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
        alt="Play button" @click="[runCompiled.start(), togglePut(true), closeHint()]"
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
  </div>
</template>

<script>
import {_} from 'underscore'
import utils from '../services/utils'
import buildUtils from '../services/BuildFunction'
import draggable from 'vuedraggable'
import FunctionBox from './Function_box'
import FunctionDrop from './Function_drop'

export default {
  mounted () {
    // this.togglePut(this.mainFunctionFunc.length < this.stepData.mainMax)
  },
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
    mainFunction () {
      return this.levelControl.functions.main
    },
    mainFunctionFunc () {
      return this.mainFunction.func
    },
    runCompiled () {
      return this.levelControl.runCompiled
    },

    editingFunction () {
      return this.$store.getters.getMainFunction.func[this.editingIndex]
    },
    congratsShowing () {
      return this.$store.getters.getCongratsShowing
    },
    tryAgainShowing () {
      return this.$store.getters.getTryAgainShowing
    },
    showMesh () {
      return this.$store.getters.getShowMesh
    },
    permanentImages () {
      return this.$store.getters.getPermanentImages
    },
    funcImages () {
      return this.permanentImages.funcImages
    },
    cmdImages () {
      return this.permanentImages.cmdImages
    },
    funcNcmdImages () {
      return _.extend(this.funcImages, this.cmdImages)
    },
    editingIndex () {
      return this.$store.getters.getEditingIndex
    },
    functionAreaShowing () {
      return this.$store.getters.getFunctionAreaShowing
    },
    currentFunc () {
      return this.$store.getters.getFunctions[this.$store.getters.getCurrentFunction]
    },
    robotSpeedDisplay () {
      return this.robot.robotSpeed.display
    },
    stepData () {
      return this.$store.getters.getStepData
    },
    currentColor () {
      return this.$store.getters.getColorSelected
    },
    colors () {
      return this.$store.getters.getColors
    }
  },
  data () {
    return {
      buttonSize: $('.commands > button').width() || 70,
      screenSize: $('#robot').width(),
      mainDraggableOptions: {
        group: {
          name: 'commands-slide',
          pull: true,
          put: true
        },
        animation: 100,
        ghostClass: 'ghost',
        chosenClass: 'chosen',
        filter: '.noDrag',
        dragClass: 'dragging',
        sort: true
      }
    }
  },
  methods: {
    closeHint () {
      this.$store.dispatch('toggleHintShowing', {showing: false, videoURL: ''})
    },
    fullMessage () {
      const messageBuilder = {
        type: 'success',
        msg: `Main full`
      }
      this.$store.dispatch('addMessage', messageBuilder)
    },
    togglePut (bool) {
      this.mainDraggableOptions.group.put = bool
    },
    editFunction () {
      this.levelControl.updateFunction(this.mainFunction)
      const mainBalance = this.mainFunctionFunc.length < this.levelControl.continent.mainMax
      this.togglePut(mainBalance)
      if (!mainBalance) {
        this.fullMessage()
      }
    },
    toggleFunctionEdit (func, ind) {
      if (func.name) {
        utils.toggleFunctionEdit(this, func, ind, 'editMain')
      }
    },
    wipeFunction () {
      this.levelControl.deleteMain()
      this.togglePut(true)
    },
    adjustSpeed () {
      this.$store.dispatch('changeRobotSpeed')
    },
    add () {
      buildUtils._positionBar()
    },
    moving () {
      this.$store.dispatch('updateTrashVisible', true)
      this.$store.dispatch('toggleShowMesh', true)
      buildUtils._positionBar()
    },
    end () {
      this.$store.dispatch('toggleShowMesh', false)
      this.$store.dispatch('updateTrashVisible', false)
      buildUtils._positionBar()
    }
  },
  components: {
    draggable,
    FunctionBox,
    FunctionDrop
  }
}
</script>

<style scoped lang="scss">
  $edit-main-side-padding: 16%;
  $edit-main-top-bottom-padding: 0;
  $bar-height: 1px;
  $click-color: #B8E986;

  .edit-main {
    position: relative;
    width: 100%;
    margin: 0 auto;
    height: 100%;
    padding: $edit-main-top-bottom-padding $edit-main-side-padding $edit-main-top-bottom-padding $edit-main-side-padding;
    z-index: 1000;
  }

  .deactivate-edit-main {
    opacity: 0;
  }

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
