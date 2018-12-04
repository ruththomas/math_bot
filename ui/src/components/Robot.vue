<template>
  <div class="container-fluid robot" data-aos="fade-in">
   <user-gravatar></user-gravatar>
    <step-congrats v-if="levelControl.continent !== null" key="step-congrats"></step-congrats>
    <splash-screen v-if="levelControl.continent === null"></splash-screen>
    <div v-else class="container">

      <div class="row" style="position: relative;">
        <trash></trash>
        <grid></grid>
        <active-drop v-if="functionAreaShowing === 'addFunction'"></active-drop>
      </div>
      <transition
        name="custom-classes-transition"
        enter-active-class="animated tada"
        leave-active-class="animated bounceOutRight"
      >
        <div v-if="!advancedMode" class="row container" style="flex: 1;">
          <div class="row" style="padding: 0; display: flex; flex: 1; position: relative; z-index: 110;">
            <popover-bucket v-if="functionAreaShowing === 'editFunction' || functionAreaShowing === 'addFunction'"></popover-bucket>
            <edit-main v-if="functionAreaShowing === 'editMain'"></edit-main>
          </div>

          <div class="row" style="padding: 0; display: flex; flex: 1;">
            <trash></trash>
            <commands></commands>
          </div>
        </div>

        <div v-else class="row container" style="flex: 1;">
          <advanced-mode></advanced-mode>
        </div>
      </transition>
      <div style="display: flex; justify-content: center; align-items: center;">
        <span :style="{opacity: advancedMode ? 0.5 : 1, color: '#ffffff', margin: '0 0.5em'}">Normal</span>
        <toggle-button
          @change="toggleAdvanced"
          :color="{checked: 'rgba(255, 255, 255, 0.5)', unchecked: 'rgba(255, 255, 255, 0.5)', disabled: 'rgba(255, 255, 255, 0.5)'}"
          :switch-color="{checked: 'linear-gradient(to left, #25EF02, #000000)', unchecked: 'linear-gradient(to left, #000000, #25EF02)'}"
        />
        <span :style="{opacity: !advancedMode ? 0.5 : 1, color: '#ffffff', margin: '0 0.5em'}">Advanced</span>
      </div>
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
import ControlPanel from './Control_panel'
import SplashScreen from './Splash_screen'
import RobotCarrying from './Robot_carrying'
import PopoverBucket from './Popover_bucket'
import StepCongrats from './Continent_congrats'
import LevelCongrats from './Planet_congrats'
import AdvancedMode from './Advanced_mode'
import ConfirmDeactivateFunc from './Confirm_deactivate_func'
import UserGravatar from './UserGravatar'
import ActiveDrop from './Activate_drop'

export default {
  mounted () {
    this.handleFreeHint()
  },
  computed: {
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
      renderGrid: false,
      advancedMode: false
    }
  },
  methods: {
    handleFreeHint () {
      if (this.levelControl.continent !== null) return this.videoControl.showFreeHint(this.levelControl.continent.freeHint)
      setTimeout(this.handleFreeHint, 10)
    },
    toggleAdvanced (evt) {
      this.advancedMode = evt.value
    }
  },
  components: {
    UserGravatar,
    ConfirmDeactivateFunc,
    Grid,
    Commands,
    Trash,
    EditMain,
    Messages,
    ControlPanel,
    SplashScreen,
    RobotCarrying,
    PopoverBucket,
    StepCongrats,
    LevelCongrats,
    AdvancedMode,
    ActiveDrop
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
    .container {
      display: flex;
      flex-direction: column;
      justify-content: flex-start;
      height: 100%;
      .row {
        margin: 0;
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
    .return-to-profile {
      position: fixed;
      right: 0;
      top: 0;
      cursor: pointer;
      height: 9vmin;
      width: 9vmin;
      display: flex;
      align-items: center;
      justify-content: center;
      border-radius: 50%;
      z-index: 112;
      img {
        border-radius: 50%;
        height: 80%;
        width: 80%;
      }
    }
  }
</style>
