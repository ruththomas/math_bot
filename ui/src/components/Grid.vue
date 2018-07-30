<template>
  <div class="total-grid">
    <transition
      mode="out-in"
      name="custom-classes-transition"
      enter-active-class="animated zoomIn"
      leave-active-class="animated fadeOut"
    >
      <congrats v-if="congratsShowing"></congrats>
      <tryagain v-else-if="tryAgainShowing"></tryagain>
      <video-hint v-else-if="hintShowing.showing"></video-hint>
      <div v-else-if="gridMap" class="grid" :class="robotCarrying.length ? 'no-radius-bottom-right' : ''">
        <div
          class="grid-row animated"
          v-for="(row, rInd) in gridMap"
          :key="'row:' + rInd"
        >
          <div
            class="grid-space animated"
            v-for="(space, sInd) in row"
            :id="`grid-cell-${rInd}-${sInd}`"
            :class="'grid-space-' + space.name.replace(/ /g, '-')"
            :key="'space:' + rInd + ':' + sInd"
          >
            <span v-if="space.name === 'final answer'"
                  class="problem single-digit-problem">{{singleDigitProblem(problem)}}</span>
            <b-img
              v-if="space.name === 'final answer'"
              class="portal glyphicon"
              :src="permanentImages.blackHole"></b-img>
            <b-img
              v-if="space.tools.length"
              class="tool animated zoomIn"
              v-for="(tool, tInd) in space.tools"
              :key="'tool:' + tInd + ':' + rInd + ':' + sInd"
              :src="toolImages[tool.image]"></b-img>
            <b-img
              class="robot animated"
              v-if="robot.robotLocation.x === rInd && robot.robotLocation.y === sInd"
              :key="'ROBOT'"
              :src="robot._robotDirections[robotOrientation]"></b-img>

            <b-popover
              v-if="space.tools.length"
              :target="`grid-cell-${rInd}-${sInd}`"
              triggers="click"
            >
              <div class="display-tools">
                <div
                  v-for="(tool, iInd) in space.tools.slice(0, 100)"
                  :key="`d-image-${iInd}`"
                  :class="tool.original ? 'replenish-tool' : ''"
                >
                  <b-img
                    :src="permanentImages.tools[tool.image]"
                    fluid
                  ></b-img>
                </div>
              </div>
            </b-popover>
          </div>
        </div>
        <RobotCarrying></RobotCarrying>
      </div>
      <splash-screen v-else></splash-screen>
    </transition>
  </div>
</template>

<script>
import assets from '../assets/assets'
import Congrats from './Congrats'
import Tryagain from './Try_again'
import VideoHint from './Video_hint'
import SplashScreen from './Splash_screen'
import RobotCarrying from './Robot_carrying'

export default {
  mounted () {
  },
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
    },
    robotCarrying () {
      return this.$store.getters.getRobotCarrying
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
    VideoHint,
    SplashScreen,
    RobotCarrying
  }
}
</script>

