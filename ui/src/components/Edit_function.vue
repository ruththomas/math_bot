<template>
  <div class="edit-function">
    <div class="edit-function-data">
      <div class="func-param-form">
        <div
          class='displayed-func'
          :style="{
            'background-image': 'url(' + funcImages[editingFunction.image] + ')',
            'border-color': colorSelected.hex}"
        >
        </div>
        <div
          class='function-control'
          :style="{'background-color': colorSelected.hex}"
          @click="applyColorConditional"
        >
        </div>
        <input v-default-value="editingFunction.name" class="func-name" type="text" maxlength="57" placeholder="Name your function here" v-model="editingFunction.name" @change="updateName()" />

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

      <img class="close-edit-function dialog-button" @click="closeEditFunction" :src="permanentImages.buttons.xButton" data-toggle="tooltip" title="Close">
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

export default {
  mounted () {
  },
  computed: {
    editingIndex () {
      return this.$store.getters.getEditingIndex
    },
    editingFunction () {
      return this.activeFunctions[this.editingIndex]
    },
    activeFunctions () {
      return this.$store.getters.getActiveFunctions
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
      buildUtils._putFunc({
        context: this,
        funcToken: this.editingFunction
      })
    },
    applyColorConditional () {
      buildUtils.adjustColor({
        context: this,
        color: this.findColor()
      })
      this.color = 'default'
    },
    deleteFuncContents () {
      this.closePopover('delete-function')
      buildUtils.deleteFunction({context: this})
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
    togglePut (bool) {
      this.functionDraggableOptions.group.put = bool
      if (!bool) this.fullMessage()
    },
    editFunction (evt, groupInd) {
      if (!evt.hasOwnProperty('removed')) {
        buildUtils.addToFunction({
          context: this,
          groupSize: this.groupSize,
          groupInd: groupInd,
          added: evt.hasOwnProperty('added') ? evt.added : evt.moved
        })
      }
      if (this.editFunction.sizeLimit < 10000 && this.editingFunction.sizeLimit > 0) {
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
    FunctionDrop
  }
}
</script>

<style scoped lang="scss">
  $form-left: 34px;
  $click-color: #B8E986;
  $danger-color: #F25C5C;

  .edit-function {
    position: relative;
    height: 100%;
    width: 100%;
    z-index: 99;
    overflow: visible;
  }

  .edit-function-data {
    position: absolute;
    top: -70px;
    right: 0;
    left: 0;
    height: 105px;
  }

  .edit-function-content {
    position: relative;
    overflow: hidden;
    height: 100%;
    display: flex;
    align-items: flex-end;
    padding-top: 5%;
  }

  .edit-function-drop > * {
    margin: 10px 10px 10px 0;
  }

  .close-edit-function {
    float: right;
    display: flex;
    position: absolute;
    bottom: 20px;
    right: -20px;
    z-index: 10001;
    cursor: pointer;
  }

  .func-param-form {
    display: flex;
    align-items: flex-end;
    position: absolute;
    bottom: 0;
    left: $form-left;
    z-index: 1001;
  }

  .displayed-func {
    height: 70px;
    width: 70px;
    background-size: 65% 65%;
    background-color: black;
    background-repeat: no-repeat;
    background-position: center;
    display: flex;
    justify-content: center;
    align-items: center;
    border: 1px solid #B8E986;
    border-radius: 3px;
    margin-right: 8px;
  }

  .function-control {
    height: 25px;
    width: 25px;
    border-radius: 5px;
    margin-right: 10px;
    cursor: pointer;
  }

  .trash-confirm {
    background-color: $danger-color;
    box-shadow: 0 2px 10px 0 $danger-color;
    animation: shake 0.8s;
    animation-iteration-count: infinite;
    margin: 40px;
  }

  .func-name {
    color: white;
    border-color: #979797;
    background-color: transparent !important;
    border-left: none !important;
    border-right: none !important;
    border-top: none !important;
    border-bottom: 1px solid #979797;
    margin-right: 20px;
    height: 23px;
    font-size: 18px;
    line-height: 21px;
    font-weight: 300;
    outline: none;
  }

  .function-drop-drop-zone {
    margin: 0;
  }

  .close-popover {
    height: 18px;
    width: 18px;
    position: absolute;
    top: -9px;
    right: -9px;
  }

  /* ipad pro Portrait */
  @media only screen
  and (min-device-width: 1025px)
  and (max-device-width: 1366px)
  and (orientation: portrait)
  and (-webkit-min-device-pixel-ratio: 1.5) {
    $piece-size: 36px;
    .edit-function-data {
      top: -85px;

      .func-param-form {
        left: 12px;

        .displayed-func {
          height: $piece-size;
          width: $piece-size;
        }

        .function-control {
          height: 18px;
          width: 18px;
        }

        .func-name {
          font-size: 12px;
          width: 100px;
        }
      }

      .close-edit-function {
        bottom: 15px;
        right: -15px;
      }
    }
  }

  // ipad landscape
  @media only screen
  and (min-device-width : 768px)
  and (max-device-width : 1024px)
  and (orientation : landscape) {
    $piece-size: 50px;
    .edit-function-data {
      top: -85px;

      .func-param-form {
        left: 30px;
        bottom: -5px;

        .displayed-func {
          height: $piece-size;
          width: $piece-size;
        }

        .function-control {
          height: 18px;
          width: 18px;
        }

        .func-name {
          font-size: 12px;
          width: 100px;
        }
      }

      .close-edit-function {
        bottom: 10px;
        right: -15px;
      }
    }
  }

  // ipad portrait
  @media only screen
  and (min-device-width : 768px)
  and (max-device-width : 1024px)
  and (orientation : portrait) {
    $piece-size: 50px;
    .edit-function-data {
      top: -85px;

      .func-param-form {
        left: 12px;
        bottom: -5px;

        .displayed-func {
          height: $piece-size;
          width: $piece-size;
        }

        .function-control {
          height: 18px;
          width: 18px;
        }

        .func-name {
          font-size: 12px;
          width: 100px;
        }
      }

      .close-edit-function {
        bottom: 10px;
        right: -15px;
      }
    }
  }

  @media only screen and (max-width : 823px) and (orientation: landscape) {
    $piece-size: 36px;
    .edit-function-data {
      top: -85px;

      .func-param-form {
        left: 12px;

        .displayed-func {
          height: $piece-size;
          width: $piece-size;
        }

        .function-control {
          height: 18px;
          width: 18px;
        }

        .func-name {
          font-size: 12px;
          width: 100px;
        }
      }

      .close-edit-function {
        bottom: 10px;
        right: -10px;
      }
    }
  }

  @media only screen and (max-width: 736px) {
    $piece-size: 36px;
    .edit-function-data {
      top: -85px;

      .func-param-form {
        left: 12px;

        .displayed-func {
          height: $piece-size;
          width: $piece-size;
        }

        .function-control {
          height: 18px;
          width: 18px;
        }

        .func-name {
          font-size: 12px;
          width: 100px;
        }
      }

      .close-edit-function {
        bottom: 10px;
        right: -10px;
      }
    }
  }

  @media only screen and (max-width : 667px) {
    $piece-size: 36px;
    .edit-function-data {
      top: -85px;

      .func-param-form {
        left: 12px;

        .displayed-func {
          height: $piece-size;
          width: $piece-size;
        }

        .function-control {
          height: 18px;
          width: 18px;
        }

        .func-name {
          font-size: 12px;
          width: 100px;
        }
      }

      .close-edit-function {
        bottom: 10px;
        right: -10px;
      }
    }
  }

  /* iphone 5 landscape */
  @media only screen and (max-width : 568px) {
    .edit-function-data {
      top: -85px;

      .func-param-form {
        left: 12px;

        .displayed-func {
          height: 32px;
          width: 32px;
        }

        .function-control {
          height: 18px;
          width: 18px;
        }

        .func-name {
          font-size: 12px;
          width: 100px;
        }
      }

      .close-edit-function {
        bottom: 10px;
        right: -10px;
      }
    }
  }

  @media only screen and (max-width : 320px) {
    .edit-function-data {
      top: -85px;

      .func-param-form {
        left: 12px;

        .displayed-func {
          height: 32px;
          width: 32px;
        }

        .function-control {
          height: 18px;
          width: 18px;
        }

        .func-name {
          font-size: 12px;
        }
      }

      .close-edit-function {
        bottom: 10px;
        right: -10px;
      }
    }
  }
</style>
