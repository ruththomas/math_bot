<template>
  <div class="main-placeholder-container" v-if="stepData.mainMax !== 10000">
    <div id="main-placeholder">
      <div
        v-for="(_, ind) in placeholders"
        :key="'placeholder/' + ind"
        class="placeholder placeholder-short-background"
        :class="placeholders.length === 1 ? 'single-placeholder' : ''"
      ></div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'main_placeholder',
  mounted () {
    this.placeHolderWidth()
  },
  computed: {
    mainFunctionFunc () {
      const mainToken = this.$store.getters.getMainFunction
      return mainToken === null ? [] : mainToken.func
    },
    mainFull () {
      return this.mainFunctionFunc.length === this.stepData.mainMax
    },
    placeholders () {
      return new Array(this.stepData.mainMax)
    },
    stepData () {
      return this.$store.getters.getStepData
    }
  },
  watch: {
    mainFull (bool) {
      const $placeHolder = $('.placeholder')
      if (bool) this.placeholderFull($placeHolder)
      else this.placeholderShort($placeHolder)
    }
  },
  data () {
    return {
      list: []
    }
  },
  methods: {
    placeHolderWidth () {
      const $functionDrop = $('.function-drop-drop-zone')
      const dropWidth = $functionDrop.width()
      $('.main-placeholder-container').css({width: `${dropWidth}px`})
    },
    placeholderFull ($placeholder) {
      const messageBuilder = {
        type: 'success',
        msg: 'Press play',
        handlers () {
          const $play = $('.play')

          return {
            runBeforeAppend () {
              $play.addClass('pulse')
            },
            runOnDelete () {
              $play.removeClass('pulse')
            }
          }
        }
      }

      this.$store.dispatch('addMessage', messageBuilder)

      $placeholder
        .addClass('placeholder-full-background')
    },
    placeholderShort ($placeholder) {
      $placeholder
        .removeClass('placeholder-full-background')
    }
  }
}
</script>

<style scoped lang="scss">
  .main-placeholder-container {
    display: flex;
    justify-content: center;
    position: relative;
    overflow: hidden;
  }

  #main-placeholder {
    display: flex;
    overflow: hidden;
  }

  .placeholder-short-background {
    background-color: rgba(184, 233, 134, 0.3);
  }

  .placeholder-info-border {
    border: 1px solid rgb(0, 0, 255);
  }

  .placeholder-full-background {
    background-color: rgba(184, 233, 134, 0.9);
  }

  .placeholder {
    height: 82px;
    width: 78px;
  }

  .single-placeholder {
    width: 78px;
  }

  /* Large Phones, landscape*/
  @media only screen and (max-width : 992px) {
    .main-placeholder-container {
    }

    .placeholder {
      height: 36px;
      width: 29px;
    }

    .single-placeholder {
      width: 34px;
    }
  }

  /* Small Devices */
  @media only screen and (max-width : 667px) {
    .main-placeholder-container {
    }

    .placeholder {
      height: 34px;
      width: 29px;
    }

    .single-placeholder {
      width: 34px;
    }
  }

  /* Extra Small Devices, Phones */
  @media only screen and (max-width : 480px) {
  }

  /* Custom, iPhone 5 Retina */
  @media only screen and (max-width : 320px) {

  }

  /* iPad */
  @media all and (device-width: 768px) and (device-height: 1024px) and (orientation:portrait) {
    .main-placeholder-container {
    }

    .placeholder {
      height: 82px;
      width: 78px;
    }

    .single-placeholder {
      width: 78px;
    }
  }

  @media all and (device-width: 768px) and (device-height: 1024px) and (orientation:landscape) {
    .main-placeholder-container {
    }

    .placeholder {
      height: 82px;
      width: 78px;
    }

    .single-placeholder {
      width: 78px;
    }
  }

</style>
