<template>
  <splash-screen v-if="!Object.keys(stepData).length"></splash-screen>
  <div class="container-fluid robot" data-aos="fade-in" v-else>
    <div
      @click="goToProfile()"
      class="return-to-profile"
      data-toggle="tooltip" title="Return to profile"
    >
      <img :src="handlePicture(userProfile.picture)" />
    </div>
    <transition
      mode="out-in"
      name="grid-transition-group"
      enter-active-class="animated zoomIn"
      leave-active-class="animated zoomOut"
    >
      <congrats v-if="congratsShowing" :congrats="true" key="congrats-1234"></congrats>
      <congrats v-else-if="tryAgainShowing" :congrats="false" key="congrats-5678"></congrats>
      <div v-else class="container">

        <div class="row" style="position: relative;">
          <trash></trash>
          <grid v-if="renderGrid"></grid>
        </div>

        <messages></messages>

        <div class="row box" style="padding: 0;">
          <popover-bucket v-if="functionAreaShowing === 'editFunction' || functionAreaShowing === 'addFunction'"></popover-bucket>
          <editmain v-if="functionAreaShowing === 'editMain'"></editmain>
        </div>

        <div class="row">
          <trash></trash>
          <commands></commands>
        </div>
      </div>
    </transition>
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
import api from '../services/api'
import utils from '../services/utils'
import Robot from '../services/RobotState'
import RobotCarrying from './Robot_carrying'
import PopoverBucket from './Popover_bucket'
import Congrats from './Congrats'

export default {
  mounted () {
    this.initializeRobot()
  },
  computed: {
    userProfile () {
      return JSON.parse(localStorage.getItem('profile'))
    },
    tokenId () {
      return this.$store.getters.getTokenId
    },
    stats () {
      return this.$store.getters.getStats
    },
    stepData () {
      return this.$store.getters.getStepData
    },
    auth () {
      return this.$store.getters.getAuth
    },
    splashScreenShowing () {
      return this.$store.getters.getSplashScreenShowing
    },
    gridMap () {
      return this.stepData.gridMap
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
      renderGrid: false
    }
  },
  methods: {
    initializeRobot () {
      utils.watcher(() => !this.auth.authenticated, () => {
        api.getStep({tokenId: this.tokenId, level: this.stats.level, step: this.stats.step}, stepData => {
          this.$store.dispatch('updateStepData', stepData)
          this.$store.dispatch('updateLambdas', stepData.lambdas)
          stepData.initialRobotState.context = this
          this.$store.dispatch('updateRobot', new Robot(stepData.initialRobotState))
          this.renderGrid = true
        })
      })
    },
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
    Congrats
  }
}
</script>

<style scoped lang="scss">
  $box-height: 18vmin;

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
