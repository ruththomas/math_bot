import Ws from './Ws'

import _ from 'underscore'

export default class AdminControl extends Ws {
  actions = {
    userCount: 'user-count',
    activeUserCount: 'active-user-count',
    signups: 'signups',
    loginsLastWeek: 'logins-last-week',
    levelStats: 'level-stats',
    currentPath: 'current-path',
    maxLevel: 'max-level'
  }

  userCount = 0
  activeUserCount = 0
  last7DaysLoginCount = 0
  userAccountSignups = []
  // todo: convert into obj
  levelStats = []
  currentPath = []
  maxLevel = []

  constructor () {
    super('admin')
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
    const { activeUserCount = -1, status = 'failure', last7DaysLoginCount = '-1', userAccountSignups = [], userCount = '-1', currentPath = [], levelStats = [], maxLevel = [] } = result

    if (status !== 'success') {
      console.error('socket error', result)
      return false
    }
    const count = parseInt(userCount)

    if (count >= 0) {
      this.userCount = count
    }

    const _activeUserCount = parseInt(activeUserCount)

    if (activeUserCount >= 0) {
      this.activeUserCount = _activeUserCount
    }

    const _logins = parseInt(last7DaysLoginCount)

    if (_logins >= 0) {
      this.last7DaysLoginCount = _logins
    }

    if (userAccountSignups.length) {
      this.userAccountSignups = userAccountSignups
    }

    if (currentPath.length) {
      this.currentPath = currentPath
    }

    if (maxLevel.length) {
      this.maxLevel = maxLevel.slice()
      // console.log(Array.from(this.maxLevel))
    }

    if (levelStats.length) {
      const _levelStats = this.levelStats.slice()

      _levelStats.push(levelStats[0])

      this.levelStats = _.uniq(_levelStats, i => i.id)

      console.log('level stats', this.levelStats)
    }
  }
}
