<template>
  <div
    @click="goToProfile()"
    class="return-to-profile rtp-mobile"
    data-toggle="tooltip" title="Return to profile"
  >
    <img :src="handlePicture(userProfile.picture)" />
  </div>
</template>

<script>
export default {
  name: 'UserGravatar',
  computed: {
    userProfile () {
      return this.auth.userProfile
    },
    auth () {
      return this.$store.getters.getAuth
    },
    permanentImages () {
      return this.$store.getters.getPermanentImages
    }
  },
  methods: {
    goToProfile () {
      this.$store.dispatch('toggleHintShowing', {showing: false, videoURL: ''})
      this.$store.dispatch('deleteMessages')
      this.$router.push({path: '/profile'})
    },
    handlePicture (picture) {
      if (!picture || picture.match(/gravatar/)) {
        return this.permanentImages.gravatar
      } else {
        return picture
      }
    }
  }
}
</script>

<style scoped lang="scss">
  @media only screen and (max-width: 1050px) {
    .rtp-mobile {
      position: relative !important;
      left: 90%;
    }
  }
  .return-to-profile {
    position: fixed;
    right: 0;
    top: 0;
    cursor: pointer;
    height: 8vmin;
    width: 8vmin;
    display: flex;
    align-items: center;
    justify-content: center;
    border-radius: 50%;

    img {
      border-radius: 50%;
      height: 80%;
      width: 80%;
      box-shadow: 0 0 100px 2vmin rgba(0,0,0,1);
    }
  }

</style>
