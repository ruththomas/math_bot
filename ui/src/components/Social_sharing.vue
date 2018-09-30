<template>
<div class="social-sharing">
  <social-sharing url="https://mathbot.com"
                  :title="message"
                  hashtags="mathbot, math_bot, math, programming, jwweatherman, weathermaniam"
                  twitter-user="JWWeatherman_"
                  inline-template>
    <div class="social-links">
      <network class="share-button share-twitter" network="twitter">
        <i class="fa fa-twitter"></i>
      </network>
      <network class="share-button share-facebook" network="facebook">
        <i class="fa fa-facebook"></i>
      </network>
      <network class="share-button share-linkedin" network="linkedin">
        <i class="fa fa-linkedin"></i>
      </network>
      <network class="share-button share-reddit" network="reddit">
        <i class="fa fa-reddit"></i>
      </network>
    </div>
  </social-sharing>
</div>
</template>

<script>
export default {
  name: 'Social_sharing',
  props: ['message']
}
</script>

<style lang="scss">
$twitter-color: #1da1f2;
$facebook-color: #3b5998;
$linkedin-color: #00a0dc;
$reddit-color: #ff4500;
$button-size: 3rem;
.social-sharing {
  .social-links {
    .share-button {
      display: inline-flex;
      justify-content: center;
      align-items: center;
      height: $button-size;
      width: $button-size;
      border-radius: 50%;
      cursor: pointer;
      margin: 1rem;

      i {
        font-size: calc(#{$button-size} - 1rem);
      }
    }
    .share-twitter {
      background-color: $twitter-color;
    }
    .share-facebook {
      background-color: $facebook-color;
    }
    .share-linkedin {
      background-color: $linkedin-color;
    }
    .share-reddit {
      background-color: $reddit-color;
    }
  }
}
</style>
