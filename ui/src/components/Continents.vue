<template>
  <div class="col-4 steps">
    <div class="row">
      <h2 class="header"><span>{{`${Number(selectedPlanet) + 1}`}}.</span> {{ parseCamelCase(planetName) }}</h2>
    </div>
    <div class="row steps-container">
      <div class="btn-group-vertical">
        <button
          v-for="(continent, continentNumber) in continents"
          type="button"
          class="btn btn-dark btn-lg btn-block"
          :id="Number(selectedContinent) === continentNumber ? 'selected-continent' : ''"
          :class="[planetStats.name, Number(selectedContinent) === continentNumber ? 'selected' : '']"
          @click="continent.stats.active ? goToRobot(continent.id) : ''"
          :key="'continent/' + continent.id"
          :disabled="!continent.stats.active"
        >
          <div class="col-6">
            <div class="step-info-text">Level {{ continentNumber + 1 }}</div>
          </div>
          <div class="col-6">
            <stars v-if="continent.stats.active" :continent-id="continent.id"></stars>
            <div v-else class="stars">
              <img :src="permanentImages.lock" style="height: 1.5em;" />
            </div>
          </div>
        </button>
        <button
          v-if="nextPlanet && continents[continents.length - 1].stats.wins > 0"
          type="button"
          class="btn btn-dark btn-lg btn-block"
          @click="goToRobot(nextPlanet.id + '0')">
          <div class="col-6">
            <div class="step-info-text"><div>Next planet!</div> {{parseCamelCase(nextPlanet.stats.name)}}</div>
          </div>
          <div class="col-6">
            <img
              class="step-next-planet"
              :class="nextPlanet.stats.name"
              :src="permanentImages.planets[nextPlanet.stats.name]"
              alt="Planet"
            />
          </div>
        </button>
        <button
          v-else-if="nextStarSystem && continents[continents.length - 1].stats.wins > 0"
          type="button"
          class="btn btn-dark btn-lg btn-block"
          @click="goToRobot(nextStarSystem.planets[0].id + '0')"
        >
          <div class="col-6">
            <div class="step-info-text"><div>Next star system!</div> {{parseCamelCase(nextStarSystem.stats.name)}}</div>
          </div>
          <div class="col-6">
            <img
              class="step-next-planet"
              :class="nextStarSystem.planets[0].stats.name"
              :src="permanentImages.planets[nextStarSystem.planets[0].stats.name]"
              alt="Planet"
            />
          </div>
        </button>
        <button
          v-else-if="!nextStarSystem && !nextPlanet"
          type="button"
          class="btn btn-dark btn-lg btn-block"
        >
          <div class="col-6">
            <div class="step-info-text"><div>You won!</div></div>
          </div>
          <div class="col-6">
            <img
              class="step-next-planet"
              :src="permanentImages.instructionsRobot"
              alt="Planet"
              style="background-color: #ffffff; border-radius: 50%;"
            />
          </div>
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import utils from '../services/utils'
import Stars from './Stars'
import SocialSharing from './Social_sharing'

export default {
  name: 'steps',
  mounted () {
    setTimeout(this.scrollToSelected, 80)
  },
  computed: {
    permanentImages () {
      return this.$store.getters.getPermanentImages
    },
    levelControl () {
      return this.$store.getters.getLevelControl
    },
    planetStats () {
      return this.levelControl.getPlanetStats()
    },
    selectedContinent () {
      return this.levelControl.path.slice(4)
    },
    path () {
      return this.levelControl.path
    },
    nextPlanet () {
      return this.levelControl.getNextPlanet()
    },
    nextStarSystem () {
      return this.levelControl.getNextStarSystem()
    }
  },
  methods: {
    parseCamelCase: utils.parseCamelCase,
    goToRobot (continentId) {
      this.levelControl.getContinent(continentId, () => {
        this.$router.push({path: '/robot'})
      })
    },
    scrollToSelected () {
      const $selected = $('#selected-continent')
      $('.steps-container').animate({
        scrollTop: $selected.position().top - 75
      }, 1000)
    }
  },
  components: {
    Stars,
    SocialSharing
  },
  props: ['selectedPlanet', 'continents', 'planetName']
}
</script>

