<template>
<div class="container-fluid auth" v-if="auth.session !== null" data-aos="zoom-in">
  <div class="row">
    <div class="card" style="width: 18rem;">
      <img class="dialog-button close-auth" @click="auth.logout" :src="permanentImages.buttons.xButtonTransparent">
      <img class="card-img-top" :src="permanentImages.instructionsRobot" alt="Card image cap">
      <div class="card-title">MATH_BOT</div>
      <div class="card-body">
        <div class="card-header">
          <ul class="nav nav-tabs">
            <li class="nav-item" @click="signupShowing = true">
              <a class="nav-link" :class="signupShowing ? 'active' : ''" href="#">Sign Up</a>
            </li>
            <li class="nav-item" @click="signupShowing = false">
              <a class="nav-link" :class="!signupShowing ? 'active' : ''" href="#">Log In</a>
            </li>
          </ul>
        </div>
        <social-auth></social-auth>
        <div class="or-divider">or</div>
        <transition
          mode="out-in"
          name="grid-transition-group"
          enter-active-class="animated fadeIn"
          leave-active-class="animated fadeOut"
        >
          <signup v-if="signupShowing"></signup>
          <login v-else></login>
        </transition>
      </div>
    </div>
  </div>
</div>
</template>

<script>
import Signup from './Signup'
import Login from './Login'
import SocialAuth from './Social_auth'

export default {
  name: 'Auth',
  mounted () {
    this.auth.login()
  },
  computed: {
    permanentImages () {
      return this.$store.getters.getPermanentImages
    },
    auth () {
      return this.$store.getters.getAuth
    }
  },
  data () {
    return {
      signupShowing: true
    }
  },
  methods: {
  },
  components: {
    Signup,
    Login,
    SocialAuth
  }
}
</script>

<style scoped lang="scss">
$title-background-color: rgba(241,241,241,0.8);
$btn-height: 3rem;
$font-size: 0.75rem;

.auth {
  height: 100%;
  overflow: auto;
  display: flex;
  justify-content: center;
  .row {
    display: flex;
    justify-content: center;
    align-items: center;
    height: min-content;
    min-height: 100%;
    width: min-content;
  }
}

.card {
  font-size: 1em;
  transition: all .5s linear;

  .card-img-top, .card-title {
    background-color: $title-background-color;
    padding-top: 0.5em;
  }
  .card-img-top {
    height: 5em;
  }
  .card-title {
    font-size: 1.5em;
    margin: 0;
  }
  .card-body {
    padding: 0;
    .card-header {
      background-color: $title-background-color;
      width: 100%;
      padding: 0;
      border-bottom: 1px solid rgba(92, 102, 111, 0.1);
      .nav {
        .nav-item {
          width: 50%;
          .nav-link {
            border: none;
            background-color: #ffffff;
            color: rgba(92,102,111,0.6);
            border-radius: 0;
            font-size: 0.75em;
          }
          .active {
            color: #000000;
            box-shadow: 0 1px 0 0 #5c666f;
            cursor: default;
          }
        }
      }
    }
    .or-divider {
      display: flex;
      justify-content: center;
      align-items: center;
      color: #5c666f;
      font-size: 1em;
    }
  }
  .close-auth {
    position: absolute;
    right: 0;
    top: 0;
    border-radius: 50%;
    height: 1.5em;
    width: 1.5em;
  }
}

@media screen and (max-width: 480px) {
  .card {
    width: 100%;
  }
}
</style>
