<template>
 <draggable
   class="active-drop"
   :list="activeFunctions"
   :options="activeDropOptions"
   @add="addToActiveFunc"
 >
 </draggable>
</template>

<script>
import draggable from 'vuedraggable'
export default {
  name: 'Activate_drop',
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
    activeFunctions () {
      return this.levelControl.functions.activeFunctions
    },
    stagedFunctions () {
      return this.levelControl.functions.stagedFunctions
    }
  },
  data () {
    return {
      activeDropOptions: {
        group: {
          name: 'commands-slide',
          pull: false,
          put: ['commands-staged']
        },
        filter: '.command-name'
      }
    }
  },
  methods: {
    addToActiveFunc (evt) {
      const createdId = $(evt.item).attr('data-created-id')
      const func = this.stagedFunctions.find((f) => f.created_id === createdId)
      if (func) {
        func.index = 0
        func.category = 'function'
        this.levelControl.activateFunction(func)
      }
    }
  },
  components: {
    draggable
  }
}
</script>

<style scoped lang="scss">
$pointer-size: 2vmin;
.active-drop {
  position: fixed;
  /*background-color: #f0f0f0;*/
  height: 100%;
  width: 100%;
  bottom: 0;
  left: 0;
  z-index: 100;
}
</style>
