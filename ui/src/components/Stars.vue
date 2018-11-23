<template>
  <div class="stars">
    <div class="star-spread">
      <star class="star-one" :active="isActive(1)"></star>
      <star class="star-two" :active="isActive(2)"></star>
      <star class="star-three" :active="isActive(3)"></star>
    </div>
    <div v-if="timer.stars < 3" class="progress">
      <div class="progress-bar progress-bar-striped progress-bar-animated"
           role="progressbar"
           aria-valuenow="0"
           aria-valuemin="0"
           aria-valuemax="100"
           :style="{width: `${timeSpent}%`, 'background-color': 'rgba(0, 0, 0, 0.5)'}"
      ></div>
    </div>
  </div>
</template>

<script>
import Star from './Star'
export default {
  name: 'Timer',
  computed: {
    timer () {
      return this.videoTimers[this.continentId] || {stars: 3}
    },
    timeSpent () {
      return 100 - this.remainingTime * 100 / 3600
    },
    remainingTime () {
      return this.timer.remainingTime
    },
    videoTimers () {
      return this.$store.getters.getVideoTimers
    }
  },
  methods: {
    isActive (starNumber) {
      if (starNumber === 1) {
        return this.timer.stars >= 3
      } else if (starNumber === 2) {
        return this.timer.stars >= 2
      } else {
        return this.timer.stars >= 1
      }
    }
  },
  components: {
    Star
  },
  props: ['continentId']
}
</script>

<style scoped lang="scss">
  $star-cluster-star-one-size: 50px;
  $star-cluster-star-two-three-size: 30px;
  $star-spread-star-size: 40px;
  $stars-shadow: inset 0 0 100px #778899;
  $star-congrats-star-size: 70px;
  $star-timer-right: 0;
  $star-timer-top: 100%;
  $star-timer-left: 0;
  $star-timer-font-size: 1.5vmin;
  $star-timer-margin-right: 5px;
  $click-color: #B8E986;
  .stars {
    position: relative;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    border-radius: 5px;
    cursor: pointer;
    width: min-content;
    margin: 0 auto;
    .progress {
      position: absolute;
      width: 100%;
      height: 100%;
      background-color: transparent;
      .progress-bar {
        min-width: 3px;
      }
    }
  }
  .help-button {
    .star-timer {
      color: #000000;
    }
  }
  .star-spread {
    display: flex;
    z-index: 101;
    .star-one, .star-two, .star-three {
      align-self: center;
    }
    .question-mark {
      position: absolute;
      height: 40%;
      top: 50%;
      transform: translateY(-62%);
    }
  }
</style>
