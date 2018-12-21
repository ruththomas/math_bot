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

  levelStats = {'0': '', '001017': '', '001610': '', '00031': '', '00143': '', '00165': '', '00149': '', '001517': '', '00009': '', '001210': '', '00106': '', '001': '', '00154': '', '00042': '', '001616': '', '000410': '', '00138': '', '001515': '', '001014': '', '00116': '', '001314': '', '00020': '', '001215': '', '0001': '', '00127': '', '00110': '', '000313': '', '001115': '', '00121': '', '00004': '', '0016': '', '00019': '', '001613': '', '001310': '', '00049': '', '00140': '', '00111': '', '0000': '', '001415': '', '00144': '', '00008': '', '00101': '', '001112': '', '00045': '', '001015': '', '00015': '', '00133': '', '00160': '', '00038': '', '001110': '', '000414': '', '0011': '', '00122': '', '00026': '', '00131': '', '0013': '', '001211': '', '001516': '', '00032': '', '00151': '', '001317': '', '00142': '', '000415': '', '00166': '', '00043': '', '00048': '', '000314': '', '00109': '', '00120': '', '00005': '', '00102': '', '001617': '', '000411': '', '0002': '', '00139': '', '00107': '', '001512': '', '00037': '', '00162': '', '001116': '', '001313': '', '000310': '', '00112': '', '00123': '', '00103': '', '001416': '', '0012': '', '001214': '', '001012': '', '00161': '', '00156': '', '00132': '', '00145': '', '0010': '', '00117': '', '00128': '', '00027': '', '00150': '', '00167': '', '00016': '', '00134': '', '001411': '', '00152': '', '00044': '', '001113': '', '00033': '', '0014': '', '00022': '', '00163': '', '00119': '', '000412': '', '00169': '', '000311': '', '00000': '', '001513': '', '00006': '', '00158': '', '000010': '', '001217': '', '00047': '', '00011': '', '00104': '', '0003': '', '00036': '', '00113': '', '000112': '', '00124': '', '00001': '', '00023': '', '00159': '', '001013': '', '001213': '', '00135': '', '001312': '', '001011': '', '00012': '', '001510': '', '00040': '', '00129': '', '000': '', '00028': '', '00146': '', '001612': '', '00118': '', '001316': '', '001412': '', '00017': '', '00155': '', '00148': '', '00014': '', '00': '', '00137': '', '00105': '', '000110': '', '001514': '', '00153': '', '00041': '', '00034': '', '001413': '', '00164': '', '001216': '', '00100': '', '0015': '', '001615': '', '00010': '', '0004': '', '001417': '', '00030': '', '001315': '', '00157': '', '000413': '', '00168': '', '001114': '', '000312': '', '00021': '', '00115': '', '000011': '', '00125': '', '00039': '', '001410': '', '00136': '', '00024': '', '001117': '', '001311': '', '00013': '', '00141': '', '00002': '', '00130': '', '000012': '', '00108': '', '00114': '', '00147': '', '000111': '', '00126': '', '00003': '', '001614': '', '001414': '', '00035': '', '00025': '', '001016': '', '00018': '', '00046': '', '001010': '', '001212': '', '001511': '', '00007': '', '001611': '', '001111': ''}
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
      const levels = levelStats.map(l => new LevelStats(l))

      levels.forEach(level => {
        Object.assign(this.levelStats, {
          [level.id]: level
        })
      })
    }
  }
}
