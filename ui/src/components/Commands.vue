<template>
  <div class="commands" v-if="commands !== null && activeFunctions !== null">
    <div class="lambdas-container">
      <div class="functions-container">
        <!--<draggable-->
          <!--class="methods"-->
          <!--:list="commands"-->
          <!--:options="commandOptions"-->
          <!--@start="start"-->
          <!--@end="end"-->
        <!--&gt;-->
          <!--<function-box-->
            <!--v-for="(command, ind) in commands"-->
            <!--:key="command.id"-->
            <!--:func="command"-->
            <!--:ind="ind"-->
            <!--:collection="commands"-->
            <!--:origin="'functions'"-->
            <!--v-on:click.native="notEditableMessage"-->
          <!--&gt;</function-box>-->
        <!--</draggable>-->
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
            :show-pointer="ind === editingIndex"
            @click.native="editFunction($event, func, ind)"
          ></function-box>
        </draggable>
      </div>
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
      return this.levelControl.functions.activeFunctions
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
      func.category = func.category === 'command' ? func.category : 'function'
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
  $pointer-size: 2vmin;

  .invisible {
    visibility: hidden;
  }

  .commands {
    display: flex;
    flex-direction: row;
    width: 100%;
    height: calc(#{$piece-height} * 2.2);
    margin: 0 auto;
    position: relative;
    z-index: 1001;

  .lambdas-container {
    transition-duration: 300ms;
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: flex-start;
      .functions-container {
        overflow: auto;
        -webkit-overflow-scrolling: touch;
        width: 100%;
        display: flex;
        padding-top: $pointer-size;

        .methods {
          display: flex;
          height: min-content;
        }

        .functions {
          display: flex;
          height: min-content;
          .func-category-command::after {
            content: "";
            position: absolute;
            top: -4px;
            left: -4px;
            right: -4px;
            bottom: -4px;
            border: 2px solid $click-color;
            border-radius: 3px;
            background-color: rgba(0, 0, 0, 0.5);
          }
        }
      }
    }
  }
</style>
