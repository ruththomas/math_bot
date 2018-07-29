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

<style scoped lang="scss">
  $popover-bucket-top: -200px;

  .popover-bucket {
    height: 190px;
    left: 0;
    right: 0;
    z-index: 1010;
    position: absolute;
    top: $popover-bucket-top;
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

  .pointer-slider {
    width: 100%;
    margin: 0 auto;
    position: relative;
  }

  .pointer {
    position: absolute;
    width: 40px;
    height: 20px;
    margin-top: -1px;
  }

  .pointer-hidden {
    opacity: 0;
  }

  .pointer-size {
    position: absolute;
    border-style: solid;
    border-width: 20px 18px 0 18px;
  }

  .pointer-body {
    top: -1px;
    z-index: 1001;
    border-color: #000000 transparent transparent transparent;
  }

  .pointer-border {
    top: 1px;
    z-index: 0;
    border-color: #737373 transparent transparent transparent;
  }

  /* Medium Devices, Desktops */
  @media only screen and (max-width : 992px) {
  }

  /* Small Devices */
  @media only screen and (max-width : 667px) {

  }

  /* iphone 5 landscape */
  @media only screen and (max-width : 568px) {
    $popover-bucket-top: -165%;

    .popover-bucket {
      top: $popover-bucket-top;
      height: 90px;
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

  /* iPad */
  @media all and (device-width: 768px) and (device-height: 1024px) and (orientation:portrait) {
    .popover-bucket {
      height: 200px;
      top: -210px;
    }

    .pointer {
      width: 40px;
      height: 20px;
    }

    .pointer-size {
      border-width: 20px 18px 0 18px;
    }
  }

  @media all and (device-width: 768px) and (device-height: 1024px) and (orientation:landscape) {

  }

</style>
