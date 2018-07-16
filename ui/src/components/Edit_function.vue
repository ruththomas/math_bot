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
        :origin="'editFunction'"
        :group-size="groupSize"
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

export default {
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
      groupSize: 14,
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
      buildUtils.deleteFunction({context: this})
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
    end (evt) {
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
    position: relative;
    height: 100%;
    width: 100%;
    z-index: 10001;
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
    left: 38px;
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
