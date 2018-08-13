<template>
  <div v-if="!congratsShowing && !tryAgainShowing" class="edit-main" :class="functionAreaShowing === 'editMain' ? '' : 'deactivate-edit-main'">
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
      <!--<main-placeholder></main-placeholder>-->
      <img class="trash noDrag dialog-button" :src="permanentImages.buttons.trashButton"  @click="wipeFunction" data-toggle="tooltip" title="Clear main" />
      <div class="speed dialog-button" @click="adjustSpeed" data-toggle="tooltip" title="Adjust speed"> {{ robotSpeedDisplay }}</div>

      <img
        v-if="runCompiled.robot.state === 'home' || runCompiled.robot.state === 'paused'"
        class="play noDrag dialog-button"
        :src="permanentImages.buttons.playButton"
        alt="Play button" @click="[runCompiled.start(), togglePut(true), closeHint()]"
        data-toggle="tooltip" title="Run program" />

      <img
        v-else-if="runCompiled.robot.state === 'running'"
        class="play noDrag dialog-button"
        :src="permanentImages.buttons.pauseButton"
        alt="Pause button" @click="runCompiled.pause"
        data-toggle="tooltip" title="Pause program" />

      <img
        v-if="runCompiled.robot.state === 'running'"
        class="stop button noDrag dialog-button"
        :src="permanentImages.buttons.stopButton"
        alt="Stop button" @click="runCompiled.stop"
        data-toggle="tooltip" title="Stop program"/>
    </div>
  </div>
</template>

<script>
import {_} from 'underscore'
import utils from '../services/utils'
import buildUtils from '../services/BuildFunction'
import draggable from 'vuedraggable'
import RunCompiled from '../services/RunCompiled'
import FunctionBox from './Function_box'
import FunctionDrop from './Function_drop'
import MainPlaceholder from './Main_placeholder'

