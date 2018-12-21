import Ws from './Ws'

import _ from 'underscore'
import { AdminEvent } from './AdminEvent'
import { LevelStats } from './LevelStats'

export default class AdminControl extends Ws {
  actions = {
    userCount: 'user-count',
    activeUserCount: 'active-user-count',
    signups: 'signups',
    loginsLastWeek: 'logins-last-week',
    levelStats: 'level-stats',
    currentPath: 'current-path',
    maxLevel: 'max-level',
    getEvents: 'events-get',
    postEvents: 'events-post',
    putEvent: 'events-put',
    delEvent: 'events-delete'
  }

  userCount = 0
  activeUserCount = 0
  last7DaysLoginCount = 0
  userAccountSignups = []
  // todo: convert into obj issues w/ vue rendering undefined params of obj
  levelStats = []
  currentPath = []
  maxLevel = []
  events = []
  event = null
  confirmDeleteEvent = {}

  constructor () {
    super('admin')
  }

  getEvents () {
    this._send(JSON.stringify({action: this.actions.getEvents}))
  }

  postEvent (event) {
    this._send(JSON.stringify({
      action: this.actions.postEvents,
      newEvent: AdminControl._mapEventToRequest(event)}))
  }

  putEvent (event) {
    this._send(JSON.stringify({
      action: this.actions.putEvent,
      event: AdminControl._mapEventToRequest(event)}))
  }

  handleDelEvent (event) {
    this.delEvent(event)
  }

  delEvent (event) {
    this._send(JSON.stringify({
      action: this.actions.delEvent,
      event: AdminControl._mapEventToRequest(event)}))
  }

  static _mapEventToRequest (event) {
    return Object.assign({}, event, {

      date: new Date(event.date).getTime()
    })
  }

  /*

    Attempt to open socket, if non-admin user opening fails, and need to handle
    @returns Promise(string | Error)

   */

  openSocket () {
    return new Promise((resolve, reject) => {
      try {
        this._getWsPath(this.connection, path => {
          this._ws = new WebSocket(path)
          this._ws.onopen = () => {
            console.log(`${this.connection.toUpperCase()} WS OPEN`)
            this._wsOnMessage(this.handleSocketResponse.bind(this))
            resolve('success')
          }
          this._ws.onerror = (err) => {
            console.error(`${this.connection.toUpperCase()} WS FAILED`, err)

            reject(err)
          }
          this._ws.onclose = () => {
            console.log(`${this.connection.toUpperCase()} WS CLOSED`)
          }
        })
      } catch (e) {
        console.error('error')
        reject(e)
      }
    })
  }

  /*

    fetch user count from socket request
    @param cb function
    @returns callback w/ {status: string = success, userCount: string = 1}
   */

  getActiveUserCount () {
    this._send(JSON.stringify({ action: this.actions.activeUserCount })) // { status: 'success',userCount: "1"}
  }

  getUserCount () {
    this._send(JSON.stringify({ action: this.actions.userCount })) // { status: 'success',userCount: "1"}
  }

  getDailySignups () {
    this._send(JSON.stringify({ action: this.actions.signups }))
  }

  getLastWeekLogins () {
    this._send(JSON.stringify({
      action: this.actions.loginsLastWeek
    }))
  }

  getLevelStats (_id) {
    console.log('id', _id)
    this._send(JSON.stringify({ action: this.actions.levelStats, level: _id }))
  }

  getMaxLevelStats () {
    this._send(JSON.stringify({ action: this.actions.maxLevel }))
  }

  handleSocketResponse (result) {
    const {
      activeUserCount = null,
      status = 'failure',
      last7DaysLoginCount = null,
      events = null,
      event = null,
      userAccountSignups = null,
      userCount = null,
      currentPath = null,
      levelStats = null,
      maxLevel = null
    } = result

    if (status !== 'success') {
      console.error('socket error', result)
      return false
    }

    if (userCount) {
      this.userCount = parseInt(userCount)
    }

    if (activeUserCount) {
      this.activeUserCount = parseInt(activeUserCount)
    }

    if (last7DaysLoginCount) {
      this.last7DaysLoginCount = parseInt(last7DaysLoginCount)
    }

    if (userAccountSignups) {
      const signups = userAccountSignups.map(item => {
        const {_id: {month, day, year}} = item
        return Object.assign({}, item, {
          date: new Date(year, month, day)
        })
      })
      this.userAccountSignups = _.sortBy(signups, 'date')
    }

    if (currentPath) {
      this.currentPath = currentPath
    }

    if (maxLevel) {
      this.maxLevel = maxLevel
      // console.log(Array.from(this.maxLevel))
    }

    if (events) {
      this.events = events.map(evt => new AdminEvent(evt))
    }

    if (event) {
      this.event = event

      this.getEvents()
    }

    if (levelStats) {
      const stats = this.levelStats.concat(...levelStats.map(l => new LevelStats(l)))
      this.levelStats = _.uniq(stats, i => i.id)
    }
  }
}
