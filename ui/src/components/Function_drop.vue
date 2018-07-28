<template>
  <div class="function-drop">
    <draggable
      class="function-drop-drop-zone"
      :class="[showMesh ? 'mesh-background' : '', origin + '-drop-zone']"
      :list="list"
      :options="options"
      @add="add"
      @change="change"
      @start="start"
      @end="end"
    >
      <function-box
        v-for="(func, ind) in list.concat(placeHolders)"
        :class="func.placeholder ? 'placeholder-piece noDrag' : ''"
        :key="origin + '-drop/' + ind"
        :func="func"
        :ind="ind"
        :collection="list.concat(placeHolders)"
        :origin="origin"
      ></function-box>
    </draggable>
  </div>
</template>

<script>
import draggable from 'vuedraggable'
import FunctionBox from './Function_box'
import _ from 'underscore'

export default {
  name: 'function_drop',
  mounted () {
  },
  computed: {
    placeHolders () {
      if (this.sizeLimit < 100) {
        return this.createPlaceHolders(this.sizeLimit).slice(this.list.length)
      } else {
        return []
      }
    },
    showMesh () {
      return this.$store.getters.getShowMesh
    },
    permanentImages () {
      return this.$store.getters.getPermanentImages
    },
    functionAreaShowing () {
      return this.$store.getters.getFunctionAreaShowing
    }
  },
  data () {
    return {
    }
  },
  methods: {
    createPlaceHolders (size) {
      return _.chain(size)
        .range()
        .map((_val, ind) => {
          return {index: ind, placeholder: true}
        })
        .value()
    },
    enter () {
      console.log('enter')
    },
    leave () {
      console.log('leave')
    }
  },
  components: {
    draggable,
    FunctionBox
  },
  props: ['id', 'className', 'origin', 'list', 'options', 'add', 'change', 'start', 'end', 'groupSize', 'sizeLimit']
}
</script>

<style scoped lang="scss">
  $drop-zone-padding-left: 30px;
  $drop-zone-padding-right: 30px;

  .function-drop {
    overflow: auto;
    height: 100%;
    width: 100%;
    z-index: 998;
    .function-drop-drop-zone {
      position: relative;
      width: min-content;
      min-width: 100%;
      height: 100%;
      display: flex;
      justify-content: center;
      align-items: center;
      z-index: 999;
      padding-left: $drop-zone-padding-left;
      padding-right: $drop-zone-padding-right;
    }
    .editFunction-drop-zone {
      justify-content: flex-start;
    }
  }

  .mesh-background {
    border-radius: 3px;
    background: repeating-linear-gradient(
        45deg,
        rgba(0, 0, 0, 0.1),
        rgba(0, 0, 0, 0.1) 5px,
        rgba(74, 74, 74, 0.3) 7px,
        rgba(74, 74, 74, 0.3) 9px
    ), repeating-linear-gradient(
        -45deg,
        rgba(0, 0, 0, 0.1),
        rgba(0, 0, 0, 0.1) 5px,
        rgba(74, 74, 74, 0.3) 7px,
        rgba(74, 74, 74, 0.3) 9px
    );
  }

  .placeholder-piece {
    opacity: 0.4;
  }

  .placeholder-piece:first-child {
  }

  .center-function-drop {
    justify-content: center!important;
    padding: 0!important;
  }

  /* Medium Devices, Desktops */
  @media only screen and (max-width : 992px) {

  }

  /* Small Devices */
  @media only screen and (max-width : 667px) {

  }

  /* iphone 5 landscape */
  @media only screen and (max-width : 568px) {
  }

  /* iphone 5 portrait */
  @media only screen and (max-width : 320px) {
  }

  /* iPad */
  @media all and (device-width: 768px) and (device-height: 1024px) and (orientation:portrait) {

  }
  @media all and (device-width: 768px) and (device-height: 1024px) and (orientation:landscape) {

  }

</style>
