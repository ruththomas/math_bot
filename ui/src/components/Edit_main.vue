<template>
  <div class="edit-main" :class="functionAreaShowing === 'editMain' ? '' : 'deactivate-edit-main'">
    <function-drop
      :id="'edit-main'"
      :class="'edit-main-drop'"
      :list="mainFunctionFunc"
      :options="mainDraggableOptions"
      :change="editFunction"
      :start="moving"
      :end="end"
      :origin="'editMain'"
      :group-size="groupSize"
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
import Swiper from '../services/Swiper'

export default {
  mounted () {
    this.groupSize = Swiper.calculateGroupSize('edit-main-drop')
    window.addEventListener('resize', () => {
      this.groupSize = Swiper.calculateGroupSize('edit-main-drop')
    })
  },
  computed: {
    mainFunctionFunc () {
      const mainToken = this.$store.getters.getMainFunction
      return mainToken === null ? [] : mainToken.func
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
        scrollSensitivity: 500,
        ghostClass: 'ghost',
        chosenClass: 'chosen',
        filter: '.noDrag',
        dragClass: 'dragging'
      },
      runCompiled: new RunCompiled(this),
      groupSize: 10
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
    editFunction (evt, groupInd) {
      if (!evt.hasOwnProperty('removed')) {
        buildUtils.addToFunction({
          context: this,
          groupSize: this.groupSize,
          groupInd: groupInd,
          added: evt.hasOwnProperty('added') ? evt.added : evt.moved
        })
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
    },
    adjustSpeed () {
      this.$store.dispatch('changeRobotSpeed')
    },
    moving () {
      this.$store.dispatch('updateTrashVisible', true)
      this.$store.dispatch('toggleShowMesh', true)
    },
    end () {
      this.$store.dispatch('toggleShowMesh', false)
      this.$store.dispatch('updateTrashVisible', false)
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
  $edit-main-padding: 12%;

  .edit-main {
    position: relative;
    width: 100%;
    height: 100%;
    z-index: 1000;
    padding: 0 $edit-main-padding 0 $edit-main-padding;
  }

  .deactivate-edit-main {
    opacity: 0;
  }

  .bar {
    position: absolute;
    left: 10px;
    right: 10px;
    top: 50%;
    height: 2px;
    background-color: #B8E986;
    display: flex;
    align-items: center;
    justify-content: center;
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
    right: -58px;
  }

  .trash {
    left: 0;
  }

  .speed {
    right: 60px;
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

  /* Medium Devices, Desktops */
  @media only screen and (max-width : 992px) {
  }

  /* Small Devices */
  @media only screen and (max-width : 667px) {

  }

  /* iphone 5 landscape */
  @media only screen and (max-width : 568px) {
    $edit-main-padding-left: 12%;
    $edit-main-padding-right: 12%;

    .edit-main {
      padding: 0 $edit-main-padding-right 0 $edit-main-padding-left;
    }

    .bar {
      right: -10px;
      left: -10px;
    }

    .dialog-button {
      top: -9px;
    }

    .play {
      right: 0;
    }

    .stop {
      right: -25px;
    }

    .speed {
      right: 24px;
      font-size: 12px;
    }

    .ghost, .sortable-chosen, .dragging {
      width: 40px;
      height: 40px;
    }

    .edit-main .function-drop {
      width: 70%!important;
    }
  }

  /* iphone 5 portrait */
  @media only screen and (max-width : 320px) {
    $edit-main-padding-left: 12%;
    $edit-main-padding-right: 12%;

    .edit-main {
      padding: 0 $edit-main-padding-right 0 $edit-main-padding-left;
    }

    .bar {
      right: -20px;
      left: -10px;
    }

    .dialog-button {
      top: -9px;
    }

    .play {
      right: 0;
    }

    .stop {
      display: none;
    }

    .speed {
      right: 24px;
      font-size: 12px;
    }

    .ghost, .sortable-chosen, .dragging {
      width: 40px;
      height: 40px;
    }

    .edit-main .function-drop {
      width: 70%!important;
    }
  }

  /* iPad */
  @media all and (device-width: 768px) and (device-height: 1024px) and (orientation:portrait) {
    .edit-main {
      height: 105px;
    }

    .dialog-button {
      top: -15px;
    }

    .stop {
      display: none;
    }

    .speed {
      right: 35px;
      font-size: 12px;
    }
  }
  @media all and (device-width: 768px) and (device-height: 1024px) and (orientation:landscape) {
    .edit-main {
      padding: 0;
    }

    .function-drop {
      height: 60px;
      width: 75%;
    }

    .dialog-button {
      top: -15px;
    }

    .stop {
      display: none;
    }

    .speed {
      right: 35px;
      font-size: 12px;
    }

    .ghost, .sortable-chosen, .dragging {
      width: 40px;
      height: 40px;
    }
  }

</style>
