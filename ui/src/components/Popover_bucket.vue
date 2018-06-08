<template>
  <div v-if="functionAreaShowing !== 'editMain'">
    <div class="popover-bucket">
      <div class="popover-bucket-content">
        <staged-functions v-if="functionAreaShowing === 'addFunction'"></staged-functions>
        <edit-function v-else></edit-function>
      </div>
      <div class="pointer-slider">
        <div class="pointer">
          <div class="pointer-size pointer-border"></div>
          <div class="pointer-size pointer-body"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import StagedFunctions from './Staged_functions'
import EditFunction from './Edit_function'
import PopoverBucket from '../services/PopoverBucket'

export default {
  mounted () {
    this.popoverBucket = new PopoverBucket(this, this.target)
  },
  computed: {
    editingIndex () {
      return this.$store.getters.getEditingIndex
    },
    functionAreaShowing () {
      return this.$store.getters.getFunctionAreaShowing
    },
    target () {
      return this.evt.target
    }
  },
  updated () {
    if (this.functionAreaShowing === 'editFunction' || this.functionAreaShowing === 'addFunction') this.popoverBucket.updateTarget(this, this.target)
    else this.popoverBucket.killBucket()
  },
  watch: {
    target (target) {
      this.popoverBucket.updateTarget(this, target)
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
  },
  props: ['evt']
}
</script>

<style scoped src="../css/scoped/popoverBucket.scss" lang="scss"></style>
