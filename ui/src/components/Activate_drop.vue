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
import buildUtils from '../services/BuildFunction'
export default {
  name: 'Activate_drop',
  computed: {
    activeFunctions () {
      return this.$store.getters.getActiveFunctions
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
      buildUtils.activateFunction({
        stagedIndex: evt.oldIndex,
        activeIndex: 0
      })
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
  height: 100%;
  width: 100%;
  top: 0;
  left: 0;
}
</style>
