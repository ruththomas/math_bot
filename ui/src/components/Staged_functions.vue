<template>
  <div class="staged-functions-container">
    <active-drop></active-drop>
    <div class="staged-functions-header">
      <img class="dialog-button close-popover" :src="permanentImages.buttons.xButton" @click="closeStagedFunctions" />
    </div>
    <div class="staged-functions-content">
      <draggable
        class="staged-functions"
        :list="stagedFunctions"
        :options="draggableOptions"
        @add="confirmDeactivateFunction"
      >
        <function-box
          v-for="(func, ind) in stagedFunctions"
          :key="'staged-func/' + ind"
          :func="func"
          :ind="func.index"
          :collection="stagedFunctions"
          :origin="'stagedFunctions'"
          :data-function-index="func.index"
        ></function-box>
      </draggable>
    </div>
  </div>
</template>

<script>
import FunctionBox from './Function_box'
import draggable from 'vuedraggable'
import PuzzlePieces from './Puzzle_pieces'
import ActiveDrop from './Activate_drop'

export default {
  mounted () {
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
    stagedFunctions () {
      return this.levelControl.functions.stagedFunctions
    },

    permanentImages () {
      return this.$store.getters.getPermanentImages
    },
    currentFunction () {
      return this.$store.getters.getCurrentFunction
    },
    editFunctionShowing () {
      return this.$store.getters.getEditFunctionShowing
    }
  },
  data () {
    return {
      draggableOptions: {
        group: {
          name: 'commands-staged',
          pull: true,
          put: ['commands-slide'],
          revertClone: true
        },
        animation: 100,
        dragClass: 'dragging',
        ghostClass: 'ghost',
        chosenClass: 'chosen',
        sort: false
      }
    }
  },
  methods: {
    closeStagedFunctions () {
      this.$store.dispatch('updateFunctionAreaShowing', 'editMain')
      this.$store.dispatch('updateEditingIndex', null)
    },
    confirmDeactivateFunction (evt) {
      //
      // const func = this.levelControl.functions.activeFuncs[evt.oldIndex]
      // func.category = 'staged'
      // func.index = evt.newIndex
      // func.func = []
      // this.levelControl.deactivateFunction(func)
      this.$store.dispatch('confirmDeactivateFunction', {
        activeIndex: evt.oldIndex,
        stagedIndex: evt.newIndex
      })

      this.$root.$emit('bv::show::modal', 'confirm-deactivate-func')
    }
  },
  components: {
    draggable,
    FunctionBox,
    PuzzlePieces,
    ActiveDrop
  }
}
</script>

<style scoped lang="scss">
  $staged-functions-padding: 32px;
  $piece-height: 7.5vmin;
  $dialog-button-size: 3.5vmin;
  $piece-height: 7.5vmin;

  .staged-functions-container {
    position: relative;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    width: 100%;
    height: calc(#{$piece-height} * 2.2);
  }

  .staged-functions-header {
    display: flex;
    position: relative;
    height: 20%;
    justify-content: flex-start;
    align-items: center;
    padding: 0 $staged-functions-padding 0 $staged-functions-padding;
    span {
      padding-left: 4px;
      font-size: 18px;
    }
  }

  .staged-functions-content {
    overflow: auto;
    -webkit-overflow-scrolling: touch;
    height: 100%;
    width: 100%;
    z-index: 101;
    .staged-functions {
      width: min-content;
      min-width: 100%;
      width: -moz-min-content;
      height: 100%;
      display: flex;
      align-items: center;
      justify-content: flex-start;
      margin: 0 auto;
    }
  }

  .close-popover {
    float: right;
    display: flex;
    position: absolute;
    bottom: calc(#{$dialog-button-size} / 2);
    right:  calc(#{-$dialog-button-size} / 2);
    z-index: 10001;
    cursor: pointer;
  }
</style>
