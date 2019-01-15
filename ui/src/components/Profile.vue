<template>
  <div class="container profile">
    <splash-screen v-if="levelControl.galaxy === null"></splash-screen>
    <div v-else class="profile-top">
      <div v-if="levelControl.galaxy !== null" class="star-system-nav">
        <level-congrats key="level-congrats"></level-congrats>
        <star-system-congrats key="star-system-congrats"></star-system-congrats>
      </div>
      <div v-if="levelControl.galaxy !== null" class="row star-systems-slot">
        <div class="star-systems">
          <star-system></star-system>
        </div>
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
import LevelCongrats from './Planet_congrats'
import StarSystemCongrats from './Star_system_congrats'
import StarSystem from './Star_system'
import BackgroundSound from '../services/sounds/BackgroundSound'

export default {
  mounted () {
    this.handleCongrats()
    this.soundControl.addSound({name: 'robotBackground', sound: new BackgroundSound()})
  },
  beforeDestroy () {
    this.soundControl.removeSounds(['robotBackground'])
  },
  computed: {
    soundControl () {
      return this.$store.getters.getSoundControl
    },
    starSystemShowing () {
      return this.levelControl.path[2]
    },
    levelControl () {
      return this.$store.getters.getLevelControl
    },
    permanentImages () {
      return this.$store.getters.getPermanentImages
    }
  },
  methods: {
    handleCongrats () {
      const data = this.$route.query
      if (data) {
        if (data.congratsShow === 'planet-congrats') {
          this.$root.$emit('bv::show::modal', 'level-congrats-modal')
        } else if (data.congratsShow === 'star-system-congrats') {
          this.$root.$emit('bv::show::modal', 'star-system-congrats-modal')
        } else {
          this.$router.push({query: {}})
        }
      }
    }
  },
  components: {
    SplashScreen,
    UserProfileControls,
    SocialSharing,
    LevelCongrats,
    StarSystem,
    StarSystemCongrats
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
    .profile-top {
      height: 90%;
      .row {
        margin: auto;
      }
    }
    .star-systems-slot {
      position: relative;
      margin: 0;
      height: 90%;
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
        align-items: center;
        justify-content: center;
        margin: 0.4em;
        border-radius: 0.25rem!important;
        background-color: #000000;
        font-size: 1.1em;

        i {
          margin-left: 10%;
        }
      }
      .selected {
        opacity: 1;
        border: 1px solid #ffffff;
      }
    }
  }

  @media (max-width: 414px) and (orientation: portrait) {
  }
</style>
