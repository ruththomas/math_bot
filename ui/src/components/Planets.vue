<template>
  <div class="col-8 space">
    <img
      v-for="(planet, ind) in planets" :key="'planet/' + planet.id"
      class="planet"
      :class="[
        planet.stats.name,
        planet.stats.active ? 'active' : 'inactive',
        planet.stats.active && Number(selectedPlanet) === ind ? 'selected' : ''
      ]"
      :src="permanentImages.planets[planet.stats.name]"
      @click="planet.stats.active ? updateSelectedPlanet(ind) : ''" />
  </div>
</template>

<script>
export default {
  name: 'planets',
  computed: {
    permanentImages () {
      return this.$store.getters.getPermanentImages
    }
  },
  data () {
    return {
      selectedLevel: ''
    }
  },
  props: ['planets', 'selectedPlanet', 'updateSelectedPlanet']
}
</script>

<style scoped lang="scss">
  $gradient-size: 50%;
  $outer-shadow-blur: 100px;
  $outer-shadow-size: 5px;
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
  $BasicProgramming-size: 23vmin;
  $Counting-size: 16vmin;
  $Numbers-size: 20vmin;
  $Recursion-size: 14vmin;
  $Conditionals-size: 12vmin;
  $Addition-size: 20vmin;
  $Subtraction-size: 11vmin;
  $Multiplication-size: 18vmin;
  $Division-size: 15vmin;
  $planet-gradient: rgba(0, 0, 0, 1);

  .space {
    height: 90%;
    position: relative;
  }

  .planet {
    position: absolute;
    color: white;
    cursor: pointer;
    border-radius: 50%;
  }

  .BasicProgramming {
    background: radial-gradient(circle at $gradient-size $gradient-size, $BasicProgramming-color, $planet-gradient);
    height: $BasicProgramming-size;
    width: $BasicProgramming-size;
    top: 12%;
    left: 7%;
  }

  .Counting {
    background: radial-gradient(circle at $gradient-size $gradient-size, $Counting-color, $planet-gradient);
    height: $Counting-size;
    width: $Counting-size;
    top: 2%;
    left: 50%;
  }

  .Numbers {
    background: radial-gradient(circle at $gradient-size $gradient-size, $Numbers-color, $planet-gradient);
    height: $Numbers-size;
    width: $Numbers-size;
    top: 33%;
    left: 60%;
  }

  .Recursion {
    background: radial-gradient(circle at $gradient-size $gradient-size, $Recursion-color, $planet-gradient);
    height: $Recursion-size;
    width: $Recursion-size;
    top: 65%;
    left: 50%;
  }

  .Conditionals {
    background: radial-gradient(circle at $gradient-size $gradient-size, $Conditionals-color, $planet-gradient);
    height: $Conditionals-size;
    width: $Conditionals-size;
    top: 80%;
    left: 25%;
  }

  .Addition {
    background: radial-gradient(circle at $gradient-size $gradient-size, $Addition-color, $planet-gradient);
    height: $Addition-size;
    width: $Addition-size;
    top: 75%;
    left: 20%;
  }

  .Subtraction {
    background: radial-gradient(circle at $gradient-size $gradient-size, $Subtraction-color, $planet-gradient);
    height: $Subtraction-size;
    width: $Subtraction-size;
    top: 60%;
    left: 60%;
  }

  .Multiplication {
    background: radial-gradient(circle at $gradient-size $gradient-size, $Multiplication-color, $planet-gradient);
    height: $Multiplication-size;
    width: $Multiplication-size;
    top: 40%;
    left: 15%;
  }

  .Division {
    background: radial-gradient(circle at $gradient-size $gradient-size, $Division-color, $planet-gradient);
    height: $Division-size;
    width: $Division-size;
    top: 20%;
    left: 50%;
  }

  .BasicProgramming.selected {
    box-shadow: 0 0 $outer-shadow-blur $outer-shadow-size $BasicProgramming-color;
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

  .planet.inactive {
    opacity: 0.8;
    background: radial-gradient(circle at $gradient-size $gradient-size, $inactive-color, $planet-gradient);
  }
</style>
