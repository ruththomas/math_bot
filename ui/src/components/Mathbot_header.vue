<template>
  <div class="mathbot-header"
    :class="color ? color.split('#').pop() + '-mathbot-header' : ''"
  >
    MATH<span class="line">_</span>BOT
  </div>
</template>

<script>
export default {
  name: 'mathbot_header',
  mounted () {
    const $mathbotHeader = $('.mathbot-header')

    if (this.fontSize) {
      if (this.fontSize) {
        $mathbotHeader.css({'font-size': `${this.fontSize}px`})
      }
    }

    if (this.lineHeight) {
      $mathbotHeader.css({'line-height': `${this.lineHeight}px`})
    }

    if (this.color) {
      $(`.${this.color.split('#').pop()}-mathbot-header`).css({color: this.color})
    }
  },
  props: ['fontSize', 'lineHeight', 'color']
}
</script>

<style scoped lang="scss">
  $font-size: 38px;
  $line-height: 38px;
  $line-color: #B8E986;

  .mathbot-header {
    font-size: $font-size;
    line-height: $line-height;
    letter-spacing: 4px;
    color: #FFFFFF;
    justify-self: left;
    z-index: 3;

    .line {
      font-weight: bold;
      color: $line-color;
    }
  }
</style>
