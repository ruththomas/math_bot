<template>
  <div class="total-grid">
    <transition
      name="custom-classes-transition"
      enter-active-class="animated zoomIn"
      leave-active-class="hidden"
    >
      <congrats v-if="congratsShowing"></congrats>
      <tryagain v-else-if="tryAgainShowing"></tryagain>
      <video-hint v-else-if="hintShowing.showing"></video-hint>
      <div v-else-if="gridMap" class="grid">
        <div
          class="grid-row animated"
          v-for="(row, rInd) in gridMap"
          :key="'row:' + rInd"
        >
          <div
            class="grid-space animated"
            v-for="(space, sInd) in row"
            :class="'grid-space-' + space.name.replace(/ /g, '-')"
            :key="'space:' + rInd + ':' + sInd"
          >
            <span v-if="space.name === 'final answer'"
                  class="problem single-digit-problem">{{singleDigitProblem(problem)}}</span>
            <img
              v-if="space.name === 'final answer'"
              class="portal glyphicon"
              :src="permanentImages.blackHole" />
            <img
              v-if="space.tools.length"
              class="tool animated zoomIn"
              v-for="(tool, tInd) in space.tools"
              :key="'tool:' + tInd + ':' + rInd + ':' + sInd"
              :src="toolImages[tool.image]" />
            <img
              class="robot animated"
              v-if="robot.robotLocation.x === rInd && robot.robotLocation.y === sInd"
              :key="'ROBOT'"
              :src="robot._robotDirections[robotOrientation]" />
          </div>
        </div>
      </div>
      <splash-screen v-else></splash-screen>
    </transition>
    <robotcarrying></robotcarrying>
  </div>
</template>

<script>
import assets from '../assets/assets'
import Congrats from './Congrats'
import Tryagain from './Try_again'
import Robotcarrying from './Robot_carrying'
import VideoHint from './Video_hint'
import SplashScreen from './Splash_screen'

export default {
  computed: {
    problem () {
      return this.currentStepData.problem.problem
    },
    currentStepData () {
      return this.$store.getters.getStepData
    },
    level () {
      return this.currentStepData.level
    },
    step () {
      return this.currentStepData.step
    },
    gridMap () {
      return this.currentStepData.gridMap
    },
    robotOrientation () {
      return this.robot.robotFacing
    },
    toolImages () {
      return assets.tools
    },
    robotDeactivated () {
      return this.$store.getters.getRobotDeactivated
    },
    robot () {
      return this.$store.getters.getRobot
    },
    congratsShowing () {
      return this.$store.getters.getCongratsShowing
    },
    tryAgainShowing () {
      return this.$store.getters.getTryAgainShowing
    },
    permanentImages () {
      return this.$store.getters.getPermanentImages
    },
    hintShowing () {
      return this.$store.getters.getHintShowing
    },
    messageShowing () {
      if (this.congratsShowing) {
        return true
      } else return !!this.tryAgainShowing
    },
    currentPaused () {
      return this.$store.getters.getPaused
    },
    mode () {
      return this.$store.getters.getMode
    },
    stepData () {
      return this.$store.getters.getStepData
    }
  },
  data () {
    return {
      runCompiled: {},
      showSpeech: true
    }
  },
  methods: {
    singleDigitProblem (problem) {
      const pNumber = Number(problem)
      if (!isNaN(pNumber) && pNumber > 0) {
        return problem
      }
    },
    pause () {
      this.robot.state = 'paused'
    },
    convertToImgName (spaceName) {
      switch (spaceName) {
        case 'wall':
          return spaceName
        default:
          return 'floor'
      }
    }
  },
  components: {
    Congrats,
    Tryagain,
    Robotcarrying,
    VideoHint,
    SplashScreen
  }
}
</script>

<style scoped lang="scss">
  img {
    height: auto;
    width: auto;
  }

  .total-grid {
    width: 100%;
    height: 100%;
  }

  .grid {
    display: table;
    margin: 0 auto;
    z-index: 100;
  }

  .grid-row {
    display: table-row;
  }

  .grid-space {
    position: relative;
    display: table-cell;
    height: 100px;
    width: 100px;
    padding: 0;
    border-top: 1px solid rgba(255, 255, 255, 0.2);
    border-right: 1px solid rgba(255, 255, 255, 0.2);
    text-align: center;
    vertical-align: middle;
    background: rgba(0, 0, 0, 0.7);
  }

  .grid-space-empty-space {
  }

  .grid-space-wall {
    height: 100px;
    width: 100px;
    box-shadow: 0 0 30px 0 rgba(0,0,0,0.5);
    background: repeating-linear-gradient(
        45deg,
        rgba(0, 0, 0, 0.3),
        rgba(0, 0, 0, 0.3) 21px,
        rgba(74, 74, 74, 0.5) 21px,
        rgba(74, 74, 74, 0.5) 24px
    )
  }

  .grid-row:nth-child(5n+0) .grid-space {
    border-bottom: 1px solid rgba(0, 0, 0, 0.2);
  }

  .grid-space:nth-child(1) {
    border-left: 1px solid rgba(255, 255, 255, 0.2);
  }

  .problem {
    z-index: 120;
    font-size: 22px;
  }

  .grid-space > img {
    position: absolute;
    margin: auto;
    height: 75%;
    width: 75%;
    left: 0;
    right: 0;
    bottom: 0;
    top: 0;
    z-index: 100;
  }

  .robot {
    z-index: 1010;
    height: 160% !important;
    width: 180% !important;
    margin: 0 !important;
    top: -42% !important;
    bottom: 0 !important;
    left: -38% !important;
    right: 0 !important;
  }

  .robot-shake {
    animation: shake 0.5s;
    animation-iteration-count: infinite;
  }

  @media all and (device-width: 768px) and (device-height: 1024px) and (orientation:landscape) {

  }

  /* 13" screen */
  @media only screen and (max-width : 1280px) {
    .grid-space {
      height: 70px;
      width: 70px;
    }
  }

  /* Large Phones, landscape*/
  @media only screen and (max-width : 992px) {
    .grid-space {
      height: 35px;
      width: 35px;
    }
  }

  /* Small Devices */
  @media only screen and (max-width : 667px) {
    .grid-space {
      height: 30px;
      width: 30px;
    }
  }

  /* Extra Small Devices, Phones */
  @media only screen and (max-width : 480px) {
  }

  /* Custom, iPhone 5 Retina */
  @media only screen and (max-width : 320px) {

  }

  /* iPad */
  @media all and (device-width: 768px) and (device-height: 1024px) and (orientation:portrait) {
    .grid-space {
      height: 70px;
      width: 70px;
    }
  }

</style>
