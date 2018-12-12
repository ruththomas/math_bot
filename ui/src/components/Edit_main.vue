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
    <div class="control-bar bar noDrag" v-if="Object.keys(robot).length">
      <img
        id="main-delete-function"
        class="trash noDrag dialog-button function-control"
        :src="permanentImages.buttons.trashButton"
        @click="animateVulnerable"
      />
      <b-popover
        v-if="levelControl.functions.main.func.length"
        target="main-delete-function"
        placement="top"
        triggers="click"
      >
        <img class="dialog-button close-popover"
             :src="permanentImages.buttons.xButton"
             @click="[animateVulnerable(), closePopover('main-delete-function')]"
        />
        <div class="button-effect trash-confirm"  @click="[animateVulnerable(), wipeFunction(), closePopover('main-delete-function')]">
          <img class="dialog-button" :src="permanentImages.openTrashCan" />
        </div>
      </b-popover>
      <div class="end-cap"></div>
    </div>
</div>
</template>

<script>
import draggable from 'vuedraggable'
import FunctionBox from './Function_box'
import FunctionDrop from './Function_drop'
import utils from '../services/utils'

export default {
  mounted () {
    this.setPut()
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
    }
  },
  data () {
    return {
      mainDraggableOptions: {
        group: {
          name: 'commands-slide',
          pull: true,
          put: true
        },
        animation: 100,
        ghostClass: 'ghost',
        chosenClass: 'chosen',
        filter: '.noDrag',
        dragClass: 'dragging',
        sort: true
      }
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
    setPut (bool = this.mainFunction.func.length < this.levelControl.continent.mainMax) {
      this.mainDraggableOptions.group.put = bool
      if (!bool) {
        this.fullMessage()
      }
    },
    editFunction () {
      if (this.mainFunction.func.length <= this.levelControl.continent.mainMax) {
        this.levelControl.updateFunction(this.mainFunction)
      }
      this.setPut()
    },
    wipeFunction () {
      this.levelControl.deleteMain()
      this.setPut(true)
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
    animateVulnerable () {
      const $functions = $('.editMain-drop-zone > .piece:not(.placeholder-piece)')
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
    FunctionDrop
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

  .edit-main {
    position: relative;
    width: 100%;
    margin: 0 auto;
    height: 100%;
    padding: 0 $dialog-button-size;
    z-index: 1000;

    .bar {
      position: absolute;
      left: 0;
      right: $dialog-button-size;
      top: calc(50%);
      height: $bar-height;
      background-color: #B8E986;
      display: flex;
      align-items: center;
      justify-content: center;
      z-index: -1;

      .end-cap {
        position: absolute;
        height: 3vmin;
        width: 1px;
        left: 100%;
        background-color: $click-color;
      }
    }

    .hidden-bar {
      background-color: transparent;
    }

    .red-bar {
      background-color: #FF0000;
    }

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

  .trash-confirm {
    background-color: $danger-color;
    box-shadow: 0 2px 10px 0 $danger-color;
    animation: shake 0.8s;
    animation-iteration-count: infinite;
    margin: 1vmin;
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

  .deactivate-edit-main {
    opacity: 0;
  }
</style>
