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
      ></function-box>
    </draggable>
  </div>
</template>

<script>
import draggable from 'vuedraggable'
import FunctionBox from './Function_box'
import _ from 'underscore'

export default {
  name: 'function_drop',
  mounted () {
    document.querySelector(`.${this.origin}-drop-zone`).addEventListener('dragover', this.hideFirstPlaceholder)
    this.centerDropped({added: {newIndex: this.list.length - 1}})
  },
  computed: {
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
    permanentImages () {
      return this.$store.getters.getPermanentImages
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
      const scrollTooIndex = evt.moved ? evt.moved.newIndex : evt.added ? evt.added.newIndex : evt.removed.oldIndex - 1
      const dropZoneChildren = $dropZone.children()
      const $dropped = $(dropZoneChildren[scrollTooIndex])
      const droppedWidth = $dropped.width()
      let childrenWidthSum = 0

      if (dropZoneChildren.length) {
        dropZoneChildren.each(function () {
          const $ele = $(this)
          childrenWidthSum += $ele.outerWidth()
          $ele.removeClass('dropped-indication')
          $ele.find('.tab-insert').removeClass('dropped-indication')
        })

        $dropped.addClass('dropped-indication')
        $dropped.find('.tab-insert').addClass('dropped-indication')

        if ((childrenWidthSum * 2) > dropWidth) {
          $dropZone.css({'padding-right': `${(dropWidth / 2) - (droppedWidth / 2)}px`})
        } else {
          $dropZone.css({'padding-right': 0})
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
  $drop-zone-padding-left: 30px;
  $drop-zone-padding-right: 30px;
  $danger-color: #F25C5C;

  .function-drop {
    overflow: auto;
    height: 100%;
    width: 100%;
    .function-drop-drop-zone {
      position: relative;
      width: min-content;
      min-width: 100%;
      height: 100%;
      display: flex;
      justify-content: center;
      align-items: center;
      z-index: 999;
      padding-left: $drop-zone-padding-left;
      padding-right: $drop-zone-padding-right;
    }
    .editFunction-drop-zone {
      justify-content: flex-start;
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

  .placeholder-piece {
    opacity: 0.4;
  }

  .center-function-drop {
    justify-content: center!important;
    padding: 0!important;
  }

  .piece-shake {
    animation: shake 0.8s;
    animation-iteration-count: infinite;
    box-shadow: 0 0 0 2px rgba(242, 92, 92, 0.9);
  }

  /* ipad pro Portrait */
  @media only screen
  and (min-device-width: 1024px)
  and (max-device-width: 1366px)
  and (orientation: portrait)
  and (-webkit-min-device-pixel-ratio: 1.5) {
  }

  /* ipad pro Landscape */
  @media only screen
  and (min-device-width: 1024px)
  and (max-device-width: 1366px)
  and (orientation: landscape)
  and (-webkit-min-device-pixel-ratio: 1.5) {

  }

  @media only screen and (max-width: 823px) {
    $drop-zone-padding-left: 10px;
    .function-drop {
      .function-drop-drop-zone {
        padding-left: $drop-zone-padding-left;
        padding-right: $drop-zone-padding-left;
      }
    }
  }

  @media only screen and (max-width: 736px) {
    $drop-zone-padding-left: 10px;
    .function-drop {
      .function-drop-drop-zone {
        padding-left: $drop-zone-padding-left;
        padding-right: $drop-zone-padding-left;
      }
    }
  }

  @media only screen and (max-width : 667px) {
    $drop-zone-padding-left: 10px;
    .function-drop {
      .function-drop-drop-zone {
        padding-left: $drop-zone-padding-left;
        padding-right: $drop-zone-padding-left;
      }
    }
  }

  @media only screen and (max-width : 568px) {
    $drop-zone-padding-left: 10px;
    .function-drop {
      .function-drop-drop-zone {
        padding-left: $drop-zone-padding-left;
        padding-right: $drop-zone-padding-left;
      }
    }
  }

  @media only screen and (max-width : 320px) {
    $drop-zone-padding-left: 10px;
    .function-drop {
      .function-drop-drop-zone {
        padding-left: $drop-zone-padding-left;
        padding-right: $drop-zone-padding-left;
      }
    }
  }
</style>
