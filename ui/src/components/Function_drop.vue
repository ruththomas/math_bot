<template>
  <div></div>
  <!--<swiper-->
    <!--:class="showMesh ? 'mesh-background' : ''"-->
    <!--:options="functionDropSwiperOptions">-->
    <!--<swiper-slide-->
      <!--v-for="(func, ind) in list"-->
      <!--:key="'function-drop-swiper/' + ind">-->
      <!--<draggable-->
        <!--class="function-drop-drop-zone"-->
        <!--:list="list"-->
        <!--:options="options"-->
        <!--@change="change"-->
        <!--@start="start"-->
        <!--@end="end"-->
      <!--&gt;-->
          <!--<function-box-->
            <!--:func="func"-->
            <!--:ind="ind"-->
            <!--:collection="list"-->
            <!--:origin="origin"-->
          <!--&gt;</function-box>-->

      <!--</draggable>-->
    <!--</swiper-slide>-->
  <!--</swiper>-->
</template>

<script>
import draggable from 'vuedraggable'
import FunctionBox from './Function_box'
import Swiper from '../services/Swiper'

export default {
  name: 'function_drop',
  computed: {
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
      functionDropSwiperOptions: new Swiper(this.list).options
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
    width: 100%;
    height: 100%;
    z-index: 2001;
    display: flex;
    align-items: center;
    justify-content: center;
    margin: 0 auto;
  }

  .function-drop-drop-zone {
    display: flex;
    align-items: center;
    flex-wrap: nowrap;
    overflow-x: scroll;
    overflow-y: visible;
    height: 100%;
    width: 99%;
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
