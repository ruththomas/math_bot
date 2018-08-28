<template>
  <svg viewBox="0 0 92 87" height="3vmin" width="3vmin" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
    <defs>
      <polygon id="path-1" points="58.9933172 61.5 34.8941218 74.1696968 39.4966586 47.3348484 20 28.3303032 46.9437195 24.4151516 58.9933172 0 71.0429148 24.4151516 97.9866343 28.3303032 78.4899758 47.3348484 83.0925125 74.1696968"></polygon>
    </defs>
    <g id="Icons" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd" transform="translate(-399.000000, -262.000000)">
      <g id="stars-5" transform="translate(386.000000, 269.000000)">
        <g id="Start-Large">
          <!-- active star -->
          <path v-if="success" fill="#F8E71C" stroke-width="4" d="M80.4362505,70.5136657 L76.3410146,46.6366086 L93.6887121,29.7267828 L69.7147838,26.2431671 L58.9933172,4.51910292 L48.2718505,26.2431671 L24.2979223,29.7267828 L41.6456197,46.6366086 L37.5503839,70.5136657 L58.9933172,59.2404485 L80.4362505,70.5136657 Z"></path>
          <!-- inactive star -->
          <path stroke="#F8E71C" stroke-width="4" d="M80.4362505,70.5136657 L76.3410146,46.6366086 L93.6887121,29.7267828 L69.7147838,26.2431671 L58.9933172,4.51910292 L48.2718505,26.2431671 L24.2979223,29.7267828 L41.6456197,46.6366086 L37.5503839,70.5136657 L58.9933172,59.2404485 L80.4362505,70.5136657 Z"></path>
          <!--border -->
          <path stroke="#000000" stroke-width="3" d="M58.9933172,63.1946636 L32.9019253,76.9117201 L37.8849377,47.8585282 L16.7765583,27.2829435 L45.9476212,23.04414 L58.9933172,-3.38932719 L72.0390131,23.04414 L101.210076,27.2829435 L80.1016966,47.8585282 L85.0847091,76.9117201 L58.9933172,63.1946636 Z"></path>
        </g>
      </g>
    </g>
    <image v-if="starGroup === 'star-spread' && success" :xlink:href="permanentImages.questionMark" height="40%" width="40%" x="30%" y="30%" />
  </svg>
</template>

<script>
export default {
  name: 'Star',
  computed: {
    permanentImages () {
      return this.$store.getters.getPermanentImages
    }
  },
  props: ['starGroup', 'success']
}
</script>

<style scoped lang="scss">
  $star-size: 4vmin;

  .star {
    height: auto;
    width: $star-size;
    svg {
      height: 100%;
      width: auto;
    }
  }
  .heavy { font: bold 30px sans-serif; }
</style>
