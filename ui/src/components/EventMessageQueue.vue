<template>

  <div>
    <div v-for="message in messages" :key="message.id">
      <div class="row alert alert-info">
        {{message.msg}}
      </div>
    </div>
  </div>

</template>

<script>
export default {
  name: 'EventMessageQueue',
  computed: {
    messages () {
      return this.adminControl.mq.messages
    },
    adminControl () {
      return this.$store.getters.getAdminControl
    }
  }
}
</script>

<style scoped>

</style>
