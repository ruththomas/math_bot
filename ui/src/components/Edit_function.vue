<template>
  <div class="edit-function">
    <div class="edit-function-data">
      <div class="func-param-form">
        <puzzle-pieces
          :id="'edit-function-displayed-func'"
          :func="editingFunction"
          :piece-to-show="'closed'"
          :show-name="false"
          @click.native="toggleFunctionImage"
        ></puzzle-pieces>
        <div
          id="denom-selector-trigger"
          class="function-control"
        >
          <tool
            :denomination="editingFunction.color"
            :hide-denom="editingFunction.color === '1'"
          ></tool>
        </div>
        <b-popover
          target="denom-selector-trigger"
          placement="top"
          triggers="click"
        >
          <img class="dialog-button close-popover" :src="permanentImages.buttons.xButton" @click="closePopover('denom-selector-trigger')" />
          <div class="denom-selector-body">
            <tool
              v-for="(denom, ind) in denominations"
              :key="'denom-selector/' + ind"
              :denomination="denom.name"
              :hide-denom="denom.name === '1'"
              @click.native="updateFunctionColor(denom.name)"
            ></tool>
          </div>
        </b-popover>

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
import uid from 'uid'
import FunctionBox from './Function_box'
import FunctionDrop from './Function_drop'
import utils from '../services/utils'
import PuzzlePieces from './Puzzle_pieces'
import Tool from './Tool'

export default {
  mounted () {
    this.setPut()
  },
  computed: {
    levelControl () {
      return this.$store.getters.getLevelControl
    },
    denominations () {
      return this.levelControl.continent.toolList
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
    editingIndex () {
      return this.$store.getters.getEditingIndex
    },
    sizeLimit () {
      return this.editingFunction.sizeLimit
    },
    currentColor () {
      return this.levelControl.getColorHex(this.editingFunction.color)
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
    }
  },
  watch: {
    editingFunction () {
      this.setPut()
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
    highlightGridDenom (denom) {
      console.log('HIT')
    },
    updateName () {
      this.levelControl.updateFunctionProperties(this.editingFunction)
    },
    updateFunctionColor (color) {
      this.levelControl.updateFunctionProperties(Object.assign(this.editingFunction, {color: color}))
    },
    toggleFunctionImage () {
      const func = this.editingFunction
      func.displayName = !func.displayName
      this.levelControl.updateFunctionProperties(func)
    },
    deleteFuncContents () {
      this.closePopover('delete-function')
      this.levelControl.deleteFunction(this.editingFunction)
      this.setPut(true)
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
    fullMessage () {
      const messageBuilder = {
        type: 'success',
        msg: `Function full`
      }
      this.$store.dispatch('addMessage', messageBuilder)
    },
    setPut (bool = this.functions.length < this.editingFunction.sizeLimit) {
      this.functionDraggableOptions.group.put = bool
      // if (!bool) {
      //   this.fullMessage()
      // }
    },
    editFunction () {
      if (this.functions.length <= this.editingFunction.sizeLimit) {
        this.levelControl.updateFunction(this.editingFunction)
      }
      this.setPut()
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
    PuzzlePieces,
    Tool
  }
}
</script>

<style scoped lang="scss">
  $click-color: #B8E986;
  $danger-color: #F25C5C;
  $piece-height: 7.5vmin;
  $dialog-button-size: 3.5vmin;
  $white: #ffffff;
  $black: #353535;
  $blue: #4A90E2;
  $purple: #CA7AFF;
  $green: #50E3C2;
  $pink: #FF98B1;
  $red: #F25C5C;

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
    .any {
      background: #696969!important; /* For browsers that do not support gradients */
      background: -webkit-linear-gradient($blue, $purple, $green, $pink, $red)!important; /* For Safari 5.1 to 6.0 */
      background: -o-linear-gradient($blue, $purple, $green, $pink, $red)!important; /* For Opera 11.1 to 12.0 */
      background: -moz-linear-gradient($blue, $purple, $green, $pink, $red)!important; /* For Firefox 3.6 to 15 */
      background: linear-gradient(to right, $blue, $purple, $green, $pink, $red)!important; /* Standard syntax (must be last) */
    }
    .empty {
      background: $black;
    }
    .white {
      background: $white;
    }
    .blue {
      background: $blue;
    }
    .purple {
      background: $purple;
    }
    .green {
      background: $green;
    }
    .pink {
      background: $pink;
    }
    .red {
      background: $red;
    }

    .function-control {
      border-radius: 0.5vmin;
      margin-right: 10px;
      cursor: pointer;

      .tool {
        position: relative;
        height: 4vmin;
        width: 4vmin;
        bottom: -0.25rem;

      }
      .tool:hover {
        cursor: pointer;
      }
    }

    .function-control.trash {
      height: 1.3em;
      width: 1.3em;
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

  .denom-selector-body {
    height: 100%;
    width: 100%;
    .tool {
      height: 4vmin;
      width: 4vmin;
      min-height: 20px;
      min-width: 20px;
      display: inline-block;
      position: relative;
      margin: 1%;
    }
    .tool:hover {
      cursor: pointer;
    }
  }
</style>
