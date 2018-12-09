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
      :start="runCompiled.start"
    ></control-bar>
  </div>
</template>

<script>
import draggable from 'vuedraggable'
import FunctionBox from './Function_box'
import FunctionDrop from './Function_drop'
import ControlBar from './Control_bar'

export default {
  mounted () {
    this.setPut()
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
    setPut (bool = this.mainFunction.func.length < this.levelControl.continent.mainMax) {
      this.mainDraggableOptions.group.put = bool
      if (!bool) {
        this.fullMessage()
      }
    },
    editFunction () {
      if (this.mainFunction.func.length <= this.levelControl.continent.mainMax) {
        this.levelControl.updateFunction(this.mainFunction)
      }
      this.setPut()
    },
    wipeFunction () {
      this.levelControl.deleteMain()
      this.setPut(true)
    },
    add () {
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
