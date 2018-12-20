<template>

  <div class="container-fluid">

    <h3 class="text-left my-3">Signups over time</h3>
    <div class="row">

      <div class="card" style="width: 100%;">

        <div class="card-header">

          <div class="row d-flex justify-content-center align-items-space-between my-1 font-weight-bold">
             <span>
                {{minDate.toLocaleString()}}
              </span>
            <span>
                  -
            </span>
            <span>
                {{maxDate.toLocaleString()}}
            </span>

          </div>

          <div class="row">
            <span class="text-monospace font-weight-bold mx-3">
              {{signupsOverRange.toLocaleString()}}

            </span>

            <span>
              Signups
            </span>

          </div>

        </div>
        <div class="card-body">
          <div class="row">
            <div id="chart"></div>
          </div>
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

    x () {
      return this.data.map(item => {
        const { _id: { month, day, year } } = item

        return new Date(year, month, day)
      })
    },

    y () {
      return this.data.map(item => item.signups)
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

        return this.minDate <= date && date <= this.maxDate
      })
      const signups = d.map(i => i.signups)

      console.log('sign', this.data.length, d.length)
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
    },

    events () {
      return this.adminControl.events.map(event => {
        return {

          value: new Date(event.date),
          text: event.title
        }
      })
    }

  },

  methods: {

    load (column) {
      let options = {

        grid: {
          x: {
            lines: this.events
          }
        }
      }
      if (column === 'signups') {
        Object.assign(options, {
          columns: [
            ['signups'].concat(...this.y)
          ]
        })
      } else if (column === 'total') {
        Object.assign(options, {
          axis: {
            y2: {
              show: true
            }
          },
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

      this.chart.load(options)
      this.activeCharts[column] = true
    },

    unload (column) {
      this.chart.unload(column)

      this.activeCharts[column] = false
    },

    setRange () {
      const { minDate: min, maxDate: max } = this

      this.chart.axis.range({ max: { x: max }, min: { x: min } })
    }
  },

  mounted () {
    const xCol = ['x'].concat(...this.x)

    const yCol = ['signups'].concat(...this.y)

    this.chart = c3.generate({
      bindto: '#chart',

      data: {
        x: 'x',
        columns: [
          xCol,
          yCol
        ]

      },

      axis: {
        x: {
          type: 'timeseries',
          min: this.minDate,
          max: this.maxDate,
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
        },
        y2: {

          show: false
        }
      },
      grid: {
        x: {
          lines: this.events
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
      if (newData !== oldData) {
        const { signups, total } = this.activeCharts

        if (signups) {
          this.load('signups')
        }

        if (total) {
          this.load('total')
        }

        this.setRange()
      }
    }
  }
}
</script>

<style scoped lang="scss">

  @import '~c3/c3.min.css';

  #chart {

    width: 100%;
    min-height: 200px;
  }

  /*.c3-line-total, .c3-line-signups {*/
  /*stroke-width: 0;*/
  /*}*/

</style>
