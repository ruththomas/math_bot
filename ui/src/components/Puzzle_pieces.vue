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

  .puzzle-piece {
    display: flex;
  }

  .piece-default {
    border-color: #FFFFFF!important;
    .border-right, .left-tab::before, .tab-insert, .top-insert, .bottom-insert {
      border-color: #FFFFFF!important;
    }
    .command-name {
      background-color: #FFFFFF!important;
    }
  }

  .piece-green {
    border-color: #50E3C2!important;
    .border-right, .left-tab::before, .tab-insert, .top-insert, .bottom-insert {
      border-color: #50E3C2!important;
    }
    .command-name {
      background-color: #50E3C2!important;
    }
  }

  .piece-blue {
    border-color: #4A90E2!important;
    .border-right, .left-tab::before, .tab-insert, .top-insert, .bottom-insert {
      border-color: #4A90E2!important;
    }
    .command-name {
      background-color: #4A90E2!important;
    }
  }

  .piece-pink {
    border-color: #FF98B1!important;
    .border-right, .left-tab::before, .tab-insert, .top-insert, .bottom-insert {
      border-color: #FF98B1!important;
    }
    .command-name {
      background-color: #FF98B1!important;
    }
  }

  .piece-red {
    border-color: #F25C5C!important;
    .border-right, .left-tab::before, .tab-insert, .top-insert, .bottom-insert {
      border-color: #F25C5C!important;
    }
    .command-name {
      background-color: #F25C5C!important;
    }
  }

  .piece-purple {
    border-color: #CA7AFF!important;
    .border-right, .left-tab::before, .tab-insert, .top-insert, .bottom-insert {
      border-color: #CA7AFF!important;
    }
    .command-name {
      background-color: #CA7AFF!important;
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
    left: 1px;
    right: 0;
    margin-left: auto;
    margin-right: auto;
    color: #000000;
    font-size: 12px;
    line-height: 14px;
    font-weight: bold;
    text-align: center;
    word-wrap: break-word;
    white-space: normal;
    border-radius: 3px;
    background-color: $puzzle-piece-border-color;
  }

  .command-name-empty {
    color: transparent;
  }

  /* 13" screen */
  @media only screen and (max-width : 1280px) {

  }

  /* Medium Devices, Desktops */
  @media only screen and (max-width : 992px) {
    .command-name {
      display: none;
    }

    .command-name {
      display: none;
    }

    .piece {
      height: 32px;
      width: 32px;
      margin-left: 4px;
    }

    .puzzle-start, .puzzle-middle {
      width: 27px;
    }

    .tab-insert {
      width: 5px;
      left: 26px;
      height: 32px;
    }

    .notch {
      width: 10px;
      height: 10px;
      left: -5px;
      background: radial-gradient(transparent 5px, rgba(0, 0, 0, 1) 5px) no-repeat 5px;
    }

    .left-tab {
      left: -5px;
    }

    .left-tab::before {
      content: "";
      width: 5px;
      height: 10px;
      border-bottom-right-radius: 10px;
      border-top-right-radius: 10px;
    }
  }

  /* Small Devices */
  @media only screen and (max-width : 667px) {
    $piece-size: 31px;
    $piece-margin: 2px;

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
      width: 27px;
    }

    .tab-insert {
      width: 5px;
      left: 26px;
      height: $piece-size;
    }

    .notch {
      width: 10px;
      height: 10px;
      left: -5px;
      background: radial-gradient(transparent 5px, rgba(0, 0, 0, 1) 5px) no-repeat 5px;
    }

    .left-tab {
      left: -5px;
    }

    .left-tab::before {
      content: "";
      width: 5px;
      height: 10px;
      border-bottom-right-radius: 10px;
      border-top-right-radius: 10px;
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
    .command-name {
      display: block;
    }

    .piece {
      height: 75px;
      width: 75px;
      margin-left: 9px;
    }

    .puzzle-start, .puzzle-middle {
      width: 65px;
    }

    .tab-insert {
      top: -1px;
      left: 64px;
      width: 10px;
      height: 75px;
    }

    .notch {
      left: -10px;
      width: 20px;
      height: 20px;
      background: radial-gradient(transparent 10px, rgba(0, 0, 0, 1) 10px) no-repeat 10px;
    }

    .left-tab {
      left: -10px;
    }

    .left-tab::before {
      content: "";
      width: 10px;
      height: 20px;
      border-bottom-right-radius: 20px;
      border-top-right-radius: 20px;
    }
  }

  @media all and (device-width: 768px) and (device-height: 1024px) and (orientation:landscape) {

  }
</style>
