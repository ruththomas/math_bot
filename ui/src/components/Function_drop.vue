<template>
  <swiper
    :id="id"
    class="function-drop-container"
    :options="swiper.options">
    <div v-if="showMesh" class="mesh-background"></div>
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
    position: absolute;
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
