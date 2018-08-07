<template>
  <div id="Marketing" class="marketing" data-aos="zoom-in">
    <top-section :gotosite="goToSite"></top-section>
    <middle-section :gotosite="goToSite"></middle-section>
    <bottom-section></bottom-section>
  </div>
</template>

<script>
import TopSection from './TopSection'
import MiddleSection from './MiddleSection'
import BottomSection from './BottomSection'

export default {
  name: 'Marketing',
  computed: {
    auth () {
      return this.$store.getters.getAuth
    }
  },
  components: {
    TopSection,
    MiddleSection,
    BottomSection
  },
  methods: {
    goToSite () {
      if (!this.auth.authenticated) {
        this.auth.login()
      }
      this.$router.push({path: '/profile'})
    }
  }
}
</script>

<style>
  @import url('https://fonts.googleapis.com/css?family=Roboto');
  @import url('https://fonts.googleapis.com/css?family=Material+Icons');

  body {
    margin: 0 auto;
  }

  * {
    font-family: 'probapro-regular', 'Roboto', Helvetica, Arial, sans-serif;
  }

  #app {
  }

  #Marketing {
    width: 100%;
    height: 100vh;
    margin: 0 auto;
    font-family: "Proba Pro Regular", serif;
    overflow: auto;
  }

  #Marketing::-webkit-scrollbar {
    display: none;
  }

  .play-now {
    height: 65px;
    width: 327px;
    color: #000000;
    font-size: 22px;
    display: flex;
    justify-content: center;
    align-items: center;
    text-align: center;
    border-radius: 1px;
    background-color: #B8E986;
    box-shadow: 0 2px 10px 0 rgba(0,0,0,0.5);
    cursor: pointer;
  }

  @media only screen and (max-width: 823px) {
    .play-now {
      height: 32px;
      width: 93px;
      font-size: 12px;
    }
  }

  @media only screen and (max-width: 736px) {
    .play-now {
      height: 32px;
      width: 93px;
      font-size: 12px;
    }
  }

  @media only screen and (max-width: 568px) {
    .play-now {
      height: 42px;
      width: 153px;
      font-size: 12px;
    }
  }

  @media only screen and (max-width: 414px) {
    .play-now {
      height: 32px;
      width: 93px;
      font-size: 12px;
    }
  }

  @media only screen and (max-width: 375px) {
    .play-now {
      height: 32px;
      width: 93px;
      font-size: 12px;
    }
  }

  @media only screen and (max-width: 320px){
    .play-now {
      height: 32px;
      width: 93px;
      font-size: 12px;
    }
  }
</style>
