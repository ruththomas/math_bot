<template>
  <div id="robot-carrying-id" v-if="robotCarrying.length" class="robot-carrying">
    <span
      v-for="(tool, ind) in organizeCarrying"
      :key="'carrying-' + ind"
    >
      <b-img class="tool-display-image" :src="toolImages[tool[0]]" /> <span>x</span> <span>{{tool[1]}}</span>
    </span>
    <b-popover
      v-if="robotCarrying.length"
      target="robot-carrying-id"
      triggers="hover click"
      placement="left"
    >
      <b-img
        v-for="(imageName, iInd) in robotCarrying"
        :key="'carrying-popover-image/' + iInd"
        class="tool-display-image"
        :src="toolImages[imageName]"
      ></b-img>
    </b-popover>
  </div>
</template>

<script>
import uid from 'uid'
import assets from '../assets/assets'
import _ from 'underscore'

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
      return assets.tools
    },
    robotCarrying () {
      return this.$store.getters.getRobotCarrying
    }
  },
  methods: {
    uID () {
      return uid(7)
    }
  }
}
</script>

<style scoped lang="scss">
  $carrying-size: 20px;
  $click-color: #B8E986;

  .robot-carrying {
    position: absolute;
    bottom: -1px;
    left: calc(100% - 0.5px);
    display: flex;
    flex-direction: column;
    justify-content: center;
    flex-wrap: wrap;
    z-index: 10000;
    font-size: $carrying-size;
    background: rgba(0, 0, 0, 0.6);
    border-radius: 3px;
    border: 1px solid $click-color;
    border-left: 1.5px solid rgba(0, 0, 0, 0.6);
    cursor: pointer;

    span {
      display: flex;
      align-items: center;
      margin-left: 3px;
    }
  }

  .tool-display-image {
    height: $carrying-size;
    width: $carrying-size;
  }

  @media only screen and (max-width: 823px) and (orientation: landscape) {
    $carrying-size: 12px;

    .robot-carrying {
      display: flex;
      flex-direction: column;
      justify-content: center;
      flex-wrap: wrap;
      z-index: 10000;
      font-size: $carrying-size;

      span {
        display: flex;
        align-items: center;

        img {
          height: $carrying-size;
          width: $carrying-size;
        }
      }
    }

    .tool-display-image {
      height: $carrying-size;
      width: $carrying-size;
    }
  }

  @media only screen and (max-width: 736px) and (orientation: landscape) {
    $carrying-size: 12px;

    .robot-carrying {
      display: flex;
      flex-direction: column;
      justify-content: center;
      flex-wrap: wrap;
      z-index: 10000;
      font-size: $carrying-size;

      span {
        display: flex;
        align-items: center;

        img {
          height: $carrying-size;
          width: $carrying-size;
        }
      }
    }

    .tool-display-image {
      height: $carrying-size;
      width: $carrying-size;
    }
  }

  @media only screen and (max-width: 667px) and (orientation: landscape) {
    $carrying-size: 8px;

    .robot-carrying {
      display: flex;
      flex-direction: column;
      justify-content: center;
      flex-wrap: wrap;
      z-index: 10000;
      font-size: $carrying-size;

      span {
        display: flex;
        align-items: center;

        img {
          height: $carrying-size;
          width: $carrying-size;
        }
      }
    }

    .tool-display-image {
      height: $carrying-size;
      width: $carrying-size;
    }
  }

  @media only screen and (max-width: 568px) and (orientation: landscape) {
    $carrying-size: 8px;

    .robot-carrying {
      display: flex;
      flex-direction: column;
      justify-content: center;
      flex-wrap: wrap;
      z-index: 10000;
      font-size: $carrying-size;

      span {
        display: flex;
        align-items: center;

        img {
          height: $carrying-size;
          width: $carrying-size;
        }
      }
    }

    .tool-display-image {
      height: $carrying-size;
      width: $carrying-size;
    }
  }

  @media only screen and (max-width: 414px) {
    $carrying-size: 12px;

    .robot-carrying {
      display: flex;
      flex-direction: column;
      justify-content: center;
      flex-wrap: wrap;
      z-index: 10000;
      font-size: $carrying-size;

      span {
        display: flex;
        align-items: center;

        img {
          height: $carrying-size;
          width: $carrying-size;
        }
      }
    }

    .tool-display-image {
      height: $carrying-size;
      width: $carrying-size;
    }
  }

  @media only screen and (max-width : 375px) {
    $carrying-size: 8px;

    .robot-carrying {
      display: flex;
      flex-direction: column;
      justify-content: center;
      flex-wrap: wrap;
      z-index: 10000;
      font-size: $carrying-size;

      span {
        display: flex;
        align-items: center;

        img {
          height: $carrying-size;
          width: $carrying-size;
        }
      }
    }

    .tool-display-image {
      height: $carrying-size;
      width: $carrying-size;
    }
  }

  @media only screen and (max-width : 320px) {
    $carrying-size: 8px;

    .robot-carrying {
      display: flex;
      flex-direction: column;
      justify-content: center;
      flex-wrap: wrap;
      z-index: 10000;
      font-size: $carrying-size;

      span {
        display: flex;
        align-items: center;

        img {
          height: $carrying-size;
          width: $carrying-size;
        }
      }
    }

    .tool-display-image {
      height: $carrying-size;
      width: $carrying-size;
    }
  }
</style>
