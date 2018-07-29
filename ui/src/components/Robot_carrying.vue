<template>
  <div v-if="robotCarrying.length" class="robot-carrying">
    <span
      v-for="(tool, ind) in organizeCarrying"
      :key="'carrying-' + ind"
    >
      <b-img :src="toolImages[tool[0]]" /> <span>x</span> <span>{{tool[1]}}</span>
    </span>
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
  .robot-carrying {
    display: flex;
    flex-direction: column;
    justify-content: center;
    flex-wrap: wrap;
    z-index: 10000;
    margin-left: 40px;

    span {
      display: flex;
      align-items: center;

      * {
        margin: 0 5px;
      }

      img {
        height: 20px;
        width: 20px;
      }

      p {
        height: 20px;
        font-size: 20px;
      }
    }
  }

  /* Large Phones, landscape*/
  @media only screen and (max-width : 992px) {

  }

  /* Small Devices */
  @media only screen and (max-width : 667px) {
    .robot-carrying {
      min-height: 20px;
    }

    .robot-carrying img {
      height: 10px;
      width: 10px;
    }

    .robot-carrying p {
      height: 10px;
      font-size: 10px;
    }
  }

  /* Extra Small Devices, Phones */
  @media only screen and (max-width : 480px) {
  }

  /* Custom, iPhone 5 Retina */
  @media only screen and (max-width : 320px) {

  }

</style>
