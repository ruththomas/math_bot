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
    z-index: 1010;
    height: calc(100% + 60px);
    margin-top: -58px;
    width: 100%;
  }

  .staged-popover-bucket {
    left: 48px;
    right: -48px;
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

  @media only screen and (max-width : 823px) and (orientation: landscape) {
    $popover-bucket-top: -100px;
    $popover-bucket-height: 90px;

    .popover-bucket {
      top: $popover-bucket-top;
      height: $popover-bucket-height;
    }

    .staged-popover-bucket {
      left: 16px;
      right: -17px;
    }
  }

  @media only screen and (max-width : 667px) and (orientation: landscape) {
    $popover-bucket-top: -100px;
    $popover-bucket-height: 90px;

    .popover-bucket {
      top: $popover-bucket-top;
      height: $popover-bucket-height;
    }

    .staged-popover-bucket {
      left: 16px;
      right: -17px;
    }
  }

  /* iphone 5 landscape */
  @media only screen and (max-width : 568px) and (orientation: landscape) {
    $popover-bucket-top: -100px;
    $popover-bucket-height: 90px;

    .popover-bucket {
      top: $popover-bucket-top;
      height: $popover-bucket-height;
    }

    .staged-popover-bucket {
      left: 16px;
      right: -17px;
    }
  }

  @media only screen and (max-width: 414px) {
    $popover-bucket-top: -100px;
    $popover-bucket-height: 90px;

    .popover-bucket {
      top: $popover-bucket-top;
      height: $popover-bucket-height;
    }
  }

  @media only screen and (max-width: 375px) {
    $popover-bucket-top: -100px;
    $popover-bucket-height: 90px;

    .popover-bucket {
      top: $popover-bucket-top;
      height: $popover-bucket-height;
    }
  }

  /* iphone 5 portrait */
  @media only screen and (max-width : 320px) {
    $popover-bucket-top: -100px;
    $popover-bucket-height: 90px;

    .popover-bucket {
      top: $popover-bucket-top;
      height: $popover-bucket-height;
    }
  }
</style>
