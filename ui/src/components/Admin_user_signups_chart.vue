<template>

  <div class="container-fluid">

    <h3 class="text-left my-3">Signups over time</h3>
    <div class="row">

      <div class="card" style="width: 100%;">

        <div class="card-header">

          <div class="row d-flex justify-content-center align-items-center">
            <span style="font-size: 1.3rem;" class="m-3">
            <span class="text-monospace font-weight-bold">
              {{signupsOverRange.toLocaleString()}}

            </span>
              signups from
              <span>
                {{minDate.toLocaleString()}}
              </span>
              <span>
                to
              </span>
              <span>
                {{maxDate.toLocaleString()}}
              </span>
            </span>
          </div>

        </div>
        <div class="card-body">
          <div id="chart"></div>
        </div>
        <div class="card-footer">

          <div class="row d-flex justify-content-end align-items-center">

            <div class="btn-group">

              <b-btn
                v-if="!activeCharts.total"
                @click="load('total')">
                load cumulative
              </b-btn>

              <b-btn
                v-if="activeCharts.total"
                @click="unload('total')">
                unload cumulative
              </b-btn>

              <b-btn
                v-if="!activeCharts.signups"
                @click="load('signups')">
                load daily
              </b-btn>
              <b-btn
                v-if="activeCharts.signups"
                @click="unload('signups')">
                unload daily
              </b-btn>
            </div>
          </div>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
import c3 from 'c3'
import Datepicker from 'vuejs-datepicker'

export default {
  name: 'UserSignupsChart',
  data () {
    return {
      chart: null,
      x: [],
      y: [],
      activeCharts: {
        signups: false,
        total: false
      }

    }
  },
  computed: {

    data () {
      return this.adminControl.userAccountSignups
    },
    adminControl () {
      return this.$store.getters.getAdminControl
    },

    eventsControl () {
      return this.$store.getters.getEventsControl
    },

    signupsOverRange () {
      const d = this.data.filter(item => {
        const { _id: { year, month, day } } = item

        const date = new Date(year, month, day)

        return new Date(this.eventsControl.minDate) <= date && date <= new Date(this.eventsControl.maxDate)
      })
      const signups = d.map(i => i.signups)
      return signups.reduce((accum, cur) => accum + cur, 0)
    },

    totalSignups () {
      let signups = this.data.map(i => i.signups)
      const _total = []
      signups.reduce((prev, curr, i) => {
        _total[i] = prev + curr
        return _total[i]
      }, 0)

      return _total
    },

    maxDate () {
      return this.eventsControl.maxDate
    },

    minDate () {
      return this.eventsControl.minDate
    }

  },

  methods: {

    load (column) {
      if (column === 'signups') {
        this.chart.load({
          columns: [
            ['signups'].concat(...this.y)
          ]
        })
      } else if (column === 'total') {
        this.chart.load({
          types: {
            total: 'area'
          },
          columns: [
            ['total'].concat(...this.totalSignups)
          ]
        })
      } else {
        throw new Error('unknown col ' + column)
      }
      this.activeCharts[column] = true
    },

    unload (column) {
      this.chart.unload(column)

      this.activeCharts[column] = false
    },

    setRange () {
      const { minDate: min, maxDate: max } = this

      this.chart.axis.range({ max: { x: max }, min: { x: min } })
    },
    _build () {
      this.x = this.data.map(item => {
        const { _id: { month, day, year } } = item

        return new Date(year, month, day)
      })

      this.y = this.data.map(item => item.signups)
    }
  },

  mounted () {
    this._build()

    const chart = document.getElementById('chart')

    this.chart = c3.generate({
      bindto: chart,
      // size: {
      //   height: width * 0.5,
      //   width
      // },
      // transition: {
      //   duration: 500
      // },
      data: {
        x: 'x',
        columns: [
          ['x'].concat(...this.x),
          ['signups'].concat(...this.y)
          // ['total'].concat(...this.total)
        ]
        // axes: {
        //   data2: 'y2' // ADD
        // }
      },

      axis: {
        x: {
          type: 'timeseries',
          min: this.eventsControl.minDate,
          max: this.eventsControl.maxDate,
          label: {
            text: 'Date',
            position: 'outer-middle'
          }
          // tick: {
          //   format: '%Y-%m-%d'
          // }
        },
        y: {
          label: { // ADD
            text: 'Signups per day',
            position: 'outer-middle'
          }
        }
      }
    }, this.options)

    this.activeCharts.signups = true
  },
  components: {
    Datepicker
  },
  watch: {

    minDate (_, __) {
      this.setRange()
    },

    maxDate (_, __) {
      this.setRange()
    },
    data (newData, oldData) {
      const { signups, total } = this.activeCharts

      if (signups) {
        this.load('signups')
      }

      if (total) {
        this.load('total')
      }
    }
  }
}
</script>

<style scoped lang="scss">

  @import '~c3/c3.min.css';

  /*.c3-line-total, .c3-line-signups {*/
  /*stroke-width: 0;*/
  /*}*/

</style>
