<template>
  <div class="commands" v-if="commands !== null && activeFunctions !== null">
    <div class="lambdas-container">
      <div class="methods-container">
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
      </div>

      <div class="functions-container">
        <draggable
          class="functions"
          :list="activeFunctions"
          :options="functionOptions"
          @start="start"
          @change="moveFunction"
          @end="end"
        >
          <function-box
            v-for="(func, ind) in activeFunctions"
            :key="ind + '/' + func.created_id"
            :func="func"
            :ind="ind"
            :collection="activeFunctions"
            :origin="'functions'"
            :data-created-id="func.created_id"
            :data-index="ind"
            @click.native="editFunction($event, func, ind)"
          ></function-box>
        </draggable>
      </div>
    </div>

    <div
      id="open-staged"
      class="dialog-button"
      @click="toggleFunctionAdd"
      @mousedown="runCompiled.resetIfFailure"
    >
    </div>
  </div>
</template>

<script>
import draggable from 'vuedraggable'
import FunctionBox from './Function_box'
import PopoverBucket from './Popover_bucket'
import uId from 'uid'
import PuzzlePieces from './Puzzle_pieces'

export default {
  name: 'Commands',
  mounted () {
  },
  computed: {
    levelControl () {
      return this.$store.getters.getLevelControl
    },
    commands () {
      return this.levelControl.functions.cmds
    },
    activeFunctions () {
      return this.levelControl.functions.activeFuncs
    },
    runCompiled () {
      return this.levelControl.runCompiled
    },
    editingIndex () {
      return this.$store.getters.getEditingIndex
    },
    functionAreaShowing () {
      return this.$store.getters.getFunctionAreaShowing
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
      currentColor: this.colorSelected,
      editFunctionOpen: false
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
    handleEditFunctionEvent (evt) {
      this.$store.dispatch('updateEditFunctionEvent', evt.target)
    },
    toggleFunctionAdd (evt) {
      this.handleEditFunctionEvent(evt)
      this.$store.dispatch('updateEditingIndex', null)
      this.$store.dispatch('updateFunctionAreaShowing', this.functionAreaShowing === 'addFunction' ? 'editMain' : 'addFunction')
    },
    editFunction (evt, func, ind) {
      $('#open-staged').show()
      this.handleEditFunctionEvent(evt)
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
      const element = evt.added || evt.moved
      const newIndex = element.newIndex
      const func = element.element
      func.category = 'function'
      func.index = newIndex
      this.levelControl.activateFunction(func)
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
  $functions-padding-left: 2vmin;
  $commands-margin-top: 2vmin;
  $piece-height: 7.5vmin;

  .invisible {
    visibility: hidden;
  }

  .commands {
    display: flex;
    align-items: center;
    flex-direction: row;
    width: 100%;
    height: calc(#{$piece-height} * 2.2);
    margin: 0 auto;
    position: relative;
    z-index: 100;
  }

  .lambdas-container {
    transition-duration: 300ms;
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: flex-start;
  }

  .command-border-info {
    border: 1px solid rgb(0, 0, 255)!important;
  }

  .methods-container {
    .methods {
      border: 1px solid $click-color;
      display: flex;
      height: min-content;
      margin-top: $commands-margin-top;
      background-color: rgba(0, 0, 0, 0.6);
      border-radius: 3px;
    }
  }

  .functions-container {
    overflow: auto;
    -webkit-overflow-scrolling: touch;
    margin-right: 7%;
    width: 100%;

    .functions {
      position: relative;
      display: flex;
      height: min-content;
      min-height: $piece-height;
      border: 1px solid transparent;
      width: min-content;
      width: -moz-min-content;
      min-width: 100%;
      margin-top: $commands-margin-top;
      padding: 0 0 0 $functions-padding-left;
    }
  }

  #commands-box {
    margin: 0;
  }

  #open-staged {
    position: absolute;
    right: 0;
    top: 0;
    height: 5vmin;
    width: 5vmin;
    z-index: 1000;
    background: url("https://res.cloudinary.com/deqjemwcu/image/upload/v1522343465/buttons/plusButton.png");
    margin-top: $commands-margin-top;
    background-size: contain;
    background-repeat: no-repeat;
  }

  .rotate-to-x {
    -webkit-transform: rotate(45deg);
    transform:rotate(45deg);
  }

  .rotate-to-plus {
    -webkit-transform: rotate(0);
    transform:rotate(0);
  }
</style>
