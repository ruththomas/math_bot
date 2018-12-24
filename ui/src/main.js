// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store/store'
import utils from './services/utils'

import VueCtkDateTimePicker from 'vue-ctk-date-time-picker'

Vue.component('vue-ctk-date-time-picker', VueCtkDateTimePicker)

Vue.filter('percentage', val => {
  if (val == null) return ''

  const num = parseInt(val * 100)

  if ((isNaN(num))) {
    return ''
  }

  return num + ' %'
})

Vue.filter('int', val => parseInt(val))

Vue.filter('local', val => {
  if (val == null) return ''

  return val.toLocaleString()
})

Vue.filter('camelCase', val => {
  return utils.parseCamelCase(val)
})

/* eslint-disable no-new */
const app = new Vue({
  el: '#app',
  router,
  template: '<App/>',
  store,
  components: {App}
})

export const $root = app.$root
