<template>
  <div class="commands" v-if="commands !== null && activeFunctions !== null">
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
</template>

<script>
import draggable from 'vuedraggable'
import FunctionBox from './Function_box'
import PopoverBucket from './Popover_bucket'
import uId from 'uid'
import PuzzlePieces from './Puzzle_pieces'
import ScrollOptions from '../services/ScrollOptions'

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
      functionOptions: ScrollOptions({
        group: {
          name: 'commands-slide',
          pull: 'clone',
          put: ['commands-staged']
        }
      }),
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
    overflow: auto;
    -webkit-overflow-scrolling: touch;
    .functions {
      width: min-content;
      min-width: 100%;
      width: -moz-min-content;
      height: 100%;
      margin: 0 auto;
      overflow: auto;
      display: flex;

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
</style>
