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
        .map(i => this.getInfo(i))
        .filter(Boolean)
    }
  },
  methods: {

    getInfo (continent) {
      const lev = continent._id.slice(4)

      if (continent._id.startsWith('0000') && continent._id.length >= 5) {
        return Object.assign({}, continent, {starSystem: 0, title: `P-${lev}`, level: lev})
      } else if (continent._id.startsWith('0010') && continent._id.length >= 5) {
        return Object.assign({}, continent, {starSystem: 1, title: `D -${lev}`, level: lev})
      }

      return null
    },

    generateChart () {
      const d = this.data

      const programming = _.sortBy(d.filter(i => i.starSystem === 0), 'level')

      const digit1 = _.sortBy(d.filter(i => i.starSystem === 1), 'level')

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
            ['Level'].concat(...programming.map(i => i.count), ...digit1.map(i => i.count))
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
