<template>
 <div class="container-fluid">
   <div class="row">
     <div class="card" style="width: 100%;">
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

export default {
  name: 'TotalUserSignupsChart',
  data () {
    return {
      chart: null
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
    const xCol = ['x'].concat(...this.x)

    const yCol = ['total'].concat(...this.totalSignups)
    this.chart = c3.generate({
      bindto: `#chart_total`,

      data: {
        colors: {
          'total': '#B8E986'
        },
        x: 'x',
        // type: 'area',
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
            text: 'Total Signups',
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
  },
  watch: {

    minDate (_, __) {
      this.setRange()
    },

    maxDate (_, __) {
      this.setRange()
    }
    // data (newData, oldData) {
    //   if (newData.length !== oldData.length) {
    //     this.load()
    //     this.setRange()
    //   }
    // }
  }
}
</script>

<style scoped lang="scss">

  @import '~c3/c3.min.css';

  .chart {

    width: 100%;
    min-height: 200px;
  }

  /*.c3-line-total, .c3-line-signups {*/
  /*stroke-width: 0;*/
  /*}*/

</style>
