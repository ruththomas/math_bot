<template>
  <div
    class="space"
    :class="[
      isButton ? 'btn btn-dark space-btn' : '',
      isButton && !starSystem.stats.active ? 'space-btn-disabled' : '',
      isButton && selectedStarSystem === starSystem.id.substr(-1) ? 'space-btn-selected' : ''
    ]"
    :style="specifiedStyle"
    @click="isButton && starSystem.stats.active ? levelControl.updateStarSystem(starSystem.id.substr(-1)) : ''"
  >
    <img
      v-for="(planet, ind) in starSystem.planets" :key="'planet/' + planet.id"
      class="planet"
      :class="[
        planet.stats.name,
        planet.stats.active ? 'active' : 'inactive',
        planet.stats.active && selectedStarSystem === starSystem.id.substr(-1) && Number(selectedPlanet) === ind ? 'selected' : ''
      ]"
      :src="permanentImages.planets[planet.stats.name]"
      @click="!isButton && planet.stats.active ? updateSelectedPlanet(ind) : ''" />
    <div v-if="isButton" class="space-text-zone">
      <span v-if="starSystem.stats.active" class="space-btn-name">{{starSystem.stats.name}}</span>
      <img v-else class="space-btn-image" :src="permanentImages.lock" />
    </div>
  </div>
</template>

<script>
export default {
  name: 'planets',
  computed: {
    levelControl () {
      return this.$store.getters.getLevelControl
    },
    permanentImages () {
      return this.$store.getters.getPermanentImages
    }
  },
  data () {
    return {
      selectedLevel: ''
    }
  },
  props: ['isButton', 'selectedPlanet', 'selectedStarSystem', 'updateSelectedPlanet', 'specifiedStyle', 'starSystem']
}
</script>

