<template>
  <div class="col-8 space">
    <img
      v-for="planet in planets" :key="planet.planetName"
      class="planet"
      v-if="planet.planetState !== 'no-show'"
      :class="[planet.planetName, planet.planetState]"
      :src="planet.image"
      @click="selectLevel(planet.level)" />
  </div>
</template>

<script>
import api from '../services/api'
export default {
  name: 'planets',
  computed: {
    level () {
      return this.$store.getters.getLevel
    },
    levels () {
      return this.$store.getters.getLevels
    },
    steps () {
      return this.$store.getters.getSteps
    },
    planets () {
      return this.levels.map((level, index) => {
        const planetNumber = index + 1
        const planet = {}
        planet.level = level
        planet.planetName = `planet-${planetNumber}`
        planet.planetState = this.level === level.name ? 'selected' : this.isLevelActive(level) ? 'active' : 'inactive'
        planet.image = this.permanentImages.planets[`planet${planetNumber}`]
        return planet
      })
    },
    tokenId () {
      return this.$store.getters.getToken.token_id
    }
  },
  data () {
    return {
      selectedLevel: ''
    }
  },
  methods: {
    isLevelActive (l) {
      const level = l.level
      return Object.keys(level).reduce((bool, step) => {
        if (level[step].active === true) {
          bool = true
        }
        return bool
      }, false)
    },
    selectLevel (level) {
      if (this.isLevelActive(level)) {
        api.switchLevel({tokenId: this.tokenId, level: level.name}, (res) => {
          // console.log(res.body)
          this.$store.dispatch('updateStats', res.body)
          this.selectedLevel = level
        })
      }
    }
  },
  props: ['permanentImages']
}
</script>

<style scoped lang="scss">
  $gradient-size: 50%;
  $outer-shadow-blur: 100px;
  $outer-shadow-size: 5px;
  $planet-1-color: rgba(202, 122, 255, 1);
  $planet-2-color: rgba(242, 92, 92, 1);
  $planet-3-color: rgba(74, 144, 226, 1);
  $planet-4-color: rgba(255, 152, 177, 1);
  $planet-5-color: rgba(80, 227, 194, 1);
  $planet-6-color: rgba(184, 233, 134, 1);
  $inactive-color: rgba(104, 104, 104, 1);
  $planet-1-size: 23vmin;
  $planet-2-size: 16vmin;
  $planet-3-size: 20vmin;
  $planet-4-size: 14vmin;
  $planet-5-size: 12vmin;
  $planet-gradient: rgba(0, 0, 0, 1);

  .space {
    height: 100%;
  }

  .planet {
    position: absolute;
    color: white;
    cursor: pointer;
    border-radius: 50%;
  }

  .planet-1 {
    background: radial-gradient(circle at $gradient-size $gradient-size, $planet-1-color, $planet-gradient);
    height: $planet-1-size;
    width: $planet-1-size;
    top: 12%;
    left: 7%;
    /*box-shadow: inset 0 0 120px #CA7AFF*/
  }

  .planet-2 {
    background: radial-gradient(circle at $gradient-size $gradient-size, $planet-2-color, $planet-gradient);
    height: $planet-2-size;
    width: $planet-2-size;
    top: 2%;
    left: 50%;
    /*box-shadow: inset 0 0 120px #F25C5C;*/
  }

  .planet-3 {
    background: radial-gradient(circle at $gradient-size $gradient-size, $planet-3-color, $planet-gradient);
    height: $planet-3-size;
    width: $planet-3-size;
    top: 33%;
    left: 60%;
    /*box-shadow: inset 0 0 120px #4A90E2;*/
  }

  .planet-4 {
    background: radial-gradient(circle at $gradient-size $gradient-size, $planet-4-color, $planet-gradient);
    height: $planet-4-size;
    width: $planet-4-size;
    top: 65%;
    left: 50%;
    /*box-shadow: inset 0 0 120px #FF98B1;*/
  }

  .planet-5 {
    background: radial-gradient(circle at $gradient-size $gradient-size, $planet-5-color, $planet-gradient);
    height: $planet-5-size;
    width: $planet-5-size;
    top: 80%;
    left: 25%;
    /*box-shadow: inset 0 0 120px #50E3C2;*/
  }

  .planet-6 {
    background: radial-gradient(circle at $gradient-size $gradient-size, $planet-5-color, $planet-gradient);
    height: 140px;
    width: 140px;
    top: 60%;
    left: 20%;
    /*box-shadow: inset 0 0 120px #50E3C2;*/
  }

  .planet-6 {
    background: radial-gradient(circle at $gradient-size $gradient-size, $planet-6-color, $planet-gradient);
    /*box-shadow: inset 0 0 120px #B8E986;*/
  }

  .planet-1.selected {
    box-shadow: 0 0 $outer-shadow-blur $outer-shadow-size $planet-1-color;
    /*box-shadow: inset 0 0 120px #CA7AFF, 0 0 120px #CA7AFF;*/
  }

  .planet-2.selected {
    box-shadow: 0 0 $outer-shadow-blur $outer-shadow-size $planet-2-color;
    /*box-shadow: inset 0 0 120px #F25C5C, 0 0 120px #F25C5C;*/
  }

  .planet-3.selected {
    box-shadow: 0 0 $outer-shadow-blur $outer-shadow-size $planet-3-color;
    /*box-shadow: inset 0 0 120px #4A90E2, 0 0 120px #4A90E2;*/
  }

  .planet-4.selected {
    box-shadow: 0 0 $outer-shadow-blur $outer-shadow-size $planet-4-color;
    /*box-shadow: inset 0 0 120px #FF98B1, 0 0 120px #FF98B1;*/
  }

  .planet-5.selected {
    box-shadow: 0 0 $outer-shadow-blur $outer-shadow-size $planet-5-color;
    /*box-shadow: inset 0 0 120px #50E3C2, 0 0 120px #50E3C2;*/
  }

  .planet-6.selected {
    box-shadow: 0 0 $outer-shadow-blur $outer-shadow-size $planet-6-color;
    /*box-shadow: inset 0 0 120px #B8E986, 0 0 120px #B8E986;*/
  }

  .planet.inactive {
    opacity: 0.8;
    background: radial-gradient(circle at $gradient-size $gradient-size, $inactive-color, $planet-gradient);
    /*box-shadow: inset 0 0 120px #686868;*/
  }
</style>
