<template>
  <div class="control-bar bar noDrag" v-if="Object.keys(levelControl.robot).length">
    <mascot
      :id="'main-delete-function'"
      @click.native="animateVulnerable"
    ></mascot>
    <div class="plugin"></div>
    <b-popover
      v-if="levelControl.functions.main.func.length"
      target="main-delete-function"
      placement="top"
      triggers="click"
    >
      <img class="dialog-button close-popover"
           :src="permanentImages.buttons.xButton"
           @click="[animateVulnerable(), closePopover('main-delete-function')]"
      />
      <div class="button-effect trash-confirm"  @click="[animateVulnerable(), wipeFunction(), closePopover('main-delete-function')]">
        <img class="dialog-button" :src="permanentImages.openTrashCan" />
      </div>
    </b-popover>
    <div class="end-cap"></div>
  </div>
</template>

<script>
import utils from '../services/utils'
import Mascot from './Mascot'
export default {
  name: 'Bar',
  computed: {
    levelControl () {
      return this.$store.getters.getLevelControl
    },
    permanentImages () {
      return this.$store.getters.getPermanentImages
    }
  },
  methods: {
    closePopover: utils.closePopover,
    wipeFunction () {
      this.levelControl.deleteMain()
      this.setPut(true)
    },
    animateVulnerable () {
      const $functions = $('.editMain-drop-zone > .piece:not(.placeholder-piece)')
      const animationClass = 'piece-shake'
      $functions.each(function () {
        const $ele = $(this)
        if ($ele.hasClass(animationClass)) {
          $ele.removeClass(animationClass)
        } else {
          $ele.addClass(animationClass)
        }
      })
    }
  },
  components: {
    Mascot
  },
  props: ['setPut']
}
</script>

<style scoped lang="scss">
$mascot-size: 12vmin;
$bar-height: 1px;
$bar-color: #B8E986;
$padding-left: calc(#{$mascot-size} / 1.2);
.bar {
  position: absolute;
  left: $mascot-size;
  right: $padding-left;
  top: calc(50%);
  height: $bar-height;
  background-color: $bar-color;
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: -1;

  .mascot {
    position: absolute;
    right: calc(100% + calc(#{$mascot-size} / 6));
    transform: translateY(calc(-15% - #{$bar-height}));
    width: $mascot-size;
    height: auto;
    z-index: 100;
  }

  .plugin {
    background-color: $bar-color;
    position: absolute;
    height: $bar-height;
    right: 100%;
    width: calc(#{$mascot-size} / 1.75);
    z-index: 101;
  }

  .end-cap {
    position: absolute;
    height: 3vmin;
    width: 1px;
    left: 100%;
    background-color: $bar-color;
  }
}
</style>
