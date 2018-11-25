<template>
  <div class="edit-main" :class="functionAreaShowing === 'editMain' ? '' : 'deactivate-edit-main'">
    <function-drop
      :id="'edit-main'"
      :class="'edit-main-drop'"
      :list="mainFunction.func"
      :options="mainDraggableOptions"
      :change="editFunction"
      :add="add"
      :start="moving"
      :end="end"
      :origin="'editMain'"
      :size-limit="levelControl.continent.mainMax"
    ></function-drop>
    <control-bar
      :wipe-function="wipeFunction"
      :toggle-put="togglePut"
      :start="runCompiled.start"
    ></control-bar>
  </div>
</template>

<script>
import buildUtils from '../services/BuildFunction'
import draggable from 'vuedraggable'
import FunctionBox from './Function_box'
import FunctionDrop from './Function_drop'
import ControlBar from './Control_bar'

export default {
  mounted () {
    // this.togglePut(this.mainFunctionFunc.length < this.stepData.mainMax)
  },
  computed: {
    levelControl () {
      return this.$store.getters.getLevelControl
    },
    mainFunction () {
      return this.levelControl.functions.main
    },
    runCompiled () {
      return this.levelControl.runCompiled
    },
    functionAreaShowing () {
      return this.$store.getters.getFunctionAreaShowing
    }
  },
  data () {
    return {
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
      const mainBalance = this.mainFunction.func.length < this.levelControl.continent.mainMax
      this.togglePut(mainBalance)
      if (!mainBalance) {
        this.fullMessage()
      }
    },
    wipeFunction () {
      this.levelControl.deleteMain()
      this.togglePut(true)
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
    ControlBar
  }
}
</script>

<style scoped lang="scss">
  $edit-main-side-padding: 16%;
  $edit-main-top-bottom-padding: 0;
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
</style>
