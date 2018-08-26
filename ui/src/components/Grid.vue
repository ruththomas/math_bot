<template>
    <div class="container grid">
      <control-panel></control-panel>
      <transition
        mode="out-in"
        name="grid-transition-group"
        enter-active-class="animated zoomIn"
        leave-active-class="animated zoomOut"
      >
        <congrats v-if="congratsShowing" :congrats="true" key="congrats-1234"></congrats>
        <congrats v-else-if="tryAgainShowing" :congrats="false" key="congrats-5678"></congrats>
        <video-hint v-else-if="hintShowing.showing" key="video-hint-1234"></video-hint>
        <div v-else-if="gridMap" class="row" :class="robotCarrying.length ? 'no-radius-bottom-right' : ''" key="grid-map-1234">
          <div class="grid-map">
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
                  placement="auto"
                  triggers="click"
                >
                  <img class="dialog-button close-popover" :src="permanentImages.buttons.xButton" @click="closePopover(`grid-cell-${rInd}-${sInd}`)" />
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
          </div>
        </div>
        <splash-screen v-else></splash-screen>
      </transition>
      <RobotCarrying></RobotCarrying>
    </div>
</template>

<script>
import assets from '../assets/assets'
import Congrats from './Congrats'
import VideoHint from './Video_hint'
import SplashScreen from './Splash_screen'
import RobotCarrying from './Robot_carrying'
import _ from 'underscore'
import utils from '../services/utils'
import ControlPanel from './Control_panel'

export default {
  mounted () {
    this.popoverSyncs = _.chain(this.gridMap)
      .map((row) => {
        return row.map(() => false)
      })
      .value()
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
      showSpeech: true,
      popoverSyncs: null
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
    },
    closePopover: utils.closePopover
  },
  components: {
    Congrats,
    VideoHint,
    SplashScreen,
    RobotCarrying,
    ControlPanel
  }
}
</script>

<style scoped lang="scss">
  $click-color: #B8E986;
  $grid-space-font-size: 22px;
  $grid-space-size: 9vmin;
  $grid-border-radius: 4px;
  $grid-background: rgba(0, 0, 0, 0.6);
  $display-tool-size: 18px;

  .grid {
    display: flex;
    flex-direction: column;
    align-items: center;

    .grid-map {
      border: 1px solid $click-color;
      border-radius: $grid-border-radius;
    }
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
      height: 90%;
    }

    img.tool {
      cursor: pointer;
      height: 75%;
      width: 75%;
    }

    img.robot {
      height: 150%;
      top: -35%;
      z-index: 9;
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
        background: #000000 url("http://res.cloudinary.com/doohickey/image/upload/v1530493572/noun_infinite_878473_cccccc_t4771o.svg");
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

  .close-popover {
    height: 18px;
    width: 18px;
    position: absolute;
    top: -9px;
    right: -9px;
  }
/*
  // ipad pro Portrait
  @media only screen
  and (min-device-width: 1024px)
  and (max-device-height: 1366px)
  and (orientation: portrait)
  and (-webkit-min-device-pixel-ratio: 1.5) {
    $grid-space-size: 56px;
    $display-tool-size: 16px;
    $grid-space-font-size: 14px;

    .grid-space {
      height: $grid-space-size;
      width: $grid-space-size;
      overflow: hidden;

      .portal {
        height: 100%;
        width: 100%;
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
          background: #000000 url("http://res.cloudinary.com/doohickey/image/upload/v1530493572/noun_infinite_878473_cccccc_t4771o.svg");
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

  // ipad landscape
  @media only screen
  and (min-device-width : 768px)
  and (max-device-width : 1024px)
  and (orientation : landscape) {
    $grid-space-size: 56px;
    $display-tool-size: 16px;
    $grid-space-font-size: 14px;

    .grid-space {
      height: $grid-space-size;
      width: $grid-space-size;
      overflow: hidden;

      .portal {
        height: 100%;
        width: 100%;
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
          background: #000000 url("http://res.cloudinary.com/doohickey/image/upload/v1530493572/noun_infinite_878473_cccccc_t4771o.svg");
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

  // ipad portrait
  @media only screen
  and (min-device-width : 768px)
  and (max-device-width : 1024px)
  and (orientation : portrait)  {
    $grid-space-size: 56px;
    $display-tool-size: 16px;
    $grid-space-font-size: 14px;

    .grid-space {
      height: $grid-space-size;
      width: $grid-space-size;
      overflow: hidden;

      .portal {
        height: 100%;
        width: 100%;
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
          background: #000000 url("http://res.cloudinary.com/doohickey/image/upload/v1530493572/noun_infinite_878473_cccccc_t4771o.svg");
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

  @media only screen and (max-width: 823px) and (orientation: landscape) {
    $grid-space-size: 32px;
    $display-tool-size: 16px;
    $grid-space-font-size: 12px;

    .grid-space {
      height: $grid-space-size;
      width: $grid-space-size;
      overflow: hidden;

      .portal {
        height: 100%;
        width: 100%;
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
          background: #000000 url("http://res.cloudinary.com/doohickey/image/upload/v1530493572/noun_infinite_878473_cccccc_t4771o.svg");
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

  @media only screen and (max-width : 736px) and (orientation: landscape) {
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
          background: #000000 url("http://res.cloudinary.com/doohickey/image/upload/v1530493572/noun_infinite_878473_cccccc_t4771o.svg");
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
          background: #000000 url("http://res.cloudinary.com/doohickey/image/upload/v1530493572/noun_infinite_878473_cccccc_t4771o.svg");
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
    $grid-space-size: 32px;
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
          background: #000000 url("http://res.cloudinary.com/doohickey/image/upload/v1530493572/noun_infinite_878473_cccccc_t4771o.svg");
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
    $grid-space-font-size: 10px;

    .grid-space {
      height: $grid-space-size;
      width: $grid-space-size;
      overflow: hidden;

      .portal {
        height: 100%;
        width: 100%;
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
          background: #000000 url("http://res.cloudinary.com/doohickey/image/upload/v1530493572/noun_infinite_878473_cccccc_t4771o.svg");
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
    $grid-space-size: 30px;
    $display-tool-size: 12px;

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
          background: #000000 url("http://res.cloudinary.com/doohickey/image/upload/v1530493572/noun_infinite_878473_cccccc_t4771o.svg");
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
  */
</style>
