<template>
  <div class="function-drop">
    <draggable
      class="function-drop-drop-zone"
      :class="showMesh ? 'mesh-background' : ''"
      :list="list"
      :options="options"
      @add="add"
      @change="change"
      @start="start"
      @end="end"
    >
      <function-box
        v-for="(func, ind) in list"
        :key="origin + '-drop/' + ind"
        :func="func"
        :ind="ind"
        :collection="list"
        :origin="origin"
      ></function-box>
    </draggable>

    <div
      class="drop-placeholder"
    >
      <function-box
        v-for="(placeHolder, ind) in placeHolders"
        :key="origin + '-placeholder/' + ind"
        :func="placeHolder"
        :ind="ind"
        :collection="placeHolders"
        :origin="origin"
      ></function-box>
    </div>
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
    placeHolders () { // todo
      return []
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
          return {index: ind}
        })
        .value()
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
  $drop-zone-padding-right: 10%;

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
      padding: 0 $drop-zone-padding-right 0 $drop-zone-padding-left;
    }
  }

  .mesh-background {
    border-radius: 3px;
    background: repeating-linear-gradient(
        45deg,
        rgba(0, 0, 0, 0.3),
        rgba(0, 0, 0, 0.3) 5px,
        rgba(74, 74, 74, 0.5) 7px,
        rgba(74, 74, 74, 0.5) 9px
    ), repeating-linear-gradient(
        -45deg,
        rgba(0, 0, 0, 0.3),
        rgba(0, 0, 0, 0.3) 5px,
        rgba(74, 74, 74, 0.5) 7px,
        rgba(74, 74, 74, 0.5) 9px
    );
  }

  .drop-placeholder {
    display: flex;
    align-items: center;
    opacity: 0.2;
    justify-content: flex-start;
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
    $function-drop-padding-left: 14px;

    .function-drop {
      padding-left: $function-drop-padding-left;
    }

    .drop-placeholder {
      padding-left: $function-drop-padding-left;
    }

    .editMain-function-drop-swiper {
      .function-drop {
        padding-left: 10px;
      }

      .drop-placeholder {
        padding-left: 10px;
      }
    }
  }

  /* iphone 5 portrait */
  @media only screen and (max-width : 320px) {
    $function-drop-padding-left: 14px;

    .function-drop {
      padding-left: $function-drop-padding-left;
    }

    .drop-placeholder {
      padding-left: $function-drop-padding-left;
    }

    .editMain-function-drop-swiper {
      .function-drop {
        padding-left: 10px;
      }

      .drop-placeholder {
        padding-left: 10px;
      }
    }
  }

  /* iPad */
  @media all and (device-width: 768px) and (device-height: 1024px) and (orientation:portrait) {

  }
  @media all and (device-width: 768px) and (device-height: 1024px) and (orientation:landscape) {

  }

</style>
