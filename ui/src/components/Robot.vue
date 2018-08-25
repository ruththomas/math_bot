<template>
  <splash-screen v-if="!Object.keys(stepData).length"></splash-screen>
  <div class="container-fluid robot" data-aos="fade-in" v-else>
    <div class="container">

      <div class="row" style="border: 1px solid red;">
        <trash></trash>
        <grid></grid>
      </div>

      <messages></messages>

      <div class="row box" style="border: 1px solid blue; opacity: 0;">
        <popover-bucket v-if="functionAreaShowing === 'editFunction' || functionAreaShowing === 'addFunction'"></popover-bucket>
        <editmain v-if="functionAreaShowing === 'editMain'"></editmain>
      </div>

      <div class="row box" style="border: 1px solid purple; opacity: 0;">
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
      return this.currentStepData.gridMap
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
    },
    currentStepData () {
      return this.$store.getters.getStepData
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
  .robot {
    background-image: url("https://res.cloudinary.com/deqjemwcu/image/upload/v1522346735/misc/Space_background.jpg");
    background-size: cover;
    height: 100%;

    .container {
      .box {
        height: 200px;
      }
    }
  }

/*
  #control-panel-box {
    display: flex;
    flex-direction: column;
    justify-content: flex-end;
    flex: 1;
  }

  #grid-box {
    display: flex;
    flex-direction: column;
    position: relative;
    align-items: center;
    justify-content: center;
    // border: 1px solid yellowgreen;
  }

  #edit-main-box {
    display: flex;
    flex: 1;
    z-index: 101;
    // border: 1px solid mediumvioletred;
  }

  #commands-box {
    position: relative;
    display: flex;
    flex-direction: column;
    justify-content: center;
    flex: 1.2;
    z-index: 102;
    // border: 1px solid firebrick;
  }

  .filler-box {
    display: flex;
  }

  .grow {
    height: 40%;
    -webkit-transition: height 1s;
    transition: height 1s;
  }

  // ipad pro Portrait
  @media only screen
  and (min-device-width: 1024px)
  and (max-device-width: 1366px)
  and (orientation: portrait)
  and (-webkit-min-device-pixel-ratio: 1.5) {
  }

  // ipad pro Landscape
  @media only screen
  and (min-device-width: 1024px)
  and (max-device-width: 1366px)
  and (orientation: landscape)
  and (-webkit-min-device-pixel-ratio: 1.5) {

  }

  @media only screen and (max-width: 823px) {
    #commands-box {
      flex: 1.8;
    }
  }

  @media only screen and (max-width: 736px) {
    #commands-box {
      flex: 1.8;
    }
  }

  @media only screen and (max-width : 667px) and (orientation: landscape) {
    #commands-box {
      flex: 1.8;
    }
  }

  // iphone 5 landscape
  @media only screen and (max-width : 568px) and (orientation: landscape) {

    #control-panel-box {
      flex: 0.8;
    }

    #grid-box {
      justify-content: flex-start;
    }

    #edit-main-box {
    }

    #commands-box {
      flex: 1.8;
    }

    #robot {
      width: 100vw;
    }
  }

  // iphone 6/7/8 plus
  @media only screen and (max-width: 414px) {
    #robot {
      padding: 0;
    }

    #control-panel-box {
      flex: 1;
    }

    #grid-box {
      justify-content: flex-start;
    }

    #edit-main-box {
    }

    #commands-box {
      // max-height: 95px
    }
  }

  // iphone 6/7 portrait
  @media only screen and (max-width : 375px) {
    #robot {
    }

    #control-panel-box {
    }

    #grid-box {
      justify-content: flex-start;
    }

    #edit-main-box {
    }

    #commands-box {
      // max-height: 95px;
    }
  }

  // iphone 5 portrait
  @media only screen and (max-width : 320px) {
    #robot {
    }

    #control-panel-box {
      flex: 1;
    }

    #grid-box {
      justify-content: flex-start;
    }

    #edit-main-box {
    }

    #commands-box {
      // max-height: 95px;
    }

    #robot {
      width: 100vw;
    }
  }

  // iPad
  @media all and (device-width: 768px) and (device-height: 1024px) and (orientation:portrait) {
  }

  @media all and (device-width: 768px) and (device-height: 1024px) and (orientation:landscape) {

  }
*/
</style>
