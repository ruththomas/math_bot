<template>
  <div class="speech-bubble">
    <div class="bubble-text" v-html="html"></div>
    <div class="bubble-stars" @click="videoHint.getHint()" v-if="videoHint !== null" data-toggle="tooltip" title="Get a hint">
      <stars
        :star-group="'star-spread'"
        :level="level"
        :step="step"
        :step-stats="stepStats"></stars>
      <span>Need a hint?</span>
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
  $speech-bubble-color: #D3D3D3;
  $speech-bubble-border-color: #f0f0f0;
  $speech-bubble-height: 116px;
  $speech-bubble-width: 594px;
  $arrow-deg: 66deg;
  $arrow-border-bottom: -20px;
  $arrow-bottom: -19px;
  $arrow-left-border: -25px;
  $arrow-left: -23px;
  $arrow-length: 40px;
  $arrow-border-height: 10px;
  $arrow-height: 9px;
  $bubble-margin-bottom: 30px;

  .speech-bubble {
    position: relative;
    height: $speech-bubble-height;
    width: $speech-bubble-width;
    background-color: $speech-bubble-color;
    border: 1px solid $speech-bubble-border-color;
    box-shadow: 0 0 10px 0 rgba(0, 0, 0, 1);
    border-radius: 5px;
    z-index: 100;
    display: flex;
    margin-left: calc(#{$arrow-length} - 10px);
    margin-bottom: $bubble-margin-bottom;

    .bubble-text {
      width: 100%;
    }

    .bubble-text {
      width: 100%;
    }

    .bubble-stars {
      display: none;
      /*display: flex;*/
      align-items: center;
      justify-content: center;
      flex-direction: column;
      padding: 1%;

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

    &:before, &:after {
      content: '';
      position: absolute;
      bottom: 0;
      left: 0;
      width: 0;
      height: 0;
    }

    &:before {
      border-top: $arrow-length solid $speech-bubble-border-color;

      transform: rotate(#{$arrow-deg});

      border-left: $arrow-border-height solid transparent;
      border-right: $arrow-border-height solid transparent;

      margin-left: $arrow-left-border;
      margin-bottom: $arrow-border-bottom;
    }

    &:after {
      border-top: $arrow-length solid $speech-bubble-color;

      border-left: $arrow-height solid transparent;
      border-right: $arrow-height solid transparent;

      transform: rotate(#{$arrow-deg});

      margin-left: $arrow-left;
      margin-bottom: $arrow-bottom;
    }
  }

  @media only screen and (max-width: 823px) and (orientation: landscape) {
    $speech-bubble-color: #D3D3D3;
    $speech-bubble-border-color: #f0f0f0;
    $speech-bubble-height: 45px;
    $speech-bubble-width: 225px;
    $arrow-deg: 70deg;
    $arrow-border-bottom: -8px;
    $arrow-bottom: -7px;
    $arrow-left-border: -14px;
    $arrow-left: -12px;
    $arrow-length: 20px;
    $arrow-border-height: 5px;
    $arrow-height: 4px;
    $bubble-margin-bottom: 8px;

    .speech-bubble {
      position: relative;
      height: $speech-bubble-height;
      width: $speech-bubble-width;
      background-color: $speech-bubble-color;
      border: 1px solid $speech-bubble-border-color;
      box-shadow: 0 0 10px 0 rgba(0, 0, 0, 1);
      border-radius: 5px;
      z-index: 100;
      display: flex;
      margin-left: $arrow-length;
      margin-bottom: $bubble-margin-bottom;

      .bubble-text {
        width: 100%;
      }

      .bubble-text {
        width: 100%;
      }

      .bubble-stars {
        display: /*flex*/ none;
        align-items: center;
        justify-content: center;
        flex-direction: column;
        padding: 1%;

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

      &:before, &:after {
        content: '';
        position: absolute;
        bottom: 0;
        left: 0;
        width: 0;
        height: 0;
      }

      &:before {
        border-top: $arrow-length solid $speech-bubble-border-color;

        transform: rotate(#{$arrow-deg});

        border-left: $arrow-border-height solid transparent;
        border-right: $arrow-border-height solid transparent;

        margin-left: $arrow-left-border;
        margin-bottom: $arrow-border-bottom;
      }

      &:after {
        border-top: $arrow-length solid $speech-bubble-color;

        border-left: $arrow-height solid transparent;
        border-right: $arrow-height solid transparent;

        transform: rotate(#{$arrow-deg});

        margin-left: $arrow-left;
        margin-bottom: $arrow-bottom;
      }
    }
  }

  @media only screen and (max-width : 736px) and (orientation: landscape) {
    $speech-bubble-color: #D3D3D3;
    $speech-bubble-border-color: #f0f0f0;
    $speech-bubble-height: 45px;
    $speech-bubble-width: 240px;
    $arrow-deg: 70deg;
    $arrow-border-bottom: -8px;
    $arrow-bottom: -7px;
    $arrow-left-border: -14px;
    $arrow-left: -12px;
    $arrow-length: 20px;
    $arrow-border-height: 5px;
    $arrow-height: 4px;
    $bubble-margin-bottom: 8px;

    .speech-bubble {
      position: relative;
      height: $speech-bubble-height;
      width: $speech-bubble-width;
      background-color: $speech-bubble-color;
      border: 1px solid $speech-bubble-border-color;
      box-shadow: 0 0 10px 0 rgba(0, 0, 0, 1);
      border-radius: 5px;
      z-index: 100;
      display: flex;
      margin-left: $arrow-length;
      margin-bottom: $bubble-margin-bottom;

      .bubble-text {
        width: 100%;
      }

      .bubble-text {
        width: 100%;
      }

      .bubble-stars {
        display: /*flex*/ none;
        align-items: center;
        justify-content: center;
        flex-direction: column;
        padding: 1%;

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

      &:before, &:after {
        content: '';
        position: absolute;
        bottom: 0;
        left: 0;
        width: 0;
        height: 0;
      }

      &:before {
        border-top: $arrow-length solid $speech-bubble-border-color;

        transform: rotate(#{$arrow-deg});

        border-left: $arrow-border-height solid transparent;
        border-right: $arrow-border-height solid transparent;

        margin-left: $arrow-left-border;
        margin-bottom: $arrow-border-bottom;
      }

      &:after {
        border-top: $arrow-length solid $speech-bubble-color;

        border-left: $arrow-height solid transparent;
        border-right: $arrow-height solid transparent;

        transform: rotate(#{$arrow-deg});

        margin-left: $arrow-left;
        margin-bottom: $arrow-bottom;
      }
    }
  }

  @media only screen and (max-width: 667px) and (orientation: landscape) {
    $speech-bubble-color: #D3D3D3;
    $speech-bubble-border-color: #f0f0f0;
    $speech-bubble-height: 35px;
    $speech-bubble-width: 215px;
    $arrow-deg: 70deg;
    $arrow-border-bottom: -8px;
    $arrow-bottom: -7px;
    $arrow-left-border: -14px;
    $arrow-left: -12px;
    $arrow-length: 20px;
    $arrow-border-height: 5px;
    $arrow-height: 4px;
    $bubble-margin-bottom: 8px;

    .speech-bubble {
      position: relative;
      height: $speech-bubble-height;
      width: $speech-bubble-width;
      background-color: $speech-bubble-color;
      border: 1px solid $speech-bubble-border-color;
      box-shadow: 0 0 10px 0 rgba(0, 0, 0, 1);
      border-radius: 5px;
      z-index: 100;
      display: flex;
      margin-left: $arrow-length;
      margin-bottom: $bubble-margin-bottom;

      .bubble-text {
        width: 100%;
      }

      .bubble-text {
        width: 100%;
      }

      .bubble-stars {
        display: /*flex*/ none;
        align-items: center;
        justify-content: center;
        flex-direction: column;
        padding: 1%;

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

      &:before, &:after {
        content: '';
        position: absolute;
        bottom: 0;
        left: 0;
        width: 0;
        height: 0;
      }

      &:before {
        border-top: $arrow-length solid $speech-bubble-border-color;

        transform: rotate(#{$arrow-deg});

        border-left: $arrow-border-height solid transparent;
        border-right: $arrow-border-height solid transparent;

        margin-left: $arrow-left-border;
        margin-bottom: $arrow-border-bottom;
      }

      &:after {
        border-top: $arrow-length solid $speech-bubble-color;

        border-left: $arrow-height solid transparent;
        border-right: $arrow-height solid transparent;

        transform: rotate(#{$arrow-deg});

        margin-left: $arrow-left;
        margin-bottom: $arrow-bottom;
      }
    }
  }

  /* iphone 5 landscape*/
  @media only screen and (max-width: 568px) and (orientation: landscape){
    $speech-bubble-color: #D3D3D3;
    $speech-bubble-border-color: #f0f0f0;
    $speech-bubble-height: 35px;
    $speech-bubble-width: 175px;
    $arrow-deg: 70deg;
    $arrow-border-bottom: -8px;
    $arrow-bottom: -7px;
    $arrow-left-border: -14px;
    $arrow-left: -12px;
    $arrow-length: 20px;
    $arrow-border-height: 5px;
    $arrow-height: 4px;
    $bubble-margin-bottom: 8px;

    .speech-bubble {
      position: relative;
      height: $speech-bubble-height;
      width: $speech-bubble-width;
      background-color: $speech-bubble-color;
      border: 1px solid $speech-bubble-border-color;
      box-shadow: 0 0 10px 0 rgba(0, 0, 0, 1);
      border-radius: 5px;
      z-index: 100;
      display: flex;
      margin-left: $arrow-length;
      margin-bottom: $bubble-margin-bottom;

      .bubble-text {
        width: 100%;
      }

      .bubble-text {
        width: 100%;
      }

      .bubble-stars {
        display: /*flex*/ none;
        align-items: center;
        justify-content: center;
        flex-direction: column;
        padding: 1%;

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

      &:before, &:after {
        content: '';
        position: absolute;
        bottom: 0;
        left: 0;
        width: 0;
        height: 0;
      }

      &:before {
        border-top: $arrow-length solid $speech-bubble-border-color;

        transform: rotate(#{$arrow-deg});

        border-left: $arrow-border-height solid transparent;
        border-right: $arrow-border-height solid transparent;

        margin-left: $arrow-left-border;
        margin-bottom: $arrow-border-bottom;
      }

      &:after {
        border-top: $arrow-length solid $speech-bubble-color;

        border-left: $arrow-height solid transparent;
        border-right: $arrow-height solid transparent;

        transform: rotate(#{$arrow-deg});

        margin-left: $arrow-left;
        margin-bottom: $arrow-bottom;
      }
    }
  }

  @media only screen and (max-width: 414px) {
    $speech-bubble-color: #D3D3D3;
    $speech-bubble-border-color: #f0f0f0;
    $speech-bubble-height: 55px;
    $speech-bubble-width: 215px;
    $arrow-deg: 70deg;
    $arrow-border-bottom: -8px;
    $arrow-bottom: -7px;
    $arrow-left-border: -14px;
    $arrow-left: -12px;
    $arrow-length: 20px;
    $arrow-border-height: 5px;
    $arrow-height: 4px;
    $bubble-margin-bottom: 8px;

    .speech-bubble {
      position: relative;
      height: $speech-bubble-height;
      width: $speech-bubble-width;
      background-color: $speech-bubble-color;
      border: 1px solid $speech-bubble-border-color;
      box-shadow: 0 0 10px 0 rgba(0, 0, 0, 1);
      border-radius: 5px;
      z-index: 100;
      display: flex;
      margin-left: $arrow-length;
      margin-bottom: $bubble-margin-bottom;

      .bubble-text {
        width: 100%;
      }

      .bubble-text {
        width: 100%;
      }

      .bubble-stars {
        display: /*flex*/ none;
        align-items: center;
        justify-content: center;
        flex-direction: column;
        padding: 1%;

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

      &:before, &:after {
        content: '';
        position: absolute;
        bottom: 0;
        left: 0;
        width: 0;
        height: 0;
      }

      &:before {
        border-top: $arrow-length solid $speech-bubble-border-color;

        transform: rotate(#{$arrow-deg});

        border-left: $arrow-border-height solid transparent;
        border-right: $arrow-border-height solid transparent;

        margin-left: $arrow-left-border;
        margin-bottom: $arrow-border-bottom;
      }

      &:after {
        border-top: $arrow-length solid $speech-bubble-color;

        border-left: $arrow-height solid transparent;
        border-right: $arrow-height solid transparent;

        transform: rotate(#{$arrow-deg});

        margin-left: $arrow-left;
        margin-bottom: $arrow-bottom;
      }
    }
  }

  @media only screen and (max-width: 375px) {
    $speech-bubble-color: #D3D3D3;
    $speech-bubble-border-color: #f0f0f0;
    $speech-bubble-height: 55px;
    $speech-bubble-width: 175px;
    $arrow-deg: 70deg;
    $arrow-border-bottom: -8px;
    $arrow-bottom: -7px;
    $arrow-left-border: -14px;
    $arrow-left: -12px;
    $arrow-length: 20px;
    $arrow-border-height: 5px;
    $arrow-height: 4px;
    $bubble-margin-bottom: 8px;

    .speech-bubble {
      position: relative;
      height: $speech-bubble-height;
      width: $speech-bubble-width;
      background-color: $speech-bubble-color;
      border: 1px solid $speech-bubble-border-color;
      box-shadow: 0 0 10px 0 rgba(0, 0, 0, 1);
      border-radius: 5px;
      z-index: 100;
      display: flex;
      margin-left: $arrow-length;
      margin-bottom: $bubble-margin-bottom;

      .bubble-text {
        width: 100%;
      }

      .bubble-text {
        width: 100%;
      }

      .bubble-stars {
        display: /*flex*/ none;
        align-items: center;
        justify-content: center;
        flex-direction: column;
        padding: 1%;

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

      &:before, &:after {
        content: '';
        position: absolute;
        bottom: 0;
        left: 0;
        width: 0;
        height: 0;
      }

      &:before {
        border-top: $arrow-length solid $speech-bubble-border-color;

        transform: rotate(#{$arrow-deg});

        border-left: $arrow-border-height solid transparent;
        border-right: $arrow-border-height solid transparent;

        margin-left: $arrow-left-border;
        margin-bottom: $arrow-border-bottom;
      }

      &:after {
        border-top: $arrow-length solid $speech-bubble-color;

        border-left: $arrow-height solid transparent;
        border-right: $arrow-height solid transparent;

        transform: rotate(#{$arrow-deg});

        margin-left: $arrow-left;
        margin-bottom: $arrow-bottom;
      }
    }
  }

  @media only screen and (max-width : 320px) {
    $speech-bubble-color: #D3D3D3;
    $speech-bubble-border-color: #f0f0f0;
    $speech-bubble-height: 50px;
    $speech-bubble-width: 175px;
    $arrow-deg: 70deg;
    $arrow-border-bottom: -8px;
    $arrow-bottom: -7px;
    $arrow-left-border: -14px;
    $arrow-left: -12px;
    $arrow-length: 20px;
    $arrow-border-height: 5px;
    $arrow-height: 4px;
    $bubble-margin-bottom: 8px;

    .speech-bubble {
      position: relative;
      height: $speech-bubble-height;
      width: $speech-bubble-width;
      background-color: $speech-bubble-color;
      border: 1px solid $speech-bubble-border-color;
      box-shadow: 0 0 10px 0 rgba(0, 0, 0, 1);
      border-radius: 5px;
      z-index: 100;
      display: flex;
      margin-left: $arrow-length;
      margin-bottom: $bubble-margin-bottom;

      .bubble-text {
        width: 100%;
      }

      .bubble-text {
        width: 100%;
      }

      .bubble-stars {
        display: /*flex*/ none;
        align-items: center;
        justify-content: center;
        flex-direction: column;
        padding: 1%;

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

      &:before, &:after {
        content: '';
        position: absolute;
        bottom: 0;
        left: 0;
        width: 0;
        height: 0;
      }

      &:before {
        border-top: $arrow-length solid $speech-bubble-border-color;

        transform: rotate(#{$arrow-deg});

        border-left: $arrow-border-height solid transparent;
        border-right: $arrow-border-height solid transparent;

        margin-left: $arrow-left-border;
        margin-bottom: $arrow-border-bottom;
      }

      &:after {
        border-top: $arrow-length solid $speech-bubble-color;

        border-left: $arrow-height solid transparent;
        border-right: $arrow-height solid transparent;

        transform: rotate(#{$arrow-deg});

        margin-left: $arrow-left;
        margin-bottom: $arrow-bottom;
      }
    }
  }

</style>
<style lang="scss">
  /* Global styles needed to handle v-html */
  .speech-bubble {
    .bubble-text {
      display: none;
      color: #000000;
      font-size: 24px;
      line-height: 29px;
      text-align: left;
      padding: 13px 13px 13px 20px;
      p {
        margin: 0!important;
      }
      img {
        height: 22px;
        background-color: black;
        border-radius: 2px;
      }
    }
  }

  @media only screen and (max-width: 823px) and (orientation: landscape) {
  }

  @media only screen and (max-width : 736px) and (orientation: landscape) {
  }

  @media only screen and (max-width: 667px) and (orientation: landscape) {
  }

  /* iphone 5 landscape*/
  @media only screen and (max-width: 568px) and (orientation: landscape){
  }

  @media only screen and (max-width: 414px) {
  }

  @media only screen and (max-width: 375px) {
  }

  @media only screen and (max-width : 320px) {
    .speech-bubble {}
  }
</style>
