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

    x () {
      return this.adminControl.maxLevel.map(i => i._id)
    },
    y () {
      return this.adminControl.maxLevel.map(i => i.count)
    }
  },
  methods: {

    generateChart () {
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
          columns: [
            ['count'].concat(this.y)
          ],
          type: 'bar'
          // axes: {
          //   data2: 'y2' // ADD
          // }
        },
        axis: {
          x: {
            type: 'category',
            categories: this.x
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

</style>
