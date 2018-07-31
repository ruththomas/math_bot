<template>
  <div class="robot-container" data-aos="fade-in" v-if="auth.authenticated">
    <splash-screen v-if="!Object.keys(stepData).length"></splash-screen>
    <div  v-else id="robot" class="row animated">

      <div id="control-panel-box">
        <control-panel></control-panel>
      </div>

      <div id="grid-box">
        <grid></grid>
        <trash></trash>
      </div>

      <messages></messages>

      <div id="edit-main-box">
        <trash></trash>
        <editmain></editmain>
      </div>

      <div id="commands-box">
        <commands></commands>
        <trash></trash>
      </div>

      <!--<div class="filler-box"></div>-->
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
    RobotCarrying
  }
}
</script>

<style scoped lang="scss">
  .robot-container {
    height: 100%;
    width: 100%;
    background-image: url("https://res.cloudinary.com/deqjemwcu/image/upload/v1522346735/misc/Space_background.jpg");
    background-size: cover;
  }

  #robot {
    position: relative;
    width: 100%;
    height: 100%;
    max-width: 1200px;
    overflow: visible;
    margin: 0 auto;
    padding: 1vh 20px 0 20px;
    display: flex;
    flex-direction: column;
  }

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
    /*border: 1px solid yellowgreen;*/
  }

  #edit-main-box {
    display: flex;
    flex: 1;
    /*border: 1px solid mediumvioletred;*/
  }

  #commands-box {
    display: flex;
    flex-direction: column;
    justify-content: center;
    flex: 1.2;
    /*border: 1px solid firebrick;*/
  }

  .filler-box {
    display: flex;
  }

  .grow {
    height: 40%;
    -webkit-transition: height 1s; /* For Safari 3.1 to 6.0 */
    transition: height 1s;
  }

  /* ipad pro Portrait */
  @media only screen
  and (min-device-width: 1024px)
  and (max-device-width: 1366px)
  and (orientation: portrait)
  and (-webkit-min-device-pixel-ratio: 1.5) {
  }

  /* ipad pro Landscape */
  @media only screen
  and (min-device-width: 1024px)
  and (max-device-width: 1366px)
  and (orientation: landscape)
  and (-webkit-min-device-pixel-ratio: 1.5) {

  }

  @media only screen and (max-width : 667px) and (orientation: landscape) {
  }

  /* iphone 5 landscape */
  @media only screen and (max-width : 568px) and (orientation: landscape) {

    #control-panel-box {
    }

    #grid-box {
      justify-content: flex-start;
    }

    #edit-main-box {
    }

    #commands-box {
      flex: 1.1;
    }

    #robot {
      width: 100vw;
    }
  }

  /* iphone 6/7/8 plus*/
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
      /*max-height: 95px;*/
    }
  }

  /* iphone 6/7 portrait */
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
      /*max-height: 95px;*/
    }
  }

  /* iphone 5 portrait */
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
      /*max-height: 95px;*/
    }

    #robot {
      width: 100vw;
    }
  }

  /* iPad */
  @media all and (device-width: 768px) and (device-height: 1024px) and (orientation:portrait) {
  }

  @media all and (device-width: 768px) and (device-height: 1024px) and (orientation:landscape) {

  }

</style>
