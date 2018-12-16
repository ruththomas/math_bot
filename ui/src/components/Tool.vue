<template>
<div
  class="tool"
>
  <div v-if="denomination === '1'">
    <img :src="permanentImages.tools[denomination]" />
    <span>1</span>
  </div>
  <div v-else>
    <svg
      xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink"
      :fill="levelControl.getColorHex(denomination)"
      version="1.2"
      baseProfile="tiny"
      viewBox="0 0 100 100"
      xml:space="preserve">
      <path d="M25.732,10.732L10.735,25.73C10.276,26.188,10,26.853,10,27.5v60c0,1.381,1.119,2.5,2.5,2.5h60   c0.648,0,1.313-0.276,1.77-0.735l14.997-14.997C89.736,73.799,90,73.163,90,72.5v-60c0-1.354-1.146-2.5-2.5-2.5h-60   C26.837,10,26.201,10.264,25.732,10.732z M70,85H15V30h55V85z M85,71.465l-10,10v-52.93l10-10V71.465z M81.465,15l-10,10h-52.93   l10-10H81.465z"/>
      <text x="17" y="58" :style="{'font-size': computeFontSize(denomination)}">{{computeName(denomination)}}</text>
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
  methods: {
    computeFontSize (text) {
      if (text.length === 1) return '3rem'
      else if (text.length <= 3) return '2rem'
      else if (text.length === 4) return '1.5rem'
      else if (text.length === 5) return '1rem'
      else return '1rem'
    },
    computeName (text) {
      if (text === 'white') return 'always'
      else return text
    }
  },
  props: ['denomination']
}
</script>

<style scoped lang="scss">
.tool {
  div {
    height: 100%;
    width: 100%;
    position: relative;
    font-weight: bolder;
    span{
      position: absolute;
      top: 35%;
      left: 33%;
    }
    img {
      height: 80%;
      width: auto;
      position: relative;
      top: 50%;
      transform: perspective(1px) translateY(-50%);
    }
  }
}
</style>
