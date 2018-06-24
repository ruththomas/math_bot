<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
import api from './services/api'
import utils from './services/utils'

export default {
  name: 'app',
  mounted () {
    window.scrollTo(0, 1)
    this.auth.isAuthenticated()
    utils.watcher(() => !this.auth.authenticated, () => {
      api.videoHintSocket.requestHintsTaken(res => {
        this.$store.dispatch('startExistingTimers', {context: this, hintsTaken: res.remainingTimes})
      })
    })
  },
  computed: {
    auth () {
      return this.$store.getters.getAuth
    }
  }
}
</script>

<style src="./css/global/styles.css"></style>