<style scoped lang="scss">
  $click-color: #B8E986;
  $grid-space-font-size: 22px;
  $grid-space-size: 96px;
  $grid-border-radius: 4px;
  $grid-background: rgba(0, 0, 0, 0.5);
  $display-tool-size: 30px;

  .total-grid {
    width: 100%;
    height: 100%;
    display: flex;
  }

  .grid {
    position: relative;
    border: 1px solid $click-color;
    border-radius: $grid-border-radius;
    flex-wrap: wrap;
    margin: 0 auto;
    z-index: 100;
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

  .no-radius-bottom-right {
    border-bottom-right-radius: 0;

    &:last-child .grid-space:last-child {
      border-bottom-right-radius: 0;
    }
  }

  .grid-space {
    position: relative;
    display: flex;
    justify-content: center;
    align-items: center;
    height: $grid-space-size;
    width: $grid-space-size;
    border-top: 1px solid rgba(255, 255, 255, 0.2);
    border-right: 1px solid rgba(255, 255, 255, 0.2);
    font-size: $grid-space-font-size;
    background: $grid-background;

    img {
      position: absolute;
    }

    img.tool {
      cursor: pointer;
      height: 75%;
      width: 75%;
    }

    img.robot {
      height: 150%;
      width: 150%;
      top: -35%;
    }
  }

  .display-tools {
    display: flex;
    max-width: 300px;
    flex-wrap: wrap;
    position: relative;

    img {
      height: $display-tool-size;
    }

    .replenish-tool {
      position: relative;
      &::before {
        background-size: 100%;
        background: rgba(255, 255, 255, 1) url("http://res.cloudinary.com/doohickey/image/upload/v1532891868/noun_Refresh_680199_000000_izzqi4.svg");
        display: inline-block;
        border-radius: 50%;
        position: absolute;
        bottom: 0;
        content: "";
        height: calc(#{$display-tool-size} / 2);
        width: calc(#{$display-tool-size} / 2);
      }
    }
  }

  .grid-space-empty-space {
  }

  .grid-space-wall {
    box-shadow: 0 0 30px 0 rgba(0,0,0,0.5);
    background: repeating-linear-gradient(
        45deg,
        rgba(0, 0, 0, 0.2),
        rgba(0, 0, 0, 0.2) 21px,
        rgba(74, 74, 74, 0.6) 21px,
        rgba(74, 74, 74, 0.6) 24px
    )
  }

  .grid-row:nth-child(5n+0) .grid-space {
    border-bottom: 1px solid rgba(0, 0, 0, 0.2);
  }

  .grid-space:nth-child(1) {
    border-left: 1px solid rgba(255, 255, 255, 0.2);
  }

  .robot-shake {
    animation: shake 0.5s;
    animation-iteration-count: infinite;
  }

  @media only screen and (max-width: 823px) and (orientation: landscape) {
    $grid-space-size: 32px;
    $display-tool-size: 16px;
    $grid-space-font-size: 14px;

    .grid-space {
      height: $grid-space-size;
      width: $grid-space-size;
      overflow: hidden;

      .portal {
        height: 150%;
        width: 150%;
      }

      .problem {
        font-size: $grid-space-font-size;
      }
    }

    .display-tools {
      display: flex;
      max-width: 300px;
      flex-wrap: wrap;
      position: relative;

      img {
        height: $display-tool-size;
      }

      .replenish-tool {
        position: relative;
        &::before {
          background-size: 100%;
          background: rgba(255, 255, 255, 1) url("http://res.cloudinary.com/doohickey/image/upload/v1532891868/noun_Refresh_680199_000000_izzqi4.svg");
          display: inline-block;
          border-radius: 50%;
          position: absolute;
          bottom: 0;
          content: "";
          height: calc(#{$display-tool-size} / 2);
          width: calc(#{$display-tool-size} / 2);
        }
      }
    }
  }

  @media only screen and (max-width : 736px) {
    $grid-space-size: 38px;
    $display-tool-size: 16px;

    .grid-space {
      height: $grid-space-size;
      width: $grid-space-size;
    }

    .display-tools {
      display: flex;
      max-width: 300px;
      flex-wrap: wrap;
      position: relative;

      img {
        height: $display-tool-size;
      }

      .replenish-tool {
        position: relative;
        &::before {
          background-size: 100%;
          background: rgba(255, 255, 255, 1) url("http://res.cloudinary.com/doohickey/image/upload/v1532891868/noun_Refresh_680199_000000_izzqi4.svg");
          display: inline-block;
          border-radius: 50%;
          position: absolute;
          bottom: 0;
          content: "";
          height: calc(#{$display-tool-size} / 2);
          width: calc(#{$display-tool-size} / 2);
        }
      }
    }
  }

  @media only screen and (max-width: 667px) and (orientation: landscape) {
    $grid-space-size: 30px;
    $display-tool-size: 16px;

    .grid-space {
      height: $grid-space-size;
      width: $grid-space-size;
    }

    .display-tools {
      display: flex;
      max-width: 300px;
      flex-wrap: wrap;
      position: relative;

      img {
        height: $display-tool-size;
      }

      .replenish-tool {
        position: relative;
        &::before {
          background-size: 100%;
          background: rgba(255, 255, 255, 1) url("http://res.cloudinary.com/doohickey/image/upload/v1532891868/noun_Refresh_680199_000000_izzqi4.svg");
          display: inline-block;
          border-radius: 50%;
          position: absolute;
          bottom: 0;
          content: "";
          height: calc(#{$display-tool-size} / 2);
          width: calc(#{$display-tool-size} / 2);
        }
      }
    }
  }

  @media only screen and (max-width: 568px) and (orientation: landscape) {
    $grid-space-size: 26px;
    $display-tool-size: 16px;

    .grid-space {
      height: $grid-space-size;
      width: $grid-space-size;
    }

    .display-tools {
      display: flex;
      max-width: 300px;
      flex-wrap: wrap;
      position: relative;

      img {
        height: $display-tool-size;
      }

      .replenish-tool {
        position: relative;
        &::before {
          background-size: 100%;
          background: rgba(255, 255, 255, 1) url("http://res.cloudinary.com/doohickey/image/upload/v1532891868/noun_Refresh_680199_000000_izzqi4.svg");
          display: inline-block;
          border-radius: 50%;
          position: absolute;
          bottom: 0;
          content: "";
          height: calc(#{$display-tool-size} / 2);
          width: calc(#{$display-tool-size} / 2);
        }
      }
    }
  }

  @media only screen and (max-width: 414px) {
    $grid-space-size: 32px;
    $display-tool-size: 18px;
    $grid-space-font-size: 14px;

    .grid-space {
      height: $grid-space-size;
      width: $grid-space-size;
      overflow: hidden;

      .portal {
        height: 180%;
        width: 180%;
      }

      .problem {
        font-size: $grid-space-font-size;
      }
    }

    .display-tools {
      display: flex;
      max-width: 300px;
      flex-wrap: wrap;
      position: relative;

      img {
        height: $display-tool-size;
      }

      .replenish-tool {
        position: relative;
        &::before {
          background-size: 100%;
          background: rgba(255, 255, 255, 1) url("http://res.cloudinary.com/doohickey/image/upload/v1532891868/noun_Refresh_680199_000000_izzqi4.svg");
          display: inline-block;
          border-radius: 50%;
          position: absolute;
          bottom: 0;
          content: "";
          height: calc(#{$display-tool-size} / 2);
          width: calc(#{$display-tool-size} / 2);
        }
      }
    }
  }

  @media only screen and (max-width: 375px) {
    $grid-space-size: 26px;
    $display-tool-size: 16px;

    .grid-space {
      height: $grid-space-size;
      width: $grid-space-size;
    }

    .display-tools {
      display: flex;
      max-width: 300px;
      flex-wrap: wrap;
      position: relative;

      img {
        height: $display-tool-size;
      }

      .replenish-tool {
        position: relative;
        &::before {
          background-size: 100%;
          background: rgba(255, 255, 255, 1) url("http://res.cloudinary.com/doohickey/image/upload/v1532891868/noun_Refresh_680199_000000_izzqi4.svg");
          display: inline-block;
          border-radius: 50%;
          position: absolute;
          bottom: 0;
          content: "";
          height: calc(#{$display-tool-size} / 2);
          width: calc(#{$display-tool-size} / 2);
        }
      }
    }
  }

  @media only screen and (max-width : 320px) {
    $grid-space-size: 24px;
    $display-tool-size: 16px;

    .grid-space {
      height: $grid-space-size;
      width: $grid-space-size;
    }

    .display-tools {
      display: flex;
      max-width: 300px;
      flex-wrap: wrap;
      position: relative;

      img {
        height: $display-tool-size;
      }

      .replenish-tool {
        position: relative;
        &::before {
          background-size: 100%;
          background: rgba(255, 255, 255, 1) url("http://res.cloudinary.com/doohickey/image/upload/v1532891868/noun_Refresh_680199_000000_izzqi4.svg");
          display: inline-block;
          border-radius: 50%;
          position: absolute;
          bottom: 0;
          content: "";
          height: calc(#{$display-tool-size} / 2);
          width: calc(#{$display-tool-size} / 2);
        }
      }
    }
  }
</style>
<style lang="scss">
  $popover-background-color: rgba(0, 0, 0, 0.6);
  $popover-border: #B8E986;
  // for popover
  .popover.bs-popover-right, .popover.bs-popover-left {
    background-color: $popover-background-color;
    border: 1px solid $popover-border;
    box-shadow: 0 0 100px 0 rgba(0, 0, 0, 1);

    .arrow::after {
      border-right-color: $popover-border;
      border-left-color: $popover-border;
    }
  }
</style>
