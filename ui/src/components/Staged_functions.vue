<template>
  <div class="staged-functions-container">
    <div class="staged-functions-header">
      <span>Staged functions</span>
    </div>
    <swiper class="staged-functions-swiper" :options="swiper.options">
      <swiper-slide
        v-for="(group, gInd) in functionGroups"
        :key="'staged-swiper/' + gInd"
      >
        <draggable
          class="staged-functions"
          :staged-group-ind="gInd"
          :list="stagedFunctions"
          :options="draggableOptions"
        >
          <function-box
            v-for="(func, fInd) in group"
            :key="'staged-func/' + fInd"
            :func="func"
            :ind="func.index"
            :collection="stagedFunctions"
            :origin="'stagedFunctions'"
            :data-function-index="func.index"
          ></function-box>
        </draggable>
      </swiper-slide>
      <div class="swiper-button-prev" slot="button-prev">
        <puzzle-pieces
          :piece-to-show="'closed'"
          :func="prevButton"
        ></puzzle-pieces>
      </div>
      <div class="swiper-button-next" slot="button-next">
        <puzzle-pieces
          :piece-to-show="'closed'"
          :func="nextButton"
        ></puzzle-pieces>
      </div>
      <div class="swiper-pagination swiper-pagination-bullets" :class="functionGroups.length < 2 ? 'hidden-swiper-pagination' : ''" slot="pagination"></div>
    </swiper>
  </div>
</template>

<script>
import FunctionBox from './Function_box'
import draggable from 'vuedraggable'
import Swiper from '../services/Swiper'
import PuzzlePieces from './Puzzle_pieces'

export default {
  mounted () {
    this.swiperMethods = document.querySelector('.staged-functions-swiper').swiper
    this.groupSize = this.swiper.calculateGroupSize('staged-functions')
    window.addEventListener('resize', () => {
      this.groupSize = this.swiper.calculateGroupSize('staged-functions')
    })
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
    nextFirstImage () {
      const nextInd = this.currentSwiperInd + 1

      if (this.functionGroups.length > nextInd) {
        return this.functionGroups[nextInd][0].image
      }
    },
    prevLastImage () {
      const prevInd = this.currentSwiperInd - 1

      if (prevInd >= 0) {
        return this.functionGroups[prevInd][this.functionGroups[prevInd].length - 1].image
      }
    },
    currentSwiperInd () {
      return this.swiperMethods.realIndex
    },
    functionGroups () {
      return this.swiper.groupFunctions(this.stagedFunctions.slice(), this.groupSize)
    },
    stagedFunctions () {
      return this.$store.getters.getStagedFunctions
    },
    permanentImages () {
      return this.$store.getters.getPermanentImages
    },
    currentFunction () {
      return this.$store.getters.getCurrentFunction
    },
    editFunctionShowing () {
      return this.$store.getters.getEditFunctionShowing
    }
  },
  data () {
    return {
      clicks: 0,
      timer: null,
      draggableOptions: {
        group: {
          name: 'commands-staged',
          pull: true,
          put: false,
          revertClone: true
        },
        animation: 100,
        dragClass: 'dragging',
        ghostClass: 'ghost',
        chosenClass: 'chosen',
        sort: false
      },
      swiperMethods: {},
      swiper: Swiper,
      groupSize: 14
    }
  },
  components: {
    draggable,
    FunctionBox,
    PuzzlePieces
  }
}
</script>

<style scoped lang="scss">
  $staged-functions-padding-left: 32px;

  .staged-functions-container {
    position: relative;
    height: 100%;
    width: 100%;
  }

  .staged-functions-header {
    position: absolute;
    top: 10px;
    left: $staged-functions-padding-left;
    span {
      font-size: 18px;
    }
  }

  .staged-functions {
    height: 100%;
    width: 100%;
    overflow: auto;
    display: flex;
    padding-left: $staged-functions-padding-left;
    justify-content: flex-start;
    align-items: center;
  }

  /* Medium Devices, Desktops */
  @media only screen and (max-width : 992px) {
  }

  /* Small Devices */
  @media only screen and (max-width : 667px) {

  }

  /* iphone 5 landscape */
  @media only screen and (max-width : 568px) {
    $staged-functions-padding-left: 14px;

    .staged-functions {
      padding-left: $staged-functions-padding-left;
    }

    .staged-functions-header {
      left: $staged-functions-padding-left;

      span {
        font-size: 12px;
      }
    }
  }

  /* iphone 5 portrait */
  @media only screen and (max-width : 320px) {
    $staged-functions-padding-left: 15.5px;

    .staged-functions {
      padding-left: $staged-functions-padding-left;
    }

    .staged-functions-header {
      left: $staged-functions-padding-left;

      span {
        font-size: 12px;
      }
    }
  }

  /* iPad */
  @media all and (device-width: 768px) and (device-height: 1024px) and (orientation:portrait) {
  }

  @media all and (device-width: 768px) and (device-height: 1024px) and (orientation:landscape) {
  }
</style>
