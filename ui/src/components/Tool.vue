<template>
<div
  class="tool"
>
  <img v-if="denomination === '1'" :src="permanentImages.tools[denomination]" />
  <div v-else>
    <svg
      xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink"
      :fill="levelControl.getColorHex(denomination)"
      version="1.2"
      baseProfile="tiny"
      viewBox="0 0 100 100"
      xml:space="preserve">
      <path d="M25.732,10.732L10.735,25.73C10.276,26.188,10,26.853,10,27.5v60c0,1.381,1.119,2.5,2.5,2.5h60   c0.648,0,1.313-0.276,1.77-0.735l14.997-14.997C89.736,73.799,90,73.163,90,72.5v-60c0-1.354-1.146-2.5-2.5-2.5h-60   C26.837,10,26.201,10.264,25.732,10.732z M70,85H15V30h55V85z M85,71.465l-10,10v-52.93l10-10V71.465z M81.465,15l-10,10h-52.93   l10-10H81.465z"/>
      <text x="20" y="50" style="font-size: 1.5vmin;">{{denomination}}</text>
    </svg>
  </div>
</div>
</template>

<script>
export default {
  name: 'Tool',
  computed: {
    permanentImages () {
      return this.$store.getters.getPermanentImages
    },
    levelControl () {
      return this.$store.getters.getLevelControl
    }
  },
  props: ['denomination']
}
</script>

<style scoped lang="scss">
.tool {
  height: 90%;
  width: 90%;
  img {
    height: 80%;
    width: auto;
    position: relative;
    top: 50%;
    transform: perspective(1px) translateY(-50%);
  }
}
</style>
