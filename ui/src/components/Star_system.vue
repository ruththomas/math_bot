<template>
<div class="star-system animated fadeIn">
  <div class="btn-group-vertical">
    <div
      v-for="(starSystem, ind) in starSystems"
      :key="'profile-star-system/' + ind"
      class="btn btn-dark star-system-btn"
      :class="[
        !starSystem.stats.active ? 'space-btn-disabled' : '',
        selectedStarSystem === starSystem.id.substr(-1) ? 'space-btn-selected' : ''
      ]"
      @click="levelControl.updateStarSystem(ind)"
    >
      <div class="star-system-planets">
        <planets
          :selected-star-system="selectedStarSystem"
          :selected-planet="selectedPlanet"
          :update-selected-planet="levelControl.updatePlanet"
          :is-button="true"
          :star-system="starSystem"
        ></planets>
      </div>
      <div class="star-system-name">
        {{starSystem.stats.name}}
      </div>
    </div>
    <div class="btn btn-dark star-system-btn" @click="levelControl.getSandbox">Sandbox</div>
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
$space-btn-size: 20vmin;
$click-color: #B8E986;
.star-system {
  display: flex;
  height: 100%;
  width: 100%;
  position: relative;
  font-size: 1.5vmin;
  .header {
    position: absolute;
    color: white;
  }
  .btn-group-vertical {
    justify-content: flex-start;
    .star-system-btn {
      height: min-content;
      width: $space-btn-size;
      margin: 0.3em 0 0.3em 0;
      background-color: #000000;
      font-size: 1.75em;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      border-radius: 3px;

      .star-system-name {
        flex: 1;
      }

      .star-system-planets {
        flex: 2;
        height: 100%;
      }
    }

    .star-system-btn.space-btn-selected {
      border-color: $click-color;
    }

    .star-system-btn.space-btn-disabled {
      opacity: 0.5;
      cursor: not-allowed;
    }
  }
}
</style>
