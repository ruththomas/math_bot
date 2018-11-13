<template>
  <div class="edit-function">
    <div class="edit-function-data">
      <div class="func-param-form">
        <puzzle-pieces
          :id="'edit-function-displayed-func'"
          :func="{name: editingFunction.name, image: editingFunction.image, color: editingFunction.color, displayImage: editingFunction.displayImage}"
          :piece-to-show="'closed'"
          :show-name="false"
          @click.native="toggleImage"
        ></puzzle-pieces>
        <div
          class='function-control'
          :style="{'background-color': colorSelected.hex}"
          @click="applyColorConditional"
        >
        </div>

        <div class="func-name">
          <input v-default-value="editingFunction.name" autofocus type="text" maxlength="20" placeholder="Name your function here" v-model="editingFunction.name" @change="updateName()" />
        </div>

        <img
          v-if="editingFunction.func.length"
          id="delete-function"
          class='function-control trash'
          :src="permanentImages.trashCan"
          @click="animateVulnerable"
        />

        <b-popover
          v-if="editingFunction.func.length"
          target="delete-function"
          placement="top"
          triggers="click"
        >
          <img class="dialog-button close-popover" :src="permanentImages.buttons.xButton" @click="[closePopover('delete-function'), animateVulnerable()]" />
          <div class="button-effect trash-confirm" variant="danger"  @click="deleteFuncContents">
            <img class="dialog-button" :src="permanentImages.openTrashCan" />
          </div>
        </b-popover>
      </div>

      <img class="dialog-button close-edit-function" @click="closeEditFunction" :src="permanentImages.buttons.xButton" data-toggle="tooltip" title="Close">
    </div>

    <div class="edit-function-content">

      <function-drop
        :id="'edit-function'"
        :list="functions"
        :options="functionDraggableOptions"
        :change="editFunction"
        :start="moving"
        :end="end"
        :add="add"
        :origin="'editFunction'"
        :size-limit="editingFunction.sizeLimit"
      ></function-drop>

    </div>
  </div>
</template>

<script>
import draggable from 'vuedraggable'
import {_} from 'underscore'
import buildUtils from '../services/BuildFunction'
import uid from 'uid'
import FunctionBox from './Function_box'
import FunctionDrop from './Function_drop'
import utils from '../services/utils'
import PuzzlePieces from './Puzzle_pieces'

