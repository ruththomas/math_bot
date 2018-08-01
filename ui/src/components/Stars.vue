<template>
  <div class="stars" :class="starGroup">
    <star class="star-two" :active="stepStats.active" :success="success(2)"></star>
    <star class="star-one" :active="stepStats.active" :success="success(1)"></star>
    <star class="star-three" :active="stepStats.active" :success="success(3)"></star>
    <span v-if="timer.stars < 3" class="star-timer" :class="'star-timer-' + starGroup">{{ convertTime(remainingTime) }}</span>
  </div>
</template>

<script>
import Star from './Star'
import _ from 'underscore'

export default {
  name: 'Timer',
  mounted () {
    // console.log(this.timer)
  },
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
    },
    permanentImages () {
      return this.$store.getters.getPermanentImages
    }
  },
  methods: {
    convertTime (totalSeconds) {
      const minutes = Math.floor(totalSeconds / 60)
      const seconds = Math.floor(totalSeconds - minutes * 60)
      return _.chain([minutes, seconds])
        .map(t => {
          if (t === 0) return '00'
          else return t
        })
        .value()
        .join(':')
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
$star-cluster-star-two-three-size: 30px;
$star-spread-star-size: 40px;
$stars-shadow: inset 0 0 100px #778899;
$star-congrats-star-size: 70px;
$star-timer-right: 100%;
$star-timer-bottom: -10px;
$star-timer-font-size: 16px;
$star-timer-margin-right: 5px;
.stars {
  position: relative;
  display: flex;
  border-radius: 5px;
  cursor: pointer;
  height: 100%;
}

.star-timer {
  position: absolute;
  right: $star-timer-right;
  bottom: $star-timer-bottom;
  font-size: $star-timer-font-size;
  margin-right: $star-timer-margin-right;
}

.star-timer.star-timer-star-spread {
  color: #000000;
}

.star-cluster {
  .star-one {
    align-self: flex-start;
    margin-top: 10%;
  }

  .star-two, .star-three {
    align-self: flex-end;
    margin-bottom: 10%;
  }

  .star-timer {
    left: 40%;
    top: calc(100% - #{$star-timer-font-size + $star-timer-font-size / 2});
  }
}

.star-spread {
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

@media only screen and (max-width: 823px) {
  $star-timer-font-size: 8px;
  .star-timer {
    font-size: $star-timer-font-size;
  }

  .star-cluster {
    .star-timer {
      left: 35%;
      top: calc(100% - #{$star-timer-font-size + $star-timer-font-size / 2});
    }
  }
}

@media only screen and (max-width: 736px) {
  $star-timer-font-size: 8px;
  .star-timer {
    font-size: $star-timer-font-size;
  }

  .star-cluster {
    .star-timer {
      left: 35%;
      top: calc(100% - #{$star-timer-font-size + $star-timer-font-size / 2});
    }
  }
}

@media only screen and (max-width: 667px) {
  $star-timer-font-size: 8px;
  .star-timer {
    font-size: $star-timer-font-size;
  }

  .star-cluster {
    .star-timer {
      left: 35%;
      top: calc(100% - #{$star-timer-font-size + $star-timer-font-size / 2});
    }
  }
}

@media only screen and (max-width: 568px) {
  $star-timer-font-size: 8px;
  .star-timer {
    font-size: $star-timer-font-size;
  }

  .star-cluster {
    .star-timer {
      left: 35%;
      top: calc(100% - #{$star-timer-font-size + $star-timer-font-size / 2});
    }
  }
}

@media only screen and (max-width: 414px) {
  $star-timer-font-size: 8px;
  .star-timer {
    font-size: $star-timer-font-size;
  }

  .star-cluster {
    .star-timer {
      left: 35%;
      top: calc(100% - #{$star-timer-font-size + $star-timer-font-size / 2});
    }
  }
}

@media only screen and (max-width: 375px) {
  $star-timer-font-size: 8px;
  .star-timer {
    font-size: $star-timer-font-size;
  }

  .star-cluster {
    .star-timer {
      left: 35%;
      top: calc(100% - #{$star-timer-font-size + $star-timer-font-size / 2});
    }
  }
}

@media only screen and (max-width: 320px) {
}
</style>
