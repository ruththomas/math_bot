<template>
    <div
      :id="id"
      v-if="pieceToShow === 'closed'"
      class="piece closed-piece border-right"
      :class="'piece-' + func.color"
      :style="{'background-image': `url(${funcAndCmdImages[func.image]})`}"
      data-toggle="tooltip" :title="func.name"
      @click="method ? method($event, func, ind) : ''">
      <div v-if="showName">
        <div v-if="func.name !== ''" class="command-name" :class="'piece-' + func.color">{{func.name}}</div>
        <div v-else class="command-name command-name-empty" :class="'piece-' + func.color">Name me!</div>
      </div>
    </div>

    <div
      :id="id"
      v-else-if="pieceToShow === 'start'"
      class="piece puzzle-start"
      :class="'piece-' + func.color"
      :style="{'background-image': `url(${funcAndCmdImages[func.image]})`}"
      data-toggle="tooltip" :title="func.name"
      @click="method ? method($event, func, ind) : ''">
      <div class="tab-insert">
        <div class="top-insert insert"></div>
        <div class="notch"></div>
        <div class="bottom-insert insert"></div>
      </div>
    </div>

    <div
      :id="id"
      v-else-if="pieceToShow === 'middle'"
      class="piece puzzle-middle"
      :class="'piece-' + func.color"
      :style="{'background-image': `url(${funcAndCmdImages[func.image]})`}"
      data-toggle="tooltip" :title="func.name"
      @click="method ? method($event, func, ind) : ''">
      <div class="left-tab"></div>
      <div class="tab-insert">
        <div class="top-insert insert"></div>
        <div class="notch"></div>
        <div class="bottom-insert insert"></div>
      </div>
    </div>

    <div
      :id="id"
      v-else-if="pieceToShow === 'end'"
      class="piece puzzle-end border-right"
      :class="'piece-' + func.color"
      :style="{'background-image': `url(${funcAndCmdImages[func.image]})`}"
      data-toggle="tooltip" :title="func.name"
      @click="method ? method($event, func, ind) : ''">
      <div class="left-tab"></div>
    </div>

</template>

<script>
import _ from 'underscore'

export default {
  name: 'puzzle_pieces',
  mounted () {
  },
  computed: {
    permanentImages () {
      return this.$store.getters.getPermanentImages
    },
    commandImages () {
      return this.permanentImages.cmdImages
    },
    funcImages () {
      return this.permanentImages.funcImages
    },
    funcAndCmdImages () {
      return _.extend(this.funcImages, this.commandImages)
    }
  },
  props: ['id', 'ind', 'func', 'pieceToShow', 'showName', 'method']
}
</script>

