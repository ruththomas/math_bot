<template>
  <div class="function-drop">
    <draggable
      class="function-drop-drop-zone"
      :class="[showMesh ? 'mesh-background' : '', origin + '-drop-zone']"
      :list="list"
      :options="options"
      @add="add($event)"
      @change="[change($event), showIndicator = true, centerDropped($event, true)]"
      @start="start"
      @end="end"
      @remove="removed"
    >
      <function-box
        v-for="(func, ind) in list.concat(placeholders)"
        :class="[
          func.placeholder ? 'placeholder-piece noDrag' : 'actual-piece',
          ind === sizeLimit - 1 && sizeLimit < 100 && !placeholders.length ? 'full-indicator' : ''
        ]"
        :key="origin + '-drop/' + ind"
        :func="func"
        :ind="ind"
        :collection="list.concat(placeholders)"
        :origin="origin"
        :data-created-id="func.created_id"
        @click.native="func.category === 'function' ? editFunction($event, func) : () => {}"
      ></function-box>
    </draggable>
  </div>
</template>

<script>
import draggable from 'vuedraggable'
import FunctionBox from './Function_box'
import _ from 'underscore'

export default {
  name: 'Function_drop',
  mounted () {
    document.querySelector(`.${this.origin}-drop-zone`).addEventListener('dragover', this.hideFirstPlaceholder)
    this.centerDropped({added: {newIndex: this.list.length - 1}})
  },
  computed: {
    levelControl () {
      return this.$store.getters.getLevelControl
    },
    placeholders () {
      if (this.sizeLimit < 100 && this.sizeLimit > 0) {
        return this.createPlaceHolders(this.sizeLimit).slice(this.list.length)
      } else {
        return []
      }
    },
    showMesh () {
      return this.$store.getters.getShowMesh
    },
    functionAreaShowing () {
      return this.$store.getters.getFunctionAreaShowing
    }
  },
  data () {
    return {
      showIndicator: true
    }
  },
  methods: {
    toggleEditFunction (ind) {
      this.$store.dispatch('updateEditingIndex', ind)
      this.$store.dispatch('updateFunctionAreaShowing', ind === null ? 'editMain' : 'editFunction')
    },
    editingFunctionMessage (func) {
      const messageBuilder = {
        type: 'success',
        msg: `${func.name ? `Edit: ${func.name}` : 'Edit: Function'}`
      }
      this.$store.dispatch('addMessage', messageBuilder)
    },
    editFunction (evt, func) {
      this.toggleEditFunction(this.levelControl.findFunctionIndex(func.created_id))
      this.$store.dispatch('updateEditFunctionEvent', evt.target)
    },
    createPlaceHolders (size) {
      return _.chain(size)
        .range()
        .map((_val, ind) => {
          return {index: ind, placeholder: true}
        })
        .value()
    },
    removed () {
      const $dropZone = $(`.${this.origin}-drop-zone`)
      const $placeholders = $dropZone.children('.placeholder-piece')
      $placeholders.each((index, piece) => {
        const $ele = $(piece)
        $ele.removeClass('hide-piece')
      })
    },
    hideFirstPlaceholder () {
      if (this.origin === this.functionAreaShowing) {
        const $dropZone = $(`.${this.origin}-drop-zone`)
        const $placeholders = $dropZone.children('.placeholder-piece')
        this.showIndicator = false
        $placeholders.each((index, piece) => {
          const $ele = $(piece)
          if (index === 0) {
            $ele.addClass('hide-piece')
          } else {
            $ele.removeClass('hide-piece')
          }
        })
      }
    },
    centerDropped (evt) {
      const $dropZone = $(`.${this.origin}-drop-zone`)
      const $functionDrop = $dropZone.parent()
      const dropWidth = $functionDrop.width()
      let scrollTooIndex = evt.moved ? evt.moved.newIndex : evt.added ? evt.added.newIndex : evt.removed.oldIndex
      const dropZoneChildren = $dropZone.children('.actual-piece')
      if (scrollTooIndex > dropZoneChildren.length - 1) {
        scrollTooIndex = dropZoneChildren.length - 1
      }
      const $dropped = $(dropZoneChildren[scrollTooIndex])
      const droppedWidth = $dropped.width()
      let childrenWidthSum = 0

      if (scrollTooIndex > -1 && $dropZone.find('.actual-piece').length) {
        dropZoneChildren.each(function () {
          const $ele = $(this)
          childrenWidthSum += $ele.outerWidth()
        })

        if ((childrenWidthSum * 2) > dropWidth) {
          $dropZone.animate({'padding-right': `${(dropWidth / 2) - (droppedWidth / 2)}px`}, 200)
        } else {
          $dropZone.animate({'padding-right': 0}, 200)
        }

        $functionDrop.animate({
          scrollLeft: $dropped.position().left - (dropWidth / 2) + (droppedWidth / 2)
        }, 800)
      }
    }
  },
  components: {
    draggable,
    FunctionBox
  },
  props: ['id', 'className', 'origin', 'list', 'options', 'add', 'change', 'start', 'end', 'groupSize', 'sizeLimit']
}
</script>

<style scoped lang="scss">
  $click-color: #B8E986;
  $danger-color: #F25C5C;
  $piece-height: 7.5vmin;

  .function-drop {
    overflow: auto;
    -webkit-overflow-scrolling: touch;
    height: 100%;
    width: 100%;
    z-index: 999;
    margin: 0;
    .function-drop-drop-zone {
      position: relative;
      width: min-content;
      width: -moz-min-content;
      min-width: 100%;
      display: flex;
      justify-content: flex-start;
      align-items: center;
      height: 100%;
    }

    .editMain-drop-zone {
      justify-content: center;
    }
  }

  .hide-piece {
    opacity: 0!important;
  }

  .mesh-background {
    border-radius: 3px;
    background: repeating-linear-gradient(
        45deg,
        rgba(0, 0, 0, 0.2),
        rgba(0, 0, 0, 0.2) 5px,
        rgba(74, 74, 74, 0.4) 7px,
        rgba(74, 74, 74, 0.4) 9px
    ), repeating-linear-gradient(
        -45deg,
        rgba(0, 0, 0, 0.2),
        rgba(0, 0, 0, 0.2) 5px,
        rgba(74, 74, 74, 0.4) 7px,
        rgba(74, 74, 74, 0.4) 9px
    );
  }

  .piece-shake {
    animation: shake 0.8s;
    animation-iteration-count: infinite;
  }

  .full-indicator {
    &::after {
      content: "";
      background-color: $danger-color;
      position: absolute;
      height: 75%;
      width: 3px;
      margin: auto;
      top: 0;
      left: 102%;
      bottom: 0;
      right: 0;
    }
  }
</style>
