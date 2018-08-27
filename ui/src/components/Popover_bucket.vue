<template>
  <div class="popover-bucket">
    <div class="popover-bucket-content">
      <staged-functions v-if="functionAreaShowing === 'addFunction'"></staged-functions>
      <edit-function v-else></edit-function>
    </div>
  </div>
</template>

<script>
import StagedFunctions from './Staged_functions'
import EditFunction from './Edit_function'
import PopoverBucket from '../services/PopoverBucket'

export default {
  mounted () {
    this.popoverBucket = new PopoverBucket(this, this.editFunctionEvent)
  },
  beforeDestroy () {
    this.popoverBucket.removePointer()
  },
  computed: {
    editFunctionEvent () {
      return this.$store.getters.getEditFunctionEvent
    },
    editingIndex () {
      return this.$store.getters.getEditingIndex
    },
    functionAreaShowing () {
      return this.$store.getters.getFunctionAreaShowing
    }
  },
  watch: {
    editFunctionEvent (evt) {
      this.popoverBucket = new PopoverBucket(this, evt)
    }
  },
  data () {
    return {
      popoverBucket: null
    }
  },
  components: {
    StagedFunctions,
    EditFunction
  }
}
</script>

<style scoped lang="scss">
  .popover-bucket {
    position: absolute;
    bottom: -0.75vmin;
    width: 100%;
    z-index: 110;
  }

  .popover-bucket-content {
    width: 100%;
    height: 100%;
    border: 1px solid #737373;
    background-color: black;
    margin: 0 auto;
    position: relative;
    display: flex;
    align-items: center;
  }
</style>
