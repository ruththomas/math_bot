<template>
  <div>
    <div id="robot-carrying-id" class="robot-carrying" @click="openPopover('robot-carrying-id')">
      <span
        v-for="(tool, key) in organizeCarrying"
        :key="'carrying-' + key"
      >
        <tool
          :denomination="key"
          :hide-denom="key === '1'"
        ></tool> <span>x</span> <span>{{tool}}</span>
      </span>
    </div>
    <b-popover
      v-if="robotCarrying.length"
      target="robot-carrying-id"
      triggers="click"
      placement="left"
    >
      <img class="dialog-button close-popover" :src="permanentImages.buttons.xButton" @click="closePopover('robot-carrying-id')" />
      <tool
        v-for="(imageName, iInd) in displayCarrying"
        :key="'carrying-popover-image/' + iInd"
        :denomination="imageName"
        :hide-denom="imageName === '1'"
      ></tool>
      <span class="tool-display-text" @click="adjustShowAmount"><strong>. . .</strong></span>
    </b-popover>
  </div>
</template>

<script>
import uid from 'uid'
import utils from '../services/utils'
import Tool from './Tool'

export default {
  computed: {
    levelControl () {
      return this.$store.getters.getLevelControl
    },
    gridMap () {
      return this.levelControl.continent.gridMap
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
    organizeCarrying () {
      return this.robotCarrying.reduce((groups, name) => {
        groups[name] = groups[name] + 1 || 1
        return groups
      }, {})
    },
    toolImages () {
      return this.permanentImages.tools
    },
    displayAmt () {
      return this.amountToShow
    },
    displayCarrying () {
      return this.robotCarrying.slice(0, this.displayAmt)
    },
    permanentImages () {
      return this.$store.getters.getPermanentImages
    }
  },
  data () {
    return {
      amountToShow: 48
    }
  },
  methods: {
    uID () {
      return uid(7)
    },
    adjustShowAmount () {
      this.amountToShow = this.amountToShow === 1000 ? 48 : 1000
    },
    closePopover: utils.closePopover,
    openPopover: utils.openPopover
  },
  components: {
    Tool
  }
}
</script>

<style scoped lang="scss">
  $carrying-size: 2vmin;
  $click-color: #B8E986;
  $popover-btn-size: 2vmin;

  .robot-carrying {
    position: absolute;
    bottom: 0;
    left: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    flex-wrap: wrap;
    z-index: 10000;
    font-size: $carrying-size;
    background: rgba(0, 0, 0, 0.6);
    border-left: none;
    cursor: pointer;
    color: #ffffff;

    span {
      display: flex;
      align-items: center;
      margin: 0 0.3em 0 0.3em;
    }
  }

  .hide-carrying {
    opacity: 0;
  }

  .tool-display-image {
    height: $carrying-size;
    width: $carrying-size;
  }

  .tool-display-text {
    color: #ffffff;
    cursor: pointer;
    display: block;
  }

  .close-popover {
    float: right;
    display: flex;
    position: absolute;
    height: $popover-btn-size;
    width: auto;
    bottom: calc(100% - #{$popover-btn-size} / 2);
    right:  calc(#{-$popover-btn-size} / 2);
    z-index: 10001;
    cursor: pointer;
  }
  .tool {
    height: 2vmin;
    width: 2vmin;
  }
</style>
