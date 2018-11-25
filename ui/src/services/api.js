import Vue from 'vue'
import vueResource from 'vue-resource'

Vue.use(vueResource)

export default {
  requestSession (successCb, errCb) {
    Vue.http.get('/api/auth/requestSession')
      .then(res => res.body)
      .then(successCb)
      .catch(errCb)
  },

  resumeSession (successCb, errCb) {
    Vue.http.post('/api/auth/resumeSession', {})
      .then(res => res.body)
      .then(successCb)
      .catch(errCb)
  },

  authorize (provider, params, successCb, errCb) {
    Vue.http.get(`/api/auth/authorize${provider}${params}`)
      .then(res => res.body)
      .then(successCb)
      .catch(errCb)
  },

  signup (form, successCb, errCb) {
    Vue.http.post('/api/auth/signup', form)
      .then(res => res.body)
      .then(successCb)
      .catch(errCb)
  },

  login (form, successCb, errCb) {
    Vue.http.post('/api/auth/authorizeMathbot', form)
      .then(res => res.body)
      .then(successCb)
      .catch(errCb)
  },

  logout (cb) {
    Vue.http.get('/api/auth/logout')
      .then(res => res.body)
      .then(cb)
      .catch(console.error)
  },

  existsCheck (email, successCb, errCb) {
    Vue.http.post('/api/auth/existsCheck', {username: email})
      .then(res => res.body)
      .then(successCb)
      .catch(errCb)
  },

  recoverPassword (email, successCb, errCb) {
    Vue.http.post('/api/auth/recoverPassword', {email: email})
      .then(res => res.body)
      .then(successCb)
      .catch(errCb)
  },

  updatePassword (params, updateForm, successCb, errCb) {
    Vue.http.post('/api/auth/updatePassword' + params, updateForm)
      .then(res => res.body)
      .then(successCb)
      .catch(errCb)
  }
}
