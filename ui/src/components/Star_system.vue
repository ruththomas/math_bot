<template>
<div class="star-system animated fadeIn">
  <planets :planets="starSystem.planets" :selected-planet="selectedPlanet" :update-selected-planet="levelControl.updatePlanet"></planets>
  <continents :selected-planet="selectedPlanet" :continents="starSystem.planets[selectedPlanet].continents" :planet-name="starSystem.planets[selectedPlanet].stats.name"></continents>
</div>
</template>

<script>
import Continents from './Continents'
import Planets from './Planets'
export default {
  name: 'Star_system',
  computed: {
    levelControl () {
      return this.$store.getters.getLevelControl
    },
    selectedPlanet () {
      return this.levelControl.path[3]
    },
    starSystem () {
      return this.levelControl.galaxy.starSystems[this.starSystemShowing]
    },
    starSystemShowing () {
      return this.levelControl.path[2]
    }
  },
  components: {
    Continents,
    Planets
  }
}
</script>

<style scoped lang="scss">
.star-system {
  display: flex;
  height: 100%;
  width: 100%;
  position: relative;
  .header {
    position: absolute;
    color: white;
  }
}
</style>
