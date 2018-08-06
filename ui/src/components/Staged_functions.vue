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
  $staged-functions-padding-right: 50%;

  .staged-functions-container {
    position: relative;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    width: 100%;
    height: 100%;
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
    height: 100%;
    width: 100%;
    .staged-functions {
      padding: 0 $staged-functions-padding-right 0 $staged-functions-padding;
      min-width: min-content;
      display: flex;
      height: 100%;
      align-items: center;
      justify-content: flex-start;
      margin: 0 auto;
    }
  }

  .close-popover {
    position: absolute;
    right: -20px;
    top: -20px;
  }

  /* Medium Devices, Desktops */
  @media only screen and (max-width : 812px) {
    .close-popover {
      position: absolute;
      right: -10px;
      top: -10px;
    }
  }

  @media only screen and (max-width: 736px) {
    .close-popover {
      position: absolute;
      right: -10px;
      top: -10px;
    }
  }

  /* Small Devices */
  @media only screen and (max-width : 667px) {
    .close-popover {
      position: absolute;
      right: -10px;
      top: -10px;
    }
  }

  /* iphone 5 landscape */
  @media only screen and (max-width : 568px) {
    $staged-functions-padding-left: 14px;

    .staged-functions {
      padding-left: $staged-functions-padding-left;
    }

    .staged-functions-header {
      left: $staged-functions-padding-left;

      span {
        font-size: 12px;
      }
    }

    .close-popover {
      position: absolute;
      right: 5px;
      top: -10px;
    }
  }

  /* iphone 5 portrait */
  @media only screen and (max-width : 414px) {
    $staged-functions-padding-left: 15.5px;

    .staged-functions {
      padding-left: $staged-functions-padding-left;
    }

    .staged-functions-header {
      left: $staged-functions-padding-left;

      span {
        font-size: 12px;
      }
    }

    .close-popover {
      position: absolute;
      right: 12px;
      top: -10px;
    }
  }

  /* iPad */
  @media all and (device-width: 768px) and (device-height: 1024px) and (orientation:portrait) {
  }

  @media all and (device-width: 768px) and (device-height: 1024px) and (orientation:landscape) {
  }
</style>
