<template>
  <draggable
    class="staged-functions"
    :list="stagedFunctions"
    :options="draggableOptions"
  >
    <function-box
      v-for="(func, ind) in stagedFunctions"
      :key="'functions/' + ind"
      :func="func"
      :ind="ind"
      :collection="stagedFunctions"
      :origin="'stagedFunctions'"
      :data-function-index="ind"
    ></function-box>
    <!--:method="addToActiveFunc"-->
  </draggable>
</template>

<script>
import FunctionBox from './Function_box'
import draggable from 'vuedraggable'

export default {
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
      clicks: 0,
      timer: null,
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
  components: {
    draggable,
    FunctionBox
  }
}
</script>

<style scoped lang="scss">
  .staged-functions {
    height: 100%;
    width: 100%;
    overflow: auto;
    display: flex;
    flex-wrap: wrap;
    padding: 10px 20px 10px 20px;
  }

  .staged-functions > * {
    margin: 10px 10px 10px 0;
  }

  .dragging, .ghost, .chosen {
    float: left;
  }

</style>
