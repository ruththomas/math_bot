<template>
  <div class="container profile">
    <level-congrats key="level-congrats"></level-congrats>
    <div class="row" style="height: 80%;">
      <space :permanent-images="permanentImages"></space>
      <steps :permanent-images="permanentImages"></steps>
    </div>
    <div class="col-8 controls" style="height: 20%;">
      <social-sharing :message="'Checkout Mathbot.com!'" :size="'1vmin'"></social-sharing>
      <user-profile-controls :permanent-images="permanentImages"></user-profile-controls>
    </div>
  </div>
</template>

<script>
import SplashScreen from './Splash_screen'
import UserProfileControls from './User_profile_controls'
import Steps from './Steps'
import Space from './Space'
import SocialSharing from './Social_sharing'
import LevelCongrats from './Level_congrats'

export default {
  mounted () {
    this.handleCongrats()
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
  methods: {
    handleCongrats () {
      if (this.$route.query.showCongrats === 'true') {
        this.$root.$emit('bv::show::modal', 'level-congrats-modal')
        this.$router.push({query: {}})
      }
    }
  },
  components: {
    SplashScreen,
    UserProfileControls,
    Steps,
    Space,
    SocialSharing,
    LevelCongrats
  }
}
</script>

<style lang="scss">
  .profile {
    height: 100%;
    position: relative;
    .controls {
      padding: 0;
      display: flex;
      .social-sharing {
        .social-links {
          display: flex;
          justify-content: space-evenly;
          flex-direction: column;
          height: 100%;
        }
      }
    }
  }
</style>
