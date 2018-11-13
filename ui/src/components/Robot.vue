<template>
  <splash-screen v-if="levelControl.continent === null"></splash-screen>
  <div class="container-fluid robot" data-aos="fade-in" v-else>
    <video-hint></video-hint>
    <div
      @click="goToProfile()"
      class="return-to-profile"
      data-toggle="tooltip" title="Return to profile"
    >
      <img :src="handlePicture(userProfile.picture)" />
    </div>
    <step-congrats key="step-congrats"></step-congrats>
    <div class="container">

      <div class="row" style="position: relative;">
        <trash></trash>
        <grid></grid>
      </div>

      <transition
        name="custom-classes-transition"
        enter-active-class="animated tada"
        leave-active-class="animated bounceOutRight"
      >
        <div v-if="!advancedMode" class="row container" style="flex: 1;">
          <div class="row" style="padding: 0; display: flex; flex: 1;">
            <popover-bucket v-if="functionAreaShowing === 'editFunction' || functionAreaShowing === 'addFunction'"></popover-bucket>
            <editmain v-if="functionAreaShowing === 'editMain'"></editmain>
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
          :switch-color="{checked: 'linear-gradient(#25EF02, #000000)', unchecked: 'linear-gradient(#000000, #25EF02)'}"
        />
        <span :style="{opacity: !advancedMode ? 0.5 : 1, color: '#ffffff', margin: '0 0.5em'}">Advanced</span>
      </div>
    </div>
  </div>
</template>

<script>
import Identicon from 'identicon.js'
import md5 from 'md5'
import Grid from './Grid'
import Commands from './Commands'
import Editmain from './Edit_main'
import Editfunction from './Edit_function'
import Trash from './Trash'
import Messages from './Messages'
import ControlPanel from './Control_panel'
import SplashScreen from './Splash_screen'
import RobotCarrying from './Robot_carrying'
import PopoverBucket from './Popover_bucket'
import StepCongrats from './Step_congrats'
import VideoHint from './Video_hint'
import LevelCongrats from './Level_congrats'
import AdvancedMode from './Advanced_mode'

export default {
  mounted () {
    this.videoHintControl.showFreeHint(this.levelControl.continent.freeHint)
  },
  computed: {
    levelControl () {
      return this.$store.getters.getLevelControl
    },
    gridMap () {
      return this.levelControl.continent.gridMap
    },
    robot () {
      return this.levelControl.robot
    },
    robotCarrying () {
      return this.robot.robotCarrying
    },
    problem () {
      return this.levelControl.continent.problem.problem
    },
    videoHintControl () {
      return this.$store.getters.getVideoHintControl
    },

    runCompiled () {
      return this.$store.getters.getRunCompiled
    },
    userProfile () {
      return this.auth.userProfile
    },
    tokenId () {
      return this.$store.getters.getTokenId
    },
    stats () {
      return this.$store.getters.getStats
    },
    auth () {
      return this.$store.getters.getAuth
    },
    splashScreenShowing () {
      return this.$store.getters.getSplashScreenShowing
    },
    Functions () {
      return this.$store.getters.getFunctions
    },
    currentFunction () {
      return this.$store.getters.getCurrentFunction
    },
    programPanel () {
      return this.$store.getters.getProgramPanelShowing
    },
    commands () {
      return this.$store.getters.getCommands
    },
    congratsShowing () {
      return this.$store.getters.getCongratsShowing
    },
    tryAgainShowing () {
      return this.$store.getters.getTryAgainShowing
    },
    game () {
      return this.$store.getters.getGame
    },
    userName () {
      let currentUser = this.$store.getters.getCurrentUser
      const loggedIn = this.$store.getters.getLoggedIn
      if (loggedIn) {
        if (currentUser.nickname) {
          return new Identicon(md5(this.$store.getters.getCurrentUser.nickname), 30).toString()
        }
      }
    },
    loggedInShowing () {
      return this.$store.getters.getLoggedInShowing
    },
    loggedIn () {
      return this.$store.getters.getLoggedIn
    },
    permanentImages () {
      return this.$store.getters.getPermanentImages
    },
    editFunctionShowing () {
      return this.$store.getters.getEditFunctionShowing
    },
    functionAreaShowing () {
      return this.$store.getters.getFunctionAreaShowing
    },
    swiperSlide () {
      return this.$store.getters.getSwiperSlide
    },
    activeFunctionGroups () {
      return this.$store.getters.getActiveFunctionGroups
    }
  },
  data () {
    return {
      renderGrid: false,
      advancedMode: false
    }
  },
  methods: {
    showProgramPanel () {
      this.$store.dispatch('controlProgramPanelShowing')
    },
    adjustSpeed () {
      this.speed = this.speed === 500 ? 200 : 500
    },
    goToProfile () {
      this.$store.dispatch('toggleHintShowing', {showing: false, videoURL: ''})
      this.$store.dispatch('deleteMessages')
      this.$router.push({path: 'profile'})
    },
    handlePicture (picture) {
      if (!picture || picture.match(/gravatar/)) {
        return this.permanentImages.gravatar
      } else {
        return picture
      }
    },
    toggleAdvanced (evt) {
      this.advancedMode = evt.value
    }
  },
  components: {
    Grid,
    Commands,
    Editfunction,
    Trash,
    Editmain,
    Messages,
    ControlPanel,
    SplashScreen,
    RobotCarrying,
    PopoverBucket,
    StepCongrats,
    LevelCongrats,
    VideoHint,
    AdvancedMode
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

      img {
        border-radius: 50%;
        height: 80%;
        width: 80%;
        box-shadow: 0 0 100px 2vmin rgba(0,0,0,1);
      }
    }
  }
</style>
