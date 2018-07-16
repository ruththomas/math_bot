<template>
  <swiper
    :id="id"
    class="function-drop-container"
    :options="swiper.options">
    <div v-if="showMesh" class="mesh-background"></div>
    <swiper-slide
      v-for="(group, gInd) in functionGroups"
      :key="'function-drop-swiper/' + gInd">
      <draggable
        class="function-drop"
        :list="list"
        :options="options"
        @change="change($event, gInd)"
        @start="start"
        @end="end"
      >
        <function-box
          v-if="group !== null"
          v-for="(func, fInd) in group"
          :key="origin + '-drop/' + fInd"
          :func="func"
          :ind="func.index"
          :collection="list"
          :origin="origin"
        ></function-box>
      </draggable>

      <div class="drop-placeholder"
        v-if="stepData.mainMax < 10000"
      >
        <function-box
          v-for="(placeHolder, pInd) in groupedPlaceholders[gInd]"
          :key="origin + '-placeholder/' + pInd"
          :func="placeHolder"
          :ind="placeHolder.index"
          :collection="placeHolders"
          :origin="'placeHolder'"
        ></function-box>
      </div>

    </swiper-slide>
    <div class="swiper-button-prev" slot="button-prev"></div>
    <div class="swiper-button-next" slot="button-next"></div>
    <div class="swiper-pagination swiper-pagination-bullets" :class="functionGroups.length < 2 ? 'hidden-swiper-pagination' : ''" slot="pagination"></div>
  </swiper>
</template>

<script>
import draggable from 'vuedraggable'
import FunctionBox from './Function_box'
import Swiper from '../services/Swiper'
import _ from 'underscore'

export default {
  name: 'function_drop',
  mounted () {
  },
  computed: {
    groupedPlaceholders () {
      return this.swiper.groupFunctions(this.placeHolders.slice(), this.groupSize)
    },
    placeHolders () {
      if (this.origin === 'editMain' && this.stepData.mainMax < 10000) {
        return _.chain(this.stepData.mainMax)
          .range()
          .map((val, ind) => {
            return {
              index: ind
            }
          })
          .value()
      } else if (this.origin === 'editFunction') {
        return [{}]
      }
    },
    stepData () {
      return this.$store.getters.getStepData
    },
    functionGroups () {
      const groups = this.swiper.groupFunctions(this.list.slice(), this.groupSize)

      return groups.length ? groups : [null]
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
      swiper: new Swiper()
    }
  },
  methods: {
  },
  components: {
    draggable,
    FunctionBox
  },
  props: ['id', 'origin', 'list', 'options', 'change', 'start', 'end', 'groupSize']
}
</script>

<style scoped lang="scss">
  .function-drop-container {
    position: relative;
    height: 100%;
    width:100%;
  }

  .function-drop {
    position: absolute;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    z-index: 2001;
    display: flex;
    align-items: center;
    justify-content: flex-start;
    padding-left: 33px;
    margin: 0 auto;
  }

  .center-function-drop {
    justify-content: center;
    padding-left: 0;
  }

  .mesh-background {
    position: absolute;
    opacity: 0.8;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    border-radius: 5px;
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
    position: absolute;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    z-index: 0;
    display: flex;
    align-items: center;
    padding-left: 33px;
    opacity: 0.2;
    justify-content: flex-start;
  }

  /* Medium Devices, Desktops */
  @media only screen and (max-width : 992px) {

  }

  /* Small Devices */
  @media only screen and (max-width : 667px) {

  }

  /* Extra Small Devices, Phones */
  @media only screen and (max-width : 480px) {

  }

  /* Custom, iPhone 5 Retina */
  @media only screen and (max-width : 320px) {

  }

  /* iPad */
  @media all and (device-width: 768px) and (device-height: 1024px) and (orientation:portrait) {

  }
  @media all and (device-width: 768px) and (device-height: 1024px) and (orientation:landscape) {

  }

</style>
