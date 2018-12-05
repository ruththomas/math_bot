<template>
  <div class="star-system-admin animated fadeIn">

    <div class="row mb-3">

      <div class="input-group">
        <label>
          star system
        </label>
        <select @change="changeStarSystem" class="form-control-sm">
          <option v-for="star in levelControl.galaxy.starSystems" :key="star.id" :value="star.id">
            {{star.stats.name}}
          </option>
        </select>
      </div>
    </div>

    <div class="row mb-3">
     <div class="input-group">
       <label>
         planets
       </label>
       <select
         @change="changeActivePlanet"
       >
         <option

           v-for="planet in starSystem.planets" :key="planet.id" :value="planet.id">
           {{planet.stats.name}}
         </option>
       </select>
     </div>
    </div>

    <div v-if="planet.continents.length">

      <h3>Level Stats</h3>

        <div class="row d-flex">
          <div v-for="(s, i) in levelStats" :key="s._id + i" class="card m-3" style="width: 18rem;">

            <div class="card-header">level: {{s.level}}</div>

            <div class="card-body">
              <p>timesPlayed: {{s.timesPlayed}}</p>
              <p>timesPlayedAvg: {{s.timesPlayedAvg}}</p>
              <p>timesPlayedMax: {{s.timesPlayedMax}}</p>
              <p>wins: {{s.wins}}</p>
              <p>winsAvg: {{s.winsAvg}}</p>
              <p>winsMax: {{s.winsMax}}</p>
            </div>

          </div>
        </div>
    </div>

  </div>
</template>

<script>
import Continents from './Continents'
import Planets from './Planets'

// const levelStats = {
//   '00001': {
//     '_id': 'BasicProgramming',
//     'timesPlayed': 8,
//     'timesPlayedAvg': 2,
//     'timesPlayedMax': 2,
//     'wins': 8,
//     'winsAvg': 2,
//     'winsMax': 2,
//     'level': '00001'
//   },
//   '00004': {
//     '_id': 'BasicProgramming',
//     'timesPlayed': 0,
//     'timesPlayedAvg': 0,
//     'timesPlayedMax': 0,
//     'wins': 4,
//     'winsAvg': 1,
//     'winsMax': 1,
//     'level': '00004'
//   },
//   '00002': {
//     '_id': 'BasicProgramming',
//     'timesPlayed': 4,
//     'timesPlayedAvg': 1,
//     'timesPlayedMax': 1,
//     'wins': 4,
//     'winsAvg': 1,
//     'winsMax': 1,
//     'level': '00002'
//   },
//   '00003': {
//     '_id': 'BasicProgramming',
//     'timesPlayed': 0,
//     'timesPlayedAvg': 0,
//     'timesPlayedMax': 0,
//     'wins': 4,
//     'winsAvg': 1,
//     'winsMax': 1,
//     'level': '00003'
//   },
//   '00006': {
//     '_id': 'BasicProgramming',
//     'timesPlayed': 0,
//     'timesPlayedAvg': 0,
//     'timesPlayedMax': 0,
//     'wins': 4,
//     'winsAvg': 1,
//     'winsMax': 1,
//     'level': '00006'
//   },
//   '00005': {
//     '_id': 'BasicProgramming',
//     'timesPlayed': 0,
//     'timesPlayedAvg': 0,
//     'timesPlayedMax': 0,
//     'wins': 4,
//     'winsAvg': 1,
//     'winsMax': 1,
//     'level': '00005'
//   },
//   '00000': {
//     '_id': 'BasicProgramming',
//     'timesPlayed': 20,
//     'timesPlayedAvg': 5,
//     'timesPlayedMax': 5,
//     'wins': 4,
//     'winsAvg': 1,
//     'winsMax': 1,
//     'level': '00000'
//   },
//   '00007': {
//     '_id': 'BasicProgramming',
//     'timesPlayed': 0,
//     'timesPlayedAvg': 0,
//     'timesPlayedMax': 0,
//     'wins': 4,
//     'winsAvg': 1,
//     'winsMax': 1,
//     'level': '00007'
//   },
//   '00008': {
//     '_id': 'BasicProgramming',
//     'timesPlayed': 0,
//     'timesPlayedAvg': 0,
//     'timesPlayedMax': 0,
//     'wins': 4,
//     'winsAvg': 1,
//     'winsMax': 1,
//     'level': '00008'
//   },
//   '00009': {
//     '_id': 'BasicProgramming',
//     'timesPlayed': 0,
//     'timesPlayedAvg': 0,
//     'timesPlayedMax': 0,
//     'wins': 4,
//     'winsAvg': 1,
//     'winsMax': 1,
//     'level': '00009'
//   },
//   '000011': {
//     '_id': 'BasicProgramming',
//     'timesPlayed': 0,
//     'timesPlayedAvg': 0,
//     'timesPlayedMax': 0,
//     'wins': 4,
//     'winsAvg': 1,
//     'winsMax': 1,
//     'level': '000011'
//   },
//   '000012': {
//     '_id': 'BasicProgramming',
//     'timesPlayed': 0,
//     'timesPlayedAvg': 0,
//     'timesPlayedMax': 0,
//     'wins': 4,
//     'winsAvg': 1,
//     'winsMax': 1,
//     'level': '000012'
//   },
//   '000010': {
//     '_id': 'BasicProgramming',
//     'timesPlayed': 0,
//     'timesPlayedAvg': 0,
//     'timesPlayedMax': 0,
//     'wins': 4,
//     'winsAvg': 1,
//     'winsMax': 1,
//     'level': '000010'
//   }
// }
export default {
  name: 'Star_system_admin',

  methods: {

    changeActivePlanet (e) {
      this.activePlanet = e.target.value

      this.getLevelStats()
    },
    changeStarSystem (e) {
      this.activeStarSystem = e.target.value

      this.changeActivePlanet({target: {value: this.activeStarSystem + '0'}})
    },
    getLevelStats () {
      const conts = this.planet.continents.map(i => i.id)

      Promise.all(conts.map(continent => this.adminControl.getLevelStats(continent)))
    }
  },
  mounted () {
    setTimeout(() => {
      this.getLevelStats()
    }, 500)
  },
  data () {
    return {
      activeStarSystem: '000',
      activePlanet: '0000'
    }
  },

  computed: {

    levelStats () {
      return Object.values(this.adminControl.levelStats).filter(level => {
        const ids = this.planet.continents.map(cont => cont.id)
        return ids.includes(level.level)
      })
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
