<template>
 <div
   class="user-signups-per-month-chart container-fluid"
   :class="adminTheme"
 >
   <div class="row">
     <div class="card" style="width: 100%;">
       <div class="card-header">
         <h4 class="text-left">New player accounts per month</h4>
       </div>
       <div class="card-body">
         <div class="row">
           <div id="chart_total" class="chart"></div>
         </div>
       </div>
     </div>
   </div>
 </div>
</template>

<script>
import c3 from 'c3'
import Datepicker from 'vuejs-datepicker'
import _ from 'underscore'

const months = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December']
export default {
  name: 'UserSignupsPerMonthChart',
  data () {
    return {
      chart: null
    }
  },
  computed: {

    data () {
      return this.adminControl.userAccountSignups
    },

    adminTheme () {
      return this.$store.getters.getAdminTheme
    },

    total_ () {
      const groupByMonth = this.data.reduce((accum, item) => {
        const {_id: {year, month: _month}} = item

        const month = _month.toString()

        if (!(accum && accum[month])) {
          accum[month] = Object.assign({}, {signups: item.signups, date: new Date(year, Number(month))})
        } else {
          accum[month].signups += item.signups
        }

        return accum
      }, {})

      return _.sortBy(groupByMonth, i => i.date)
    },

    x () {
      return this.data.map(item => {
        const { _id: { month, day, year } } = item

        return new Date(year, month, day)
      })
    },

    adminControl () {
      return this.$store.getters.getAdminControl
    },

    eventsControl () {
      return this.$store.getters.getEventsControl
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

          value: event.date,
          text: event.title
        }
      })
    }

  },

  methods: {

    load () {
      let options = {

        unload: true,
        grid: {
          x: {
            lines: this.events
          }
        },
        columns: [
          ['total'].concat(...this.totalSignups)
        ]
      }
      this.chart.load(options)
    },
    setRange () {
      const { minDate: min, maxDate: max } = this

      this.chart.axis.range({ max: { x: max }, min: { x: min } })
    }
  },

  mounted () {
    const xCol = ['x'].concat(...this.total_.map(i => i.date))

    const yCol = ['created'].concat(...this.total_.map(i => i.signups))
    this.chart = c3.generate({
      bindto: `#chart_total`,

      data: {
        colors: {
          'created': '#B8E986'
        },
        x: 'x',
        // type: 'bar',
        columns: [
          xCol,
          yCol
        ]

      },

      axis: {
        x: {
          type: 'timeseries',
          label: {
            text: 'Created',
            position: 'middle'
          },
          tick: {
            // format: 'MMMM'
            format: function (x) {
              return months[x.getMonth()] + ', ' + x.getFullYear()
            }
          }
        },
        y: {
          label: { // ADD
            text: 'Number',
            position: 'outer-middle'
          }
        }
      },
      grid: {
        x: {
          lines: this.events
        }
      }
    }, this.options)
  },
  components: {
    Datepicker
  }

}
</script>

<style scoped lang="scss">

  .chart {

    width: 100%;
    min-height: 200px;
  }

  .user-signups-per-month-chart.dark {

    .card {
      background-color: #303030;
      border: 1px solid rgba(0, 0, 0, 0.125);

    }

    .card-header {
      color: inherit;
      background-color: #444;
      border-bottom: 1px solid rgba(0, 0, 0, 0.125);
    }

  }

</style>
