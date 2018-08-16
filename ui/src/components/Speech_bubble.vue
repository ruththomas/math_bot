<template>
  <div class="speech-bubble">
    <div class="bubble-text" v-html="html"></div>
    <div class="bubble-stars" @click="videoHint.getHint()" v-if="videoHint !== null" data-toggle="tooltip" title="???">
      <div
        class="button-effect help-button"
        size="sm"
      >
        <stars
          :star-group="'star-spread'"
          :level="level"
          :step="step"
          :step-stats="stepStats"></stars>
      </div>
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
  $click-color: #B8E986;
  $click-color-faded: rgba(0, 0, 0, 0.6);

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
      position: relative;
      width: 40%;
      display: flex;
      align-items: center;

      .help-button {
        position: relative;
        height: 50%;
        display: flex;
        align-items: center;
        justify-content: center;
        min-height: 25px;
        margin: 5%;
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

  /* ipad pro Portrait */
  @media only screen
  and (min-device-width: 1024px)
  and (max-device-width: 1366px)
  and (orientation: portrait)
  and (-webkit-min-device-pixel-ratio: 1.5) {
    $speech-bubble-color: #D3D3D3;
    $speech-bubble-border-color: #f0f0f0;
    $speech-bubble-height: 85px;
    $speech-bubble-width: 380px;
    $arrow-deg: 70deg;
    $arrow-border-bottom: -8px;
    $arrow-bottom: -7px;
    $arrow-left-border: -19px;
    $arrow-left: -17px;
    $arrow-length: 30px;
    $arrow-border-height: 5px;
    $arrow-height: 4px;
    $bubble-margin-bottom: 8px;
    $hint-font-size: 8px;
    $hint-line-height: 8px;

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

      .bubble-stars {
        display: flex;
        align-items: center;
        justify-content: center;
        flex-direction: column;
        padding: 1%;

        span {
          color: #4a90e2;
          margin: 0;
          font-size: $hint-font-size;
          line-height: $hint-line-height;
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

  /* ipad portrait */
  @media only screen
  and (min-device-width : 768px)
  and (max-device-width : 1024px)
  and (orientation : portrait) {
    $speech-bubble-color: #D3D3D3;
    $speech-bubble-border-color: #f0f0f0;
    $speech-bubble-height: 85px;
    $speech-bubble-width: 380px;
    $arrow-deg: 70deg;
    $arrow-border-bottom: -8px;
    $arrow-bottom: -7px;
    $arrow-left-border: -19px;
    $arrow-left: -17px;
    $arrow-length: 30px;
    $arrow-border-height: 5px;
    $arrow-height: 4px;
    $bubble-margin-bottom: 8px;
    $hint-font-size: 8px;
    $hint-line-height: 8px;

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

      .bubble-stars {
        display: flex;
        align-items: center;
        justify-content: center;
        flex-direction: column;
        padding: 1%;

        span {
          color: #4a90e2;
          margin: 0;
          font-size: $hint-font-size;
          line-height: $hint-line-height;
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

  /* ipad landscape */
  @media only screen
  and (min-device-width : 768px)
  and (max-device-width : 1024px)
  and (orientation : landscape) {
    $speech-bubble-color: #D3D3D3;
    $speech-bubble-border-color: #f0f0f0;
    $speech-bubble-height: 85px;
    $speech-bubble-width: 380px;
    $arrow-deg: 70deg;
    $arrow-border-bottom: -8px;
    $arrow-bottom: -7px;
    $arrow-left-border: -19px;
    $arrow-left: -17px;
    $arrow-length: 30px;
    $arrow-border-height: 5px;
    $arrow-height: 4px;
    $bubble-margin-bottom: 8px;
    $hint-font-size: 8px;
    $hint-line-height: 8px;

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

      .bubble-stars {
        display: flex;
        align-items: center;
        justify-content: center;
        flex-direction: column;
        padding: 1%;

        span {
          color: #4a90e2;
          margin: 0;
          font-size: $hint-font-size;
          line-height: $hint-line-height;
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
    $hint-font-size: 22px;
    $hint-line-height: 24px;

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
        display: flex;
        align-items: center;
        justify-content: center;
        flex-direction: column;
        padding: 1%;
        position: relative;

        span {
          color: #4a90e2;
          position: absolute;
          margin: 0;
          font-size: $hint-font-size;
          line-height: $hint-line-height;
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
    $hint-font-size: 8px;
    $hint-line-height: 10px;

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
        display: flex;
        align-items: center;
        justify-content: center;
        flex-direction: column;
        padding: 1%;

        span {
          color: #4a90e2;
          margin: 0;
          font-size: $hint-font-size;
          line-height: $hint-line-height;
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
    $hint-font-size: 8px;
    $hint-line-height: 10px;

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
        display: flex;
        align-items: center;
        justify-content: center;
        flex-direction: column;
        padding: 1%;

        span {
          color: #4a90e2;
          margin: 0;
          font-size: $hint-font-size;
          line-height: $hint-line-height;
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
    $hint-font-size: 8px;
    $hint-line-height: 10px;

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
        display: flex;
        align-items: center;
        justify-content: center;
        flex-direction: column;
        padding: 1%;

        span {
          color: #4a90e2;
          margin: 0;
          font-size: $hint-font-size;
          line-height: $hint-line-height;
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
    $hint-font-size: 8px;
    $hint-line-height: 8px;

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
        display: flex;
        align-items: center;
        justify-content: center;
        flex-direction: column;
        padding: 1%;

        span {
          color: #4a90e2;
          margin: 0;
          font-size: $hint-font-size;
          line-height: $hint-line-height;
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
    $hint-font-size: 8px;
    $hint-line-height: 8px;

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
        display: flex;
        align-items: center;
        justify-content: center;
        flex-direction: column;
        padding: 1%;

        span {
          color: #4a90e2;
          margin: 0;
          bottom: -10px;
          font-size: $hint-font-size;
          line-height: $hint-line-height;
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
    $hint-font-size: 8px;
    $hint-line-height: 8px;

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
        display: flex;
        align-items: center;
        justify-content: center;
        flex-direction: column;
        padding: 1%;

        span {
          color: #4a90e2;
          margin: 0;
          font-size: $hint-font-size;
          line-height: $hint-line-height;
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
  $bubble-font-size: 24px;
  $bubble-line-height: 29px;
  $bubble-text-padding: 13px 13px 13px 20px;
  .speech-bubble {
    .bubble-text {
      color: #000000;
      font-size: $bubble-font-size;
      line-height: $bubble-line-height;
      text-align: left;
      padding: $bubble-text-padding;
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

  /* ipad pro Portrait */
  @media only screen
  and (min-device-width: 1024px)
  and (max-device-width: 1366px)
  and (orientation: portrait)
  and (-webkit-min-device-pixel-ratio: 1.5) {
    $bubble-font-size: 18px;
    $bubble-line-height: 14px;
    $bubble-text-padding: 5px 5px 5px 10px;
    .speech-bubble {
      .bubble-text {
        font-size: $bubble-font-size;
        line-height: $bubble-line-height;
        padding: $bubble-text-padding;
      }
    }
  }

  // ipad landscape
  @media only screen
  and (min-device-width : 768px)
  and (max-device-width : 1024px)
  and (orientation : landscape) {
    $bubble-font-size: 18px;
    $bubble-line-height: 14px;
    $bubble-text-padding: 5px 5px 5px 10px;
    .speech-bubble {
      .bubble-text {
        font-size: $bubble-font-size;
        line-height: $bubble-line-height;
        padding: $bubble-text-padding;
      }
    }
  }

  // ipad portrait
  @media only screen
  and (min-device-width : 768px)
  and (max-device-width : 1024px)
  and (orientation : portrait) {
    $bubble-font-size: 18px;
    $bubble-line-height: 14px;
    $bubble-text-padding: 5px 5px 5px 10px;
    .speech-bubble {
      .bubble-text {
        font-size: $bubble-font-size;
        line-height: $bubble-line-height;
        padding: $bubble-text-padding;
      }
    }
  }

  @media only screen and (max-width: 823px) and (orientation: landscape) {
    $bubble-font-size: 12px;
    $bubble-line-height: 14px;
    $bubble-text-padding: 0px 0px 0px 10px;
    .speech-bubble {
      .bubble-text {
        font-size: $bubble-font-size;
        line-height: $bubble-line-height;
        padding: $bubble-text-padding;
      }
    }
  }

  @media only screen and (max-width : 736px) and (orientation: landscape) {
    $bubble-font-size: 12px;
    $bubble-line-height: 14px;
    $bubble-text-padding: 0px 0px 0px 10px;
    .speech-bubble {
      .bubble-text {
        font-size: $bubble-font-size;
        line-height: $bubble-line-height;
        padding: $bubble-text-padding;
      }
    }
  }

  @media only screen and (max-width: 667px) and (orientation: landscape) {
    $bubble-font-size: 12px;
    $bubble-line-height: 14px;
    $bubble-text-padding: 0px 0px 0px 10px;
    .speech-bubble {
      .bubble-text {
        font-size: $bubble-font-size;
        line-height: $bubble-line-height;
        padding: $bubble-text-padding;
      }
    }
  }

  /* iphone 5 landscape*/
  @media only screen and (max-width: 568px) and (orientation: landscape){
    $bubble-font-size: 10px;
    $bubble-line-height: 14px;
    $bubble-text-padding: 0px 0px 0px 10px;
    .speech-bubble {
      .bubble-text {
        font-size: $bubble-font-size;
        line-height: $bubble-line-height;
        padding: $bubble-text-padding;
      }
    }
  }

  @media only screen and (max-width: 414px) {
    $bubble-font-size: 12px;
    $bubble-line-height: 14px;
    $bubble-text-padding: 0px 0px 0px 10px;
    .speech-bubble {
      .bubble-text {
        font-size: $bubble-font-size;
        line-height: $bubble-line-height;
        padding: $bubble-text-padding;
      }
    }
  }

  @media only screen and (max-width: 375px) {
    $bubble-font-size: 12px;
    $bubble-line-height: 14px;
    $bubble-text-padding: 0px 0px 0px 10px;
    .speech-bubble {
      .bubble-text {
        font-size: $bubble-font-size;
        line-height: $bubble-line-height;
        padding: $bubble-text-padding;
      }
    }
  }

  @media only screen and (max-width : 320px) {
    $bubble-font-size: 12px;
    $bubble-line-height: 14px;
    $bubble-text-padding: 0px 0px 0px 5px;
    .speech-bubble {
      .bubble-text {
        font-size: $bubble-font-size;
        line-height: $bubble-line-height;
        padding: $bubble-text-padding;
      }
    }
  }
</style>
