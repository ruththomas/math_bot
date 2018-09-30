<template>
  <div class="container profile">
    <div class="row" style="height: 80%;">
      <space :permanent-images="permanentImages"></space>
      <steps :permanent-images="permanentImages"></steps>
    </div>
    <div class="row" style="height: 20%;">
      <user-profile-controls :permanent-images="permanentImages"></user-profile-controls>
      <social-sharing :message="'Checkout Mathbot.com!'" :size="'5vmin'"></social-sharing>
    </div>
  </div>
</template>

<script>
import SplashScreen from './Splash_screen'
import UserProfileControls from './User_profile_controls'
import Steps from './Steps'
import Space from './Space'
import SocialSharing from './Social_sharing'

export default {
  mounted () {
    // this.$store.dispatch('updateStepData', {})
    // this.$store.dispatch('updateRobot', {})
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
    Space,
    SocialSharing
  }
}
</script>

<style scoped lang="scss">
  .profile {
    height: 100%;
    position: relative;
    .row {
      display: flex;
      .social-sharing {
        height: 50%;
        align-self: flex-end;
      }
    }
  }
</style>
