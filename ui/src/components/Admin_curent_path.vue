<template>

    <div class="card" style="width: 100%; min-width: 30rem;">
      <div class="card-header">

        <h3>User Current Path</h3>
      </div>
      <div class="card-body">
        <div id="current_path_chart"></div>
      </div>
  </div>

</template>

<script>

import c3 from 'c3'

const ar = [
  {
    '_id': '00004',
    'count': 1
  },
  {
    '_id': '00003',
    'count': 2
  }
]
export default {
  name: 'Admin_curent_path',
  data () {
    return {
      chart: null
    }
  },
  mounted () {
    this.generateChart()
  },

  computed: {

    adminControl () {
      return this.$store.getters.getAdminControl
    },

    x () {
      return this.adminControl.currentPath.map(i => i._id)
    },
    y () {
      return this.adminControl.currentPath.map(i => i.count)
    }
  },
  methods: {

    getCurrentPath () {
      this.adminControl.getCurrentPath()
    },

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
          ]
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
              text: 'currentPath',
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
