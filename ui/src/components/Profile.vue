<template>
  <div class="profile-container">
    <splash-screen v-if="!auth.authenticated"></splash-screen>
    <div v-else class="profile" data-aos="fade-in">
      <div class="profile-action">
        <space :permanent-images="permanentImages"></space>
        <steps :permanent-images="permanentImages"></steps>
      </div>
      <user-profile-controls :permanent-images="permanentImages"></user-profile-controls>
    </div>
  </div>
</template>

<script>
import SplashScreen from './Splash_screen'
import UserProfileControls from './User_profile_controls'
import Steps from './Steps'
import Space from './Space'

export default {
  mounted () {
    this.$store.dispatch('updateStepData', {})
    this.$store.dispatch('updateRobot', {})
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
    },
    currentUserName () {
      let currentUser = this.$store.getters.getCurrentUser
      if (currentUser === null) {
        return 'Profile'
      } else {
        return currentUser.given_name || currentUser.nickname
      }
    },
    step () {
      return this.$store.getters.getStep
    }
  },
  components: {
    SplashScreen,
    UserProfileControls,
    Steps,
    Space
  }
}
</script>

<style scoped src="../css/scoped/profile.scss" lang="scss"></style>
