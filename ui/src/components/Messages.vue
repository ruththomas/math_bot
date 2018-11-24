<template>
  <div class="message-container">
    <transition-group
      name="message-list"
      v-bind:css="false"
      v-on:before-enter="beforeEnter"
      v-on:enter="enter"
      v-on:leave="leave"
    >
      <div v-for="(message, ind) in messageList"
           class="message"
           :class="message.type"
           :id="'message-' + ind"
           :key="message.id"
           @click="removeMessage(ind)"
      >
        {{ message.msg }}
      </div>
    </transition-group>
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
    },
    levelControl () {
      return this.$store.getters.getLevelControl
    },
    runCompiled () {
      return this.levelControl.runCompiled
    }
  },
  methods: {
    removeMessage (message) {
      if (this.runCompiled.robot.state === 'failure') {
        this.runCompiled.reset()
      }
      this.$store.dispatch('removeMessage', message)
    },
    beforeEnter (el) {
      el.style.opacity = 0
      el.style.height = 0
    },
    enter (el, done) {
      const delay = el.dataset.index * 150
      setTimeout(function () {
        $(el).animate(
          { opacity: 1, height: '1.6em' },
          done
        )
      }, delay)
    },
    leave (el, done) {
      const delay = el.dataset.index * 150
      setTimeout(function () {
        $(el).animate(
          { opacity: 0, height: 0 },
          done
        )
      }, delay)
    }
  }
}
</script>

<style scoped lang="scss">
  .message-list-move {
    transition: transform 1s;
  }

  .message-container {
    position: fixed;
    top: 0;
    left: 50%;
    transform: translateX(-50%);
    width: 30vmin;
    z-index: 1001;
    display: flex;
    flex-direction: column;
    color: #ffffff;
  }

  .message {
    display: flex;
    width: 99%;
    height: min-content!important;
    margin-top: 8px;
    border-radius: 2px;
    justify-content: center;
    align-items: center;
    font-size: 2vmin;
    line-height: 2.5vmin;
    cursor: pointer;
  }

  .message:first-child {
    margin-top: 0;
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
    opacity: 0.8!important;
  }

  .message:nth-child(n+3) {
    opacity: 0.6!important;
  }

  .message:nth-child(n+4) {
    opacity: 0.4!important;
  }

  .message:nth-child(n+5) {
    opacity: 0.2!important;
  }

  .message:nth-child(n+6) {
    display: none;
  }
</style>
