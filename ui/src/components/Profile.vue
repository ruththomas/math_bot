<template>
  <div class="profile-container">
    <div v-if="auth.authenticated" class="profile">
      <space :permanent-images="permanentImages"></space>
      <steps :permanent-images="permanentImages"></steps>
    </div>
    <user-profile-controls :permanent-images="permanentImages"></user-profile-controls>
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

<style scoped lang="scss">
  .profile-container {
    width: 100%;
    max-width: 1166px;
    height: 100%;
    margin: 0 auto;
    display: flex;
    align-items: center;
  }

  .profile {
    display: flex;
    width: 100%;
    height: 100%;
    max-height: 900px;
    overflow: hidden;
    margin: 0 auto;
    justify-content: space-between;
    /*align-items: center;*/
    align-self: center;
  }
</style>