<style scoped lang="scss">
  $puzzle-piece-border-color: #FFFFFF;
  $piece-margin: 4px;
  $piece-default: #FFFFFF;
  $piece-grey: #696969;
  $piece-green: #50E3C2;
  $piece-blue: #4A90E2;
  $piece-pink: #FF98B1;
  $piece-red: #F25C5C;
  $piece-purple: #CA7AFF;

  .puzzle-piece {
    display: flex;
  }

  .piece-default {
    border-color: $piece-default!important;
    .border-right, .left-tab::before, .tab-insert, .top-insert, .bottom-insert {
      border-color: $piece-default!important;
    }
    .command-name {
      background-color: $piece-default!important;
    }
  }

  .piece-grey {
    border-color: $piece-grey!important;
    .border-right, .left-tab::before, .tab-insert, .top-insert, .bottom-insert {
      border-color: $piece-grey!important;
    }
    .command-name {
      background-color: $piece-grey!important;
    }
  }

  .piece-green {
    border-color: $piece-green!important;
    .border-right, .left-tab::before, .tab-insert, .top-insert, .bottom-insert {
      border-color: $piece-green!important;
    }
    .command-name {
      background-color: $piece-green!important;
    }
  }

  .piece-blue {
    border-color: $piece-blue!important;
    .border-right, .left-tab::before, .tab-insert, .top-insert, .bottom-insert {
      border-color: $piece-blue!important;
    }
    .command-name {
      background-color: $piece-blue!important;
    }
  }

  .piece-pink {
    border-color: $piece-pink!important;
    .border-right, .left-tab::before, .tab-insert, .top-insert, .bottom-insert {
      border-color: $piece-pink!important;
    }
    .command-name {
      background-color: $piece-pink!important;
    }
  }

  .piece-red {
    border-color: $piece-red!important;
    .border-right, .left-tab::before, .tab-insert, .top-insert, .bottom-insert {
      border-color: $piece-red!important;
    }
    .command-name {
      background-color: $piece-red!important;
    }
  }

  .piece-purple {
    border-color: $piece-purple!important;
    .border-right, .left-tab::before, .tab-insert, .top-insert, .bottom-insert {
      border-color: $piece-purple!important;
    }
    .command-name {
      background-color: $piece-purple!important;
    }
  }

  .piece {
    position: relative;
    height: 75px;
    width: 75px;
    margin: $piece-margin;
    border-bottom-left-radius: 3px;
    border-top-left-radius: 3px;
    color: white;
    background-color: #000000;
    border: 1px solid $puzzle-piece-border-color;
    border-right: none;
    background-size: 70%;
    background-position: center;
    background-repeat: no-repeat;
    cursor: grab;
    cursor: -moz-grab;
    cursor: -webkit-grab;
  }

  .piece:active {
    cursor: grabbing;
    cursor: -moz-grabbing;
    cursor: -webkit-grabbing;
  }

  .puzzle-start, .puzzle-middle {
    width: 65px;
  }

  .border-right {
    border-right: 1px solid $puzzle-piece-border-color;
    border-top-right-radius: 3px;
    border-bottom-right-radius: 3px;
  }

  .closed-piece {
    width: 70px;
  }

  .left-tab {
    display: flex;
    position: absolute;
    align-items: center;
    left: -10px;
    height: 100%;
    background-color: transparent;
  }

  .left-tab::before {
    content: "";
    width: 10px;
    height: 20px;
    background-color: #000000;
    border-bottom-right-radius: 20px;
    border-top-right-radius: 20px;
    border: 1px solid $puzzle-piece-border-color;
    border-left: 0;
    box-sizing: border-box;
    transform: rotate(180deg);
  }

  .tab-insert {
    top: -1px;
    left: 64px;
    position: absolute;
    display: flex;
    flex-direction: column;
    width: 10px;
    height: 75px;
    background: radial-gradient(transparent 10px, rgba(0,0,0,1) 20px);
    border-top: 1px solid $puzzle-piece-border-color;
    border-bottom: 1px solid $puzzle-piece-border-color;
  }

  .top-insert, .bottom-insert {
    content: "";
    flex-grow: 1;
    width: 100%;
    border-right: 1px solid $puzzle-piece-border-color;
    background-color: #000000;
  }

  .insert {
    position: relative;
    overflow: hidden;
  }

  .notch {
    position: relative;
    left: -10px;
    width: 20px;
    height: 20px;
    background: radial-gradient(transparent 10px, rgba(0, 0, 0, 1) 10px) no-repeat 10px;
    pointer-events: none;
  }

  .command-name {
    position: absolute;
    top: 93%;
    width: 90%;
    overflow: hidden;
    left: 1px;
    right: 0;
    margin-left: auto;
    margin-right: auto;
    color: #000000;
    font-size: 12px;
    line-height: 14px;
    font-weight: bold;
    text-align: center;
    /*word-wrap: break-word;*/
    white-space: normal;
    border-radius: 3px;
    background-color: $puzzle-piece-border-color;
  }

  .command-name-empty {
    color: transparent;
  }

  /* ipad pro Portrait */
  @media only screen
  and (min-device-width: 1024px)
  and (max-device-width: 1366px)
  and (orientation: portrait)
  and (-webkit-min-device-pixel-ratio: 1.5) {
    $piece-size: 50px;
    $piece-margin: 2px;
    $tab-insert-width: 5px;
    $tab-size: 5px;

    .piece {
      height: $piece-size;
      width: $piece-size;
      margin: $piece-margin;
      z-index: 1000;
    }

    .puzzle-start, .puzzle-middle {
      width: calc(#{$piece-size} - #{$tab-insert-width});
    }

    .tab-insert {
      width: $tab-insert-width;
      left: calc(#{$piece-size} - #{$tab-insert-width} - 1px);
      height: $piece-size;
    }

    .notch {
      width: 10px;
      height: 10px;
      left: 100px;
      background: radial-gradient(transparent 5px, rgba(0, 0, 0, 1) 5px) no-repeat 5px;
    }

    .left-tab {
      left: calc(#{$tab-size} - #{$tab-size} - #{$tab-size});
    }

    .left-tab::before {
      content: "";
      width: $tab-size;
      height: calc(#{$tab-size} * 2);
      border-bottom-right-radius: 10px;
      border-top-right-radius: 10px;
    }
  }

  // ipad landscape
  @media only screen
  and (min-device-width : 768px)
  and (max-device-width : 1024px)
  and (orientation : landscape) {
    $piece-size: 50px;
    $piece-margin: 2px;
    $tab-insert-width: 5px;
    $tab-size: 5px;

    .piece {
      height: $piece-size;
      width: $piece-size;
      margin: $piece-margin;
      z-index: 1000;
    }

    .puzzle-start, .puzzle-middle {
      width: calc(#{$piece-size} - #{$tab-insert-width});
    }

    .tab-insert {
      width: $tab-insert-width;
      left: calc(#{$piece-size} - #{$tab-insert-width} - 1px);
      height: $piece-size;
    }

    .notch {
      width: 10px;
      height: 10px;
      left: calc(#{$tab-size} - #{$tab-size} - #{$tab-size});
      background: radial-gradient(transparent 5px, rgba(0, 0, 0, 1) 5px) no-repeat 5px;
    }

    .left-tab {
      left: calc(#{$tab-size} - #{$tab-size} - #{$tab-size});
    }

    .left-tab::before {
      content: "";
      width: $tab-size;
      height: calc(#{$tab-size} * 2);
      border-bottom-right-radius: 10px;
      border-top-right-radius: 10px;
    }
  }

  // ipad portrait
  @media only screen
  and (min-device-width : 768px)
  and (max-device-width : 1024px)
  and (orientation : portrait) {
    $piece-size: 50px;
    $piece-margin: 2px;
    $tab-insert-left: 26px;
    $tab-insert-width: 5px;
    $tab-size: 5px;

    .piece {
      height: $piece-size;
      width: $piece-size;
      margin: $piece-margin;
      z-index: 1000;
    }

    .puzzle-start, .puzzle-middle {
      width: calc(#{$piece-size} - #{$tab-insert-width});
    }

    .tab-insert {
      width: $tab-insert-width;
      left: calc(#{$piece-size} - #{$tab-insert-width} - 1px);
      height: $piece-size;
    }

    .notch {
      width: 10px;
      height: 10px;
      left: calc(#{$tab-size} - #{$tab-size} - #{$tab-size});
      background: radial-gradient(transparent 5px, rgba(0, 0, 0, 1) 5px) no-repeat 5px;
    }

    .left-tab {
      left: calc(#{$tab-size} - #{$tab-size} - #{$tab-size});
    }

    .left-tab::before {
      content: "";
      width: $tab-size;
      height: calc(#{$tab-size} * 2);
      border-bottom-right-radius: 10px;
      border-top-right-radius: 10px;
    }
  }

  @media only screen and (max-width: 823px) and (orientation: landscape) {
    $piece-size: 36px;
    $piece-margin: 2px;
    $tab-insert-width: 5px;
    $tab-size: 5px;

    .command-name {
      display: none;
    }

    .piece {
      height: $piece-size;
      width: $piece-size;
      margin: $piece-margin;
      z-index: 1000;
    }

    .puzzle-start, .puzzle-middle {
      width: calc(#{$piece-size} - #{$tab-insert-width});
    }

    .tab-insert {
      width: $tab-insert-width;
      left: calc(#{$piece-size} - #{$tab-insert-width} - 1px);
      height: $piece-size;
    }

    .notch {
      width: 10px;
      height: 10px;
      left: calc(#{$tab-size} - #{$tab-size} - #{$tab-size});
      background: radial-gradient(transparent 5px, rgba(0, 0, 0, 1) 5px) no-repeat 5px;
    }

    .left-tab {
      left: calc(#{$tab-size} - #{$tab-size} - #{$tab-size});
    }

    .left-tab::before {
      content: "";
      width: $tab-size;
      height: calc(#{$tab-size} * 2);
      border-bottom-right-radius: 10px;
      border-top-right-radius: 10px;
    }
  }

  @media only screen and (max-width : 736px) and (orientation: landscape) {
    $piece-size: 36px;
    $piece-margin: 2px;
    $tab-insert-width: 5px;
    $tab-size: 5px;

    .command-name {
      display: none;
    }

    .piece {
      height: $piece-size;
      width: $piece-size;
      margin: $piece-margin;
      z-index: 1000;
    }

    .puzzle-start, .puzzle-middle {
      width: calc(#{$piece-size} - #{$tab-insert-width});
    }

    .tab-insert {
      width: $tab-insert-width;
      left: calc(#{$piece-size} - #{$tab-insert-width} - 1px);
      height: $piece-size;
    }

    .notch {
      width: 10px;
      height: 10px;
      left: calc(#{$tab-size} - #{$tab-size} - #{$tab-size});
      background: radial-gradient(transparent 5px, rgba(0, 0, 0, 1) 5px) no-repeat 5px;
    }

    .left-tab {
      left: calc(#{$tab-size} - #{$tab-size} - #{$tab-size});
    }

    .left-tab::before {
      content: "";
      width: $tab-size;
      height: calc(#{$tab-size} * 2);
      border-bottom-right-radius: 10px;
      border-top-right-radius: 10px;
    }
  }

  @media only screen and (max-width: 667px) and (orientation: landscape) {
    $piece-size: 31px;
    $piece-margin: 2px;
    $tab-insert-width: 5px;
    $tab-size: 5px;

    .command-name {
      display: none;
    }

    .piece {
      height: $piece-size;
      width: $piece-size;
      margin: $piece-margin;
      z-index: 1000;
    }

    .puzzle-start, .puzzle-middle {
      width: calc(#{$piece-size} - #{$tab-insert-width});
    }

    .tab-insert {
      width: $tab-insert-width;
      left: calc(#{$piece-size} - #{$tab-insert-width} - 1px);
      height: $piece-size;
    }

    .notch {
      width: 10px;
      height: 10px;
      left: calc(#{$tab-size} - #{$tab-size} - #{$tab-size});
      background: radial-gradient(transparent 5px, rgba(0, 0, 0, 1) 5px) no-repeat 5px;
    }

    .left-tab {
      left: calc(#{$tab-size} - #{$tab-size} - #{$tab-size});
    }

    .left-tab::before {
      content: "";
      width: $tab-size;
      height: calc(#{$tab-size} * 2);
      border-bottom-right-radius: 10px;
      border-top-right-radius: 10px;
    }
  }

  @media only screen and (max-width: 568px) and (orientation: landscape){
    $piece-size: 31px;
    $piece-margin: 2px;
    $tab-insert-width: 5px;
    $tab-size: 5px;

    .command-name {
      display: none;
    }

    .piece {
      height: $piece-size;
      width: $piece-size;
      margin: $piece-margin;
      z-index: 1000;
    }

    .puzzle-start, .puzzle-middle {
      width: calc(#{$piece-size} - #{$tab-insert-width});
    }

    .tab-insert {
      width: $tab-insert-width;
      left: calc(#{$piece-size} - #{$tab-insert-width} - 1px);
      height: $piece-size;
    }

    .notch {
      width: 10px;
      height: 10px;
      left: calc(#{$tab-size} - #{$tab-size} - #{$tab-size});
      background: radial-gradient(transparent 5px, rgba(0, 0, 0, 1) 5px) no-repeat 5px;
    }

    .left-tab {
      left: calc(#{$tab-size} - #{$tab-size} - #{$tab-size});
    }

    .left-tab::before {
      content: "";
      width: $tab-size;
      height: calc(#{$tab-size} * 2);
      border-bottom-right-radius: 10px;
      border-top-right-radius: 10px;
    }
  }

  @media only screen and (max-width: 414px) {
    $piece-size: 31px;
    $piece-margin: 2px;
    $tab-insert-width: 5px;
    $tab-size: 5px;

    .command-name {
      display: none;
    }

    .piece {
      height: $piece-size;
      width: $piece-size;
      margin: $piece-margin;
      z-index: 1000;
    }

    .puzzle-start, .puzzle-middle {
      width: calc(#{$piece-size} - #{$tab-insert-width});
    }

    .tab-insert {
      width: $tab-insert-width;
      left: calc(#{$piece-size} - #{$tab-insert-width} - 1px);
      height: $piece-size;
    }

    .notch {
      width: 10px;
      height: 10px;
      left: calc(#{$tab-size} - #{$tab-size} - #{$tab-size});
      background: radial-gradient(transparent 5px, rgba(0, 0, 0, 1) 5px) no-repeat 5px;
    }

    .left-tab {
      left: calc(#{$tab-size} - #{$tab-size} - #{$tab-size});
    }

    .left-tab::before {
      content: "";
      width: $tab-size;
      height: calc(#{$tab-size} * 2);
      border-bottom-right-radius: 10px;
      border-top-right-radius: 10px;
    }
  }

  @media only screen and (max-width: 375px) {
  }

  /* Custom, iPhone Retina */
  @media only screen and (max-width : 320px) {

  }
</style>
