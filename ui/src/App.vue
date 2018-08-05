<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
// import api from './services/api'
// import utils from './services/utils'

export default {
  name: 'app',
  mounted () {
    window.scrollTo(0, 1)
    this.auth.isAuthenticated()
  },
  computed: {
    auth () {
      return this.$store.getters.getAuth
    }
  }
}
</script>

<style lang="scss">
  $click-color: #B8E986;

  body {
    font-family: "Proba Pro Regular", serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #FFFFFF;
    height: 100vh;
    width: 100vw;
    background-color: #000000!important;
    position: relative;
    display: flex;
    z-index: 200;
  }

  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    position: relative;
    height: 100vh;
    width: 100vw;
    padding: 0;
    overflow: hidden;
  }

  .handle {
    cursor: move;
    cursor: -webkit-grabbing;
  }

  .dialog-button {
    height: 40px;
    width: 40px;
    cursor: pointer;
  }

  .button-effect {
    color: #000000;
    font-size: 22px;
    display: flex;
    justify-content: center;
    align-items: center;
    text-align: center;
    border-radius: 3px;
    background-color: $click-color;
    box-shadow: 0 2px 10px 0 rgba(0,0,0,0.5);
    cursor: pointer;
    padding: 3px;
    &:hover {
      background-color: $click-color;
    }
  }

  .popover {
    background-color: rgba(0, 0, 0, 0.8);
  }

  /*This is where v-html styles must go*/
  .speechImage {
    height: 20px !important;
    width: 20px !important;
  }

  .dragging, .ghost, .chosen {
    height: 70px;
    width: 70px;

    .pointer {
      display: none;
    }
  }

  .dragging {
    opacity: 1;
  }

  .ghost, .chosen {
    margin: 0 12px 0 12px;
  }

  .ghost {
    opacity: 0;
  }

  .pulse {
    box-shadow: 0 0 0 rgb(184, 233, 134);
    animation: pulse 1s;
    animation-iteration-count: 2;
  }

  .pointer {
    position: absolute;
    width: 40px;
    height: 20px;
    bottom: 100%;
    left: 50%;
    transform: translateX(-50%);
    z-index: 1011;
  }

  .pointer-hidden {
    opacity: 0;
  }

  .pointer-size {
    position: absolute;
    border-style: solid;
    border-width: 20px 18px 0 18px;
  }

  .pointer-body {
    top: -1px;
    z-index: 1001;
    border-color: #000000 transparent transparent transparent;
  }

  .pointer-border {
    top: 1px;
    z-index: 0;
    border-color: #737373 transparent transparent transparent;
  }

  @keyframes pulse {
    0% {
      box-shadow: 0 0 0 0 rgb(184, 233, 134);
    }
    70% {
      box-shadow: 0 0 0 50px rgba(184, 233, 134, 0);
    }
    100% {
      box-shadow: 0 0 0 0 rgba(184, 233, 134, 0);
    }
  }

  @keyframes shake {
    0% { transform: translate(1px, 1px) rotate(0deg); }
    10% { transform: translate(-1px, -2px) rotate(-1deg); }
    20% { transform: translate(-3px, 0px) rotate(1deg); }
    30% { transform: translate(3px, 2px) rotate(0deg); }
    40% { transform: translate(1px, -1px) rotate(1deg); }
    50% { transform: translate(-1px, 2px) rotate(-1deg); }
    60% { transform: translate(-3px, 1px) rotate(0deg); }
    70% { transform: translate(3px, 1px) rotate(-1deg); }
    80% { transform: translate(-1px, -1px) rotate(1deg); }
    90% { transform: translate(1px, 2px) rotate(0deg); }
    100% { transform: translate(1px, -2px) rotate(-1deg); }
  }

  /* ipad landscape */
  @media only screen
  and (min-device-width : 768px)
  and (max-device-width : 1024px)
  and (orientation : landscape) {
    .dialog-button {
      height: 30px;
      width: 30px;
    }
  }

  /* Small Devices */
  @media only screen and (max-width : 823px) {
    .dialog-button {
      height: 20px;
      width: 20px;
    }
  }

  @media only screen
  and (min-device-width : 768px)
  and (max-device-width : 1024px)
  and (orientation : portrait) {
    .dialog-button {
      height: 30px;
      width: 30px;
    }
  }

  /* Extra Small Devices, Phones */
  @media only screen and (max-width : 480px) {
  }

  /* iphone 5 landscape */
  @media only screen and (max-width : 568px) {
  }

  /* iphone 5 portrait */
  @media only screen and (max-width : 414px) {
    ::-webkit-scrollbar {
      width: 12px;
      height: 12px;
    }
  }

  ::-webkit-scrollbar {
    width: 22px;
    height: 22px;
  }
  ::-webkit-scrollbar-track {
    visibility: hidden;
    background: transparent;
  }
  ::-webkit-scrollbar-thumb {
    box-shadow: inset 0 0 10px 10px rgba(216, 216, 216, 0.5);
    border: solid 8px transparent;
    border-radius: 10px;
    background: transparent;
  }
