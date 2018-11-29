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
              <div
                v-if="space.name === 'final answer' && robot.state !== 'running'"
                class="problem exponent"
                :class="[
                  isMultiProblem(problem) ? 'multi-problem' : ''
                ]"
                style="z-index: 1000;"
              >
                <span v-if="problem.includes('sqrt')" class="square-root-problem">&#8730;{{extractInteger(problem)}}</span>
                <span v-else-if="problem.includes('cbrt')" class="square-root-problem">&#8731;{{extractInteger(problem)}}</span>
                <span v-else-if="problem.includes('^')" class="exponent-problem">{{extractExpValues(problem, 0)}}<span>{{extractExpValues(problem, 1)}}</span></span>
                <span v-else>{{blankZero(problem)}}</span>
              </div>
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
                placement="bottom"
                triggers="click"
                :show.sync="space.name === 'final answer'"
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
    blankZero (problem) {
      return problem !== '0' ? problem : ''
    },
    isMultiProblem (problem) {
      return problem.split(' ').length > 1 || problem.includes('sqrt') || problem.includes('cbrt')
    },
    extractInteger (exp) {
      return exp.replace(/\D/g, '')
    },
    extractExpValues (exp, position) {
      return exp.split('^')[position].trim()
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
  $grid-space-font-size: 2vmin;
  $grid-space-size: 9vmin;
  $grid-border-radius: 4px;
  $grid-background: rgba(0, 0, 0, 0.6);
  $popover-background: rgba(0, 0, 0, 1);
  $display-tool-size: 2vmin;
  $popover-btn-size: 2vmin;
  $grid-space-border-color: rgba(255, 255, 255, 0.2);
  $multi-problem-font-size: 2.5vmin;

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
      border-top: 1px solid $grid-space-border-color;
      border-right: 1px solid $grid-space-border-color;
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

      .multi-problem {
        position: absolute;
        bottom: 70%;
        background-color: $popover-background;
        border-radius: 3px;
        padding: 0 0.2em;
        color: $click-color;
        font-size: $multi-problem-font-size;
        min-font-size: 16px;
        animation: bounce 1s infinite alternate;
        box-shadow: 0 0 100px 2vmin rgba(0,0,0,1);
        border: 1px solid $click-color;
        z-index: 100;
      }

      .exponent-problem span {
        position: relative;
        top: -1vmin;
        font-size: calc(#{$multi-problem-font-size} / 1.45);
      }

      .multi-problem::after {
        content: "";
        position: absolute;
        width: 0;
        height: 0;
        border-left: 0.5em solid transparent;
        border-right: 0.5em solid transparent;
        border-top: 0.5em solid $popover-background;
        top: 96%;
        left: 50%;
        transform: translate(-50%, 0);
      }

      .multi-problem::before {
        content: "";
        position: absolute;
        width: 0;
        height: 0;
        border-left: 0.5em solid transparent;
        border-right: 0.5em solid transparent;
        border-top: 0.5em solid $click-color;
        top: 100%;
        left: 50%;
        transform: translate(-50%, 0);
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
<style lang="scss">
$popover-color: #F8E71C;

.popover {
}

.arrow {
  border: none;
}

.popover-body {
  background-color: #000000;
  border-radius: 3px;
}

.bs-popover-bottom .arrow::after,
.bs-popover-auto[x-placement^="bottom"] .arrow::after,
.bs-popover-bottom .arrow::before,
.bs-popover-auto[x-placement^="bottom"] .arrow::before,
.bs-popover-top .arrow::before,
.bs-popover-auto[x-placement^="top"] .arrow::before,
.bs-popover-top .arrow::after,
.bs-popover-auto[x-placement^="top"] .arrow::after {
  border-bottom-color: #000000!important;
  border-top-color: #000000!important;
  left: 0!important;
}

.bs-popover-left .arrow::before,
.bs-popover-auto[x-placement^="left"] .arrow::before,
.bs-popover-left .arrow::after,
.bs-popover-auto[x-placement^="left"] .arrow::after,
.bs-popover-right .arrow::before,
.bs-popover-auto[x-placement^="right"] .arrow::before,
.bs-popover-right .arrow::after,
.bs-popover-auto[x-placement^="right"] .arrow::after {
  border-left-color: #000000!important;
  border-right-color: #000000!important;
}
</style>
