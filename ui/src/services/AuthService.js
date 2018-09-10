import api from './api'
import $router from './../router'
import $store from '../store/store'

export class AuthService {
  authenticated = false
  userToken = {}
  userProfile = {}
  session = {}

  constructor () {
    this.login = this.login.bind(this)
    this._setSession = this._setSession.bind(this)
    this.logout = this.logout.bind(this)
  }

  _getUserProfile () {
    return JSON.parse(localStorage.getItem('profile'))
  }

  _storeLastRoute () {
    localStorage.setItem('last_location', $router.history.current.fullPath)
  }

  _getUserToken () {
    const tokenId = this.userProfile.sub || this.userProfile.user_id
    api.getUserToken({tokenId: tokenId}, token => {
      this.userToken = token
      this.authenticated = true
      window.onbeforeunload = this._storeLastRoute
      const lastPath = localStorage.getItem('last_location')
      this._getVideoHints()
      console.log(this.authenticated)
      if (lastPath && lastPath !== '/about' && lastPath !== '/auth') {
        $router.push({path: lastPath})
      } else {
        $router.push({path: '/profile'})
      }
    })
  }

  _getVideoHints () {
    api.videoHintSocket.requestHintsTaken((hints) => {
      $store.dispatch('startExistingTimers', hints.remainingTimes)
    })
  }

  _setSession (profile) {
    this.userProfile = profile
    localStorage.setItem('profile', JSON.stringify(profile))
    this._getUserToken()
  }

  _requestSession () {
    api.requestSession((session) => {
      this.session = session
    })
  }

  handleAuthentication () {
    const params = window.location.search
    const provider = localStorage.getItem('authProvider')
      .split('')
      .map((v, i) => {
        if (i === 0) return v.toUpperCase()
        else return v
      })
      .join('')
    api.authorize(provider, params, (profile) => {
      localStorage.removeItem('authProvider')
      this._setSession(profile)
    })
  }

  login () {
    const profile = this._getUserProfile()
    this._requestSession()
    if (profile) {
      this.userProfile = this._getUserProfile()
      this._getUserToken()
    } else {
      $router.push({path: '/auth'})
    }
  }

  logout () {
    localStorage.clear()
    this.authenticated = false
    $router.push({path: '/about'})
  }
}
