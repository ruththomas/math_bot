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

// const ar = [
//   {
//     '_id': '00004',
//     'count': 1
//   },
//   {
//     '_id': '00003',
//     'count': 2
//   }
// ]
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
      return this.adminControl.maxLevel.map(i => this.getInfo(i)).filter(Boolean)
    }
  },
  methods: {

    getInfo (continent) {
      const lev = continent._id.slice(4)
      if (continent._id.startsWith('0000')) {
        return Object.assign({}, continent, {starSystem: 0, title: `P-${lev}`})
      } else if (continent._id.startsWith('0010')) {
        return Object.assign({}, continent, {starSystem: 1, title: `Digit-${lev}`})
      }

      return null
    },

    generateChart () {
      const d = this.data

      const x = d.map(i => i.title)

      const programming = d.filter(i => i.starSystem === 0).map(i => i.count)
      const digit1 = d.filter(i => i.starSystem === 1).map(i => i.count)

      this.chart = c3.generate({
        bindto: document.getElementById('current_path_chart'),
        // size: {
        //   height: width * 0.5,
        //   width
        // },
        // transition: {
        //   duration: 500
        // },
        data: {
          color: function (color, d) {
            // d will be 'id' when called for legends
            console.log(color, d)

            if (d.id === 'programming') {
              return 'red'
            } else if (d.id === '1 digit') {
              return 'blue'
            }
          },
          columns: [
            ['programming'].concat(...programming),
            ['1 digit'].concat(...digit1)
            // ['count'].concat(...y)
          ],
          type: 'bar'

          // axes: {
          //   data2: 'y2' // ADD
          // }
        },
        axis: {
          x: {
            type: 'bar',
            categories: x
          },
          y: {
            label: { // ADD
              text: 'number of users',
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
