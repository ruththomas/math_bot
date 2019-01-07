<template>
  <div
    class="space"
    :class="[
      isButton ? 'space-btn' : ''
    ]"
    :style="specifiedStyle"
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
      @error="defaultImage(planet.stats.name)"
      @click="!isButton && planet.stats.active ? updateSelectedPlanet(ind) : ''" />
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
  methods: {
    defaultImage (planet) {
      this.permanentImages.planets[planet] = 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNkYAIAAAgABJ60+RsAAAAASUVORK5CYII='
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
  $Coding-size: 35% /*23vim*/;
  $Counting-size: 28%/*16vmin*/;
  $Numbers-size: 25%/*20vmin*/;
  $Recursion-size: 22%/*14vmin*/;
  $Conditionals-size: 18%/*12vmin*/;
  $Coordinates-size: 20% /*13vmin*/;
  $Addition-size: 18%/*11vmin*/;
  $Subtraction-size: 22%/*10vmin*/;
  $Multiplication-size: 20% /*13vmin*/;
  $Division-size: 18% /*12vmin*/;
  $Exponents-size: 25% /*15vmin*/;
  $Roots-size: 18% /*9vmin*/;
  $Refactor-size: 16% /*5vmin*/;
  $planet-gradient: rgba(0, 0, 0, 1);
  $space-btn-size: 10vmin;

  .space {
    height: 100%;
    width: 100%;
    position: relative;
  }

  .space-btn {
    height: $space-btn-size;
    width: $space-btn-size;
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

  @media (max-width: 414px) and (orientation: portrait) {
    .Coding {
      width: calc(#{$Coding-size} * 1.3);
      height: auto;
    }

    .Counting {
      width: calc(#{$Counting-size} * 1.3);
      height: auto;
    }

    .Numbers {
      width: calc(#{$Numbers-size} * 1.3);
      height: auto;
    }

    .Recursion {
      width: calc(#{$Recursion-size} * 1.3);
      height: auto;
    }

    .Conditionals {
      width: calc(#{$Conditionals-size} * 1.3);
      height: auto;
    }

    .Coordinates {
      width: calc(#{$Coordinates-size} * 1.3);
      height: auto;
    }

    .Addition {
      width: calc(#{$Addition-size} * 1.3);
      height: auto;
    }

    .Subtraction {
      width: calc(#{$Subtraction-size} * 1.3);
      height: auto;
    }

    .Multiplication {
      width: calc(#{$Multiplication-size} * 1.3);
      height: auto;
    }

    .Division {
      width: calc(#{$Division-size} * 1.3);
      height: auto;
    }

    .Exponents {
      width: calc(#{$Exponents-size} * 1.3);
      height: auto;
    }

    .Roots {
      width: calc(#{$Roots-size} * 1.3);
      height: auto;
    }

    .Refactor {
      width: calc(#{$Division-size} * 1.3);
      height: auto;
    }
  }
</style>
