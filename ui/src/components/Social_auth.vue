<template>
<div class="social-auth">
  <button class="btn btn-block btn-dark" @click="auth('Github')">
    <span class="btn-icon">
      <i class="fa fa-github"></i>
    </span>
    <span class="btn-text">LOG IN WITH GITHUB</span>
  </button>
  <button class="btn btn-block btn-primary" @click="auth('Google')">
    <span class="btn-icon">
      <i class="fa fa-google"></i>
    </span>
    <span class="btn-text">LOG IN WITH GOOGLE</span>
  </button>
</div>
</template>

<script>
import api from '../services/api'
export default {
  name: 'Social_auth',
  methods: {
    auth (provider) {
      console.log('GITHUB')
      api[`authorize${provider}`]((body) => {
        console.log(body)
      })
    }
  }
}
</script>

<style scoped lang="scss">
$btn-height: 3rem;
$font-size: 0.75rem;
.social-auth {
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 2vmin 0;
  display: flex;
  .btn {
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-size: $font-size;
    font-weight: bold;
    margin: 0.75em 0;
    width: 90%;
    height: $btn-height;
    position: relative;
    .btn-icon {
      position: absolute;
      display: flex;
      justify-content: center;
      align-items: center;
      top: 0;
      left: 0;
      bottom: 0;
      height: 100%;
      width: $btn-height;
      background-color: rgba(0, 0, 0, 0.5);
      i {
        font-size: 1.75rem;
        float: left;
        margin: 0 0.375rem 0 0.375rem;
      }
    }
    .btn-text {
      flex: 1;
    }
  }
}
</style>
