<template>
  <div class="commands" v-if="commands !== null && activeFunctions !== null">

    <popover-bucket
      v-if="commandEvt !== null"
      :evt="evt"
    ></popover-bucket>

    <div class="command-control-button-group">
      <img class="command-button commands-up dialog-button" @click="moveSwiper('up')" :src="permanentImages.buttons.playButton">
      <img class="command-button commands-down dialog-button" @click="moveSwiper('down')" :src="permanentImages.buttons.playButton">
    </div>

    <div class="commands-slide">
      <draggable
        class="methods"
        :list="commands"
        :options="commandOptions"
        @start="start"
        @end="end"
      >
        <function-box
          v-for="(command, ind) in commands"
          :key="command.id"
          :func="command"
          :ind="ind"
          :collection="commands"
          :origin="'functions'"
          v-on:click.native="notEditableMessage"
        ></function-box>
      </draggable>
      <draggable
        class="functions"
        :list="activeFunctions"
        :options="functionOptions"
        @start="start"
        @change="moveFunction"
        @end="end"
        @add="addToActiveFunc"
      >
        <function-box
          v-for="(func, ind) in activeFunctions"
          :key="ind + '/' + func.created_id"
          :func="func"
          :ind="ind"
          :collection="activeFunctions"
          :origin="'functions'"
          :method="toggleFunctionEdit"
          v-on:click.native="editingFunctionMessage(func)"
        ></function-box>
      </draggable>
    </div>

    <img
      id="open-staged"
      class="dialog-button"
      v-if="this.stepData.stagedEnabled"
      :class="functionAreaShowing === 'addFunction' ? 'rotate-to-x' : 'rotate-to-plus'"
      @click="toggleFunctionAdd"
      :src="permanentImages.buttons.plusButton"
      data-toggle="tooltip" :title="functionAreaShowing === 'addFunction' ? 'Close' : 'Open'" />
  </div>
</template>

<script>
import draggable from 'vuedraggable'
import api from '../services/api'
import FunctionBox from './Function_box'
import PopoverBucket from './Popover_bucket'
import uId from 'uid'

