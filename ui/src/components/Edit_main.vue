<template>
  <div class="edit-main" :class="functionAreaShowing === 'editMain' ? '' : 'deactivate-edit-main'">
    <function-drop
      :id="'edit-main'"
      :class="'edit-main-drop'"
      :list="mainFunction.func"
      :options="mainDraggableOptions"
      :change="editFunction"
      :add="add"
      :start="moving"
      :end="end"
      :origin="'editMain'"
      :size-limit="levelControl.continent.mainMax"
    ></function-drop>
    <bar></bar>
</div>
</template>

<script>
import draggable from 'vuedraggable'
import FunctionBox from './Function_box'
import FunctionDrop from './Function_drop'
import utils from '../services/utils'
import Mascot from './Mascot'
import Bar from './Bar'
import DraggableOptionGenerator from '../services/DraggableOptionGenerator'

export default {
  mounted () {
    setTimeout(this.levelControl._positionBar, 500)
  },
  computed: {
    permanentImages () {
      return this.$store.getters.getPermanentImages
    },
    robot () {
      return this.levelControl.robot
    },
    levelControl () {
      return this.$store.getters.getLevelControl
    },
    mainFunction () {
      return this.levelControl.functions.main
    },
    runCompiled () {
      return this.levelControl.runCompiled
    },
    functionAreaShowing () {
      return this.$store.getters.getFunctionAreaShowing
    },
    path () {
      return this.levelControl.path
    }
  },
  data () {
    return {
      mainDraggableOptions: DraggableOptionGenerator({
        group: {
          name: 'edit-main-drag',
          put: (to, from) => {
            const fromGroup = from.options.group.name
            const $childs = $(to.el).children(':not(.piece-placeholder)')
            return fromGroup === 'commands-drag' && $childs.length < this.levelControl.continent.mainMax
          },
          revertClone: true
        }
      })
    }
  },
  methods: {
    fullMessage () {
      const messageBuilder = {
        type: 'success',
        msg: `Main full`
      }
      this.$store.dispatch('addMessage', messageBuilder)
    },
    editFunction () {
      if (this.mainFunction.func.length <= this.levelControl.continent.mainMax) {
        this.levelControl.updateFunction(this.mainFunction)
      }
    },
    add () {
    },
    moving () {
      this.$store.dispatch('updateTrashVisible', true)
      this.$store.dispatch('toggleShowMesh', true)
    },
    end () {
      this.$store.dispatch('toggleShowMesh', false)
      this.$store.dispatch('updateTrashVisible', false)
    },
    closePopover: utils.closePopover,
    adjustSpeed () {
      this.levelControl.robot.adjustSpeed()
    },
    closeHint () {
      this.$store.dispatch('toggleHintShowing', {showing: false, videoURL: ''})
    }
  },
  components: {
    draggable,
    FunctionBox,
    FunctionDrop,
    Mascot,
    Bar
  }
}
</script>

<style scoped lang="scss">
  $click-color: #B8E986;
  $dialog-button-size: 3.5vmin;
  $bar-height: 1px;
  $click-color: #B8E986;
  $dialog-button-size: 3.5vmin;
  $danger-color: #F25C5C;
  $mascot-size: 12vmin;
  $padding-left: 10%;
  $bar-color: #B8E986;

  .edit-main {
    position: relative;
    width: 100%;
    margin: 0 auto;
    height: 100%;
    padding: 0 $padding-left 0 $padding-left;
    z-index: 1000;

    .dialog-button {
      z-index: 2010;
      display: flex;
      cursor: pointer;
      position: absolute;
      float: right;
      border-radius: 50%;
    }

    .play {
      border-radius: 50%;
      right: 15vmin;
    }

    .reset {
      width: auto;
      right: -1px;
    }

    .play-border {
      border: 3px solid rgb(135, 206, 250);
    }

    .stop {
      right: 8vmin;
    }

    .trash {
      left: 0;
    }

    .direction {
      right: 12vmin;
    }

    .direction-rotate-back {
      transition: .3s;
      transform: rotate(180deg)
    }

    .direction-rotate-forward {
      transition: .3s;
      transform: rotate(0)
    }

    .speed {
      right: 4vmin;
      background-color: #B8E986;
      border-radius: 50%;
      display: flex;
      align-items: center;
      justify-content: center;
      color: black;
      font-size: 2vmin;
      font-weight: 500;
    }

    .x {
      float: left;
    }
  }

</style>
