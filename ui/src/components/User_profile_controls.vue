<template>
  <div class="user-profile-controls">
    <div class="user-profile-controls-image-container">
      <img :src="permanentImages.instructionsRobot">
    </div>
    <div class="user-profile-controls-text-container">
      <div class="user-profile-user">
        <div class="user-profile-user-name text">{{currentUser.name}}</div>
      </div>
      <mathbot-header :font-size="headerFont" :line-height="headerFont + 2"></mathbot-header>
      <div class="user-profile-controls-text-footer">
        <div class="text nav-links about" @click="gotoMarketing">About</div>
        <div class="text nav-links sign-in" v-if="!auth.authenticated" @click="auth.login()">Sign In</div>
        <div class="text nav-links sign-out" v-else @click="auth.logout()">Sign Out</div>
      </div>
    </div>
  </div>
</template>

<script>
import MathbotHeader from './Mathbot_header'
export default {
  name: 'user_profile_controls',
  computed: {
    currentUser () {
      return this.auth.userProfile
    },
    auth () {
      return this.$store.getters.getAuth
    },
    windowWidth () {
      return window.innerWidth
    },
    headerFont () {
      if (this.windowWidth < 1024) {
        return 30
      }
    }
  },
  methods: {
    gotoMarketing () {
      this.$router.push({path: '/about'})
    }
  },
  components: {
    MathbotHeader
  },
  props: ['permanentImages']
}
</script>

<style scoped lang="scss">
  .user-profile-controls {
    flex-grow: 1;
    display: flex;
    position: fixed;
    bottom: 0;
    left: 0;
  }

  .user-profile-controls-image-container {
    display: flex;
    align-items: flex-end;
  }

  .user-profile-controls-image-container > img {
    width: 240px;
    height: 212px;
  }

  .user-profile-controls-text-container {
    display: flex;
    flex-direction: column;
    justify-content: flex-end;
  }

  .user-profile-controls-text-footer {
  }

  .user-profile-controls-text-footer {
    display: flex;
    justify-content: space-between;
    margin-bottom: 10px;
  }

  .user-profile-user {
    display: flex;
    align-items: center;
    flex-direction: column-reverse;
  }

  .user-profile-user-image {
    height: 30px;
    width: 30px;
    border-radius: 50%;
  }

  .text {
    opacity: 0.3;
    color: #FFFFFF;
    font-size: 32px;
    font-weight: 600;
    line-height: 30px;
    cursor: pointer;
  }

  .nav-links:hover {
    opacity: 1;
  }

  .text-filler {
    height: 28px;
  }

  @media only screen and (max-width : 992px) {
    .user-profile-controls-image-container > img {
      height: 120px;
      width: 144px;
    }

    .mathbot-header {
      //border: 1px solid maroon;
      font-size: 36px;
      line-height: 38px;
    }

    .text {
      //border: 1px solid yellowgreen;
      font-size: 24px;
      line-height: 26px;
    }
  }

  @media only screen and (max-height: 411px) {

  }

  /* Small Devices */
  @media only screen and (max-width : 736px) {
  }

  @media only screen and (max-width: 568px) {
    .user-profile-controls-image-container > img {
      height: 100px;
      width: 120px;
    }

    .mathbot-header {
      //border: 1px solid maroon;
      font-size: 30px;
      line-height: 34px;
    }

    .text {
      //border: 1px solid yellowgreen;
      font-size: 20px;
      line-height: 24px;
    }
  }

  /* Extra Small Devices, Phones */
  @media only screen and (max-width : 480px) {

  }

  /* Custom, iPhone Retina */
  @media only screen and (max-width : 360px) {

  }

</style>
