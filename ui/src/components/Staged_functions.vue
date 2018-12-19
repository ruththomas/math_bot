<template>
  <div class="staged-functions-container">
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
        :data-created-id="func.created_id"
      ></function-box>
    </draggable>
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
    confirmDeactiveFunction () {
      return this.$store.getters.getConfirmDeactiveFunction
    },
    levelControl () {
      return this.$store.getters.getLevelControl
    },
    stagedFunctions () {
      return this.levelControl.functions.stagedFunctions
    },
    permanentImages () {
      return this.$store.getters.getPermanentImages
    }
  },
  data () {
    return {
      draggableOptions: {
        group: {
          name: 'commands-staged',
          pull: 'clone',
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
      const createdId = $(evt.item).attr('data-created-id')
      const func = this.levelControl.functions.activeFuncs.find(f => f.created_id === createdId)

      // if user drags command function ignore
      if (!func) return

      this.$store.dispatch('confirmDeactivateFunction',
        Object.assign(func, {
          index: evt.newIndex
        })
      )
      this.$root.$emit('bv::show::modal', 'confirm-deactivate-func')
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
    overflow: auto;
    -webkit-overflow-scrolling: touch;
    .staged-functions {
      width: min-content;
      min-width: 100%;
      width: -moz-min-content;
      height: 100%;
      margin: 0 auto;
      overflow: auto;
      display: flex;
      .piece {
        opacity: 0.8;
        height: 5vmin;
        width: 5vmin;
      }
    }
  }
</style>
