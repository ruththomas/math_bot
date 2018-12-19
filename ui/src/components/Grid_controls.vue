<template>
<div class="grid-controls">
  <vue-slider
    ref="slider"
    v-model="sliderValue"
    v-bind="sliderOptions"
    @drag-end="resetSpeed"
    @callback="changeSpeed"
  ></vue-slider>
  <div class="buttons">
    <div class="control-btn-group">
      <img
        class="advanced-mode-button dialog-button"
        :src="permanentImages.buttons.advancedMode"
        @click="toggleAdvanced"
      >
    </div>
    <div class="control-btn-group">
      <img
        v-if="levelControl.robot.state === 'running'"
        class="pause play dialog-button"
        :src="permanentImages.buttons.pauseButton"
        @click="[levelControl.runCompiled._pausedMessage(), levelControl.runCompiled.pause()]"
      >
      <img
        v-else
        class="actual-play play dialog-button"
        :class="isLastFrame ? 'disabled' : ''"
        :src="permanentImages.buttons.playButton"
        @click="!isLastFrame ? levelControl.runCompiled.start() : ''"
      />
    </div>
    <div class="control-btn-group reset-group">
      <img
        class="reset dialog-button"
        :src="permanentImages.buttons.resetButton"
        @click="levelControl.runCompiled.reset()"
      />
    </div>
    <div
      class="control-btn-group video-group"
    >
      <!--todo-->
      <img
        v-for="(v, i) in ['1', '2', '3']"
        :key="'video-button/' + i"
        class="dialog-button"
        :class="v"
        :src="permanentImages.buttons.hint"
      >
    </div>
  </div>
</div>
</template>

<script>
import VueSlider from 'vue-slider-component'
export default {
  name: 'Grid_controls',
  computed: {
    levelControl () {
      return this.$store.getters.getLevelControl
    },
    permanentImages () {
      return this.$store.getters.getPermanentImages
    },
    isLastFrame () {
      const robotFrames = this.levelControl.runCompiled.robotFrames
      return robotFrames.length && this.levelControl.runCompiled.currentFrame === robotFrames.length - 1
    }
  },
  data () {
    return {
      sliderValue: 50,
      sliderOptions: {
        width: '75%',
        height: 2,
        'bg-style': {
          'background-color': '#B8E986',
          height: '0.2vmin'
        },
        'process-style': {
          'background-color': 'transparent'
        },
        tooltip: false,
        clickable: false
      }
    }
  },
  methods: {
    convertToSpeed () {
      if (this.sliderValue > 50) {
        return Math.abs(this.sliderValue - 100) * 8
      } else {
        return this.sliderValue * 8
      }
    },
    resetSpeed () {
      this.sliderValue = 50
      this.levelControl.runCompiled.pause()
    },
    changeSpeed () {
      this.levelControl.runCompiled.setDirection(this.sliderValue > 50)
      if (this.levelControl.robot.state === 'home' || this.levelControl.robot.state === 'paused') {
        this.levelControl.runCompiled.start()
      } else if (this.sliderValue === 50) {
        this.levelControl.runCompiled.pause()
      } else {
        this.levelControl.robot.setState('running')
        this.levelControl.robot.setSpeed(this.convertToSpeed())
      }
    },
    toggleAdvanced (evt) {
      this.levelControl.mode = this.levelControl.mode === 'normal' ? 'advanced' : 'normal'
    }
  },
  components: {
    VueSlider
  }
}
</script>

<style scoped lang="scss">
$grid-background: rgba(0, 0, 0, 0.6);
$click-color: #B8E986;
$grid-space-size: 9vmin;
$grid-space-border-color: rgba(255, 255, 255, 0.2);
.grid-controls {
  background-color: $grid-background;
  height: calc(#{$grid-space-size} / 1.85);
  width: calc(#{$grid-space-size} * 10);
  display: flex;
  flex-direction: column;
  align-items: center;

  .buttons {
    width: 100%;
    display: flex;
    justify-content: space-between;
    .control-btn-group {
      display: flex;
      flex: 1;
    }

    .control-btn-group.reset-group {
      justify-content: flex-end;
    }

    .control-btn-group.video-group {
      justify-content: flex-end;
      .dialog-button {
        transform: scale(1.5);
      }
    }

    .dialog-button {
      height: 2.5vmin;
    }

    .advanced-mode-button {

    }

    .play {
    }

    .play.dialog-button.disabled {
      opacity: 0.5;
      cursor: not-allowed;
    }

    .reset {
    }
  }
}
</style>
<style lang="scss">
.vue-slider-component.vue-slider-horizontal {
  padding: 1vmin 1vmin 0.5vmin 1vmin!important;
}
.vue-slider-dot {
  height: 1.5vmin!important;
  width: 1.5vmin!important;
  top: -0.6vmin!important;
}
</style>
