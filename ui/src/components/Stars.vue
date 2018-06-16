<template>
  <div class="stars">
    <i class="star star-one" :class="[starGroup, starOneActiveClass]"></i>
    <i class="star star-two" :class="[starGroup, starTwoActiveClass]"></i>
    <i class="star star-three" :class="[starGroup, starThreeActiveClass]"></i>
  </div>
</template>

<script>
export default {
  name: 'Stars',
  mounted () {
  },
  data () {
    return {
      starOneActiveClass: 'active-star',
      starTwoActiveClass: 'active-star',
      starThreeActiveClass: 'active-star'
    }
  },
  methods: {
  },
  props: ['level', 'step', 'starGroup']
}
</script>

<style scoped lang="sass">
  $star-active-color: #F8E71C
  $star-inactive-color: rgba(104, 104, 104, 1)
  $star-size: 25px
  .stars
    display: inline-flex

  .star
    position: relative

    display: inline-block
    width: 0
    height: 0

    border-right:  .3em solid transparent
    border-left:   .3em solid transparent

    /* Controls the size of the stars. */
    font-size: $star-size

    &:before, &:after
      content: ''

      display: block
      width: 0
      height: 0

      position: absolute
      top: .6em
      left: -1em

      border-right:  1em solid transparent
      border-left:   1em solid transparent

      transform: rotate(-35deg)

    &:after
      transform: rotate(35deg)

  .active-star
    border-bottom: .7em solid $star-active-color

    &:before, &:after
      border-bottom: .7em solid $star-active-color

  .inactive-star
    opacity: 0.5
    border-bottom: .7em solid $star-inactive-color

    &:before, &:after
      border-bottom: .7em solid $star-inactive-color

  .star-cluster
    margin-left: 0
    margin-right: 0
    margin-bottom: 1.2em

    &:first-child, &:last-child
      margin-top: 1.2em

    &:first-child
      margin-right: 1em

    &:last-child
      margin-left: 1em

  .star-spread
    margin-left: .7em
    margin-right: .7em
    margin-bottom: 1.2em
</style>
