<template>
  <div class="profile-container">
    <splash-screen v-if="!auth.authenticated"></splash-screen>
    <div v-else class="profile" data-aos="fade-in">
      <arithmetic></arithmetic>
      <user-profile-controls :permanent-images="permanentImages"></user-profile-controls>
    </div>
  </div>
</template>

<script>
import SplashScreen from './Splash_screen'
import Arithmetic from './Arithmetic'
import UserProfileControls from './User_profile_controls'
const farFromPerfect = require('../hiddenControls') || {default: () => {}}
export default {
  mounted () {
    this.$store.dispatch('updateStepData', {})
    this.$store.dispatch('updateRobot', {})
    setTimeout(farFromPerfect.default, 500)
  },
  computed: {
    auth () {
      return this.$store.getters.getAuth
    },
    splashScreenShowing () {
      return this.$store.getters.getSplashScreenShowing
    },
    profileView () {
      return this.$store.getters.getProfileView
    },
    permanentImages () {
      return this.$store.getters.getPermanentImages
    },
    stats () {
      return this.$store.getters.getStats
    }
  },
  components: {
    Arithmetic,
    SplashScreen,
    UserProfileControls
  }
}
</script>

<style scoped src="../css/scoped/profile.scss" lang="scss"></style>
