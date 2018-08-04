<template>
  <div class="commands" v-if="commands !== null && activeFunctions !== null && !congratsShowing && !tryAgainShowing">
    <popover-bucket
      v-if="commandEvt !== null"
      :evt="evt"
    ></popover-bucket>

    <div class="lambdas-container">
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

      <div class="functions-container">
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
            @click.native="editFunction($event, func, ind)"
          ></function-box>
        </draggable>
      </div>
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
import FunctionBox from './Function_box'
import PopoverBucket from './Popover_bucket'
import uId from 'uid'
import buildUtils from '../services/BuildFunction'
import PuzzlePieces from './Puzzle_pieces'

export default {
  name: 'FunctionDrop',
  mounted () {
  },
  computed: {
    congratsShowing () {
      return this.$store.getters.getCongratsShowing
    },
    tryAgainShowing () {
      return this.$store.getters.getTryAgainShowing
    },
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
        chosenClass: 'chosen',
        ghostClass: 'ghost',
        sort: false
      },
      functionOptions: {
        group: {
          name: 'commands-slide',
          pull: 'clone',
          put: ['commands-staged']
        },
        animation: 100,
        filter: '.command-name',
        chosenClass: 'chosen',
        ghostClass: 'ghost',
        sort: false
      },
      currentColor: this.colorSelected
    }
  },
  methods: {
    uid: int => uId(int),
    notEditableMessage () {
      const messageBuilder = {
        type: 'warn',
        msg: 'Can\'t edit'
      }

      this.$store.dispatch('addMessage', messageBuilder)
    },
    editingFunctionMessage (func) {
      const messageBuilder = {
        type: 'success',
        msg: `${func.name ? `Edit: ${func.name}` : 'Edit: Function'}`
      }
      this.$store.dispatch('addMessage', messageBuilder)
    },
    findColor () {
      return this.colors[this.colorSelected].next
    },
    applyColorConditional () {
      const color = this.findColor()
      this.$store.dispatch('colorSelected', color)
    },
    toggleEditFunction (ind) {
      this.$store.dispatch('updateEditingIndex', ind)
      this.$store.dispatch('updateFunctionAreaShowing', ind === null ? 'editMain' : 'editFunction')
    },
    toggleFunctionAdd (evt) {
      this.commandEvt = evt
      this.$store.dispatch('updateEditingIndex', null)
      this.$store.dispatch('updateFunctionAreaShowing', this.functionAreaShowing === 'addFunction' ? 'editMain' : 'addFunction')
    },
    editFunction (evt, func, ind) {
      this.commandEvt = evt
      const i = ind === this.editingIndex ? null : ind
      if (i !== null) this.editingFunctionMessage(func)
      this.toggleEditFunction(i)
    },
    start () {
      if (this.functionAreaShowing === 'editMain') {
        this.$store.dispatch('toggleShowMesh', true)
      }
    },
    end () {
      this.$store.dispatch('toggleShowMesh', false)
    },
    moveFunction (evt) {
      if (evt.moved) {
        buildUtils.moveFunction({
          oldIndex: evt.moved.oldIndex,
          newIndex: evt.moved.newIndex
        })
      }
    },
    addToActiveFunc (evt) {
      buildUtils.activateFunction({
        stagedIndex: evt.oldIndex,
        activeIndex: evt.newIndex
      })
    }
  },
  components: {
    draggable,
    FunctionBox,
    PopoverBucket,
    PuzzlePieces
  }
}
</script>

