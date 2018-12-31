<template>

  <div class="card" style="width: 100%; min-width: 30rem;">
    <div class="card-header">
      <h3>How Max Levels Are Distributed</h3>
    </div>
    <div class="card-body">
      <div id="current_path_chart"></div>
    </div>
  </div>

</template>

<script>

import c3 from 'c3'
import _ from 'underscore'

export default {
  name: 'AdminMaxLevel',
  data () {
    return {
      chart: null
    }
  },
  mounted () {
    this.generateChart()
  },
  // fixme: watch data

  // watch: {
  //   y (newData, oldData) {
  //     this.chart.load({
  //       columns: ['count'].concat(...newData)
  //     })
  //   }
  // },

  computed: {

    adminControl () {
      return this.$store.getters.getAdminControl
    },

    data () {
      return Object.values(this.adminControl.maxLevel)
        .filter(level => level._id.length >= 5)
        .map(i => this.getInfo(i))
    },
    levelControl () {
      return this.$store.getters.getLevelControl
    }
  },
  methods: {

    getInfo (continent) {
      const {_id} = continent

      let [superCluster, galaxy, starSystem, planet, ..._continent] = _id

      let level = Number(_continent.join('')) + 1

      return Object.assign({}, continent, {superCluster, galaxy, starSystem, planet, level})
    },

    generateChart () {
      const mapToGraph = (item) => {
        const starSystem = this.levelControl.galaxy.starSystems[item.starSystem]

        const {planets, stats: {name: starSystemName}} = starSystem

        const planet = planets[item.planet]

        const {stats: {name: planetName}} = planet

        return Object.assign({}, item, {starSystemName, planetName, title: `${starSystemName} ${planetName} ${item.level}`})
      }

      const d = this.data.map(mapToGraph)

      const programming = _.sortBy(
        d.filter(i => i.starSystem === '0')
        , i => i.planet + i.level)

      const digit1 = _.sortBy(d.filter(i => i.starSystem === '1'), i => i.planet + i.level)

      this.chart = c3.generate({
        bindto: document.getElementById('current_path_chart'),
        data: {
          color: function (color, d) {
            if (programming.length <= d.index) {
              return 'orange'
            }
            return 'steelblue'
          },
          columns: [
            ['Count'].concat(...programming.map(i => i.count), ...digit1.map(i => i.count))
          ],
          type: 'bar'

        },
        axis: {
          x: {
            type: 'category',
            categories: [...programming.map(i => i.title), ...digit1.map(i => i.title)]
          },
          y: {
            label: { // ADD
              text: 'number',
              position: 'outer-middle'
            }
          }
        }
      })
    }
  }

}
</script>

<style scoped>
  @import '~c3/c3.min.css';

  #current_path_chart {

    width: 100%;
    min-height: 200px;
  }

</style>
