<template>
  <swiper
    :class="showMesh ? 'mesh-background' : ''"
    :options="swiper.options">
    <swiper-slide
      v-for="(group, sInd) in functionGroups"
      :key="'function-drop-swiper/' + sInd">
      <draggable
        class="function-drop"
        :list="list"
        :options="options"
        @change="change"
        @start="start"
        @end="end"
      >
        <function-box
          v-if="group !== null"
          v-for="(func, fInd) in group"
          :key="'staged-func/' + fInd"
          :func="func"
          :ind="func.index"
          :collection="list"
          :origin="origin"
        ></function-box>
      </draggable>
    </swiper-slide>
    <div class="swiper-button-prev" slot="button-prev"></div>
    <div class="swiper-button-next" slot="button-next"></div>
    <div class="swiper-pagination swiper-pagination-bullets" slot="pagination"></div>
  </swiper>
</template>

<script>
import draggable from 'vuedraggable'
import FunctionBox from './Function_box'
import Swiper from '../services/Swiper'

export default {
  name: 'function_drop',
  computed: {
    functionGroups () {
      const groups = this.swiper.groupFunctions(this.list.slice(), 14)
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
    adjustJustify () {
      const $dropZone = document.querySelector('.function-drop-drop-zone')
      if (this.list.length && this.functionAreaShowing === 'editMain' && $dropZone !== null) {
        const dropZoneWidth = $dropZone.offsetWidth
        const $lastButton = $dropZone.lastChild
        const lastButtonWidth = $lastButton.offsetWidth
        const lastButtonLeft = $lastButton.offsetLeft

        if ((dropZoneWidth + lastButtonWidth) < lastButtonLeft) {
          $dropZone.style['justify-content'] = 'flex-start'
        } else {
          $dropZone.style['justify-content'] = 'center'
        }
      } else {
        $dropZone.style['justify-content'] = 'center'
      }
    }
  },
  components: {
    draggable,
    FunctionBox
  },
  props: ['origin', 'list', 'options', 'change', 'start', 'end']
}
</script>

<style scoped lang="scss">
  .function-drop {
    /*border: 1px solid teal;*/
    width: 100%;
    height: 100%;
    z-index: 2001;
    display: flex;
    align-items: center;
    justify-content: flex-start;
    padding-left: 33px;
    margin: 0 auto;
  }

  .mesh-background {
    background: url("https://res.cloudinary.com/deqjemwcu/image/upload/v1522345998/misc/grid-mesh_auu3wh.svg") top center no-repeat;
    background-size: contain;
    background-position: 0 -10px;
    z-index: 1;
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
