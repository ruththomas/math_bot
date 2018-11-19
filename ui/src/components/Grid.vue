<template>
    <div class="container grid">
      <div v-if="gridMap" class="row" :class="robotCarrying.length ? 'no-radius-bottom-right' : ''" key="grid-map-1234">
        <div class="grid-map">
          <control-panel></control-panel>
          <div
            class="grid-row animated"
            v-for="(row, rInd) in gridMap"
            :key="'row:' + rInd"
          >
            <div
              class="grid-space animated"
              v-for="(space, sInd) in row"
              :id="`grid-cell-${rInd}-${sInd}`"
              :class="[
                !rInd ? 'no-border-top': '',
                rInd === gridMap.length - 1 ? 'no-border-bottom' : '',
                !sInd ? 'no-border-left' : '',
                sInd === row.length - 1 ? 'no-border-right' : '',
                'grid-space-' + space.name.replace(/ /g, '-')
              ]"
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
            <RobotCarrying></RobotCarrying>
          </div>
        </div>
      </div>
      <splash-screen v-else></splash-screen>
    </div>
</template>

<script>
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
    levelControl () {
      return this.$store.getters.getLevelControl
    },
    gridMap () {
      return this.levelControl.gridMap
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
    robotOrientation () {
      return this.robot.robotFacing
    },
    toolImages () {
      return this.permanentImages.tools
    },
    permanentImages () {
      return this.$store.getters.getPermanentImages
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
    closePopover: utils.closePopover
  },
  components: {
    SplashScreen,
    RobotCarrying,
    ControlPanel
  }
}
</script>

<style scoped lang="scss">
  $click-color: #B8E986;
  $grid-space-font-size: 2.5vmin;
  $grid-space-size: 9vmin;
  $grid-border-radius: 4px;
  $grid-background: rgba(0, 0, 0, 0.6);
  $display-tool-size: 2vmin;
  $popover-btn-size: 2vmin;

  .grid {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    color: #ffffff;

    .grid-map {
      position: relative;
      border-radius: $grid-border-radius 0 $grid-border-radius $grid-border-radius;
    }
  }

  .grid-row {
    display: flex;

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

      &.grid-space-wall {
        box-shadow: 0 0 30px 0 rgba(0, 0, 0, 0.5);
        background: repeating-linear-gradient(
            45deg,
            rgba(0, 0, 0, 0.3),
            rgba(0, 0, 0, 0.3) calc(25% - 5px),
            rgba(74, 74, 74, 1) calc(25% + 0.75px),
            rgba(74, 74, 74, 1) calc(25% - 5px)
        )
      }

      &.no-border-top {
        border-top: none;
      }

      &.no-border-right {
        border-right: none;
      }

      &.no-border-bottom {
        border-bottom: none;
      }

      &.no-border-left {
        border-left: none;
      }
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

  .robot-shake {
    animation: shake 0.5s;
    animation-iteration-count: infinite;
  }

  .close-popover {
    float: right;
    display: flex;
    position: absolute;
    height: $popover-btn-size;
    width: auto;
    bottom: calc(100% - #{$popover-btn-size} / 2);
    right:  calc(#{-$popover-btn-size} / 2);
    z-index: 10001;
    cursor: pointer;
  }
</style>
