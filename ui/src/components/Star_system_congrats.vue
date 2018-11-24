<template>
<b-modal
  id="star-system-congrats-modal"
  ref="star-system-congrats-modal"
  v-if="nextStarSystem"
>

</b-modal>
</template>

<script>
export default {
  name: 'Star_system_congrats',
  computed: {
    levelControl () {
      return this.$store.getters.getLevelControl
    },
    nextStarSystem () {
      return this.levelControl.getNextStarSystem()
    }
  }
}
</script>

<style scoped lang="scss">

</style>
