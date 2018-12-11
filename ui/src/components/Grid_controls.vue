<template>
<div class="grid-controls">
  <vue-slider
    ref="slider"
    v-model="sliderValue"
    v-bind="sliderOptions"
    @drag-end="resetSpeed"
  ></vue-slider>
  <div class="buttons">
    <img class="play dialog-button" :src="permanentImages.buttons.playButton" />
    <img class="reset dialog-button" :src="permanentImages.buttons.resetButton" />
  </div>
</div>
</template>

<script>
import VueSlider from 'vue-slider-component'
export default {
  name: 'Grid_controls',
  computed: {
    permanentImages () {
      return this.$store.getters.getPermanentImages
    }
  },
  data () {
    return {
      sliderValue: 50,
      sliderOptions: {
        width: '50%',
        height: 2,
        'bg-style': {
          'background-color': '#B8E986',
          height: '0.2vmin'
        },
        'process-style': {
          'background-color': 'transparent'
        },
        tooltip: false
      }
    }
  },
  methods: {
    resetSpeed () {
      this.sliderValue = 50
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
  border-top: 1px solid $grid-space-border-color;

  .buttons {
    width: 50%;
    .dialog-button {
      height: 2.5vmin;
    }

    .play {
      float: left;
    }

    .reset {
      float: right;
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
