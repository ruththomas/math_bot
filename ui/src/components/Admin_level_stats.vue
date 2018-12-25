<template>
  <div class="star-system-admin">

    <div class="row my-3">
      <unlock-all-levels></unlock-all-levels>
    </div>

    <div class="row mb-3">
      <admin-max-level v-if="someMaxLevel()"></admin-max-level>
      <div v-else>
        <h6>Loading...</h6>
      </div>
    </div>

    <div>

      <h3 class="my-3">Level Stats</h3>

      <div class="row d-flex justify-content-center align-items-center my-3">
        <div class="mx-3">

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

        <div class="mx-3">
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
      </div>

      <level-stats-table
        :level-stats="adminControl.levelStats"
        :continent-ids="continentIds"
      ></level-stats-table>

    </div>
  </div>

</template>

<script>
import Continents from './Continents'
import Planets from './Planets'
import AdminMaxLevel from './Admin_max_level'
import UnlockAllLevels from './UnlockAllLevels'
import LevelStatsTable from './LevelStatsTable'

// code 4 0000

// 1 digit 0010

export default {
  name: 'LevelStats',

  methods: {

    someMaxLevel () {
      return Object.values(this.adminControl.maxLevel).some(level => {
        return level.count > 0
      })
    },

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
    Promise.all([
      this.adminControl.getMaxLevelStats(),
      this.getLevelStats()
    ])
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
    LevelStatsTable,
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

    th, label {

      text-transform: capitalize;
      font-weight: bold;
    }

  }

</style>
