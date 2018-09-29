<template>
  <div class="message-container">
    <div v-for="(message, ind) in messageList" class="message" :class="message.type" :id="'message-' + ind" :key="message.id" @click="removeMessage(ind)">
      {{ message.msg }}
    </div>
  </div>
</template>

<script>
export default {
  mounted () {
  },
  computed: {
    messageList () {
      return this.$store.getters.getMessageList
    },
    permanentImages () {
      return this.$store.getters.getPermanentImages
    }
  },
  methods: {
    removeMessage (message) {
      this.$store.dispatch('removeMessage', message)
    }
  }
}
</script>

<style scoped lang="scss">
  .message-container {
    position: fixed;
    top: 1vh;
    left: 50%;
    transform: translateX(-50%);
    width: 20vmin;
    z-index: 1001;
    display: flex;
    flex-direction: column;
    color: #ffffff;
  }

  .message {
    display: flex;
    width: 99%;
    margin-top: 8px;
    border-radius: 2px;
    justify-content: center;
    align-items: center;
    font-size: 1.5vmin;
    line-height: 2.5vmin;
    cursor: pointer;
  }

  .info {
    border: 1px solid rgb(0, 0, 255);
    background-color: rgba(0, 0, 255, 0.3);
  }

  .success {
    background-color: rgba(184,233,134,0.3);
    border: 1px solid rgb(184, 233, 134);
  }

  .warn {
    border: 1px solid rgb(255, 0, 0);
    background-color: rgba(255, 0, 0, 0.3);
  }

  .message:nth-child(n+2) {
    opacity: 0.5;
  }

  .message:nth-child(n+3) {
    opacity: 0.4;
  }

  .message:nth-child(n+4) {
    opacity: 0.3;
  }

  .message:nth-child(n+5) {
    opacity: 0.2;
  }
</style>
