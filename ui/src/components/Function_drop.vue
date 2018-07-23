<template>
  <swiper
    :id="id"
    :class="[`${origin}-function-drop-swiper`]"
    :options="swiper.options">
    <swiper-slide
      v-for="(group, gInd) in functionGroups"
      :key="'function-drop-swiper/' + gInd">
      <div v-if="showMesh" class="mesh-background"></div>
      <draggable
        class="function-drop"
        :class="[(functionGroups.length <= 1 ? 'center-function-drop' : ''), className]"
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

      <div
        class="drop-placeholder"
        :class="groupedPlaceholders.length <= 1 ? 'center-function-drop' : ''"
        v-if="sizeLimit < 10000"
      >
        <function-box
          v-for="(placeHolder, pInd) in groupedPlaceholders[gInd]"
          :key="origin + '-placeholder/' + pInd"
          :func="placeHolder"
          :ind="placeHolder.index"
          :collection="placeHolders"
          :origin="origin"
        ></function-box>
      </div>
    </swiper-slide>
    <div class="swiper-button swiper-button-prev" slot="button-prev">
      <function-box
        v-if="prevLastFunc"
        :func="prevLastFunc"
        :ind="prevLastFunc.index"
        :collection="list"
        :origin="origin"
      ></function-box>
    </div>
    <div class="swiper-button swiper-button-next" slot="button-next">
      <function-box
        v-if="nextFirstFunc"
        :func="nextFirstFunc"
        :ind="nextFirstFunc.index"
        :collection="list"
        :origin="origin"
      ></function-box>
    </div>
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
    this.swiperMethods = document.querySelector(`.${this.origin}-function-drop-swiper`).swiper
  },
  computed: {
    nextButton () {
      return {
        name: '',
        image: this.nextFirstImage,
        color: 'default'
      }
    },
    prevButton () {
      return {
        name: '',
        image: this.prevLastImage,
        color: 'default'
      }
    },
    nextFirstFunc () {
      const nextInd = this.currentSwiperInd + 1

      if (this.functionGroups.length > nextInd) {
        return this.functionGroups[nextInd][0]
      }
    },
    prevLastFunc () {
      const prevInd = this.currentSwiperInd - 1

      if (prevInd >= 0) {
        return this.functionGroups[prevInd][this.functionGroups[prevInd].length - 1]
      }
    },
    currentSwiperInd () {
      return this.swiperMethods.realIndex
    },
    groupedPlaceholders () {
      return this.swiper.groupFunctions(this.placeHolders.slice(), this.groupSize)
    },
    placeHolders () {
      if (this.sizeLimit < 10000 && this.sizeLimit > 0) {
        return this.createPlaceHolders(this.sizeLimit)
      } else {
        return []
      }
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
      swiperMethods: {},
      swiper: Swiper
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
  props: ['id', 'className', 'origin', 'list', 'options', 'change', 'start', 'end', 'groupSize', 'sizeLimit']
}
</script>

<style scoped lang="scss">
  $function-drop-padding-left: 33px;

  .function-drop-swiper {
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
    padding-left: $function-drop-padding-left;
    margin: 0 auto;
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
    padding-left: $function-drop-padding-left;
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