<style scoped lang="scss">
  $click-color: #B8E986;
  $functions-padding-left: 30px;
  $functions-padding-right: 75%;

  .invisible {
    visibility: hidden;
  }

  .commands {
    display: flex;
    align-items: center;
    flex-direction: row;
    width: 1000px;
    height: 100%;
    margin: 0 auto;
    position: relative;
    padding: 10px 0;
  }

  .lambdas-container {
    transition-duration: 300ms;
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: flex-start;
  }

  .lambdas-container > * {
    display: flex;
    padding: 0 3px 0 0;
    z-index: 100;
    justify-content: flex-start;
  }

  .command-border-info {
    border: 1px solid rgb(0, 0, 255)!important;
  }

  .methods {
    border: 1px solid $click-color;
    background-color: rgba(0, 0, 0, 0.6);
    height: 94px;
    border-radius: 3px;
    margin-left: -50px;
  }

  .methods > * {
    float: left;
  }

  .functions-container {
    overflow: auto;
    width: 100%;
    height: 100%;
    max-height: 140px;
    margin-left: 12px;

    .functions {
      display: flex;
      height: 100%;
      width: min-content;
      padding: 0 $functions-padding-right 0 $functions-padding-left;
    }
  }

  #commands-box {
    margin: 0;
  }

  #open-staged {
    position: absolute;
    right: -50px;
    top: 10px;
    z-index: 1000;
  }

  .rotate-to-x {
    -webkit-transform: rotate(45deg);
    transform:rotate(45deg);
  }

  .rotate-to-plus {
    -webkit-transform: rotate(0);
    transform:rotate(0);
  }

  /* ipad pro Portrait */
  @media only screen
  and (min-device-width: 1024px)
  and (max-device-width: 1366px)
  and (orientation: portrait)
  and (-webkit-min-device-pixel-ratio: 1.5) {
    $functions-padding-left: 0;
    $functions-padding-right: 0;
    $methods-height: 61px;
    $functions-height: 150px;
    $methods-margin-left: 0;
    $functions-margin-right: 35px;
    $open-staged-right: 0;
    $methods-margin-top: -3px;

    .commands {
      width: 100%;
    }

    .methods {
      margin-top: $methods-margin-top;
      margin-left: $methods-margin-left;
      padding: 3px;
      height: $methods-height;
    }

    .functions-container {
      min-height: $functions-height;
      height: $functions-height;
      margin-left: 5px;
      margin-right: $functions-margin-right;
      .functions {
        padding: 0 $functions-padding-right 0 $functions-padding-left;
      }
    }

    #open-staged {
      right: $open-staged-right;
    }
  }

  @media only screen
  and (min-device-width : 768px)
  and (max-device-width : 1024px)
  and (orientation : landscape) {
    $functions-padding-left: 0;
    $functions-padding-right: 0;
    $methods-height: 61px;
    $functions-height: 150px;
    $methods-margin-left: 150px;
    $functions-margin-right: 190px;
    $open-staged-right: 150px;
    $methods-margin-top: -4px;

    .commands {
      width: 100%;
    }

    .methods {
      margin-top: $methods-margin-top;
      margin-left: $methods-margin-left;
      padding: 3px;
      height: $methods-height;
    }

    .functions-container {
      min-height: $functions-height;
      height: $functions-height;
      margin-left: 5px;
      margin-right: $functions-margin-right;
      .functions {
        padding: 0 $functions-padding-right 0 $functions-padding-left;
      }
    }

    #open-staged {
      right: $open-staged-right;
    }
  }

  @media only screen
  and (min-device-width : 768px)
  and (max-device-width : 1024px)
  and (orientation : portrait) {
    $functions-padding-left: 0;
    $functions-padding-right: 0;
    $methods-height: 61px;
    $functions-height: 150px;
    $methods-margin-left: 0;
    $functions-margin-right: 35px;
    $open-staged-right: 0;
    $methods-margin-top: -3px;

    .commands {
      width: 100%;
    }

    .methods {
      margin-top: $methods-margin-top;
      margin-left: $methods-margin-left;
      padding: 3px;
      height: $methods-height;
    }

    .functions-container {
      min-height: $functions-height;
      height: $functions-height;
      margin-left: 5px;
      margin-right: $functions-margin-right;
      .functions {
        padding: 0 $functions-padding-right 0 $functions-padding-left;
      }
    }

    #open-staged {
      right: $open-staged-right;
    }
  }

  @media only screen and (max-width: 823px) and (orientation: landscape) {
    $functions-padding-left: 0;
    $functions-padding-right: 0;
    $methods-height: 48px;
    $functions-height: 65px;
    $methods-margin-left: 10px;
    $functions-margin-right: 40px;
    $open-staged-right: 10px;
    $methods-margin-top: -4px;

    .commands {
      width: 100%;
    }

    .methods {
      margin-top: $methods-margin-top;
      margin-left: $methods-margin-left;
      padding: 3px;
      height: $methods-height;
    }

    .functions-container {
      min-height: $functions-height;
      height: $functions-height;
      margin-left: 5px;
      margin-right: $functions-margin-right;
      .functions {
        padding: 0 $functions-padding-right 0 $functions-padding-left;
      }
    }

    #open-staged {
      right: $open-staged-right;
    }
  }
  @media only screen and (max-width : 736px) and (orientation: landscape) {
    $functions-padding-left: 0;
    $functions-padding-right: 0;
    $methods-height: 48px;
    $functions-height: 65px;
    $methods-margin-left: 10px;
    $functions-margin-right: 40px;
    $open-staged-right: 10px;
    $methods-margin-top: -4px;

    .commands {
      width: 100%;
    }

    .methods {
      margin-top: $methods-margin-top;
      margin-left: $methods-margin-left;
      padding: 3px;
      height: $methods-height;
    }

    .functions-container {
      min-height: $functions-height;
      height: $functions-height;
      margin-left: 5px;
      margin-right: $functions-margin-right;
      .functions {
        padding: 0 $functions-padding-right 0 $functions-padding-left;
      }
    }

    #open-staged {
      right: $open-staged-right;
    }
  }

  /* Small Devices */
  @media only screen and (max-width : 667px) and (orientation: landscape) {
    $functions-padding-left: 0;
    $functions-padding-right: 0;
    $methods-height: 43px;
    $functions-height: 60px;
    $methods-margin-left: 10px;
    $functions-margin-right: 40px;
    $open-staged-right: 10px;
    $methods-margin-top: -4px;

    .commands {
      width: 100%;
    }

    .methods {
      margin-top: $methods-margin-top;
      margin-left: $methods-margin-left;
      padding: 3px;
      height: $methods-height;
    }

    .functions-container {
      min-height: $functions-height;
      height: $functions-height;
      margin-left: 5px;
      margin-right: $functions-margin-right;
      .functions {
        padding: 0 $functions-padding-right 0 $functions-padding-left;
      }
    }

    #open-staged {
      right: $open-staged-right;
    }
  }

  /* iphone 5 landscape */
  @media only screen and (max-width : 568px) and (orientation: landscape){
    $functions-padding-left: 0;
    $functions-padding-right: 0;
    $methods-height: 43px;
    $functions-height: 60px;
    $methods-margin-left: 10px;
    $functions-margin-right: 40px;
    $open-staged-right: 10px;
    $methods-margin-top: -4px;

    .commands {
      width: 100%;
    }

    .methods {
      margin-top: $methods-margin-top;
      margin-left: $methods-margin-left;
      padding: 3px;
      height: $methods-height;
    }

    .functions-container {
      min-height: $functions-height;
      height: $functions-height;
      margin-left: 5px;
      margin-right: $functions-margin-right;
      .functions {
        padding: 0 $functions-padding-right 0 $functions-padding-left;
      }
    }

    #open-staged {
      right: $open-staged-right;
    }
  }

  @media only screen and (max-width: 414px) {
    $functions-padding-left: 0;
    $functions-padding-right: 0;
    $methods-height: 43px;
    $functions-height: 69px;
    $methods-margin-left: 5px;
    $functions-margin-right: 30px;
    $open-staged-right: 5px;
    $methods-margin-top: -4px;

    .commands {
      width: 100%;
    }

    .methods {
      margin-top: $methods-margin-top;
      margin-left: $methods-margin-left;
      padding: 3px;
      height: $methods-height;
    }

    .functions-container {
      min-height: $functions-height;
      height: $functions-height;
      margin-left: 5px;
      margin-right: $functions-margin-right;
      .functions {
        padding: 0 $functions-padding-right 0 $functions-padding-left;
      }
    }

    #open-staged {
      right: $open-staged-right;
    }
  }

  /* iphone 6/7 portrait */
  @media only screen and (max-width: 375px) {
    $functions-padding-left: 0;
    $functions-padding-right: 0;
    $methods-height: 43px;
    $functions-height: 69px;
    $methods-margin-left: 5px;
    $functions-margin-right: 30px;
    $open-staged-right: 5px;
    $methods-margin-top: -4px;

    .commands {
      width: 100%;
    }

    .methods {
      margin-top: $methods-margin-top;
      margin-left: $methods-margin-left;
      padding: 3px;
      height: $methods-height;
    }

    .functions-container {
      min-height: $functions-height;
      height: $functions-height;
      margin-left: 5px;
      margin-right: $functions-margin-right;
      .functions {
        padding: 0 $functions-padding-right 0 $functions-padding-left;
      }
    }

    #open-staged {
      right: $open-staged-right;
    }
  }
  /* iphone 5 portrait */
  @media only screen and (max-width : 320px) {
    $functions-padding-left: 0;
    $functions-padding-right: 0;
    $methods-height: 43px;
    $functions-height: 69px;
    $methods-margin-left: 5px;
    $functions-margin-right: 30px;
    $open-staged-right: 5px;
    $methods-margin-top: -4px;

    .commands {
      width: 100%;
    }

    .methods {
      margin-top: $methods-margin-top;
      margin-left: $methods-margin-left;
      padding: 3px;
      height: $methods-height;
    }

    .functions-container {
      min-height: $functions-height;
      height: $functions-height;
      margin-left: 5px;
      margin-right: $functions-margin-right;
      .functions {
        padding: 0 $functions-padding-right 0 $functions-padding-left;
      }
    }

    #open-staged {
      right: $open-staged-right;
    }
  }
</style>
