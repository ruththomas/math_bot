<template>
  <div class="container profile">
    <div v-if="levelControl.galaxy !== null" class="star-system-nav">
      <level-congrats key="level-congrats"></level-congrats>
      <b-button
        v-for="(starSystem, ind) in levelControl.galaxy.starSystems"
        :key="'star-system/' + ind"
        @click="levelControl.updateStarSystem(ind)"
        class="btn-dark"
        :class="Number(starSystemShowing) === ind ? 'selected' : ''"
      >{{starSystem.stats.name}}</b-button>
    </div>
    <div v-if="levelControl.galaxy !== null" class="row" style="height: 90%;">
      <div class="star-systems">
        <star-system></star-system>
      </div>
    </div>
    <div class="col-8 controls" style="height: 20%;">
      <social-sharing :size="'1vmin'"></social-sharing>
      <user-profile-controls :permanent-images="permanentImages"></user-profile-controls>
    </div>
  </div>
</template>

<script>
import SplashScreen from './Splash_screen'
import UserProfileControls from './User_profile_controls'
import SocialSharing from './Social_sharing'
import LevelCongrats from './Level_congrats'
import api from '../services/api'
import StarSystem from './Star_system'

export default {
  mounted () {
    this.handleCongrats()
    setTimeout(this.slideToCurrent, 50)
  },
  computed: {
    starSystemShowing () {
      return this.levelControl.path[2]
    },
    swiper () {
      return this.$refs.galaxySwiper.swiper
    },
    levelControl () {
      return this.$store.getters.getLevelControl
    },
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
    },
    tokenId () {
      return this.$store.getters.getTokenId
    }
  },
  methods: {
    handleCongrats () {
      const showCongrats = this.$route.query.showCongrats
      if (showCongrats) {
        this.$root.$emit('bv::show::modal', 'level-congrats-modal')
      }
    },
    changeSystem (dir) {
      const newPos = this.starSystemShowing + dir
      const starSystems = this.levelControl.galaxy.starSystems
      if (newPos < 0) this.starSystemShowing = 0
      else if (newPos > starSystems.length - 1) this.starSystemShowing = starSystems.length - 1
      else this.starSystemShowing = newPos
    },
    goToSandbox () {
      this.$store.dispatch('updateRunCompiled', this)
      api.switchLevel({tokenId: this.tokenId, level: 'Sandbox', step: '1'}, (res) => {
        this.$store.dispatch('updateStats', res.body)
        this.$router.push({path: '/robot'})
      })
    }
  },
  components: {
    SplashScreen,
    UserProfileControls,
    SocialSharing,
    LevelCongrats,
    StarSystem
  }
}
</script>

<style lang="scss">
  $font-color: #ffffff;
  .profile {
    height: 100%;
    position: relative;
    .controls {
      position: absolute;
      bottom: 0;
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
    .row {
      position: relative;
      .star-systems {
        height: 100%;
        width: 100%;
      }
    }
    .star-system-nav {
      display: flex;
      position: absolute;
      z-index: 100;
      right: 0;
      justify-content: space-evenly;
      .btn {
        color: $font-color;
        display: flex;
        justify-content: center;
        padding: 3%;
        margin: 0.4em;
        border-radius: 0.25rem!important;
        background-color: #000000;
        font-size: 1.1em;
        opacity: 0.7;
      }
      .selected {
        opacity: 1;
        border: 1px solid #ffffff;
      }
    }
  }
</style>
