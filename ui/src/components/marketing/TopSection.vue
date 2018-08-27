<template>
 <div id="Marketing-top-section" data-aos="zoom-in">
   <div class="marketing-container">
     <div class="header-wrapper">
      <mathbot-header :font-size="headerFont" :line-height="headerFont + 2"></mathbot-header>
     </div>
     <div class="header-content">
       <div class="slogan-box">
         <div class="slogan">
           Learn math by programming a robot
         </div>
         <div class="minor-slogan">
           From counting to calculus.
         </div>
         <div class="play-now" @click="gotosite()">PLAY NOW</div>
       </div>
     </div>
     <img class="logo" :src="imgResources['mathBotLogo']" alt="Image of a robot.">
     <img class="planet planet-one" :src="imgResources['planetOne']">
     <img class="planet planet-two" :src="imgResources['planetTwo']">
     <img class="planet planet-three" :src="imgResources['planetThree']">
     <img class="planet planet-four" :src="imgResources['planetFour']">
     <img class="planet planet-five" :src="imgResources['planetFive']">
     <img class="space-ship" :src="imgResources['spaceShip']">
     <img class="arched-line" :src="imgResources['archedLine']">
   </div>
 </div>
</template>

<script>
import MathbotHeader from '../Mathbot_header'
export default {
  name: 'TopSection',
  data () {
    return {
      imgResources: {
        'mathBotLogo': 'https://res.cloudinary.com/doohickey/image/upload/v1521989155/Logo_yplcxn.svg',
        planetOne: 'https://res.cloudinary.com/doohickey/image/upload/v1528476678/planet-1_otshkt.png',
        planetTwo: 'https://res.cloudinary.com/doohickey/image/upload/v1528476494/planet-2_za8zja.png',
        planetThree: 'https://res.cloudinary.com/doohickey/image/upload/v1528476209/planet-3_ehflik.png',
        planetFour: 'https://res.cloudinary.com/doohickey/image/upload/v1528476786/planet-4_mgoqir.png',
        planetFive: 'https://res.cloudinary.com/doohickey/image/upload/v1528476931/planet-5_va9bvg.png',
        'spaceShip': 'https://res.cloudinary.com/doohickey/image/upload/v1534029884/black-space-ship_kihoqt.svg',
        'archedLine': 'https://res.cloudinary.com/doohickey/image/upload/v1521989179/Path_2_ggddw3.svg'
      },
      headerFont: 0
    }
  },
  components: {
    MathbotHeader
  },
  props: ['gotosite']
}
</script>

