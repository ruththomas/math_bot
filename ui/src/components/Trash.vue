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
import buildUtils from '../services/BuildFunction'

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
    permanentImages () {
      return this.$store.getters.getPermanentImages
    },
    trashVisible () {
      return this.$store.getters.getTrashVisible
    }
  },
  methods: {
    dumpTrash () {
      this.$store.dispatch('updateTrashVisible', false)
      buildUtils.deleteItemFromFunction({context: this})
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
    z-index: 100;
    height: 100%;
    width: 100%;
    background: rgba(0, 0, 0, 0.3);
  }
</style>
