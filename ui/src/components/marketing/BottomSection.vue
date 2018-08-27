<template>
  <div id="Marketing-bottom-section">
    <div class="media bottom-info" v-if="showBottomInfo">
      <p>Right now Mathbot is still in development, but you can sign up and play with the robot for free. Once the site is open, prices will be determined for each class.</p>
      <a href="#">About</a><a href="#">Pricing</a>
    </div>
  </div>
</template>

<script>
export default {
  name: 'BottomSection',
  data () {
    return {
      showBottomInfo: false
    }
  }
}
</script>

<style scoped>
  #Marketing-bottom-section {
    grid-area: bottomsection;
    padding: 100px 0 100px 0;
    background-image: radial-gradient(circle farthest-corner at 50% 50%, transparent, rgba(0, 0, 0, 0.1) 24%, rgba(0, 0, 0, 0.4));
    display: grid;
    grid-template-columns: [c-one]auto [c-two]553px [c-three]auto;
    grid-template-rows: auto;
  }

  .bottom-info {
    grid-column: c-two;
    color: #FFFFFF;
    font-size: 18px;
    line-height: 23px;
    text-align: center;
  }

  .bottom-info > a {
    color: #FFFFFF;
    text-decoration: none;
    font-size: 26px;
    line-height: 32px;
    margin: 39px;
  }

  @media only screen and (max-width: 320px){

  }
</style>
