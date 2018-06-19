<template>
  <div class="stars">
    <i class="star star-one" :class="[starGroup, starClass(stepStats.stars >= 1)]"></i>
    <i class="star star-two" :class="[starGroup, starClass(stepStats.stars >= 2)]"></i>
    <i class="star star-three" :class="[starGroup, starClass(stepStats.stars >= 3)]"></i>
  </div>
</template>

<script>
export default {
  name: 'Stars',
  mounted () {
  },
  methods: {
    starClass (starActive) {
      return starActive ? 'active-star' : 'inactive-star'
    }
  },
  props: ['level', 'stepStats', 'starGroup']
}
</script>

<style scoped lang="sass">
  $star-active-color: #F8E71C
  $star-inactive-color: rgba(104, 104, 104, 1)
  $star-size: 25px
  $star-border: 1px solid #FFFF00
  $stars-shadow: inset 0 0 100px #707070
  .stars
    display: inline-flex
    box-shadow: $stars-shadow
    border-radius: 5px
    padding: 5px

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
    box-shadow: none!important

    &:first-child, &:last-child
      margin-top: 1.2em

    &:first-child
      margin-right: 1em

    &:last-child
      margin-left: 1em

  .star-spread
    margin-left: 1em
    margin-right: 1em
    margin-bottom: 1.2em
</style>
