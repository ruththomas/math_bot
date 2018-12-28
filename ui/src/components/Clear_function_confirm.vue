<template>
<div
  class="confirm-delete"
>
  <div class="button-effect trash-btn trash-reject">
    <img class="dialog-button" :src="permanentImages.buttons.xButton" @click="closeMethod()" />
  </div>
  <div class="button-effect trash-btn trash-confirm">
    <img class="dialog-button" :src="permanentImages.openTrashCan" @click="trashMethod()" />
  </div>
</div>
</template>

<script>
export default {
  name: 'Clear_function_confirm',
  mounted () {
    this.animationMethod()
  },
  beforeDestroy () {
    this.deAnimationMethod()
  },
  computed: {
    permanentImages () {
      return this.$store.getters.getPermanentImages
    }
  },
  methods: {
  },
  props: ['animationMethod', 'deAnimationMethod', 'closeMethod', 'trashMethod']
}
</script>

<style scoped lang="scss">
$danger-color: #F25C5C;
.confirm-delete {
  display: flex;

  .trash-btn {
    margin: 0.5rem;
  }

  .trash-reject {}

  .trash-confirm {
    background-color: $danger-color;
    box-shadow: 0 2px 10px 0 $danger-color;
    animation: shake 0.8s;
    animation-iteration-count: infinite
  }
}
</style>
