<template>
  <swiper :options="swiper.options">
    <swiper-slide
      v-for="(group, gInd) in functionGroups"
      :key="'staged-swiper/' + gInd"
      :dude="logGroup(group)"
    >
      <draggable
        class="staged-functions"
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
        <!--:method="addToActiveFunc"-->
      </draggable>
    </swiper-slide>
    <div class="swiper-button-prev" slot="button-prev"></div>
    <div class="swiper-button-next" slot="button-next"></div>
    <div class="swiper-pagination swiper-pagination-bullets" slot="pagination"></div>
  </swiper>
</template>

<script>
import FunctionBox from './Function_box'
import draggable from 'vuedraggable'
import Swiper from '../services/Swiper'

export default {
  computed: {
    functionGroups () {
      return this.swiper.groupFunctions(this.stagedFunctions.slice(), 14)
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
      swiper: new Swiper()
    }
  },
  methods: {
    logGroup (group) {
      // console.log(group)
    }
  },
  components: {
    draggable,
    FunctionBox
  }
}
</script>

<style scoped lang="scss">
  .staged-functions {
    height: 100%;
    width: 100%;
    overflow: auto;
    display: flex;
    flex-wrap: wrap;
    padding: 10px 20px 10px 20px;
    justify-content: center;
    align-items: center;
  }
</style>
