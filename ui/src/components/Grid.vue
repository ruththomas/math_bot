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
  $click-color: #B8E986;
  $grid-space-font-size: 22px;
  $grid-space-size: 96px;
  $grid-border-radius: 4px;
  $grid-background: rgba(0, 0, 0, 0.6);

  .total-grid {
    width: 100%;
    height: 100%;
    display: flex;
  }

  .grid {
    border: 1px solid $click-color;
    border-radius: $grid-border-radius;
    flex-wrap: wrap;
    margin: 0 auto;
    z-index: 100;
    background: $grid-background;
  }

  .grid-row {
    display: flex;
    &:first-child .grid-space:first-child {
      border-top-left-radius: $grid-border-radius;
    }

    &:first-child .grid-space:last-child {
      border-top-right-radius: $grid-border-radius;
    }

    &:last-child .grid-space:first-child {
      border-bottom-left-radius: $grid-border-radius;
    }

    &:last-child .grid-space:last-child {
      border-bottom-right-radius: $grid-border-radius;
    }
  }

  .grid-space {
    position: relative;
    display: flex;
    justify-content: center;
    align-items: center;
    height: $grid-space-size;
    width: $grid-space-size;
    padding: 0;
    border-top: 1px solid rgba(255, 255, 255, 0.2);
    border-right: 1px solid rgba(255, 255, 255, 0.2);
    font-size: $grid-space-font-size;

    img:not(.robot) {
      height: 75%;
      width: 75%;
    }

    .robot {
      width: 150%;
      height: 150%;
      position: absolute;
      top: -35%;
    }

    .problem {
      z-index: 120;
      position: absolute;
    }
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

  .grid-space > .robot {
    z-index: 1010;
    /*height: 175%;*/
    /*width: 175%;*/
  }

  .robot-shake {
    animation: shake 0.5s;
    animation-iteration-count: infinite;
  }

  /* 13" screen */
  @media only screen and (max-width : 1280px) {
    .grid-space {
      height: 70px;
      width: 70px;
    }
  }

  @media only screen and (max-width: 823px) and (orientation: landscape) {
    $grid-space-size: 32px;

    .grid-space {
      height: $grid-space-size;
      width: $grid-space-size;
    }
  }

  @media only screen and (max-width : 736px) {
    $grid-space-size: 38px;

    .grid-space {
      height: $grid-space-size;
      width: $grid-space-size;
    }
  }

  @media only screen and (max-width: 667px) and (orientation: landscape) {
    $grid-space-size: 30px;

    .grid-space {
      height: $grid-space-size;
      width: $grid-space-size;
    }
  }

  @media only screen and (max-width: 568px) and (orientation: landscape) {
    $grid-space-size: 26px;

    .grid-space {
      height: $grid-space-size;
      width: $grid-space-size;
    }
  }

  @media only screen and (max-width: 414px) {
    $grid-space-size: 32px;

    .grid-space {
      height: $grid-space-size;
      width: $grid-space-size;
    }
  }

  @media only screen and (max-width: 375px) {
    $grid-space-size: 26px;

    .grid-space {
      height: $grid-space-size;
      width: $grid-space-size;
    }
  }

  /* Custom, iPhone 5 Retina */
  @media only screen and (max-width : 320px) {
    $grid-space-size: 26px;

    .grid-space {
      height: $grid-space-size;
      width: $grid-space-size;
    }
  }

  /* iPad */
  @media all and (device-width: 768px) and (device-height: 1024px) and (orientation:portrait) {
    .grid-space {
      height: 70px;
      width: 70px;
    }
  }

</style>
