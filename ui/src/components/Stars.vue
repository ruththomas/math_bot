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
$star-cluster-star-two-three-size: 30px;
$star-spread-star-size: 50px;
$stars-shadow: inset 0 0 100px #D3D3D3;
$star-congrats-star-size: 70px;
$star-margin: 5px 0 5px 0;
$star-timer-right: 100%;
$star-timer-bottom: -10px;
$star-timer-font-size: 16px;
$star-timer-margin-right: 5px;
.stars {
  position: relative;
  display: flex;
  justify-content: space-around;
  box-shadow: $stars-shadow;
  border-radius: 5px;
  cursor: pointer;
  * {
    margin: $star-margin;
  }
}

.star-timer {
  position: absolute;
  right: $star-timer-right;
  bottom: $star-timer-bottom;
  font-size: $star-timer-font-size;
  margin-right: $star-timer-margin-right;
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
}

.star-spread {
  .star-one, .star-two, .star-three {
    height: $star-spread-star-size;
    width: $star-spread-star-size;
    align-self: center;
  }

}
@media only screen and (max-width : 1280px) and (max-height: 900px) {
}

@media only screen and (max-width : 992px) {
  $star-cluster-star-one-size: 15px;
  $star-cluster-star-two-three-size: 13px;
  $star-spread-star-size: 18px;
  $stars-shadow: inset 0 0 100px #D3D3D3;
  $star-congrats-star-size: 70px;
  $star-timer-font-size: 10px;
  .star-spread {
    .star-one, .star-two, .star-three {
      height: $star-spread-star-size;
      width: $star-spread-star-size;
    }

    .star-timer {
      font-size: $star-timer-font-size;
    }
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
      font-size: $star-timer-font-size;
    }
  }
}

/* Small Devices */
@media only screen and (max-width : 736px) {
  $star-cluster-star-one-size: 15px;
  $star-cluster-star-two-three-size: 13px;
  $star-spread-star-size: 18px;
  $stars-shadow: inset 0 0 100px #D3D3D3;
  $star-congrats-star-size: 70px;
  $star-timer-font-size: 10px;
  .star-spread {
    .star-one, .star-two, .star-three {
      height: $star-spread-star-size;
      width: $star-spread-star-size;
    }

    .star-timer {
      right: $star-timer-right;
      bottom: $star-timer-bottom;
      font-size: $star-timer-font-size;
    }
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
      font-size: $star-timer-font-size;
      top: 80%;
      left: 50%;
    }
  }
}

/* Small Devices */
@media only screen and (max-width : 667px) {
}

@media only screen and (max-width: 568px) {
}

/* Extra Small Devices, Phones */
@media only screen and (max-width : 480px) {
}

/* iPad */
@media all and (device-width: 768px) and (device-height: 1024px) and (orientation:portrait) {
  $star-cluster-star-one-size: 30px;
  $star-cluster-star-two-three-size: 25px;
  $star-spread-star-size: 40px;
  $stars-shadow: inset 0 0 100px #D3D3D3;
  $star-congrats-star-size: 70px;
  $star-timer-right: 100%;
  $star-timer-bottom: 0;
  $star-timer-font-size: 16px;
  .star-spread {
    .star-one, .star-two, .star-three {
      height: $star-spread-star-size;
      width: $star-spread-star-size;
    }

    .star-timer {
      right: $star-timer-right;
      bottom: $star-timer-bottom;
      font-size: $star-timer-font-size;
    }
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
      right: $star-timer-right;
      bottom: $star-timer-bottom;
      font-size: $star-timer-font-size;
    }
  }
}

@media all and (device-width: 768px) and (device-height: 1024px) and (orientation:landscape) {
  $star-cluster-star-one-size: 15px;
  $star-cluster-star-two-three-size: 13px;
  $star-spread-star-size: 30px;
  $stars-shadow: inset 0 0 100px #D3D3D3;
  $star-congrats-star-size: 70px;
  $star-timer-font-size: 18px;
  .star-spread {
    .star-one, .star-two, .star-three {
      height: $star-spread-star-size;
      width: $star-spread-star-size;
    }

    .star-timer {
      right: $star-timer-right;
      bottom: $star-timer-bottom;
      font-size: $star-timer-font-size;
    }
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
      font-size: $star-timer-font-size;
      top: 80%;
      left: 50%;
    }
  }
}
</style>