<style scoped lang="scss">
  $gradient-size: 50%;
  $outer-shadow-blur: 100px;
  $outer-shadow-size: 5px;
  $Coding-color: rgba(202, 122, 255, 1);
  $Counting-color: rgba(242, 92, 92, 1);
  $Numbers-color: rgba(74, 144, 226, 1);
  $Recursion-color: rgba(255, 152, 177, 1);
  $Conditionals-color: rgba(80, 227, 194, 1);
  $Coordinates-color: rgba(185, 80, 86, 1);
  $Addition-color: rgba(41, 254, 28, 1);
  $Subtraction-color: rgba(253, 254, 137, 1);
  $Multiplication-color: rgba(254, 151, 78, 1);
  $Division-color: rgba(64, 169, 254, 1);
  $Exponents-color: rgba(185, 62, 167, 1);
  $Roots-color: rgba(54, 63, 185, 1);
  $BlankSlate-color: rgba(54, 63, 185, 1);
  $PracticeArithmetic-color: rgba(185, 62, 167, 1);
  $Refactor-color: transparent;
  $inactive-color: rgba(104, 104, 104, 1);
  $Coding-size: 25% /*23vim*/;
  $Coordinates-size: 14% /*13vmin*/;
  $Counting-size: 18%/*16vmin*/;
  $Numbers-size: 20%/*20vmin*/;
  $Recursion-size: 16%/*14vmin*/;
  $Conditionals-size: 12%/*12vmin*/;
  $Addition-size: 11%/*11vmin*/;
  $Subtraction-size: 10%/*10vmin*/;
  $Multiplication-size: 14% /*13vmin*/;
  $Division-size: 12% /*12vmin*/;
  $Exponents-size: 15% /*15vmin*/;
  $Roots-size: 10% /*9vmin*/;
  $Refactor-size: 6% /*5vmin*/;
  $planet-gradient: rgba(0, 0, 0, 1);
  $space-btn-size: 10vmin;

  .space {
    height: 100%;
    width: 100%;
    position: relative;
  }

  .space-btn {
    width: $space-btn-size;
    height: $space-btn-size;
    margin: 0.3em 0 0.3em 0;
    border-radius: 0.25rem!important;
    background-color: transparent;
    font-size: 1.1em;
    padding: 8% 0;
    display: flex;
    align-items: center;
    justify-content: center;
    border-color: transparent;
  }

  .space-btn-selected {
    border-color: #343a40;
  }

  .space-btn:first-child {
    margin-top: 1.5em;
  }

  .space-text-zone {
    z-index: 101;
    .space-btn-name {
      font-size: 1.5em;
      margin: 0;
      color: #ffffff;
    }
    .space-btn-image {
      height: 2em;
      width: auto;
    }
  }

  .planet {
    position: absolute;
    color: white;
    cursor: pointer;
    border-radius: 50%;
  }

  .space-btn-disabled {
    opacity: 0.5;
    cursor: not-allowed!important;
  }

  .Coding {
    background: radial-gradient(circle at $gradient-size $gradient-size, $Coding-color, $planet-gradient);
    width: $Coding-size;
    height: auto;
    top: 12%;
    left: 7%;
  }

  .Counting {
    background: radial-gradient(circle at $gradient-size $gradient-size, $Counting-color, $planet-gradient);
    width: $Counting-size;
    height: auto;
    top: 2%;
    left: 50%;
  }

  .Numbers {
    background: radial-gradient(circle at $gradient-size $gradient-size, $Numbers-color, $planet-gradient);
    width: $Numbers-size;
    height: auto;
    top: 33%;
    left: 60%;
  }

  .Recursion {
    background: radial-gradient(circle at $gradient-size $gradient-size, $Recursion-color, $planet-gradient);
    width: $Recursion-size;
    height: auto;
    top: 65%;
    left: 50%;
  }

  .Conditionals {
    background: radial-gradient(circle at $gradient-size $gradient-size, $Conditionals-color, $planet-gradient);
    width: $Conditionals-size;
    height: auto;
    top: 80%;
    left: 25%;
  }

  .Coordinates {
    background: radial-gradient(circle at $gradient-size $gradient-size, $Coordinates-color, $planet-gradient);
    width: $Coordinates-size;
    height: auto;
    top: 80%;
    left: 10%;
  }

  .Addition {
    background: radial-gradient(circle at $gradient-size $gradient-size, $Addition-color, $planet-gradient);
    width: $Addition-size;
    height: auto;
    top: 70%;
    left: 65%;
  }

  .Subtraction {
    background: radial-gradient(circle at $gradient-size $gradient-size, $Subtraction-color, $planet-gradient);
    width: $Subtraction-size;
    height: auto;
    top: 55%;
    left: 35%;
  }

  .Multiplication {
    background: radial-gradient(circle at $gradient-size $gradient-size, $Multiplication-color, $planet-gradient);
    width: $Multiplication-size;
    height: auto;
    top: 40%;
    left: 70%;
  }

  .Division {
    background: radial-gradient(circle at $gradient-size $gradient-size, $Division-color, $planet-gradient);
    width: $Division-size;
    height: auto;
    top: 30%;
    left: 15%;
  }

  .Exponents {
    background: radial-gradient(circle at $gradient-size $gradient-size, $Exponents-color, $planet-gradient);
    width: $Exponents-size;
    height: auto;
    top: 13%;
    left: 60%;
  }

  .Roots {
    background: radial-gradient(circle at $gradient-size $gradient-size, $Roots-color, $planet-gradient);
    width: $Roots-size;
    height: auto;
    top: 8%;
    left: 10%;
  }

  .Refactor {
    background: radial-gradient(circle at $gradient-size $gradient-size, $Refactor-color, $planet-gradient);
    width: $Division-size;
    height: auto;
    top: 70%;
    left: 0;
  }

  .Coding.selected {
    box-shadow: 0 0 $outer-shadow-blur $outer-shadow-size $Coding-color;
  }

  .Counting.selected {
    box-shadow: 0 0 $outer-shadow-blur $outer-shadow-size $Counting-color;
  }

  .Numbers.selected {
    box-shadow: 0 0 $outer-shadow-blur $outer-shadow-size $Numbers-color;
  }

  .Recursion.selected {
    box-shadow: 0 0 $outer-shadow-blur $outer-shadow-size $Recursion-color;
  }

  .Conditionals.selected {
    box-shadow: 0 0 $outer-shadow-blur $outer-shadow-size $Conditionals-color;
  }

  .Coordinates.selected {
    box-shadow: 0 0 $outer-shadow-blur $outer-shadow-size $Coordinates-color;
  }

  .Addition.selected {
    box-shadow: 0 0 $outer-shadow-blur $outer-shadow-size $Addition-color;
  }

  .Subtraction.selected {
    box-shadow: 0 0 $outer-shadow-blur $outer-shadow-size $Subtraction-color;
  }

  .Multiplication.selected {
    box-shadow: 0 0 $outer-shadow-blur $outer-shadow-size $Multiplication-color;
  }

  .Division.selected {
    box-shadow: 0 0 $outer-shadow-blur $outer-shadow-size $Division-color;
  }

  .Exponents.selected {
    box-shadow: 0 0 $outer-shadow-blur $outer-shadow-size $Exponents-color;
  }

  .Roots.selected {
    box-shadow: 0 0 $outer-shadow-blur $outer-shadow-size $Roots-color;
  }

  .BlankSlate.selected {
    box-shadow: 0 0 $outer-shadow-blur $outer-shadow-size $BlankSlate-color;
  }

  .PracticeArithmetic.selected {
    box-shadow: 0 0 $outer-shadow-blur $outer-shadow-size $PracticeArithmetic-color;
  }

  .planet.inactive {
    opacity: 0.8;
    cursor: not-allowed;
    background: radial-gradient(circle at $gradient-size $gradient-size, $inactive-color, $planet-gradient);
  }
</style>
