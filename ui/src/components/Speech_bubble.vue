<template>
  <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="60vmin" viewBox="0 0 630 147" version="1.1">
    <defs>
      <path d="M19,6.99301658 C19,3.13088017 22.131848,0 26.0086526,0 L603.991347,0 C607.862119,0 611,3.13561414 611,6.99914214 L611,108.000858 C611,111.866377 607.875974,115 603.997452,115 L30.3554688,115 L24.4885981,117.719882 L4.34467923,127.058606 C0.84060965,128.683093 -0.145602149,127.478833 2.14892423,124.359284 L19,101.449219 L19,6.99301658 Z" id="path-1"/>
      <filter x="-2.5%" y="-11.7%" width="104.9%" height="123.5%" filterUnits="objectBoundingBox" id="filter-2">
        <feOffset dx="0" dy="0" in="SourceAlpha" result="shadowOffsetOuter1"/>
        <feGaussianBlur stdDeviation="5" in="shadowOffsetOuter1" result="shadowBlurOuter1"/>
        <feComposite in="shadowBlurOuter1" in2="SourceAlpha" operator="out" result="shadowBlurOuter1"/>
        <feColorMatrix values="0 0 0 0 0   0 0 0 0 0   0 0 0 0 0  0 0 0 0.5 0" type="matrix" in="shadowBlurOuter1"/>
      </filter>
    </defs>
    <g id="Desktop/SpeachBubble"  class="speech-bubble" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd" transform="translate(9.000000, 10.000000)" opacity="0.616904438">
      <g id="Group-2">
        <g id="Rectangle-4">
          <use fill="black" fill-opacity="1" filter="url(#filter-2)" xlink:href="#path-1"/>
          <use fill="#E8E8E8" fill-rule="evenodd" xlink:href="#path-1"/>
          <path stroke="#FFFFFF" stroke-width="1" d="M19.5,6.99301658 L19.5,101.613299 L2.55170409,124.655542 C0.564368154,127.357446 1.09832666,128.012497 4.13437945,126.604983 L30.145169,114.546377 L30.3554688,114.5 L603.997452,114.5 C607.595922,114.5 610.5,111.594147 610.5,108.000858 L610.5,6.99914214 C610.5,3.4111988 607.585418,0.5 603.991347,0.5 L26.0086526,0.5 C22.4098906,0.5 19.5,3.40512603 19.5,6.99301658 Z"/>
        </g>
      </g>
    </g>
    <foreignObject x="6%" y="20%" width="58%" height="100%">
      <div @click="videoHint.getHint()" xmlns="http://www.w3.org/1999/xhtml" class="bubble-text" v-html="needHelpAdded"></div>
    </foreignObject>
    <foreignObject x="65%" y="20%" width="30%" height="100%">
      <div
        class="button-effect help-button bubble-stars"
        size="sm"
        @click="videoHint.getHint()"
      >
        <stars
          :star-group="'star-spread'"
          :level="level"
          :step="step"
          :step-stats="stepStats"></stars>
      </div>
    </foreignObject>
  </svg>
</template>

<script>
import VideoHint from '../services/VideoHint'
import Stars from './Stars'
export default {
  name: 'speech_bubble',
  computed: {
    needHelpAdded () {
      const $html = $(this.html)
      $html.append($('<a style="color: #0000EE; cursor: pointer;"> Need help?</a>'))
      return $html.prop('outerHTML')
    },
    level () {
      return this.$store.getters.getLevel
    },
    step () {
      return this.$store.getters.getStep
    },
    steps () {
      return this.$store.getters.getSteps
    },
    stepStats () {
      const stepName = this.step
      return this.steps.find(s => s.name === stepName)
    },
    permanentImages () {
      return this.$store.getters.getPermanentImages
    }
  },
  data () {
    return {
      getTime: false,
      videoHint: new VideoHint(this)
    }
  },
  components: {Stars},
  props: ['html', 'showing']
}
</script>

<style scoped lang="scss">
  $bubble-font-size: 1.5rem;
  $click-color: #B8E986;

  .bubble-text {
    color: #000000;
    text-align: left;
    font-size: $bubble-font-size;
    line-height: calc(#{$bubble-font-size} + 30%);
  }

  .bubble-stars {
    height: 4rem;
  }
</style>