</style>

<style lang="scss">
  $pagination-active-color: #B8E986;
  $pagination-inactive-color: rgba(255, 255, 255, 0.3);
  $pagination-bullet-size: 14px;
  $button-height: 100%;
  $button-offset: -47px;
  $edit-main-button-offset-left: -41px;
  $edit-main-button-offset-right: -29px;

  .swiper-container {
    display: flex;
    height: 100%;
    width: 100%;
  }

  .swiper-wrapper {
    width: 100%;
  }

  .swiper-slide {
    width: 100%;
  }

  .swiper-slide:first-child {
    .editMain-function-drop {
      justify-content: flex-end;
      padding-right: 49px;
    }
  }

  .swiper-pagination.swiper-pagination-bullets {
    bottom: -3px;
  }

  .swiper-pagination-bullet-custom {
    width: $pagination-bullet-size;
    height: $pagination-bullet-size;
    text-align: center;
    line-height: 20px;
    font-size: 12px;
    color: #000;
    opacity: 1;
    background: $pagination-inactive-color;
  }

  .swiper-pagination-bullet-custom.swiper-pagination-bullet-active {
    color: #fff;
    background: $pagination-active-color;
    &:focus {
      outline: none;
    }
  }

  .swiper-button-next, .swiper-button-prev {
    height: 100%;
    top: 0;
    bottom: 0;
    margin: 0;
    width: auto;
    display: flex;
    align-items: center;
    background: none;
    cursor: pointer;
    .piece {
      cursor: pointer!important;
    }
    &:focus {
      outline: none;
    }
  }

  .functions-swiper {
    .swiper-button-next, .swiper-button-prev {
      align-items: flex-start;
    }
  }

  .swiper-button-prev {
    left: $button-offset;
  }

  .swiper-button-next {
    right: $button-offset;
  }

  .swiper-button-slide-right {
    transition-duration: 300ms;
    transform: translate3d(90px, 0px, 0px);
  }

  .swiper-button-slide-left {
    transition-duration: 300ms;
    transform: translate3d(-90px, 0px, 0px);
  }

  .editMain-function-drop-swiper {
    .swiper-button-prev {
      left: $edit-main-button-offset-left;
    }

    .swiper-button-next {
      right: $edit-main-button-offset-right;
    }
  }

  .swiper-button-disabled {
    display: none
  }

  .hidden-swiper-pagination {
    opacity: 0;
  }

  /* Medium Devices, Desktops */
  @media only screen and (max-width : 992px) {
  }

  /* Small Devices */
  @media only screen and (max-width : 667px) {

  }

  /* iphone 5 landscape */
  @media only screen and (max-width : 568px) {
    $pagination-bullet-size: 8px;
    $button-offset: -21px;
    $button-height: 32px;
    $edit-main-button-offset-right: -13px;
    $edit-main-button-offset-left: -21px;

    .swiper-button-prev {
      left: $button-offset;
    }

    .swiper-button-next {
      right: $button-offset;
    }

    .swiper-pagination-bullet-custom {
      width: $pagination-bullet-size;
      height: $pagination-bullet-size;
    }

    .functions-swiper {
      .swiper-button-prev, .swiper-button-next {
        height: $button-height;
      }

      .swiper-pagination.swiper-pagination-bullets {
        top: $button-height;
      }
    }

    .editMain-function-drop-swiper {
      .swiper-button-prev {
        left: $edit-main-button-offset-left;
      }

      .swiper-button-next {
        right: $edit-main-button-offset-right;
      }
    }
  }

  /* iphone 5 portrait */
  @media only screen and (max-width : 320px) {
    $pagination-bullet-size: 8px;
    $button-offset: -21px;
    $button-height: 32px;
    $edit-main-button-offset-right: -13px;
    $edit-main-button-offset-left: -21px;

    .swiper-button-prev {
      left: $button-offset;
    }

    .swiper-button-next {
      right: $button-offset;
    }

    .swiper-pagination-bullet-custom {
      width: $pagination-bullet-size;
      height: $pagination-bullet-size;
    }

    .functions-swiper {
      .swiper-button-prev, .swiper-button-next {
        height: $button-height;
      }

      .swiper-pagination.swiper-pagination-bullets {
        top: $button-height;
      }
    }

    .editMain-function-drop-swiper {
      .swiper-button-prev {
        left: $edit-main-button-offset-left;
      }

      .swiper-button-next {
        right: $edit-main-button-offset-right;
      }
    }
  }

  /* iPad */
  @media all and (device-width: 768px) and (device-height: 1024px) and (orientation:portrait) {
  }

  @media all and (device-width: 768px) and (device-height: 1024px) and (orientation:landscape) {
  }
</style>
