<template>
  <div class="stars" :class="starGroup">
    <star class="star-two" :active="stepStats.active" :success="success(2)"></star>
    <star class="star-one" :active="stepStats.active" :success="success(1)"></star>
    <star class="star-three" :active="stepStats.active" :success="success(3)"></star>
    <span v-if="timer.stars < 3" class="star-timer">{{ convertTime(remainingTime) }}</span>
  </div>
</template>

<script>
import Star from './Star'

export default {
  name: 'Timer',
  computed: {
    timer () {
      return this.videoTimers[`${this.level}/${this.step}`] || {stars: 3}
    },
    remainingTime () {
      if (this.timer) return this.timer.remainingTime
      else return 0
    },
    videoTimers () {
      return this.$store.getters.getVideoTimers
    }
  },
  methods: {
    convertTime (minutes) {
      let h = Math.floor(minutes / 60)
      let m = minutes % 60
      m = m < 10 ? '0' + m : m
      return h + ':' + m
    },
    success (starNumber) {
      if (starNumber === 1) {
        return this.timer.stars >= 1
      } else if (starNumber === 2) {
        return this.timer.stars >= 3
      } else {
        return this.timer.stars >= 2
      }
    }
  },
  components: {
    Star
  },
  props: ['level', 'step', 'stepStats', 'starGroup']
}
</script>

<style scoped lang="scss">
$star-cluster-star-one-size: 50px;
$star-cluster-star-two-three-size: 40px;
$star-spread-star-size: 60px;
$stars-shadow: inset 0 0 100px #D3D3D3;
$star-congrats-star-size: 70px;
.stars {
  position: relative;
  display: flex;
  height: 70px;
  width: 200px;
  justify-content: space-around;
  box-shadow: $stars-shadow;
  border-radius: 5px;
}

.star-timer {
  position: absolute;
}

.star-cluster {
  .star-one {
    height: $star-cluster-star-one-size;
    width: $star-cluster-star-one-size;
    align-self: flex-start;
  }

  .star-two, .star-three {
    height: $star-cluster-star-two-three-size;
    width: $star-cluster-star-two-three-size;
    align-self: flex-end;
  }

  .star-timer {
    bottom: 0;
  }
}

.star-spread {
  .star-one, .star-two, .star-three {
    height: $star-spread-star-size;
    width: $star-spread-star-size;
    align-self: center;
  }

  .star-timer {
    right: -30px;
    bottom: 0;
    font-size: 16px;
  }
}
</style>
