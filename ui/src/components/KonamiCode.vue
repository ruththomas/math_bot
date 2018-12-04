<template>
  <div class="key-detection">
  </div>
</template>

<script>

const secretCode = 'mathbotadmin'

export default {
  name: 'KeyDetection',
  data () {
    return {
      userInput: secretCode.split('')
    }
  },
  mounted () {
    this.$nextTick(() => {
      window.addEventListener('keyup', this.handleKeyup.bind(this))
    })
  },
  methods: {

    handleKeyup (e) {
      this.userInput = [...this.userInput.slice(1), e.key]

      if (this.userInput.join('') === secretCode) {
        this.goToAdmin()
      }
    },
    goToAdmin () {
      this.$router.push({path: '/admin'})
    }
  },
  beforeDestroy () {
    window.removeEventListener('keyup', this.handleKeyup.bind(this))
  }
}
</script>