export default {
  computed: {
    mainFunctionFunc () {
      const mainToken = this.$store.getters.getMainFunction
      return mainToken === null ? [] : mainToken.func
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
    editingFunction () {
      return this.$store.getters.getMainFunction.func[this.editingIndex]
    },
    functionAreaShowing () {
      return this.$store.getters.getFunctionAreaShowing
    },
    currentFunc () {
      return this.$store.getters.getFunctions[this.$store.getters.getCurrentFunction]
    },
    robot () {
      return this.$store.getters.getRobot
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
      },
      runCompiled: new RunCompiled(this)
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
      if (!bool) this.fullMessage()
    },
    editFunction (evt) {
      if (!evt.hasOwnProperty('removed')) {
        buildUtils.addToFunction()
      }
      this.togglePut(this.mainFunctionFunc.length < this.stepData.mainMax)
    },
    toggleFunctionEdit (func, ind) {
      if (func.name) {
        utils.toggleFunctionEdit(this, func, ind, 'editMain')
      }
    },
    wipeFunction () {
      buildUtils.deleteFunction({context: this})
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
    FunctionDrop,
    MainPlaceholder
  }
}
</script>

<style scoped lang="scss">
  $edit-main-side-padding: 16%;
  $edit-main-top-bottom-padding: 0;
  $bar-height: 2px;

  .edit-main {
    position: relative;
    width: 100%;
    margin: 0 auto;
    height: 100%;
    z-index: 102;
    padding: $edit-main-top-bottom-padding $edit-main-side-padding $edit-main-top-bottom-padding $edit-main-side-padding;
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
    top: -20px;
    float: right;
  }

  .play {
    border-radius: 50%;
    right: 0;
  }

  .play-border {
    border: 3px solid rgb(135, 206, 250);
  }

  .stop {
    right: 100px;
  }

  .trash {
    left: 0;
  }

  .speed {
    right: 50px;
    background-color: #B8E986;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    color: black;
    font-size: 22px;
    font-weight: 500;
  }

  .x {
    float: left;
  }

  /* ipad pro Portrait */
  @media only screen
  and (min-device-width: 1024px)
  and (max-device-width: 1366px)
  and (orientation: portrait)
  and (-webkit-min-device-pixel-ratio: 1.5) {
    $edit-main-side-padding: 16%;
    $edit-main-top-bottom-padding: 0;
    $bar-height: 2px;
    $edit-main-per-margin: 0;

    .edit-main {
      margin-right: $edit-main-per-margin;
      margin-left: $edit-main-per-margin;
    }

    .bar {
      right: 0;
      left: 0;
      height: $bar-height;
    }

    .dialog-button {
      top: -15px;
    }

    .play {
      right: 0;
    }

    .stop {
      right: -40px;
    }

    .speed {
      right: 40px;
      font-size: 16px;
    }

    .ghost, .sortable-chosen, .dragging {
      width: 40px;
      height: 40px;
    }
  }

  // ipad landscape
  @media only screen
  and (min-device-width : 768px)
  and (max-device-width : 1024px)
  and (orientation : landscape) {
    $edit-main-padding-left: 12%;
    $edit-main-padding-right: 12%;
    $bar-height: 1px;

    .bar {
      right: 10px;
      left: 10px;
      height: $bar-height;
    }

    .dialog-button {
      top: -15px;
    }

    .play {
      right: 0;
    }

    .stop {
      right: 80px;
    }

    .speed {
      right: 40px;
      font-size: 12px;
    }

    .ghost, .sortable-chosen, .dragging {
      width: 40px;
      height: 40px;
    }
  }

  // ipad portrait
  @media only screen
  and (min-device-width : 768px)
  and (max-device-width : 1024px)
  and (orientation : portrait) {
    $edit-main-side-padding: 16%;
    $edit-main-top-bottom-padding: 0;
    $bar-height: 2px;

    .bar {
      right: 0;
      left: 0;
      height: $bar-height;
    }

    .dialog-button {
      top: -15px;
    }

    .play {
      right: 0;
    }

    .stop {
      right: 80px;
    }

    .speed {
      right: 40px;
      font-size: 16px;
    }

    .ghost, .sortable-chosen, .dragging {
      width: 40px;
      height: 40px;
    }
  }

  @media only screen and (max-width: 823px) and (orientation: landscape) {
    $edit-main-side-padding: 16%;
    $edit-main-top-bottom-padding: 0;
    $bar-height: 2px;
    $bar-height: 1px;

    .bar {
      right: 10px;
      left: 10px;
      height: $bar-height;
    }

    .dialog-button {
      top: -9px;
    }

    .play {
      right: 0;
    }

    .stop {
      right: 50px;
    }

    .speed {
      right: 25px;
      font-size: 16px;
    }

    .ghost, .sortable-chosen, .dragging {
      width: 40px;
      height: 40px;
    }
  }

  @media only screen and (max-width: 736px) and (orientation: landscape) {
    $edit-main-padding-left: 12%;
    $edit-main-padding-right: 12%;
    $bar-height: 1px;

    .bar {
      right: 10px;
      left: 10px;
      height: $bar-height;
    }

    .dialog-button {
      top: -9px;
    }

    .play {
      right: 0;
    }

    .stop {
      right: 24px;
    }

    .speed {
      left: 24px;
      font-size: 12px;
    }

    .ghost, .sortable-chosen, .dragging {
      width: 40px;
      height: 40px;
    }
  }

  @media only screen and (max-width : 667px) and (orientation: landscape) {
    $edit-main-padding-left: 12%;
    $edit-main-padding-right: 12%;
    $bar-height: 1px;

    .bar {
      right: 10px;
      left: 10px;
      height: $bar-height;
    }

    .dialog-button {
      top: -9px;
    }

    .play {
      right: 0;
    }

    .stop {
      right: 24px;
    }

    .speed {
      left: 24px;
      font-size: 12px;
    }

    .ghost, .sortable-chosen, .dragging {
      width: 40px;
      height: 40px;
    }
  }

  /* iphone 5 landscape */
  @media only screen and (max-width : 568px) and (orientation: landscape) {
    $edit-main-padding-left: 12%;
    $edit-main-padding-right: 12%;
    $bar-height: 1px;

    .bar {
      right: 10px;
      left: 10px;
      height: $bar-height;
    }

    .dialog-button {
      top: -9px;
    }

    .play {
      right: 0;
    }

    .stop {
      right: 24px;
    }

    .speed {
      left: 24px;
      font-size: 12px;
    }

    .ghost, .sortable-chosen, .dragging {
      width: 40px;
      height: 40px;
    }
  }

  @media only screen and (max-width: 414px) {
    $edit-main-padding-left: 12%;
    $edit-main-padding-right: 12%;
    $bar-height: 1px;

    .bar {
      right: 5px;
      left: 5px;
      height: $bar-height;
    }

    .dialog-button {
      top: -9px;
    }

    .play {
      right: 0;
    }

    .stop {
      right: 24px;
    }

    .speed {
      left: 24px;
      font-size: 12px;
    }

    .ghost, .sortable-chosen, .dragging {
      width: 40px;
      height: 40px;
    }
  }

  /* iphone 6/7 portrait */
  @media only screen and (max-width : 375px) {
    $edit-main-padding-left: 12%;
    $edit-main-padding-right: 12%;
    $bar-height: 1px;

    .bar {
      right: 5px;
      left: 5px;
      height: 1px;
    }

    .dialog-button {
      top: -9px;
    }

    .play {
      right: 0;
    }

    .stop {
      right: 24px;
    }

    .speed {
      left: 24px;
      font-size: 12px;
    }

    .ghost, .sortable-chosen, .dragging {
      width: 40px;
      height: 40px;
    }
  }

  /* iphone 5 portrait */
  @media only screen and (max-width : 320px) {
    .bar {
      right: 5px;
      left: 5px;
    }

    .dialog-button {
      top: -9px;
    }

    .play {
      right: 0;
    }

    .stop {
      right: 24px;
    }

    .speed {
      left: 24px;
      font-size: 12px;
    }

    .ghost, .sortable-chosen, .dragging {
      width: 40px;
      height: 40px;
    }
  }
</style>
