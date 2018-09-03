<template>
  <div class="staged-functions-container">
    <div class="staged-functions-header">
      <img class="dialog-button close-popover" :src="permanentImages.buttons.xButton" @click="closeStagedFunctions" />
    </div>
    <div class="staged-functions-content">
      <draggable
        class="staged-functions"
        :list="stagedFunctions"
        :options="draggableOptions"
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

export default {
  mounted () {
  },
  computed: {
    stagedFunctions () {
      return this.$store.getters.getStagedFunctions
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
          put: false,
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
    }
  },
  components: {
    draggable,
    FunctionBox,
    PuzzlePieces
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
    .staged-functions {
      width: min-content;
      min-width: 100%;
      width: -moz-min-content;
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
