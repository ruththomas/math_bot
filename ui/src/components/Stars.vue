<template>
  <div v-if="stepStats.active" class="stars">
    <div class="star-spread">
      <star class="star-one" :star-group="starGroup" :success="success(1)"></star>
      <star class="star-two" :star-group="starGroup" :success="success(2)"></star>
      <star class="star-three" :star-group="starGroup" :success="success(3)"></star>
    </div>
    <div v-if="remainingTime > 0" class="row star-timer">
      <b-progress :style="{'background-color': 'rgba(255, 255, 255, 0.6)', height: '1vmin'}" variant="secondary" :value="remainingTimeInPercent" :max="max" animated></b-progress>
    </div>
  </div>
  <div v-else class="stars">
    <img :src="permanentImages.lock" style="height: 1.5em;" />
  </div>
</template>

<script>
import Star from './Star'
import _ from 'underscore'

export default {
  name: 'Timer',
  computed: {
    timer () {
      return this.videoTimers[`${this.level}/${this.step}`] || {stars: 3}
    },
    remainingTimeInPercent () {
      return this.remainingTime * 100 / 3600
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
  data () {
    return {
      counter: 45,
      max: 100
    }
  },
  methods: {
    convertTime (totalSeconds) {
      const minutes = Math.floor(totalSeconds / 60)
      const seconds = Math.floor(totalSeconds - minutes * 60)
      return _.chain([minutes, seconds])
        .map(t => {
          if (t < 10) return '0' + t
          else if (t === 0) return '00'
          else return t
        })
        .value()
        .join(':')
    },
    success (starNumber) {
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
  props: ['level', 'step', 'stepStats', 'starGroup']
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
}

.star-timer {
  position: absolute;
  top: 100%;
  z-index: 100;
  width: 100%;
  font-size: $star-timer-font-size;
  color: #ffffff;
  margin: 0;

  * {
    width: 100%;
  }
}

.help-button {
  .star-timer {
    color: #000000;
  }
}

.progress-bar {
}

.star-spread {
  display: flex;
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
