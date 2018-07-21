import auth0 from 'auth0-js'
import { AUTH0_DOMAIN, AUTH0_ID } from '../keys'
import router from './../router'
import api from '../services/api'

class AuthService {
  authenticated = this.isAuthenticated()
  userToken = {}
  userProfile = {}

  constructor () {
    this.login = this.login.bind(this)
    this.setSession = this.setSession.bind(this)
    this.logout = this.logout.bind(this)
    this.isAuthenticated = this.isAuthenticated.bind(this)
  }

  auth0 = new auth0.WebAuth({
    domain: AUTH0_DOMAIN,
    clientID: AUTH0_ID,
    redirectUri: 'http://localhost:8080/callback',
    audience: 'https://math-academy.auth0.com/userinfo',
    responseType: 'token id_token',
    scope: 'openid profile'
  })

  login () {
    this.auth0.authorize()
  }

  handleAuthentication () {
    this.auth0.parseHash((err, authResult) => {
      if (authResult && authResult.accessToken && authResult.idToken) {
        console.log('AUTHENTICATED')
        this.setSession(authResult)
      } else if (err) {
        console.log('NOT AUTHENTICATED')
        router.push({path: '/about'})
        console.log(err)
      }
    })
  }

  getUserProfile () {
    return JSON.parse(localStorage.getItem('profile'))
  }

  getUserToken () {
    this.userProfile = this.getUserProfile()
    const tokenId = this.userProfile.sub || this.userProfile.user_id
    api.getUserToken({tokenId: tokenId}, token => {
      this.userToken = token
      this.authenticated = true
      router.push({path: '/profile'})
    })
  }

  getProfile (accessToken, continueSetSession) {
    this.auth0.client.userInfo(accessToken, (err, profile) => {
      if (err) {
        console.error(err)
        this.logout()
      }
      continueSetSession(profile)
    })
  }

  setSession (authResult) {
    this.getProfile(authResult.accessToken, profile => {
      // Set Access Token time limit
      const expiresAt = JSON.stringify(
        authResult.expiresIn * 2.592e+9 + new Date().getTime()
      )
      localStorage.setItem('access_token', authResult.accessToken)
      localStorage.setItem('id_token', authResult.idToken)
      localStorage.setItem('expires_at', expiresAt)
      localStorage.setItem('profile', JSON.stringify(profile))
      this.getUserToken()
    })
  }

  logout () {
    // Clear Access Token and ID Token from local storage
    localStorage.removeItem('access_token')
    localStorage.removeItem('id_token')
    localStorage.removeItem('expires_at')
    localStorage.removeItem('profile')
    this.authenticated = false
    router.push({path: '/marketing'})
  }

  isAuthenticated () {
    const expiresAt = JSON.parse(localStorage.getItem('expires_at'))
    const expired = new Date().getTime() < expiresAt
    if (expired) {
      this.getUserToken()
    }
  }
}

export default AuthService