<style scoped lang="scss">
  $gradient-size: 50%;
  $outer-shadow-blur: 100px;
  $outer-shadow-size: 5px;
  $planet-1-color: rgba(202, 122, 255, 0.7);
  $planet-2-color: rgba(242, 92, 92, 0.7);
  $planet-3-color: rgba(74, 144, 226, 0.7);
  $planet-4-color: rgba(255, 152, 177, 0.7);
  $planet-5-color: rgba(80, 227, 194, 0.7);
  $planet-6-color: rgba(184, 233, 134, 0.7);
  $inactive-color: rgba(104, 104, 104, 0.7);
  $planet-gradient: rgba(0, 0, 0, 1);
  $planet-1-size: 262px;
  $planet-2-size: 164px;
  $planet-3-size: 187px;
  $planet-4-size: 149px;
  $planet-5-size: 100px;
  $space-ship-size: 128px;
  $robot-size: 250px;
  $mathbot-header-size: 60px;

  #Marketing-top-section {
    grid-area: topsection;
    width: 100%;
  }

  .marketing-container {
    display: grid;
    width: 100%;
    max-width: 1366px;
    margin: 0 auto;
    padding: 100px 0 100px 0;
    grid-template-columns: [c-one]12.5% [c-two]12.5% [c-three]12.5% [c-four]12.5% [c-five]12.5% [c-six]12.5% [c-seven]12.5% [c-eight]12.5%;
    grid-template-rows: [r-header]50px [r-one]auto [r-two]auto [r-three]auto [r-four]auto [r-five]auto [r-six]auto [r-seven]auto [r-eight]auto [r-nine]auto;
  }

  .header-wrapper {
    grid-column: c-two / span 2;
    grid-row: r-header;
    text-align: left;

    .mathbot-header {
      font-size: $mathbot-header-size;
    }
  }

  .header-content {
    text-align: left;
    width: auto;
    grid-column: c-two / span 4;
    grid-row: r-three / span 3;
    z-index: 4;
    display: flex;
    align-content: flex-end;
  }

  .slogan-box {
    display: flex;
    flex-direction: column;
    align-content: flex-end;
  }

  .slogan {
    color: #FFFFFF;
    font-size: 64px;
    line-height: 72px;
    margin-bottom: 15px;
  }

  .minor-slogan {
    opacity: 0.8;
    color: #FFFFFF;
    font-size: 32px;
    line-height: 38px;
    margin-bottom: 59px;
  }

  .logo {
    display: inline-block;
    grid-column: c-six / span 2;
    grid-row: r-three / span 2;
    z-index: 4;
    width: $robot-size;
  }

  .planet {
    z-index: 3;
    border-radius: 50%;
    opacity: 0;
  }

  .planet-one {
    grid-column: c-five / span 2;
    grid-row: r-one / span 3;
    align-self: end;
    justify-self: end;
    background: radial-gradient(circle at $gradient-size $gradient-size, $planet-1-color, $planet-gradient);
    height: $planet-1-size;
  }

  .planet-two {
    grid-column: c-seven / span 2;
    grid-row: r-four / span 2;
    justify-self: center;
    background: radial-gradient(circle at $gradient-size $gradient-size, $planet-2-color, $planet-gradient);
    height: $planet-2-size;
  }

  .planet-three {
    grid-column: c-six / span 2;
    grid-row: r-six / span 2;
    justify-self: center;
    align-self: start;
    background: radial-gradient(circle at $gradient-size $gradient-size, $planet-3-color, $planet-gradient);
    height: $planet-3-size;
  }

  .planet-four {
    grid-column: c-three / span 2;
    grid-row: r-five / span 2;
    justify-self: center;
    align-self: start;
    margin-top: 30px;
    background: radial-gradient(circle at $gradient-size $gradient-size, $planet-4-color, $planet-gradient);
    height: $planet-4-size;
  }

  .planet-five {
    grid-column: c-seven / span 2;
    grid-row: r-header / span 2;
    justify-self: center;
    background: radial-gradient(circle at $gradient-size $gradient-size, $planet-5-color, $planet-gradient);
    height: $planet-5-size;
  }

  .space-ship {
    grid-column: c-five;
    grid-row: r-six;
    justify-self: end;
    width: $space-ship-size;
  }

  .arched-line {
    grid-column: c-one / span 8;
    grid-row: r-four / span 3;
    z-index: 2;
    align-self: end;
  }

  /* ipad pro Portrait */
  @media only screen
  and (min-device-width: 1024px) and (max-device-width: 1024px)
  and (min-device-height: 1366px) and (max-device-height: 1366px)
  and (min-width: 1024px) and (max-width: 1024px)
  and (orientation: portrait)
  and (-webkit-min-device-pixel-ratio: 1.5) {
    $planet-1-size: 262px;
    $planet-2-size: 164px;
    $planet-3-size: 187px;
    $planet-4-size: 149px;
    $planet-5-size: 100px;
    $space-ship-size: 128px;
    $robot-size: 250px;
    $slogan-font-size: 64px;
    $slogan-line-height: 68px;
    $minor-slogan-font-size: 32px;
    $minor-slogan-line-height: 36px;
    $mathbot-header-size: 60px;

    .marketing-container {
      padding: 50px 0 50px 0;
      grid-template-columns: [c-one]12.5% [c-two]12.5% [c-three]12.5% [c-four]12.5% [c-five]12.5% [c-six]12.5% [c-seven]12.5% [c-eight]12.5%;
      grid-template-rows: [r-header]auto [r-one]auto [r-two]auto [r-three]auto [r-four]auto [r-five]auto [r-six]auto [r-seven]auto [r-eight]auto [r-nine]auto;
    }

    .header-wrapper {
      text-align: left;
      margin-bottom: 10px;

      .mathbot-header {
        font-size: $mathbot-header-size;
      }
    }

    .header-content {
      text-align: left;
      width: auto;
      z-index: 4;
      display: flex;
      align-content: flex-end;
      grid-column: c-two / span 4;
      grid-row: r-two / span 3;
    }

    .slogan {
      font-size: $slogan-font-size;
      line-height: $slogan-line-height;
      margin-bottom: 15px;
    }

    .minor-slogan {
      font-size: $minor-slogan-font-size;
      line-height: $minor-slogan-line-height;
      margin-bottom: 59px;
    }

    .logo {
      display: inline-block;
      grid-column: c-six / span 2;
      grid-row: r-three / span 2;
      z-index: 4;
      width: $robot-size;
    }

    .planet-one {
      grid-column: c-five / span 2;
      grid-row: r-one / span 3;
      align-self: end;
      justify-self: end;
      height: $planet-1-size;
    }

    .planet-two {
      grid-column: c-seven / span 2;
      grid-row: r-four / span 2;
      justify-self: center;
      display: none;
      height: $planet-2-size;
    }

    .planet-three {
      grid-column: c-six / span 2;
      grid-row: r-six / span 2;
      justify-self: center;
      align-self: start;
      height: $planet-3-size;
    }

    .planet-four {
      grid-column: c-two / span 2;
      grid-row: r-five / span 2;
      justify-self: center;
      align-self: start;
      margin-top: 30px;
      height: $planet-4-size;
    }

    .planet-five {
      grid-column: c-seven / span 2;
      grid-row: r-header / span 2;
      justify-self: center;
      height: $planet-5-size;
    }

    .space-ship {
      grid-column: c-four;
      grid-row: r-six;
      justify-self: end;
      width: $space-ship-size;
    }

    .arched-line {
      grid-column: c-one / span 8;
      grid-row: r-four / span 3;
      z-index: 2;
      align-self: end;
      max-width: 100vw;
    }
  }

  // ipad landscape
  @media only screen
  and (min-device-height : 768px)
  and (max-device-width: 1024px)
  and (orientation : landscape) {
    $planet-1-size: 141px;
    $planet-2-size: 72px;
    $planet-3-size: 104px;
    $planet-4-size: 85px;
    $planet-5-size: 80px;
    $space-ship-size: 74px;
    $robot-size: 135px;
    $slogan-font-size: 64px;
    $slogan-line-height: 68px;
    $minor-slogan-font-size: 32px;
    $minor-slogan-line-height: 36px;
    $mathbot-header-size: 60px;

    .marketing-container {
      padding: 50px 0 50px 0;
      grid-template-columns: [c-one]12.5% [c-two]12.5% [c-three]12.5% [c-four]12.5% [c-five]12.5% [c-six]12.5% [c-seven]12.5% [c-eight]12.5%;
      grid-template-rows: [r-header]auto [r-one]auto [r-two]auto [r-three]auto [r-four]auto [r-five]auto [r-six]auto [r-seven]auto [r-eight]auto [r-nine]auto;
    }

    .header-wrapper {
      text-align: left;
      margin-bottom: 10px;

      .mathbot-header {
        font-size: $mathbot-header-size;
      }
    }

    .header-content {
      text-align: left;
      width: auto;
      z-index: 4;
      display: flex;
      align-content: flex-end;
      grid-column: c-two / span 6;
      grid-row: r-two / span 3;
    }

    .slogan {
      font-size: $slogan-font-size;
      line-height: $slogan-line-height;
      margin-bottom: 15px;
    }

    .minor-slogan {
      font-size: $minor-slogan-font-size;
      line-height: $minor-slogan-line-height;
      margin-bottom: 59px;
    }

    .logo {
      display: inline-block;
      grid-column: c-six / span 2;
      grid-row: r-three / span 2;
      z-index: 4;
      width: $robot-size;
    }

    .planet-one {
      grid-column: c-five / span 2;
      grid-row: r-one / span 3;
      align-self: end;
      justify-self: end;
      height: $planet-1-size;
    }

    .planet-two {
      grid-column: c-seven / span 2;
      grid-row: r-four / span 2;
      justify-self: center;
      display: none;
      height: $planet-2-size;
    }

    .planet-three {
      grid-column: c-six / span 2;
      grid-row: r-six / span 2;
      justify-self: center;
      align-self: start;
      height: $planet-3-size;
    }

    .planet-four {
      grid-column: c-two / span 2;
      grid-row: r-five / span 2;
      justify-self: center;
      align-self: start;
      margin-top: 30px;
      height: $planet-4-size;
    }

    .planet-five {
      grid-column: c-seven / span 2;
      grid-row: r-header / span 2;
      justify-self: center;
      height: $planet-5-size;
    }

    .space-ship {
      grid-column: c-four;
      grid-row: r-six;
      justify-self: end;
      width: $space-ship-size;
    }

    .arched-line {
      grid-column: c-one / span 8;
      grid-row: r-four / span 3;
      z-index: 2;
      align-self: end;
      max-width: 100vw;
    }
  }

  // ipad portrait
  @media only screen
  and (min-device-width : 768px)
  and (max-device-height: 1024px)
  and (orientation : portrait)  {
    $planet-1-size: 141px;
    $planet-2-size: 72px;
    $planet-3-size: 104px;
    $planet-4-size: 85px;
    $planet-5-size: 80px;
    $space-ship-size: 74px;
    $robot-size: 185px;
    $slogan-font-size: 64px;
    $slogan-line-height: 68px;
    $minor-slogan-font-size: 32px;
    $minor-slogan-line-height: 36px;
    $mathbot-header-size: 60px;

    .marketing-container {
      padding: 50px 0 50px 0;
      grid-template-columns: [c-one]12.5% [c-two]12.5% [c-three]12.5% [c-four]12.5% [c-five]12.5% [c-six]12.5% [c-seven]12.5% [c-eight]12.5%;
      grid-template-rows: [r-header]auto [r-one]auto [r-two]auto [r-three]auto [r-four]auto [r-five]auto [r-six]auto [r-seven]auto [r-eight]auto [r-nine]auto;
    }

    .header-wrapper {
      text-align: left;
      margin-bottom: 10px;

      .mathbot-header {
        font-size: $mathbot-header-size;
      }
    }

    .header-content {
      text-align: left;
      width: auto;
      z-index: 4;
      display: flex;
      align-content: flex-end;
      grid-column: c-two / span 6;
      grid-row: r-two / span 3;
    }

    .slogan {
      font-size: $slogan-font-size;
      line-height: $slogan-line-height;
      margin-bottom: 15px;
    }

    .minor-slogan {
      font-size: $minor-slogan-font-size;
      line-height: $minor-slogan-line-height;
      margin-bottom: 59px;
    }

    .logo {
      display: inline-block;
      grid-column: c-six / span 2;
      grid-row: r-three / span 2;
      z-index: 4;
      width: $robot-size;
    }

    .planet-one {
      grid-column: c-five / span 2;
      grid-row: r-one / span 3;
      align-self: end;
      justify-self: end;
      height: $planet-1-size;
    }

    .planet-two {
      grid-column: c-seven / span 2;
      grid-row: r-four / span 2;
      justify-self: center;
      display: none;
      height: $planet-2-size;
    }

    .planet-three {
      grid-column: c-six / span 2;
      grid-row: r-six / span 2;
      justify-self: center;
      align-self: start;
      height: $planet-3-size;
    }

    .planet-four {
      grid-column: c-two / span 2;
      grid-row: r-five / span 2;
      justify-self: center;
      align-self: start;
      margin-top: 30px;
      height: $planet-4-size;
    }

    .planet-five {
      grid-column: c-seven / span 2;
      grid-row: r-header / span 2;
      justify-self: center;
      height: $planet-5-size;
    }

    .space-ship {
      grid-column: c-four;
      grid-row: r-six;
      justify-self: end;
      width: $space-ship-size;
    }

    .arched-line {
      grid-column: c-one / span 8;
      grid-row: r-four / span 3;
      z-index: 2;
      align-self: end;
      max-width: 100vw;
    }
  }

  @media only screen and (max-width: 823px) and (orientation: landscape) {
    $planet-1-size: 141px;
    $planet-2-size: 72px;
    $planet-3-size: 104px;
    $planet-4-size: 85px;
    $planet-5-size: 80px;
    $space-ship-size: 74px;
    $robot-size: 135px;
    $mathbot-header-size: 50px;

    .marketing-container {
      padding: 50px 0 50px 0;
      grid-template-columns: [c-one]12.5% [c-two]12.5% [c-three]12.5% [c-four]12.5% [c-five]12.5% [c-six]12.5% [c-seven]12.5% [c-eight]12.5%;
      grid-template-rows: [r-header]auto [r-one]auto [r-two]auto [r-three]auto [r-four]auto [r-five]auto [r-six]auto [r-seven]auto [r-eight]auto [r-nine]auto;
    }

    .header-wrapper {
      text-align: left;
      margin-bottom: 10px;

      .mathbot-header {
        font-size: $mathbot-header-size;
      }
    }

    .header-content {
      text-align: left;
      width: auto;
      z-index: 4;
      display: flex;
      align-content: flex-end;
      grid-column: c-two / span 6;
      grid-row: r-two / span 3;
    }

    .slogan {
      font-size: 18px;
      line-height: 24px;
      margin-bottom: 15px;
    }

    .minor-slogan {
      font-size: 16px;
      line-height: 20px;
      margin-bottom: 59px;
    }

    .logo {
      display: inline-block;
      grid-column: c-six / span 2;
      grid-row: r-three / span 2;
      z-index: 4;
      width: $robot-size;
    }

    .planet-one {
      grid-column: c-five / span 2;
      grid-row: r-one / span 3;
      align-self: end;
      justify-self: end;
      height: $planet-1-size;
    }

    .planet-two {
      grid-column: c-seven / span 2;
      grid-row: r-four / span 2;
      justify-self: center;
      display: none;
      height: $planet-2-size;
    }

    .planet-three {
      grid-column: c-six / span 2;
      grid-row: r-six / span 2;
      justify-self: center;
      align-self: start;
      height: $planet-3-size;
    }

    .planet-four {
      grid-column: c-two / span 2;
      grid-row: r-five / span 2;
      justify-self: center;
      align-self: start;
      margin-top: 30px;
      height: $planet-4-size;
    }

    .planet-five {
      grid-column: c-seven / span 2;
      grid-row: r-header / span 2;
      justify-self: center;
      height: $planet-5-size;
    }

    .space-ship {
      grid-column: c-four;
      grid-row: r-six;
      justify-self: end;
      width: $space-ship-size;
    }

    .arched-line {
      grid-column: c-one / span 8;
      grid-row: r-four / span 3;
      z-index: 2;
      align-self: end;
      max-width: 100vw;
    }
  }

  @media only screen and (max-width: 736px) and (orientation: landscape) {
    $planet-1-size: 121px;
    $planet-2-size: 52px;
    $planet-3-size: 84px;
    $planet-4-size: 65px;
    $planet-5-size: 40px;
    $space-ship-size: 54px;
    $robot-size: 115px;
    $mathbot-header-size: 40px;

    .marketing-container {
      padding: 50px 0 50px 0;
      grid-template-columns: [c-one]12.5% [c-two]12.5% [c-three]12.5% [c-four]12.5% [c-five]12.5% [c-six]12.5% [c-seven]12.5% [c-eight]12.5%;
      grid-template-rows: [r-header]auto [r-one]auto [r-two]auto [r-three]auto [r-four]auto [r-five]auto [r-six]auto [r-seven]auto [r-eight]auto [r-nine]auto;
    }

    .header-wrapper {
      text-align: left;
      margin-bottom: 10px;

      .mathbot-header {
        font-size: $mathbot-header-size;
      }
    }

    .header-content {
      text-align: left;
      width: auto;
      z-index: 4;
      display: flex;
      align-content: flex-end;
      grid-column: c-two / span 6;
      grid-row: r-two / span 3;
    }

    .slogan {
      font-size: 18px;
      line-height: 24px;
      margin-bottom: 15px;
    }

    .minor-slogan {
      font-size: 16px;
      line-height: 20px;
      margin-bottom: 59px;
    }

    .logo {
      display: inline-block;
      grid-column: c-six / span 2;
      grid-row: r-three / span 2;
      z-index: 4;
      width: $robot-size;
    }

    .planet-one {
      grid-column: c-five / span 2;
      grid-row: r-one / span 3;
      align-self: end;
      justify-self: end;
      height: $planet-1-size;
    }

    .planet-two {
      grid-column: c-seven / span 2;
      grid-row: r-four / span 2;
      justify-self: center;
      display: none;
      height: $planet-2-size;
    }

    .planet-three {
      grid-column: c-six / span 2;
      grid-row: r-six / span 2;
      justify-self: center;
      align-self: start;
      height: $planet-3-size;
    }

    .planet-four {
      grid-column: c-two / span 2;
      grid-row: r-five / span 2;
      justify-self: center;
      align-self: start;
      margin-top: 30px;
      height: $planet-4-size;
    }

    .planet-five {
      grid-column: c-seven / span 2;
      grid-row: r-header / span 2;
      justify-self: center;
      height: $planet-5-size;
    }

    .space-ship {
      grid-column: c-four;
      grid-row: r-six;
      justify-self: end;
      width: $space-ship-size;
    }

    .arched-line {
      grid-column: c-one / span 8;
      grid-row: r-four / span 3;
      z-index: 2;
      align-self: end;
      max-width: 100vw;
    }
  }

  @media only screen and (max-width: 667px) and (orientation: landscape) {
    $planet-1-size: 121px;
    $planet-2-size: 62px;
    $planet-3-size: 84px;
    $planet-4-size: 65px;
    $planet-5-size: 40px;
    $space-ship-size: 44px;
    $robot-size: 115px;
    $mathbot-header-size: 30px;

    .marketing-container {
      padding: 50px 0 50px 0;
      grid-template-columns: [c-one]12.5% [c-two]12.5% [c-three]12.5% [c-four]12.5% [c-five]12.5% [c-six]12.5% [c-seven]12.5% [c-eight]12.5%;
      grid-template-rows: [r-header]auto [r-one]auto [r-two]auto [r-three]auto [r-four]auto [r-five]auto [r-six]auto [r-seven]auto [r-eight]auto [r-nine]auto;
    }

    .header-wrapper {
      text-align: left;
      margin-bottom: 10px;

      .mathbot-header {
        font-size: $mathbot-header-size;
      }
    }

    .header-content {
      text-align: left;
      width: auto;
      z-index: 4;
      display: flex;
      align-content: flex-end;
      grid-column: c-two / span 6;
      grid-row: r-two / span 3;
    }

    .slogan {
      font-size: 18px;
      line-height: 24px;
      margin-bottom: 15px;
    }

    .minor-slogan {
      font-size: 16px;
      line-height: 20px;
      margin-bottom: 59px;
    }

    .logo {
      display: inline-block;
      grid-column: c-six / span 2;
      grid-row: r-three / span 2;
      z-index: 4;
      width: $robot-size;
    }

    .planet-one {
      grid-column: c-five / span 2;
      grid-row: r-one / span 3;
      align-self: end;
      justify-self: end;
      height: $planet-1-size;
    }

    .planet-two {
      grid-column: c-seven / span 2;
      grid-row: r-four / span 2;
      justify-self: center;
      display: none;
      height: $planet-2-size;
    }

    .planet-three {
      grid-column: c-six / span 2;
      grid-row: r-six / span 2;
      justify-self: center;
      align-self: start;
      height: $planet-3-size;
    }

    .planet-four {
      grid-column: c-two / span 2;
      grid-row: r-five / span 2;
      justify-self: center;
      align-self: start;
      margin-top: 30px;
      height: $planet-4-size;
    }

    .planet-five {
      grid-column: c-seven / span 2;
      grid-row: r-header / span 2;
      justify-self: center;
      height: $planet-5-size;
    }

    .space-ship {
      grid-column: c-four;
      grid-row: r-six;
      justify-self: end;
      width: $space-ship-size;
    }

    .arched-line {
      grid-column: c-one / span 8;
      grid-row: r-four / span 3;
      z-index: 2;
      align-self: end;
      max-width: 100vw;
    }
  }

  @media only screen and (max-width: 568px) and (orientation: landscape) {
    $planet-1-size: 121px;
    $planet-2-size: 62px;
    $planet-3-size: 84px;
    $planet-4-size: 65px;
    $planet-5-size: 40px;
    $space-ship-size: 44px;
    $robot-size: 115px;
    $mathbot-header-size: 30px;

    .marketing-container {
      padding: 50px 0 50px 0;
      grid-template-columns: [c-one]12.5% [c-two]12.5% [c-three]12.5% [c-four]12.5% [c-five]12.5% [c-six]12.5% [c-seven]12.5% [c-eight]12.5%;
      grid-template-rows: [r-header]auto [r-one]auto [r-two]auto [r-three]auto [r-four]auto [r-five]auto [r-six]auto [r-seven]auto [r-eight]auto [r-nine]auto;
    }

    .header-wrapper {
      text-align: left;
      margin-bottom: 10px;

      .mathbot-header {
        font-size: $mathbot-header-size;
        margin-bottom: 20px;
      }
    }

    .header-content {
      text-align: left;
      width: auto;
      z-index: 4;
      display: flex;
      align-content: flex-end;
      grid-column: c-two / span 6;
      grid-row: r-two / span 3;
    }

    .slogan {
      font-size: 18px;
      line-height: 24px;
      margin-bottom: 15px;
    }

    .minor-slogan {
      font-size: 16px;
      line-height: 20px;
      margin-bottom: 59px;
    }

    .logo {
      display: inline-block;
      grid-column: c-six / span 2;
      grid-row: r-three / span 2;
      z-index: 4;
      width: $robot-size;
    }

    .planet-one {
      grid-column: c-five / span 2;
      grid-row: r-one / span 3;
      align-self: end;
      justify-self: end;
      height: $planet-1-size;
    }

    .planet-two {
      grid-column: c-seven / span 2;
      grid-row: r-four / span 2;
      justify-self: center;
      display: none;
      height: $planet-2-size;
    }

    .planet-three {
      grid-column: c-six / span 2;
      grid-row: r-six / span 2;
      justify-self: center;
      align-self: start;
      height: $planet-3-size;
    }

    .planet-four {
      grid-column: c-two / span 2;
      grid-row: r-five / span 2;
      justify-self: center;
      align-self: start;
      margin-top: 30px;
      height: $planet-4-size;
    }

    .planet-five {
      grid-column: c-seven / span 2;
      grid-row: r-header / span 2;
      justify-self: center;
      height: $planet-5-size;
    }

    .space-ship {
      grid-column: c-four;
      grid-row: r-six;
      justify-self: end;
      width: $space-ship-size;
    }

    .arched-line {
      grid-column: c-one / span 8;
      grid-row: r-four / span 3;
      z-index: 2;
      align-self: end;
      max-width: 100vw;
    }
  }

  @media only screen and (max-width: 414px) {
    $planet-1-size: 101px;
    $planet-2-size: 32px;
    $planet-3-size: 64px;
    $planet-4-size: 45px;
    $planet-5-size: 20px;
    $space-ship-size: 34px;
    $robot-size: 95px;
    $mathbot-header-size: 30px;

    .marketing-container {
      padding: 50px 0 50px 0;
      grid-template-columns: [c-one]12.5% [c-two]12.5% [c-three]12.5% [c-four]12.5% [c-five]12.5% [c-six]12.5% [c-seven]12.5% [c-eight]12.5%;
      grid-template-rows: [r-header]auto [r-one]auto [r-two]auto [r-three]auto [r-four]auto [r-five]auto [r-six]auto [r-seven]auto [r-eight]auto [r-nine]auto;
    }

    .header-wrapper {
      text-align: left;
      margin-bottom: 10px;

      .mathbot-header {
        font-size: $mathbot-header-size;
        margin-bottom: 20px;
      }
    }

    .header-content {
      text-align: left;
      width: auto;
      z-index: 4;
      display: flex;
      align-content: flex-end;
      grid-column: c-two / span 6;
      grid-row: r-two / span 3;
    }

    .slogan {
      font-size: 18px;
      line-height: 24px;
      margin-bottom: 15px;
    }

    .minor-slogan {
      font-size: 16px;
      line-height: 20px;
      margin-bottom: 59px;
    }

    .logo {
      display: inline-block;
      grid-column: c-six / span 2;
      grid-row: r-three / span 2;
      z-index: 4;
      width: $robot-size;
    }

    .planet-one {
      grid-column: c-five / span 2;
      grid-row: r-one / span 3;
      align-self: end;
      justify-self: end;
      height: $planet-1-size;
    }

    .planet-two {
      grid-column: c-seven / span 2;
      grid-row: r-four / span 2;
      justify-self: center;
      display: none;
      height: $planet-2-size;
    }

    .planet-three {
      grid-column: c-six / span 2;
      grid-row: r-six / span 2;
      justify-self: center;
      align-self: start;
      height: $planet-3-size;
    }

    .planet-four {
      grid-column: c-two / span 2;
      grid-row: r-five / span 2;
      justify-self: center;
      align-self: start;
      margin-top: 30px;
      height: $planet-4-size;
    }

    .planet-five {
      grid-column: c-seven / span 2;
      grid-row: r-header / span 2;
      justify-self: center;
      height: $planet-5-size;
    }

    .space-ship {
      grid-column: c-four;
      grid-row: r-six;
      justify-self: end;
      width: $space-ship-size;
    }

    .arched-line {
      grid-column: c-one / span 8;
      grid-row: r-four / span 3;
      z-index: 2;
      align-self: end;
      max-width: 100vw;
    }
  }

  @media only screen and (max-width: 375px) {
    $planet-1-size: 101px;
    $planet-2-size: 32px;
    $planet-3-size: 64px;
    $planet-4-size: 45px;
    $planet-5-size: 20px;
    $space-ship-size: 34px;
    $robot-size: 95px;
    $mathbot-header-size: 30px;

    .marketing-container {
      padding: 50px 0 50px 0;
      grid-template-columns: [c-one]12.5% [c-two]12.5% [c-three]12.5% [c-four]12.5% [c-five]12.5% [c-six]12.5% [c-seven]12.5% [c-eight]12.5%;
      grid-template-rows: [r-header]auto [r-one]auto [r-two]auto [r-three]auto [r-four]auto [r-five]auto [r-six]auto [r-seven]auto [r-eight]auto [r-nine]auto;
    }

    .header-wrapper {
      text-align: left;
      margin-bottom: 10px;

      .mathbot-header {
        font-size: $mathbot-header-size;
        margin-bottom: 20px;
      }
    }

    .header-content {
      text-align: left;
      width: auto;
      z-index: 4;
      display: flex;
      align-content: flex-end;
      grid-column: c-two / span 6;
      grid-row: r-two / span 3;
    }

    .slogan {
      font-size: 18px;
      line-height: 24px;
      margin-bottom: 15px;
    }

    .minor-slogan {
      font-size: 16px;
      line-height: 20px;
      margin-bottom: 59px;
    }

    .logo {
      display: inline-block;
      grid-column: c-six / span 2;
      grid-row: r-three / span 2;
      z-index: 4;
      width: $robot-size;
    }

    .planet-one {
      grid-column: c-five / span 2;
      grid-row: r-one / span 3;
      align-self: end;
      justify-self: end;
      height: $planet-1-size;
    }

    .planet-two {
      grid-column: c-seven / span 2;
      grid-row: r-four / span 2;
      justify-self: center;
      display: none;
      height: $planet-2-size;
    }

    .planet-three {
      grid-column: c-six / span 2;
      grid-row: r-six / span 2;
      justify-self: center;
      align-self: start;
      height: $planet-3-size;
    }

    .planet-four {
      grid-column: c-two / span 2;
      grid-row: r-five / span 2;
      justify-self: center;
      align-self: start;
      margin-top: 30px;
      height: $planet-4-size;
    }

    .planet-five {
      grid-column: c-seven / span 2;
      grid-row: r-header / span 2;
      justify-self: center;
      height: $planet-5-size;
    }

    .space-ship {
      grid-column: c-four;
      grid-row: r-six;
      justify-self: end;
      width: $space-ship-size;
    }

    .arched-line {
      grid-column: c-one / span 8;
      grid-row: r-four / span 3;
      z-index: 2;
      align-self: end;
      max-width: 100vw;
    }
  }

  @media only screen and (max-width: 320px){
    $planet-1-size: 101px;
    $planet-2-size: 32px;
    $planet-3-size: 64px;
    $planet-4-size: 45px;
    $planet-5-size: 20px;
    $space-ship-size: 34px;
    $robot-size: 95px;
    $mathbot-header-size: 30px;

    .marketing-container {
      padding: 50px 0 50px 0;
      grid-template-columns: [c-one]12.5% [c-two]12.5% [c-three]12.5% [c-four]12.5% [c-five]12.5% [c-six]12.5% [c-seven]12.5% [c-eight]12.5%;
      grid-template-rows: [r-header]auto [r-one]auto [r-two]auto [r-three]auto [r-four]auto [r-five]auto [r-six]auto [r-seven]auto [r-eight]auto [r-nine]auto;
    }

    .header-wrapper {
      text-align: left;
      margin-bottom: 10px;

      .mathbot-header {
        font-size: $mathbot-header-size;
        margin-bottom: 20px;
      }
    }

    .header-content {
      text-align: left;
      width: auto;
      z-index: 4;
      display: flex;
      align-content: flex-end;
      grid-column: c-two / span 6;
      grid-row: r-two / span 3;
    }

    .slogan {
      font-size: 18px;
      line-height: 24px;
      margin-bottom: 15px;
    }

    .minor-slogan {
      font-size: 16px;
      line-height: 20px;
      margin-bottom: 59px;
    }

    .logo {
      display: inline-block;
      grid-column: c-six / span 2;
      grid-row: r-three / span 2;
      z-index: 4;
      width: $robot-size;
    }

    .planet-one {
      grid-column: c-five / span 2;
      grid-row: r-one / span 3;
      align-self: end;
      justify-self: end;
      height: $planet-1-size;
    }

    .planet-two {
      grid-column: c-seven / span 2;
      grid-row: r-four / span 2;
      justify-self: center;
      display: none;
      height: $planet-2-size;
    }

    .planet-three {
      grid-column: c-six / span 2;
      grid-row: r-six / span 2;
      justify-self: center;
      align-self: start;
      height: $planet-3-size;
    }

    .planet-four {
      grid-column: c-two / span 2;
      grid-row: r-five / span 2;
      justify-self: center;
      align-self: start;
      margin-top: 30px;
      height: $planet-4-size;
    }

    .planet-five {
      grid-column: c-seven / span 2;
      grid-row: r-header / span 2;
      justify-self: center;
      height: $planet-5-size;
    }

    .space-ship {
      grid-column: c-four;
      grid-row: r-six;
      justify-self: end;
      width: $space-ship-size;
    }

    .arched-line {
      grid-column: c-one / span 8;
      grid-row: r-four / span 3;
      z-index: 2;
      align-self: end;
      max-width: 100vw;
    }
  }
</style>
