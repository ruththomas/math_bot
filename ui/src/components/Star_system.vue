<template>
<div class="star-system animated fadeIn">
  <h2 class="header">{{ starSystemData.stats.name }}</h2>
  <planets :planets="starSystemData.planets" :selected-planet="selectedPlanet" :update-selected-planet="updateSelectedPlanet"></planets>
  <continents :selected-planet="selectedPlanet" :continents="starSystemData.planets[selectedPlanet].continents" :planet-name="starSystemData.planets[selectedPlanet].stats.name"></continents>
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
    }
  },
  data () {
    return {
      selectedPlanet: 0
    }
  },
  methods: {
    updateSelectedPlanet (ind) {
      this.selectedPlanet = ind
    }
  },
  components: {
    Continents,
    Planets
  },
  props: ['starSystemData']
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
