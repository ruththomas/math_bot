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
    if (this.editingIndex !== null) {
      this.popoverBucket = new PopoverBucket(this)
    }
  },
  computed: {
    editingIndex () {
      return this.$store.getters.getEditingIndex
    },
    functionAreaShowing () {
      return this.$store.getters.getFunctionAreaShowing
    }
  },
  watch: {
    editingIndex (ind) {
      if (ind !== null) {
        this.popoverBucket = new PopoverBucket(this)
      }
    }
  },
  updated () {
    if (this.editingIndex !== null) {
      this.popoverBucket = new PopoverBucket(this)
    }
  },
  data () {
    return {
      popoverBucket: {}
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
