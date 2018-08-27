<template>
  <div>
    <div id="robot-carrying-id" class="robot-carrying" :class="!organizeCarrying.length ? 'hide-carrying' : ''" @click="openPopover('robot-carrying-id')">
      <span
        v-for="(tool, ind) in organizeCarrying"
        :key="'carrying-' + ind"
      >
        <b-img class="tool-display-image" :src="toolImages[tool[0]]" /> <span>x</span> <span>{{tool[1]}}</span>
      </span>
    </div>
    <b-popover
      v-if="robotCarrying.length"
      target="robot-carrying-id"
      triggers="click"
      placement="left"
    >
      <img class="dialog-button close-popover" :src="permanentImages.buttons.xButton" @click="closePopover('robot-carrying-id')" />
      <b-img
        v-for="(imageName, iInd) in displayCarrying"
        :key="'carrying-popover-image/' + iInd"
        class="tool-display-image"
        :src="toolImages[imageName]"
      ></b-img>
      <span class="tool-display-text" @click="adjustShowAmount"><strong>. . .</strong></span>
    </b-popover>
  </div>
</template>

<script>
import uid from 'uid'
import _ from 'underscore'
import utils from '../services/utils'

export default {
  computed: {
    organizeCarrying () {
      return _.chain(this.robotCarrying)
        .reduce((organized, tool) => {
          organized[tool] = organized[tool] + 1 || 1
          return organized
        }, {})
        .pairs()
        .sortBy((tup) => {
          const values = {kitty: 1, ten: 10, oneHundred: 100, oneThousand: 1000, tenThousand: 10000}
          return values[tup[0]]
        })
        .value()
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
    robotCarrying () {
      return this.$store.getters.getRobotCarrying
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
  }
}
</script>

<style scoped lang="scss">
  $carrying-size: 2vmin;
  $click-color: #B8E986;

  .robot-carrying {
    position: absolute;
    bottom: -1px;
    left: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    flex-wrap: wrap;
    z-index: 10000;
    font-size: $carrying-size;
    background: rgba(0, 0, 0, 0.6);
    border-bottom-right-radius: 0.4vmin;
    border-top-right-radius: 0.4vmin;
    border: 1px solid $click-color;
    border-left: 1.5px solid rgba(0, 0, 0, 0.6);
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
</style>
