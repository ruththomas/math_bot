<template>
  <div class="control-bar bar noDrag" v-if="Object.keys(levelControl.robot).length">
    <mascot
      :id="'main-delete-function'"
      @click.native="animateVulnerable"
      :color="levelControl.runCompiled.failure ? '#F25C5C' : '#ffffff'"
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
$mascot-size: 9vmin;
$bar-height: 1px;
$bar-color: #B8E986;
$padding-left: calc(#{$mascot-size} / 1.2);
$danger-color: #F25C5C;
$dialog-button-size: 3.5vmin;
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
    right: calc(100% + calc(#{$mascot-size} / 2));
    transform: translateY(calc(-15% - #{$bar-height}));
    width: $mascot-size;
    height: auto;
    z-index: 100;
    cursor: pointer;
  }

  .plugin {
    background-color: $bar-color;
    position: absolute;
    height: $bar-height;
    right: 100%;
    width: calc(#{$mascot-size} / 1.1);
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

.trash-confirm {
  background-color: $danger-color;
  box-shadow: 0 2px 10px 0 $danger-color;
  animation: shake 0.8s;
  animation-iteration-count: infinite;
  margin: 1vmin;
}

.close-popover {
  float: right;
  display: flex;
  position: absolute;
  bottom: calc(100% - #{$dialog-button-size} / 2);
  right:  calc(#{-$dialog-button-size} / 2);
  z-index: 10001;
  cursor: pointer;
}

.deactivate-edit-main {
  opacity: 0;
}
</style>
