<template>
  <div id="Marketing-bottom-section">
    <div class="media bottom-info">
      <social-sharing :message="'Check out Mathbot.com!'"></social-sharing>
    </div>
  </div>
</template>

<script>
import SocialSharing from '../Social_sharing'
export default {
  name: 'BottomSection',
  data () {
    return {
      showBottomInfo: false
    }
  },
  props: ['goToSite'],
  components: {
    SocialSharing
  }
}
</script>

<style lang="scss">
  $social-btn-size: 3rem;
  $social-btn-font-size: 2rem;
  #Marketing-bottom-section {
    padding: 100px 0 100px 0;

    .bottom-info {
      grid-column: c-two;
      color: #FFFFFF;
      font-size: 18px;
      line-height: 23px;
      text-align: center;
      display: flex;
      justify-content: center;
      align-items: center;

      .social-sharing {
        .social-links {
          .share-button {
            height: $social-btn-size;
            width: $social-btn-size;
            i {
              font-size: $social-btn-font-size;
            }
          }
        }
      }
    }
  }
</style>
