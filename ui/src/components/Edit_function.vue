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
        <input v-default-value="editingFunction.name" class="func-name" type="text" maxlength="52" placeholder="Name your function here" v-model="editingFunction.name" @change="updateName()" />

        <div
          class='function-control trash'
          @click="deleteFuncContents"
        >
        </div>

        <span class="function-limit-indicator" v-if="editingFunction.sizeLimit > 0">Size limit: {{ editingFunction.sizeLimit }}</span>
      </div>

      <img class="close-edit-function dialog-button" @click="closeEditFunction" :src="permanentImages.buttons.xButton" data-toggle="tooltip" title="Close">
    </div>

    <div class="edit-function-content">

      <function-drop
        :list="functions"
        :options="mainDraggableOptions"
        :change="copyCommand"
        :start="moving"
        :end="end"
        :origin="'editFunction'"
      ></function-drop>

    </div>

  </div>
</template>

<script>
import draggable from 'vuedraggable'
import {_} from 'underscore'
import buildUtils from '../services/build_function_utils'
import uid from 'uid'
import FunctionBox from './Function_box'
import FunctionDrop from './Function_drop'

export default {
  computed: {
    editingIndex () {
      return this.$store.getters.getEditingIndex
    },
    editingFunction () {
      return this.$store.getters.getActiveFunctions[this.editingIndex]
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
          hex: '#CCCCCC',
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
      mainDraggableOptions: {
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
      buildUtils.updateFunctionsOnChange({context: this, currentFunction: this.editingFunction, addedFunction: null, newIndex: this.editingFunction.index, newColor: null})
    },
    applyColorConditional () {
      const newColor = this.findColor()
      this.color = newColor
      buildUtils.updateFunctionsOnChange({ context: this, currentFunction: this.editingFunction, addedFunction: null, newIndex: this.editingFunction.index, newColor: newColor })
      this.color = 'default'
    },
    deleteFuncContents () {
      let deleteFuncContents = buildUtils.currentFunc(this)
      deleteFuncContents.func = []
      buildUtils.updateFunctionsOnChange(({context: this, currentFunction: deleteFuncContents, addedFunction: null, newIndex: null}))
    },
    copyCommand (evt) {
      if (!evt.hasOwnProperty('removed')) {
        const command = evt.hasOwnProperty('added') ? evt.added.element : evt.moved.element
        const ind = evt.hasOwnProperty('added') ? evt.added.newIndex : evt.moved.newIndex
        buildUtils.updateFunctionsOnChange({context: this, currentFunction: buildUtils.currentFunc(this), addedFunction: command, newIndex: ind})
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
    end () {
      this.$store.dispatch('updateTrashVisible', false)
    }
  },
  components: {
    draggable,
    FunctionBox,
    FunctionDrop
  }
}
</script>

<style scoped lang="scss">
  .edit-function {
    position: absolute;
    /*height: 250px;*/
    width: 100%;
    top: -70px;
    z-index: 10001;
    overflow: visible;
  }

  .edit-function-data {
    position: relative;
    height: 105px;
    margin-bottom: 12px;
  }

  .edit-function-content {
    position: relative;
    overflow: hidden;
    padding: 10px 20px 10px 20px;
    height: 130px;
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
    left: 26px;
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
    margin-right: 12px;
  }

  .function-control {
    height: 25px;
    width: 25px;
    border-radius: 5px;
    margin-right: 10px;
    cursor: pointer;
  }

  .function-control.trash {
    background: url("https://res.cloudinary.com/deqjemwcu/image/upload/v1522342913/buttons/trashButton.png");
    background-size: cover;
    float: right;
  }

  .func-name {
    color: white;
    border-color: #979797;
    background-color: transparent !important;
    border-left: none !important;
    border-right: none !important;
    border-top: none !important;
    border-bottom: 1px solid #979797;
    width: 350px;
    height: 23px;
    font-size: 18px;
    line-height: 21px;
    font-weight: 300;
    outline: none;
  }

  .function-drop-drop-zone {
    justify-content: flex-start!important;
    margin: 0;
  }

  /* Medium Devices, Desktops */
  @media only screen and (max-width : 992px) {
    .displayed-func {
      height: 40px;
      width: 40px;
    }

    .func-param-form {
      left: 1px;
      right: 0;
    }
    .func-param-form > * {
      margin-right: 3px;
    }

    .func-name {
      width: auto;
      font-size: 10px;
    }

    .edit-function-data {
      margin: 0;
    }

    .edit-function-content {
      padding: 0;
      height: 60px;
    }

    .close-edit-function {
      right: -10px;
      bottom: 26px;
    }
  }

  /* Small Devices */
  @media only screen and (max-width : 667px) {
    .displayed-func {
      height: 40px;
      width: 40px;
    }

    .func-param-form {
      left: 1px;
      right: 0;
    }
    .func-param-form > * {
      margin-right: 3px;
    }

    .func-name {
      width: auto;
      font-size: 10px;
    }

    .edit-function-data {
      margin: 0;
    }

    .edit-function-content {
      padding: 0;
      height: 60px;
    }

    .close-edit-function {
      right: -10px;
      bottom: 26px;
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
    .displayed-func {
      height: 70px;
      width: 70px;
    }

    .func-param-form {
      left: 1px;
      right: 0;
    }
    .func-param-form > * {
      margin-right: 3px;
    }

    .func-name {
      width: auto;
      font-size: 18px;
    }

    .edit-function-data {
      margin: 0;
    }

    .edit-function-content {
      padding: 10px 20px 10px 20px;
      height: 130px;
    }

    .close-edit-function {
      bottom: 20px;
      right: -20px;
    }
  }

  @media all and (device-width: 768px) and (device-height: 1024px) and (orientation:landscape) {

  }

</style>
