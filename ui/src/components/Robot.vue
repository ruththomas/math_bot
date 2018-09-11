<template>
  <splash-screen v-if="!Object.keys(stepData).length"></splash-screen>
  <div class="container-fluid robot" data-aos="fade-in" v-else>
    <div class="container">

      <div class="row" style="position: relative;">
        <trash></trash>
        <grid></grid>
      </div>

      <div class="row box" style="padding: 0;">
        <popover-bucket v-if="functionAreaShowing === 'editFunction' || functionAreaShowing === 'addFunction'"></popover-bucket>
        <editmain v-if="functionAreaShowing === 'editMain'"></editmain>
      </div>

      <div class="row">
        <trash></trash>
        <commands></commands>
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
import api from '../services/api'
import utils from '../services/utils'
import Robot from '../services/RobotState'
import RobotCarrying from './Robot_carrying'
import PopoverBucket from './Popover_bucket'

export default {
  mounted () {
    this.initializeRobot()
  },
  computed: {
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
  methods: {
    initializeRobot () {
      utils.watcher(() => !this.auth.authenticated, () => {
        api.getStep({tokenId: this.tokenId, level: this.stats.level, step: this.stats.step}, stepData => {
          this.$store.dispatch('updateStepData', stepData)
          this.$store.dispatch('updateLambdas', stepData.lambdas)
          stepData.initialRobotState.context = this
          this.$store.dispatch('updateRobot', new Robot(stepData.initialRobotState))
        })
      })
    },
    showProgramPanel () {
      this.$store.dispatch('controlProgramPanelShowing')
    },
    adjustSpeed () {
      this.speed = this.speed === 500 ? 200 : 500
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
    PopoverBucket
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
  }
</style>
