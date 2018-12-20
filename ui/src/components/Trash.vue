<template>
  <draggable
    v-if="trashVisible"
    class="fire"
    :list="trash"
    :options="trashDraggableData"
    @change="dumpTrash($event)"
    @onDragOver="test($event)"
  >
  </draggable>
</template>

<script>
import draggable from 'vuedraggable'

export default {
  data () {
    return {
      trash: [],
      trashDraggableData: {
        group: {
          name: 'commands-slide',
          put: true,
          pull: false
        },
        scroll: false
      }
    }
  },
  computed: {
    trashVisible () {
      return this.$store.getters.getTrashVisible
    }
  },
  methods: {
    dumpTrash () {
      this.$store.dispatch('updateTrashVisible', false)
    }
  },
  components: {
    draggable
  }
}
</script>

<style scoped lang="scss">
  .fire {
    position: absolute;
    z-index: 101;
    height: 100%;
    width: 100%;
  }
</style>
