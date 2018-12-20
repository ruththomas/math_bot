<template>
  <div class="star-system-admin">

    <div class="row my-3">
      <unlock-all-levels></unlock-all-levels>
    </div>

    <div class="row mb-3">

      <div class="form-group">
        <label for="starSystems">
          star system
        </label>
        <select @change="changeStarSystem" class="form-control-sm" id="starSystems" name="starSystems">
          <option v-for="star in levelControl.galaxy.starSystems" :key="star.id" :value="star.id">
            {{star.stats.name}}
          </option>
        </select>
      </div>
    </div>

    <div class="row mb-3">
      <div class="form-group">
        <label for="planets">
          planets
        </label>
        <select
          id="planets"
          name="planets"
          @change="changeActivePlanet"
        >
          <option

            v-for="planet in starSystem.planets" :key="planet.id" :value="planet.id">
            {{planet.stats.name}}
          </option>
        </select>
      </div>
    </div>

    <div class="row mb-3" v-if="adminControl.maxLevel.length > 0">
      <admin-max-level></admin-max-level>
    </div>

    <div v-if="adminControl.levelStats.length">

      <h3>Continent Stats</h3>

      <table class="table table-striped">
        <thead>

        <tr>
          <th>
            id
          </th>
          <th>timesPlayed</th>
          <th>timesPlayedAvg</th>
          <th>timesPlayedMax</th>
          <th>wins</th>
          <th>winsAvg</th>
          <th>winsMax</th>
        </tr>
        </thead>
        <tbody>
        <tr  v-for="(continent) in adminControl.levelStats"
             :key="continent.id"
             v-show="continentIds.includes(continent.id)"
        >
          <td>{{continent.id}}</td>
          <td class="text-monospace">{{continent.timesPlayed}}</td>
          <td class="text-monospace">{{continent.timesPlayedAvg}}</td>
          <td class="text-monospace">{{continent.timesPlayedMax}}</td>
          <td class="text-monospace">{{continent.wins}}</td>
          <td class="text-monospace">{{continent.winsAvg}}</td>
          <td class="text-monospace">{{continent.winsMax}}</td>
        </tr>
        </tbody>
      </table>

      <!--<div class="row d-flex">-->
        <!--<div-->
          <!--v-for="(continent) in adminControl.levelStats" :key="continent.id" class="card m-3" style="width: 18rem;"-->
          <!--v-show="continentIds.includes(continent.id)"-->
        <!--&gt;-->

          <!--<div class="card-header">continent: {{continent.id}}</div>-->

          <!--<div class="card-body">-->
            <!--<p>timesPlayed: {{continent.timesPlayed}}</p>-->
            <!--<p>timesPlayedAvg: {{continent.timesPlayedAvg}}</p>-->
            <!--<p>timesPlayedMax: {{continent.timesPlayedMax}}</p>-->
            <!--<p>wins: {{continent.wins}}</p>-->
            <!--<p>winsAvg: {{continent.winsAvg}}</p>-->
            <!--<p>winsMax: {{continent.winsMax}}</p>-->
          <!--</div>-->

        <!--</div>-->
      <!--</div>-->
    </div>
  </div>

</template>

<script>
import Continents from './Continents'
import Planets from './Planets'
import AdminMaxLevel from './Admin_max_level'
import UnlockAllLevels from './UnlockAllLevels'

export default {
  name: 'LevelStats',

  methods: {

    async changeActivePlanet (e) {
      this.activePlanet = e.target.value
      this.getLevelStats()
    },
    changeStarSystem (e) {
      const { target: { value } } = e
      this.activeStarSystem = value

      this.changeActivePlanet({ target: { value: value + '0' } })
    },
    getLevelStats () {
      const ids = this.planet.continents.map(i => i.id)

      Promise.all(ids.map(continentId => this.adminControl.getLevelStats(continentId)))
    }
  },
  mounted () {
    this.getLevelStats()
  },
  data () {
    return {
      activeStarSystem: '000',
      activePlanet: '0000'
    }
  },

  computed: {

    continentIds () {
      return this.planet.continents.map(cont => cont.id)
    },
    adminControl () {
      return this.$store.getters.getAdminControl
    },
    levelControl () {
      return this.$store.getters.getLevelControl
    },
    starSystem () {
      return this.levelControl.galaxy.starSystems.find(star => star.id === this.activeStarSystem)
    },
    planet () {
      return this.starSystem.planets.find(planet => planet.id === this.activePlanet)
    }
  },
  components: {
    UnlockAllLevels,
    AdminMaxLevel,
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
