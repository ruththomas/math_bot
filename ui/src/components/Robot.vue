<template>
  <div class="container-fluid robot" data-aos="fade-in">
   <user-gravatar></user-gravatar>
    <step-congrats v-if="levelControl.continent !== null" key="step-congrats"></step-congrats>
    <splash-screen v-if="levelControl.continent === null"></splash-screen>
    <div v-else class="robot-container">

      <div class="row" style="flex: 0.6;">
        <trash></trash>
        <grid></grid>
        <active-drop v-if="functionAreaShowing === 'addFunction'"></active-drop>
      </div>
      <transition
        name="custom-classes-transition"
        enter-active-class="animated tada"
        leave-active-class="animated bounceOutRight"
      >
        <div v-if="levelControl.mode === 'normal'" class="row container" style="flex: 0.5;">
          <div class="row">
            <popover-bucket v-if="functionAreaShowing === 'editFunction'"></popover-bucket>
            <edit-main v-if="functionAreaShowing === 'editMain'"></edit-main>
          </div>

          <div class="row">
            <trash></trash>
            <commands></commands>
          </div>

          <div class="row">
            <trash></trash>
            <staged-functions></staged-functions>
          </div>
        </div>

        <div v-else class="row container" style="flex: 1; padding: 0;">
          <advanced-mode></advanced-mode>
        </div>
      </transition>
    </div>
    <confirm-deactivate-func></confirm-deactivate-func>
  </div>
</template>

<script>
import Grid from './Grid'
import Commands from './Commands'
import EditMain from './Edit_main'
import Trash from './Trash'
import Messages from './Messages'
import SplashScreen from './Splash_screen'
import RobotCarrying from './Robot_carrying'
import PopoverBucket from './Popover_bucket'
import StepCongrats from './Continent_congrats'
import LevelCongrats from './Planet_congrats'
import AdvancedMode from './Advanced_mode'
import ConfirmDeactivateFunc from './Confirm_deactivate_func'
import UserGravatar from './UserGravatar'
import StagedFunctions from './Staged_functions'

export default {
  mounted () {
    this.handleFreeHint()
  },
  computed: {
    soundControl () {
      return this.$store.getters.getSoundControl
    },
    videoControl () {
      return this.$store.getters.getVideoHintControl
    },
    levelControl () {
      return this.$store.getters.getLevelControl
    },
    userProfile () {
      return this.auth.userProfile
    },
    auth () {
      return this.$store.getters.getAuth
    },
    functionAreaShowing () {
      return this.$store.getters.getFunctionAreaShowing
    },
    permanentImages () {
      return this.$store.getters.getPermanentImages
    }
  },
  data () {
    return {
      renderGrid: false
    }
  },
  methods: {
    handleFreeHint () {
      if (this.levelControl.continent !== null) {
        return this.videoControl.showFreeHint(this.levelControl.continent.freeHint)
      }
      setTimeout(this.handleFreeHint, 10)
    },
    goToProfile () {
      this.$store.dispatch('toggleHintShowing', {showing: false, videoURL: ''})
      this.$store.dispatch('deleteMessages')
      this.$router.push({path: '/profile'})
    },
    handlePicture (picture) {
      if (!picture || picture.match(/gravatar/)) {
        return this.permanentImages.gravatar
      } else {
        return picture
      }
    }
  },
  components: {
    ConfirmDeactivateFunc,
    UserGravatar,
    Grid,
    Commands,
    Trash,
    EditMain,
    Messages,
    SplashScreen,
    RobotCarrying,
    PopoverBucket,
    StepCongrats,
    LevelCongrats,
    AdvancedMode,
    StagedFunctions
  }
}
</script>

<style scoped lang="scss">
  $box-height: 18vmin;
  label {
    margin: 0;
  }
  .robot {
    background-image: url("https://res.cloudinary.com/deqjemwcu/image/upload/v1522346735/misc/Space_background.jpg");
    background-size: cover;
    height: 100%;
    padding: 2vmin 0;
    .robot-container {
      display: flex;
      flex-direction: column;
      justify-content: flex-start;
      height: 100%;
      .row {
        margin: 0 auto;
        width: 100%;
        z-index: 111;
      }
      .box {
        background: transparent;
        position: relative;
        height: $box-height;
        z-index: 100;
      }
    }
  }
</style>