export default {
  name: 'FunctionDrop',
  mounted () {
    window.addEventListener('resize', () => {
      if (window.location.hash === '#/robot') {
        this.functionsPosition = 0
        this.moveSwiper('up')
      }
    })
  },
  computed: {
    evt () {
      return this.commandEvt
    },
    mainFunctionFunc () {
      return this.$store.getters.getMainFunction.func
    },
    stepData () {
      return this.$store.getters.getStepData
    },
    currentStepData () {
      return this.$store.getters.getStepData
    },
    token () {
      return this.$store.getters.getToken
    },
    editingIndex () {
      return this.$store.getters.getEditingIndex
    },
    editingFunction () {
      return this.$store.getters.getActiveFunctions[this.editingIndex]
    },
    functionAreaShowing () {
      return this.$store.getters.getFunctionAreaShowing
    },
    commands () {
      return this.$store.getters.getCommands
    },
    activeFunctions () {
      return this.$store.getters.getActiveFunctions
    },
    colorSelected () {
      return this.$store.getters.getColorSelected
    },
    permanentImages () {
      return this.$store.getters.getPermanentImages
    },
    cmdImages () {
      return this.permanentImages.cmdImages
    },
    colorPallet () {
      return this.permanentImages.colorPallet
    },
    colors () {
      return this.$store.getters.getColors
    },
    funcImages () {
      return this.permanentImages.funcImages
    },
    swiperSlide () {
      return this.$store.getters.getSwiperSlide
    }
  },
  data () {
    return {
      commandEvt: null,
      functionsPosition: 0,
      commandOptions: {
        group: {
          name: 'commands-slide',
          pull: 'clone',
          put: false
        },
        filter: '.command-name',
        dragClass: 'dragging',
        ghostClass: 'ghost',
        sort: false
      },
      functionOptions: {
        group: {
          name: 'commands-slide',
          pull: 'clone',
          put: ['commands-staged']
        },
        filter: '.command-name',
        dragClass: 'dragging',
        ghostClass: 'ghost'
      },
      currentColor: this.colorSelected
    }
  },
  methods: {
    uid: int => uId(int),
    notEditableMessage (evt) {
      const messageBuilder = {
        type: 'warn',
        msg: 'Can\'t edit'
      }

      this.$store.dispatch('addMessage', messageBuilder)
    },
    editingFunctionMessage (func) {
      if (this.editingFunction) {
        const messageBuilder = {
          type: 'success',
          msg: `${func.name ? `Edit: ${func.name}` : 'Edit: Function'}`
        }
        this.$store.dispatch('addMessage', messageBuilder)
      }
    },
    findColor () {
      return this.colors[this.colorSelected].next
    },
    applyColorConditional () {
      const color = this.findColor()
      this.$store.dispatch('colorSelected', color)
    },
    togglePopoverBucket ({ind, show}) {
      this.$store.dispatch('updateEditingIndex', ind)
      this.$store.dispatch('updateFunctionAreaShowing', show)
    },
    toggleFunctionEdit (evt, _2, ind) {
      this.commandEvt = evt
      const i = ind === this.editingIndex ? null : ind
      const show = i === null ? 'editMain' : 'editFunction'
      this.togglePopoverBucket({ind: i, show: show})
    },
    toggleFunctionAdd (evt) {
      this.commandEvt = evt
      this.togglePopoverBucket({ind: null, show: this.functionAreaShowing === 'addFunction' ? 'editMain' : 'addFunction'})
    },
    closeFunctionBox () {
      this.commandEvt = null
      this.togglePopoverBucket({ind: null, show: 'editMain'})
    },
    start () {
      if (this.functionAreaShowing === 'editMain') {
        this.$store.dispatch('toggleShowMesh', true)
        // If main is full apply message letting the user know
        if (this.mainFunctionFunc.length >= this.stepData.mainMax) {
          const messageBuilder = {
            type: 'warn',
            msg: `Main full`
          }
          this.$store.dispatch('addMessage', messageBuilder)
        }
      }
    },
    end () {
      this.$store.dispatch('toggleShowMesh', false)
    },
    moveFunction (evt) {
      if (evt.moved) {
        api.updateActives({tokenId: this.token.token_id, actives: this.activeFunctions}, actives => {
          this.$store.dispatch('updateActives', actives)
        })
      }
    },
    addToActiveFunc (evt) {
      const index = evt.item.getAttribute('data-function-index')
      // console.log('INDEX IN ~ ', index);

      // if (evt.added) {
      api.activateFunction({tokenId: this.token.token_id, stagedIndex: index, activeIndex: evt.newIndex}, lambdas => {
        // console.log('NEW LAMBDAS ~ ', lambdas)
        this.$store.dispatch('updateLambdas', lambdas)
      })
      // }
    },
    moveSwiper (direction) {
      const $functions = $('.functions')
      const $functionBoxes = $functions.children()
      if ($functionBoxes.length) {
        (function (windowWidth, dis) {
          const functionsWidth = $functions.width()
          const $firstFunctionBox = $functionBoxes.first()
          const functionBoxMarginRight = Number($firstFunctionBox.css('margin-right').replace('px', ''))
          const functionBoxMarginBottom = Number($firstFunctionBox.css('margin-bottom').replace('px', ''))
          const functionBoxWidth = $firstFunctionBox.outerWidth() + (functionBoxMarginRight * 2)
          const functionBoxHeight = $firstFunctionBox.outerHeight() + (functionBoxMarginBottom)
          const amtPerRow = Math.floor(functionsWidth / functionBoxWidth)
          const rowCount = Math.ceil($functionBoxes.length / amtPerRow)
          const allRowsHeight = functionBoxHeight * rowCount
          const ableToScrollDown = (dis.functionsPosition + functionBoxHeight) < allRowsHeight

          if (direction === 'up' && dis.functionsPosition > 0) {
            dis.functionsPosition -= functionBoxHeight
          } else if (direction === 'down' && ableToScrollDown) {
            dis.functionsPosition += functionBoxHeight
          }

          $functions.animate({scrollTop: dis.functionsPosition + 'px'}, 300, 'linear')
        })($(window).width(), this)
      }
    }
  },
  components: {
    draggable,
    FunctionBox,
    PopoverBucket
  }
}
</script>

