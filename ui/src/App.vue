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
  $pointer-size: 20px;

  body {
    font-family: "Proba Pro Regular", serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #000000;
    height: 100vh;
    width: 100vw;
    background-color: #000000!important;
    position: relative;
    display: flex;
    z-index: 200;
    background-image: url("https://res.cloudinary.com/deqjemwcu/image/upload/v1522347137/misc/profileSpace.png");
    background-size: cover;
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
    width: calc(#{$pointer-size} * 2);
    height: $pointer-size;
    bottom: 100%;
    left: 50%;
    transform: translateX(-50%);
    z-index: 100;
  }

  .pointer-hidden {
    opacity: 0;
  }

  .pointer-size {
    position: absolute;
    border-style: solid;
    border-width: $pointer-size $pointer-size 0 $pointer-size;
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

  .arrow {
    border: solid black;
    border-width: 0 3px 3px 0;
    display: inline-block;
    padding: 5px;
  }

  .right {
    transform: rotate(-45deg);
    -webkit-transform: rotate(-45deg);
  }

  .left {
    transform: rotate(135deg);
    -webkit-transform: rotate(135deg);
  }

  .up {
    transform: rotate(-135deg);
    -webkit-transform: rotate(-135deg);
  }

  .down {
    transform: rotate(145deg);
    -webkit-transform: rotate(45deg);
  }

  @keyframes bounce {
    from {
      transform: translateY(0px);
    }
    to {
      transform: translateY(-5px);
    }
  }
  @-webkit-keyframes bounce {
    from {
      transform: translateY(0px);
    }
    to {
      transform: translateY(-5px);
    }
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

  @media only screen
  and (min-device-width : 768px)
  and (max-device-width : 1024px)
  and (orientation : landscape) {
    $pointer-size: 20px;
    $dialog-button-size: 30px;

    .pointer {
      position: absolute;
      width: calc(#{$pointer-size} * 2);
      height: $pointer-size;
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
      border-width: $pointer-size $pointer-size 0 $pointer-size;
    }

    .dialog-button {
      height: $dialog-button-size;
      width: $dialog-button-size;
    }
  }

  @media only screen
  and (min-device-width : 768px)
  and (max-device-width : 1024px)
  and (orientation : portrait) {
    $pointer-size: 20px;
    $dialog-button-size: 30px;

    .pointer {
      position: absolute;
      width: calc(#{$pointer-size} * 2);
      height: $pointer-size;
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
      border-width: $pointer-size $pointer-size 0 $pointer-size;
    }

    .dialog-button {
      height: $dialog-button-size;
      width: $dialog-button-size;
    }
  }

  @media only screen and (max-width: 823px) and (orientation: landscape){
    $pointer-size: 10px;
    $dialog-button-size: 20px;

    .pointer {
      position: absolute;
      width: calc(#{$pointer-size} * 2);
      height: $pointer-size;
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
      border-width: $pointer-size $pointer-size 0 $pointer-size;
    }

    .dialog-button {
      height: $dialog-button-size;
      width: $dialog-button-size;
    }
  }
  @media only screen and (max-width: 736px) {
    $pointer-size: 10px;
    $dialog-button-size: 20px;

    .pointer {
      position: absolute;
      width: calc(#{$pointer-size} * 2);
      height: $pointer-size;
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
      border-width: $pointer-size $pointer-size 0 $pointer-size;
    }

    .dialog-button {
      height: $dialog-button-size;
      width: $dialog-button-size;
    }
  }
  @media only screen and (max-width: 667px) {
    $pointer-size: 10px;
    $dialog-button-size: 20px;

    .pointer {
      position: absolute;
      width: calc(#{$pointer-size} * 2);
      height: $pointer-size;
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
      border-width: $pointer-size $pointer-size 0 $pointer-size;
    }

    .dialog-button {
      height: $dialog-button-size;
      width: $dialog-button-size;
    }
  }
  @media only screen and (max-width: 568px) {
    $pointer-size: 10px;
    $dialog-button-size: 20px;

    .pointer {
      position: absolute;
      width: calc(#{$pointer-size} * 2);
      height: $pointer-size;
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
      border-width: $pointer-size $pointer-size 0 $pointer-size;
    }

    .dialog-button {
      height: $dialog-button-size;
      width: $dialog-button-size;
    }
  }
  @media only screen and (max-width: 414px) {
    $pointer-size: 10px;
    $dialog-button-size: 20px;

    .pointer {
      position: absolute;
      width: calc(#{$pointer-size} * 2);
      height: $pointer-size;
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
      border-width: $pointer-size $pointer-size 0 $pointer-size;
    }

    .dialog-button {
      height: $dialog-button-size;
      width: $dialog-button-size;
    }
  }
  @media only screen and (max-width: 375px) {
    $pointer-size: 10px;
    $dialog-button-size: 20px;

    .pointer {
      position: absolute;
      width: calc(#{$pointer-size} * 2);
      height: $pointer-size;
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
      border-width: $pointer-size $pointer-size 0 $pointer-size;
    }

    .dialog-button {
      height: $dialog-button-size;
      width: $dialog-button-size;
    }
  }
  /* iphone 5 portrait */
  @media only screen and (max-width : 320px) {
    $pointer-size: 10px;
    $dialog-button-size: 20px;

    .pointer {
      position: absolute;
      width: calc(#{$pointer-size} * 2);
      height: $pointer-size;
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
      border-width: $pointer-size $pointer-size 0 $pointer-size;
    }

    .dialog-button {
      height: $dialog-button-size;
      width: $dialog-button-size;
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
