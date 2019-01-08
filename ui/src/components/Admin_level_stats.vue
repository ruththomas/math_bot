<template>
  <div class="star-system-admin">

    <div class="row my-3">
      <unlock-all-levels></unlock-all-levels>
    </div>

    <div class="row mb-3" style="min-height: 10rem;">
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
            <label for="starSystems"
                   id="starSystemHelp"
                   @click="showStarSystemData = !showStarSystemData"
            >
              star system
              <i

                class="fa fa-info-circle"
              >

              </i>

            </label>
            <b-popover

              :show.sync="showStarSystemData" target="#starSystemHelp"
              :title="adminControl.levelStats[starSystem.id]._id">

              <div
                @click="showStarSystemData = !showStarSystemData"
                v-for="(value, key) in adminControl.levelStats[starSystem.id]" :key="'event_detail/' + key"
                class="container text-white">
                <div class="row font-weight-bold">
                  {{key}}
                </div>
                <div class="row" style="min-width: 55rem;">
                  {{value}}
                </div>
              </div>
            </b-popover>
            <select @change="changeStarSystem" class="form-control-sm" id="starSystems" name="starSystems">
              <option v-for="star in levelControl.galaxy.starSystems" :key="star.id" :value="star.id">
                {{star.stats.name}}
              </option>
            </select>
          </div>
        </div>

        <div class="mx-3">
          <div class="form-group">
            <label for="planets" id="showPlanetData"
                   @click="showPlanetData = !showPlanetData"
            >
              planets
              <i

                class="fa fa-info-circle"
              >

              </i>

            </label>

            <b-popover

              :show.sync="showPlanetData" target="#showPlanetData" :title="adminControl.levelStats[planet.id]._id">

              <div
                @click="showPlanetData = !showPlanetData"
                v-for="(value, key) in adminControl.levelStats[starSystem.id]" :key="'event_detail/' + key"
                class="container text-white">
                <div class="row font-weight-bold">
                  {{key}}
                </div>
                <div class="row" style="min-width: 55rem;">
                  {{value}}
                </div>
              </div>
            </b-popover>
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

export default {
  name: 'LevelStats',

  methods: {
    someMaxLevel () {
      return Object.values(this.adminControl.maxLevel).some(level => {
        return level.count > 0
      })
    },
    changeActivePlanet (e) {
      this.activePlanet = e.target.value

      this.adminControl.getPlanetStats(this.activePlanet)
    },
    changeStarSystem (e) {
      const { target: { value } } = e
      this.activeStarSystem = value

      this.changeActivePlanet({ target: { value: value + '0' } })
    },
    getPlanetStats () {
      this.adminControl.getPlanetStats(this.planet.id)
    }

  },
  mounted () {
    Promise.all([
      this.adminControl.getMaxLevelStats(),
      this.getPlanetStats()
    ])
  },
  data () {
    return {
      activeStarSystem: '000',
      showPlanetData: false,
      showStarSystemData: false,
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
  .star-system-admin {
    height: 100%;
    width: 100%;
    position: relative;

    th, label {

      text-transform: capitalize;
      font-weight: bold;
    }

    .fa {

      cursor: pointer;
    }
  }

</style>