<style scoped lang="scss">
  $steps-font-size: 2vmin;
  $gradient-size: 100px;
  $outer-shadow-blur: 50px;
  $outer-shadow-size: 1px;
  $BasicProgramming-color: rgba(202, 122, 255, 1);
  $Counting-color: rgba(242, 92, 92, 1);
  $Numbers-color: rgba(74, 144, 226, 1);
  $Recursion-color: rgba(255, 152, 177, 1);
  $Conditionals-color: rgba(80, 227, 194, 1);
  $Addition-color: rgba(41, 254, 28, 1);
  $Subtraction-color: rgba(253, 254, 137, 1);
  $Multiplication-color: rgba(254, 151, 78, 1);
  $Division-color: rgba(64, 169, 254, 1);
  $inactive-color: rgba(104, 104, 104, 1);
  $planet-gradient: rgba(0, 0, 0, 1);
  $gradient-size: 100px;
  $font-color: #ffffff;

  .steps {
    position: absolute;
    top: 8%;
    right: 0;
    bottom: 0;
    color: $font-color;
    font-size: $steps-font-size;

    .header {
      font-size: 1.5em;
      padding: 2% 0;
      text-align: left;
    }

    .btn {
      color: $font-color;
      display: flex;
      margin: 0.15em 0 0.15em 0;
      border-radius: 0.25rem!important;
      background-color: #000000;
      font-size: 1.1em;
      padding: 8% 0;
    }

    .steps-container {
      overflow-y: auto;
      -webkit-overflow-scrolling: touch;
      height: 90%;
      .btn-group-vertical {
        min-height: min-content;
        width: 100%;

        #selected-step {
          border: 2px solid pink;
        }
      }

      .step-info-text {
        width: 100%;
        height: 100%;
      }
    }

    .step-next-planet {
      height: 2.5em;
      border-radius: 50%;
    }
  }

  .step-next-planet.BasicProgramming {
    background: radial-gradient(circle at $gradient-size $gradient-size, $BasicProgramming-color, $planet-gradient);
  }

  .step-next-planet.Counting {
    background: radial-gradient(circle at $gradient-size $gradient-size, $Counting-color, $planet-gradient);
  }

  .step-next-planet.Numbers {
    background: radial-gradient(circle at $gradient-size $gradient-size, $Numbers-color, $planet-gradient);
  }

  .step-next-planet.Recursion {
    background: radial-gradient(circle at $gradient-size $gradient-size, $Recursion-color, $planet-gradient);
  }

  .step-next-planet.Conditionals {
    background: radial-gradient(circle at $gradient-size $gradient-size, $Conditionals-color, $planet-gradient);
  }

  .step-next-planet.Addition {
    background: radial-gradient(circle at $gradient-size $gradient-size, $Addition-color, $planet-gradient);
  }

  .step-next-planet.Subtraction {
    background: radial-gradient(circle at $gradient-size $gradient-size, $Subtraction-color, $planet-gradient);
  }

  .step-next-planet.Multiplication {
    background: radial-gradient(circle at $gradient-size $gradient-size, $Multiplication-color, $planet-gradient);
  }

  .step-next-planet.Division {
    background: radial-gradient(circle at $gradient-size $gradient-size, $Division-color, $planet-gradient);
  }

  .BasicProgramming.selected {
    box-shadow: inset 0 0 $outer-shadow-blur $outer-shadow-size $BasicProgramming-color;
  }

  .Counting.selected {
    box-shadow: inset 0 0 $outer-shadow-blur $outer-shadow-size $Counting-color;
  }

  .Numbers.selected {
    box-shadow: inset 0 0 $outer-shadow-blur $outer-shadow-size $Numbers-color;
  }

  .Recursion.selected {
    box-shadow: inset 0 0 $outer-shadow-blur $outer-shadow-size $Recursion-color;
  }

  .Conditionals.selected {
    box-shadow: inset 0 0 $outer-shadow-blur $outer-shadow-size $Conditionals-color;
  }

  .Addition.selected {
    box-shadow: inset 0 0 $outer-shadow-blur $outer-shadow-size $Addition-color;
  }

  .Subtraction.selected {
    box-shadow: inset 0 0 $outer-shadow-blur $outer-shadow-size $Subtraction-color;
  }

  .Multiplication.selected {
    box-shadow: inset 0 0 $outer-shadow-blur $outer-shadow-size $Multiplication-color;
  }

  .Division.selected {
    box-shadow: inset 0 0 $outer-shadow-blur $outer-shadow-size $Division-color;
  }

  .step-image-stars {
    height: 100%;
  }

  .step-image-lock {
    height: 51px;
    width: 42px;
  }

  .step-active {
    opacity: 1;
  }

  .step-disables {
    opacity: 0.8;
  }
</style>