export default {
  mounted () {
    this.togglePut(this.functions.length < this.sizeLimit)
    this.functionDraggableOptions.group.put = true
  },
  computed: {
    levelControl () {
      return this.$store.getters.getLevelControl
    },
    gridMap () {
      return this.levelControl.continent.gridMap
    },
    robot () {
      return this.levelControl.robot
    },
    robotCarrying () {
      return this.robot.robotCarrying
    },
    problem () {
      return this.levelControl.continent.problem.problem
    },
    activeFunctions () {
      return this.levelControl.functions.activeFuncs
    },
    editingFunction () {
      return this.activeFunctions[this.editingIndex]
    },

    sizeLimit () {
      return this.editingFunction.sizeLimit < 0 ? 10000 : this.editingFunction.sizeLimit
    },
    stats () {
      return this.$store.getters.getStats
    },
    editingIndex () {
      return this.$store.getters.getEditingIndex
    },
    colorSelected () {
      return this.colors[this.currentColor]
    },
    currentColor () {
      return this.editingFunction.color
    },
    functions () {
      return this.editingFunction.func
    },
    permanentImages () {
      return this.$store.getters.getPermanentImages
    },
    funcImages () {
      return this.permanentImages.funcImages
    },
    cmdImages () {
      return this.permanentImages.cmdImages
    },
    funcNcmdImages () {
      return _.extend(this.funcImages, this.cmdImages)
    }
  },
  data () {
    return {
      func: {
        color: 'default',
        commandId: null,
        created_id: '12345',
        name: ''
      },
      color: this.currentColor,
      colors: {
        default: {
          hex: '#FFFFFF',
          next: 'grey'
        },
        grey: {
          hex: '#696969',
          next: 'blue'
        },
        blue: {
          hex: '#4A90E2',
          next: 'purple'
        },
        purple: {
          hex: '#CA7AFF',
          next: 'green'
        },
        green: {
          hex: '#50E3C2',
          next: 'pink'
        },
        pink: {
          hex: '#FF98B1',
          next: 'red'
        },
        red: {
          hex: '#F25C5C',
          next: 'default'
        }
      },
      functionDraggableOptions: {
        group: {
          name: 'commands-slide',
          pull: true,
          put: true
        },
        animation: 100,
        ghostClass: 'ghost',
        chosenClass: 'chosen',
        filter: '.noDrag'
      }
    }
  },
  methods: {
    findColor () {
      return this.colors[this.currentColor].next
    },
    updateName () {
      this.levelControl.updateFunction(this.editingFunction)
    },
    applyColorConditional () {
      const level = this.stats.level
      if (level !== 'BasicProgramming' && level !== 'Counting' && level !== 'Numbers' && level !== 'Recursion') {
        buildUtils.adjustColor({
          context: this,
          color: this.findColor()
        })
      }
      this.color = 'default'
    },
    deleteFuncContents () {
      this.closePopover('delete-function')
      this.levelControl.deleteFunction(this.editingFunction)
    },
    animateVulnerable () {
      const $functions = $('.editFunction-drop-zone > .piece:not(.placeholder-piece)')
      const animationClass = 'piece-shake'
      $functions.each(function () {
        const $ele = $(this)
        if ($ele.hasClass(animationClass)) {
          $ele.removeClass(animationClass)
        } else {
          $ele.addClass(animationClass)
        }
      })
    },
    toggleImage () {
      buildUtils.toggleImage()
    },
    fullMessage () {
      const messageBuilder = {
        type: 'success',
        msg: `Function full`
      }
      this.$store.dispatch('addMessage', messageBuilder)
    },
    togglePut (bool) {
      this.functionDraggableOptions.group.put = bool
    },
    editFunction () {
      this.levelControl.updateFunction(this.editingFunction)
      if (this.editingFunction.sizeLimit < 10000 && this.editingFunction.sizeLimit > 0) {
        this.togglePut(this.functions.length < this.editingFunction.sizeLimit)
      }
    },
    closeEditFunction () {
      this.$store.dispatch('updateFunctionAreaShowing', 'editMain')
      this.$store.dispatch('updateEditingIndex', null)
    },
    uID () {
      return uid(7)
    },
    moving (evt) {
      this.$store.dispatch('updateTrashVisible', true)
    },
    add (evt) {
    },
    end (evt) {
      this.$store.dispatch('updateTrashVisible', false)
    },
    closePopover: utils.closePopover
  },
  components: {
    draggable,
    FunctionBox,
    FunctionDrop,
    PuzzlePieces
  }
}
</script>

<style scoped lang="scss">
  $click-color: #B8E986;
  $danger-color: #F25C5C;
  $piece-height: 7.5vmin;
  $dialog-button-size: 3.5vmin;

  .edit-function {
    position: relative;
    height: 100%;
    width: 100%;
    z-index: 99;
    overflow: visible;
  }

  .edit-function-data {
    position: absolute;
    top: -5vmin;
    right: 0;
    left: 0;
    display: flex;
    align-items: flex-end;
  }

  .edit-function-content {
    position: relative;
    overflow: hidden;
    display: flex;
    align-items: flex-end;
    height: calc(#{$piece-height} + 5vmin);
    margin-top: 3vmin;
  }

  .edit-function-drop > * {
    margin: 10px 10px 10px 0;
  }

  .close-edit-function {
    float: right;
    display: flex;
    position: absolute;
    bottom: calc(#{$dialog-button-size} / 2);
    right:  calc(#{-$dialog-button-size} / 2);
    z-index: 10001;
    cursor: pointer;
  }

  .func-param-form {
    display: flex;
    align-items: flex-end;
    z-index: 1001;
    font-size: 2vmin;
    width: 90%;

    .function-control {
      height: 1.3em;
      width: 1.3em;
      border-radius: 0.5vmin;
      margin-right: 10px;
      cursor: pointer;
    }

    .func-name {
      background-color: transparent !important;
      border-left: none !important;
      border-right: none !important;
      border-top: none !important;
      border: 1px solid #979797;
      border-radius: 0;
      width: 50%;
      height: 3vmin;

      input {
        width: 100%;
        background: transparent;
        color: white;
        border: none;
        vertical-align: bottom;
        outline: none;
      }
    }

    .piece {
      margin-bottom: 0;
    }
  }

  .trash-confirm {
    background-color: $danger-color;
    box-shadow: 0 2px 10px 0 $danger-color;
    animation: shake 0.8s;
    animation-iteration-count: infinite;
    margin: 3vmin;
  }

  .function-drop-drop-zone {
    margin: 0;
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
</style>
