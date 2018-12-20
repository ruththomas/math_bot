<template>
  <div class="popover-bucket">
    <div class="popover-bucket-content">
      <edit-function></edit-function>
    </div>
  </div>
</template>

<script>
import StagedFunctions from './Staged_functions'
import EditFunction from './Edit_function'

export default {
  computed: {
    editFunctionEvent () {
      return $(this.$store.getters.getEditFunctionEvent)
    },
    functionAreaShowing () {
      return this.$store.getters.getFunctionAreaShowing
    }
  },
  components: {
    StagedFunctions,
    EditFunction
  }
}
</script>

<style scoped lang="scss">
  $pointer-size: 2vmin;

  .popover-bucket {
    position: absolute;
    bottom: -1px;
    right: 0;
    left: 0;
    z-index: 110;
    margin: 0 auto;
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
