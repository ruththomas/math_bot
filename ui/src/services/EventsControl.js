const moment = require('moment')

const days = 30

export class EventsControl {
  minYear = '2018'
  days = days
  minDate = moment().subtract(days, 'days').toDate() // 28 days
  maxDate = new Date()

  action = 'before'
  opt = 'days'

  actions = ['previous', 'current', 'before', 'after', 'on', 'between']
  opts = ['days', 'weeks', 'months', 'years']

  updateFilter (action = 'previous', days = 30, opt = 'days', day = new Date(), minDate = new Date(), maxDate = new Date()) {
    if (!(this.actions.includes(action))) {
      throw new Error('invalid request')
    }

    var d = moment().subtract(days, opt).toDate()

    this.days = days
    this.action = action
    this.opt = opt

    if (action === 'previous') {
      this.minDate = d
    } else if (action === 'before') {
      this.minDate = new Date(this.minYear)
      this.maxDate = maxDate
    } else if (action === 'after') {
      this.minDate = minDate

      this.maxDate = new Date()
    } else if (action === 'current') {
      this.minDate = moment().startOf(opt).toDate()
      this.maxDate = moment().endOf(opt).toDate()
    } else if (action === 'on') {
      let min = moment(day).startOf('day').toDate()
      let max = moment(day).endOf('day').toDate()

      this.minDate = min
      this.maxDate = max
    } else if (action === 'between') {
      this.minDate = minDate
      this.maxDate = maxDate
    }
  }
}
