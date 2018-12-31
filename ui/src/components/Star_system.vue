<template>
<div class="star-system animated fadeIn">
  <div class="col-2" style="padding: 0; display: flex; flex-direction: column; align-items: center;">
    <planets
      v-for="(starSystem, ind) in starSystems"
      :key="'profile-star-system/' + ind"
      :selected-star-system="selectedStarSystem"
      :selected-planet="selectedPlanet"
      :update-selected-planet="levelControl.updatePlanet"
      :is-button="true"
      :star-system="starSystem"
    ></planets>
    <div class="btn btn-dark sandbox-btn" @click="levelControl.getSandbox">Sandbox</div>
  </div>
  <div class="col-6">
    <planets
      :selected-planet="selectedPlanet"
      :selected-star-system="selectedStarSystem"
      :update-selected-planet="levelControl.updatePlanet"
      :star-system="starSystem"
      :is-button="false"
    ></planets>
  </div>
  <div class="col-4">
    <continents :selected-planet="selectedPlanet" :continents="starSystem.planets[selectedPlanet].continents" :planet-name="starSystem.planets[selectedPlanet].stats.name"></continents>
  </div>
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
    starSystems () {
      return this.levelControl.galaxy.starSystems
    },
    selectedStarSystem () {
      return this.levelControl.path[2]
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
$space-btn-size: 10vmin;
.star-system {
  display: flex;
  height: 85%;
  width: 100%;
  position: relative;
  font-size: 1.5vmin;
  .header {
    position: absolute;
    color: white;
  }
  .col-2 {
    .sandbox-btn {
      width: $space-btn-size;
      margin: 0.3em 0 0.3em 0;
      border-radius: 0.25rem!important;
      background-color: transparent;
      font-size: 1.5em;
      padding: 8% 0;
      display: flex;
      align-items: center;
      justify-content: center;
      border: none;
    }
  }
}
</style>
