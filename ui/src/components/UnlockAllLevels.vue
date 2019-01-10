<template>
  <b-btn
    class="text-capitalize"
    @click="unlockLevels" :disabled="levelControl.unlockedAllLevels">
    {{levelControl.unlockedAllLevels ? 'Levels unlocked!' : 'unlock all levels'}}
  </b-btn>
</template>

<script>
export default {
  name: 'UnlockAllLevels',
  computed: {
    levelControl () {
      return this.$store.getters.getLevelControl
    }
  },
  methods: {
    // unlock all levels for user
    unlockLevels () {
      this.levelControl.getUnlock()
    }

  }
}
</script>

<style scoped>

</style>
