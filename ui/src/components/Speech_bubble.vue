<template>
  <div class="speech-bubble" :class="!showing ? 'hide-speech-bubble' : ''">
    <div class="bubble-text" v-html="html"></div>
    <div class="bubble-stars" @click="videoHint.getHint()" v-if="videoHint !== null" data-toggle="tooltip" title="Get a hint">
      <stars
        :star-group="'star-spread'"
        :level="level"
        :step="step"
        :step-stats="stepStats"></stars>
      <span href="#">Need a hint?</span>
    </div>
  </div>
</template>

<script>
import VideoHint from '../services/VideoHint'
import Stars from './Stars'
export default {
  name: 'speech_bubble',
  computed: {
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
  $speech-bubble-color: #F0F0F0;

  .speech-bubble {
    position: relative;
    height: 116px;
    width: 594px;
    border: 1px solid #FFFF;
    background-color: $speech-bubble-color;
    box-shadow: 0 0 10px 0 rgba(0,0,0,0.5);
    border-radius: 5px;
    z-index: 100;
    visibility: visible;
    transition: opacity 1s linear;
    display: flex;
  }

  .bubble-text {
    width: 100%;
  }

  .bubble-stars {
    display: flex;
    align-items: center;
    justify-content: flex-end;
    flex-direction: column;
    span {
      color: #4a90e2;
      margin: 0;
      font-size: 16px;
      bottom: -10px;
    }

    span:hover {
      cursor: pointer;
      text-decoration: underline;
    }
  }

  .hide-speech-bubble {
    visibility: hidden;
    opacity: 0;
    transition: visibility 0s 1s, opacity 1s linear;
  }

  @keyframes fade {
    0%,100% { opacity: 0 }
    50% { opacity: 1 }
  }

  .speech-bubble:before, .speech-bubble:after {
    content: '';
    position: absolute;
    bottom: 0;
    left: 0;
    width: 0;
    height: 0;
  }

  .speech-bubble:before {
    //border-top: 40px solid #FFFF;

    transform: rotate(50deg);

    border-left: 10px solid transparent;
    border-right: 10px solid transparent;

    margin-left: -20px;
    margin-bottom: -27px;
  }

  .speech-bubble:after {
    border-top: 40px solid $speech-bubble-color;

    border-left: 9px solid transparent;
    border-right: 9px solid transparent;

    transform: rotate(50deg);

    margin-left: -18px;
    margin-bottom: -26px;
  }

  @media only screen and (max-width : 1280px) {
    .speech-bubble {
      top: 15px;
      height: 65px;
      width: 400px;
      margin-left: 10px;
    }

    .speech-bubble:before {
      border-top: 20px solid $speech-bubble-color;
      border-left: 5px solid transparent;
      border-right: 5px solid transparent;

      transform: rotate(70deg);

      margin-left: -13px;
      margin-bottom: 0;
    }

    .speech-bubble:after {
      border-top: 20px solid $speech-bubble-color;
      border-left: 5px solid transparent;
      border-right: 5px solid transparent;

      transform: rotate(70deg);

      margin-left: -11px;
      margin-bottom: 1px;
    }
  }

  /* Medium Devices, Desktops */
  @media only screen and (max-width : 992px) {
    .speech-bubble {
      top: 10px;
      height: 45px;
      width: 240px;
      margin-left: -2px;
    }

    .bubble-stars {
      span {
        font-size: 8px;
      }
    }

    .speech-bubble:before {
      border-top: 20px solid $speech-bubble-color;
      border-left: 5px solid transparent;
      border-right: 5px solid transparent;

      transform: rotate(70deg);

      margin-left: -13px;
      margin-bottom: 0;
    }

    .speech-bubble:after {
      border-top: 20px solid $speech-bubble-color;
      border-left: 5px solid transparent;
      border-right: 5px solid transparent;

      transform: rotate(70deg);

      margin-left: -11px;
      margin-bottom: 1px;
    }
  }

  /* Small Devices */
  @media only screen and (max-width : 667px) {
    .speech-bubble {
      height: 40px;
      width: 200px;
      margin-left: -2px;
      top: 5px;
    }

    .speech-bubble:before {
      border-top: 20px solid $speech-bubble-color;
      border-left: 5px solid transparent;
      border-right: 5px solid transparent;

      transform: rotate(70deg);

      margin-left: -13px;
      margin-bottom: 0;
    }

    .speech-bubble:after {
      border-top: 20px solid $speech-bubble-color;
      border-left: 5px solid transparent;
      border-right: 5px solid transparent;

      transform: rotate(70deg);

      margin-left: -11px;
      margin-bottom: 1px;
    }
  }

  /* Extra Small Devices, Phones */
  @media only screen and (max-width : 480px) {
    .speech-bubble {
      top: 0;
    }
  }

  /* Custom, iPhone 5 Retina */
  @media only screen and (max-width : 320px) {

  }

  /* iPad */
  @media all and (device-width: 768px) and (device-height: 1024px) and (orientation:portrait) {
    .speech-bubble {
      height: 100px;
      width: 450px;
      margin-left: -2px;
    }

    .speech-bubble:before, .speech-bubble:after {
      content: '';
      position: absolute;
      bottom: 0;
      left: 0;
      width: 0;
      height: 0;
    }

    .speech-bubble:before {
      border-top: 40px solid $speech-bubble-color;

      transform: rotate(50deg);

      border-left: 10px solid transparent;
      border-right: 10px solid transparent;

      margin-left: -20px;
      margin-bottom: -27px;
    }

    .speech-bubble:after {
      border-top: 40px solid $speech-bubble-color;

      border-left: 9px solid transparent;
      border-right: 9px solid transparent;

      transform: rotate(50deg);

      margin-left: -18px;
      margin-bottom: -26px;
    }

  }

  @media all and (device-width: 768px) and (device-height: 1024px) and (orientation:landscape) {
    .speech-bubble {
      height: 100px;
      width: 400px;
      margin-left: -2px;
    }
  }

</style>
<style lang="scss">
  /* GLobal styles needed to handle v-html */
  .speech-bubble {
    color: #000000;
    font-size: 24px;
    line-height: 29px;
    text-align: left;
    padding: 13px 13px 13px 20px;
  }

  .speech-bubble p {
    margin: 0!important;
  }

  /* None scoped styles for speech images */
  .speech-bubble img {
    height: 22px;
    background-color: black;
    border-radius: 2px;
  }

  @media only screen and (max-width : 1280px) {
    .speech-bubble {
      font-size: 18px;
      line-height: 20px;
      padding: 0 0 0 10px;
    }

    .speech-bubble img {
      height: 10px;
    }
  }

  /* Medium Devices, Desktops */
  @media only screen and (max-width : 992px) {
    .speech-bubble {
      font-size: 14px;
      line-height: 16px;
      padding: 0 0 0 10px;
    }

    .speech-bubble img {
      height: 10px;
    }
  }

  /* Small Devices */
  @media only screen and (max-width : 667px) {
    .speech-bubble {
      font-size: 12px;
      line-height: 14px;
      padding: 0 0 0 10px;
    }

    .speech-bubble img {
      height: 10px;
    }
  }

  /* Extra Small Devices, Phones */
  @media only screen and (max-width : 480px) {
    .speech-bubble {
      font-size: 12px;
      line-height: 14px;
      padding: 0 0 0 10px;
    }

    .speech-bubble img {
      height: 10px;
    }
  }

  /* Custom, iPhone 5 Retina */
  @media only screen and (max-width : 320px) {

  }

  /* Custom, iPhone 5 Retina */
  @media only screen and (max-width : 320px) {

  }

  /* iPad */
  @media all and (device-width: 768px) and (device-height: 1024px) and (orientation:portrait) {
    .speech-bubble {
      font-size: 24px;
      line-height: 29px;
      padding: 7px 7px 7px 15px;
    }

    .speech-bubble img {
      height: 22px;
    }
  }

  @media all and (device-width: 768px) and (device-height: 1024px) and (orientation:landscape) {
    .speech-bubble {
      font-size: 24px;
      line-height: 29px;
      padding: 7px 7px 7px 15px;
    }

    .speech-bubble img {
      height: 22px;
    }
  }
</style>
