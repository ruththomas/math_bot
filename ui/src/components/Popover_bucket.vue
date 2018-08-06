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
  $popover-margin-top: 60px;

  .popover-bucket {
    z-index: 1010;
    height: calc(100% + #{$popover-margin-top} + 2px);
    width: 100%;
    margin: calc(-#{$popover-margin-top}) auto;
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

  @media only screen
  and (min-device-width : 768px)
  and (max-device-width : 1024px)
  and (orientation : landscape) {
    $popover-width: 100%;
    $popover-margin-top: 50px;

    .popover-bucket {
      z-index: 1010;
      height: calc(100% + #{$popover-margin-top});
      margin-top: calc(-#{$popover-margin-top} + 2px);
      width: $popover-width;
    }
  }

  @media only screen
  and (min-device-width : 768px)
  and (max-device-width : 1024px)
  and (orientation : portrait) {
    $popover-width: 100%;
    $popover-margin-top: 70px;

    .popover-bucket {
      z-index: 1010;
      height: calc(100% + #{$popover-margin-top});
      margin-top: calc(-#{$popover-margin-top} + 2px);
      width: $popover-width;
    }
  }

  @media only screen and (max-width : 823px) and (orientation: landscape) {
    $popover-width: 98%;
    $popover-margin-top: 50px;

    .popover-bucket {
      z-index: 1010;
      height: calc(100% + #{$popover-margin-top});
      margin-top: calc(-#{$popover-margin-top} + 2px);
      width: $popover-width;
    }
  }

  @media only screen and (max-width : 667px) and (orientation: landscape) {
    $popover-width: 98%;
    $popover-margin-top: 50px;

    .popover-bucket {
      z-index: 1010;
      height: calc(100% + #{$popover-margin-top});
      margin-top: calc(-#{$popover-margin-top} + 2px);
      width: $popover-width;
    }
  }

  /* iphone 5 landscape */
  @media only screen and (max-width : 568px) and (orientation: landscape) {
    $popover-width: 98%;
    $popover-margin-top: 50px;

    .popover-bucket {
      z-index: 1010;
      height: calc(100% + #{$popover-margin-top});
      margin-top: calc(-#{$popover-margin-top} + 2px);
      width: $popover-width;
    }
  }

  @media only screen and (max-width: 414px) {
    $popover-width: 98%;

    .popover-bucket {
      z-index: 1010;
      height: 100%;
      margin-top: 2px;
      width: $popover-width;
    }
  }

  @media only screen and (max-width: 375px) {
    $popover-width: 98%;

    .popover-bucket {
      z-index: 1010;
      height: 100%;
      margin-top: 2px;
      width: $popover-width;
    }
  }

  /* iphone 5 portrait */
  @media only screen and (max-width : 320px) {
    $popover-width: 98%;

    .popover-bucket {
      z-index: 1010;
      height: 100%;
      margin-top: 2px;
      width: $popover-width;
    }
  }
</style>