<style scoped lang="scss">
  .invisible {
    visibility: hidden;
  }

  .commands {
    display: flex;
    align-items: center;
    flex-direction: row;
    width: 100%;
    height: 105px;
    position: relative;
    padding: 10px 0;
  }

  .commands-slide {
    transition-duration: 300ms;
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: flex-start;
  }

  .commands-slide > * {
    display: flex;
    padding: 0 3px 0 0;
    height: 100%;
    z-index: 100;
    justify-content: flex-start;
  }

  .command-border-info {
    border: 1px solid rgb(0, 0, 255)!important;
  }

  .methods {
    border-right: 2px solid #B8E986;
  }

  .methods > * {
    float: left;
  }

  .functions {
    flex-wrap: wrap;
    overflow: hidden;
    flex-grow: 1;
  }

  .functions-show-overflow {
    overflow: visible;
  }

  .functions > * {
    margin-bottom: 20px!important;
  }

  .two-x-command-name {
    top: 40px;
    height: 25px;
  }

  .three-x-command-name {
    top: 46px;
    height: 37px;
  }

  .four-x-command-name {
    top: 52px;
    height: 49px;
  }

  .five-x-command-name {
    top: 58px;
    height: 60px;
  }

  .funcText {
    width: 94%;
    position: absolute;
    top: 1px;
    left: 0;
    right: 0;
    margin-left: auto;
    margin-right: auto;
  }

  .commandText {
    position: absolute;
    top: 1px;
    left: 0;
    right: 0;
    white-space: nowrap;
  }

  .command-control-button-group {
    height: 100%;
    position: relative;
    display: flex;
    justify-content: space-between;
    flex-direction: column;
    margin-right: 20px;
  }

  .command-control-button-group:after {
    content:"";
    position: absolute;
    z-index: -1;
    top: 0;
    bottom: 0;
    left: 50%;
    border-left: 2px solid #B8E986;
    transform: translate(-50%);
  }

  .command-control-button {
    cursor: pointer;
    height: 30px;
    width: 30px;
    right: -21px;
  }

  .command-button {
    max-height: 30px;
    max-width: 30px;
  }

  .commands-up {
    -webkit-transform: rotate(-90deg);
    transform:rotate(-90deg);
  }

  .commands-down {
    -webkit-transform: rotate(90deg);
    transform:rotate(90deg);
  }

  #commands-box {
    margin: 0;
  }

  #open-staged {
    display: flex;
    align-self: flex-start;
    cursor: pointer;
  }

  .rotate-to-x {
    -webkit-transform: rotate(45deg);
    transform:rotate(45deg);
  }

  .rotate-to-plus {
    -webkit-transform: rotate(0);
    transform:rotate(0);
  }

  /* Medium Devices, Desktops */
  @media only screen and (max-width : 992px) {
    .commands {
      margin-bottom: 5px;
      height: 55px;
    }

    .commands-slide {
      margin: 0 auto;
    }

    .commands-slide > * {
      /*height: 35px;*/
    }

    .command-control-button-group {
      margin-right: 10px;
    }

    .command-control-button {
      height: 15px;
      width: 15px;
    }
  }

  /* Small Devices */
  @media only screen and (max-width : 667px) {
    .commands {
      margin-bottom: 5px;
      height: 65px;
    }

    .commands-slide {
      margin: 0 auto;
    }

    .commands-slide > * {
      /*height: 35px;*/
    }

    .methods {
    }

    .command-control-button-group {
      height: 35px;
      margin-right: 10px;
    }

    .command-control-button {
      height: 15px;
      width: 15px;
    }
  }

  /* Extra Small Devices, Phones */
  @media only screen and (max-width : 480px) {

  }

  /* Custom, iPhone Retina */
  @media only screen and (max-width : 320px) {

  }

  /* iPad */
  @media all and (device-width: 768px) and (device-height: 1024px) and (orientation:portrait) {
    .commands {
      height: 115px;
    }
  }

  @media all and (device-width: 768px) and (device-height: 1024px) and (orientation:landscape) {
    .commands {
      height: 115px;
    }
  }

  ::-webkit-scrollbar {
    display: none;
  }

</style>
