<template>
  <div class="commands" v-if="commands !== null && activeFunctions !== null">
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
        sort: true
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
    width: 100%;
    height: 100%;
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
    height: 100%;
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
    margin-left: -20px;
  }

  .methods > * {
    float: left;
  }

  .functions-container {
    overflow: auto;
    width: 100%;
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

  /* Medium Devices, Desktops */
  @media only screen and (max-width : 992px) {
  }

  /* Small Devices */
  @media only screen and (max-width : 667px) {

  }

  /* iphone 5 landscape */
  @media only screen and (max-width : 568px) {
    .commands {
      padding-right: 7px;
    }

    .lambdas-container {
    }

    .lambdas-container > * {
      /*height: 35px;*/
    }

    .methods {
      height: 37px;
      margin-right: 7px;
    }

    .functions {
      padding-left: 13px;
    }

    .command-control-button-group {
      height: 35px;
      margin-right: 10px;
    }

    .command-control-button {
      height: 15px;
      width: 15px;
    }

    #open-staged {
      right: -20px;
    }
  }

  /* iphone 5 portrait */
  @media only screen and (max-width : 320px) {
    .commands {
      padding-right: 8px;
    }

    .lambdas-container {
    }

    .lambdas-container > * {
      /*height: 35px;*/
    }

    .methods {
      height: 37px;
      margin-right: 9px;
    }

    .functions {
      padding-left: 15px;
    }

    .command-control-button-group {
      height: 35px;
      margin-right: 10px;
    }

    .command-control-button {
      height: 15px;
      width: 15px;
    }

    #open-staged {
      right: -20px;
    }
  }

  /* iPad */
  @media all and (device-width: 768px) and (device-height: 1024px) and (orientation:portrait) {
  }

  @media all and (device-width: 768px) and (device-height: 1024px) and (orientation:landscape) {
  }
</style>
