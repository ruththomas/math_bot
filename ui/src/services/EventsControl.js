const moment = require('moment')

const days = 30

export class EventsControl {
  minYear = 2018
  days = days
  minDate = moment().subtract(days, 'days').toDate() // 28 days
  maxDate = new Date()

  verb = 'previous'
  opt = 'days'

  verbs = ['previous', 'current', 'before', 'after', 'on', 'between']
  opts = ['days', 'weeks', 'months', 'years']

  updateFilter (verb = 'previous', days = 30, opt = 'days', day = new Date(), minDate = new Date(), maxDate = new Date()) {
    console.log('upate filter', arguments)
    if (!(this.verbs.includes(verb))) {
      throw new Error('invalid request')
    }

    var d = moment().subtract(days, opt).toDate()

    this.days = days
    this.verb = verb
    this.opt = opt

    if (verb === 'previous') {
      this.minDate = d
    } else if (verb === 'before') {
      this.minDate = new Date(2018)
      this.maxDate = minDate
    } else if (verb === 'after') {
      this.minDate = minDate

      this.maxDate = new Date()
    } else if (verb === 'current') {
      this.minDate = moment().startOf(opt).toDate()
      this.maxDate = moment().endOf(opt).toDate()
    } else if (verb === 'on') {
      let min = moment(day).startOf('day').toDate()
      let max = moment(day).endOf('day').toDate()

      this.minDate = min
      this.maxDate = max
    } else if (verb === 'between') {
      this.minDate = minDate
      this.maxDate = maxDate
    }
  }
}
