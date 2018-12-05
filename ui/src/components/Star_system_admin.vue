<template>
  <div class="star-system-admin animated fadeIn">

    <div class="row">
      <h3>Active Star system

        <small>
          {{starSystem.stats.name}}
        </small>
      </h3>
    </div>

    <div class="row">

      <label>
        star system
      </label>
      <select @change="changeStarSystem">
        <option v-for="star in levelControl.galaxy.starSystems" :key="star.id" :value="star.id">
          {{star.stats.name}}
        </option>
      </select>
    </div>

    <div class="row">
      <label>
         planets
      </label>
      <select>
        <option
          @change="changeActivePlanet"
          v-for="planet in starSystem.planets" :key="planet.id" :value="planet.id">
          {{planet.stats.name}}
        </option>
      </select>
    </div>

    <div class="row d-flex justify-content-center">

      <h3>Level Stats</h3>
      <ul class="list-group">

        <li v-for="s in levelStats" :key="s._id" class="list-group-item">

          <h3>level: {{s.level}}</h3>
          <p>timesPlayed: {{s.timesPlayed}}</p>
          <p>timesPlayedAvg: {{s.timesPlayedAvg}}</p>
          <p>timesPlayedMax: {{s.timesPlayedMax}}</p>
          <p>wins: {{s.wins}}</p>
          <p>winsAvg: {{s.winsAvg}}</p>
          <p>winsMax: {{s.winsMax}}</p>

        </li>
      </ul>
      <!--<b-btn-->
        <!--v-for="(starSystem, ind) in levelStats"-->
        <!--:key="'star-system/' + ind"-->
        <!--@click="updateStarSystem(ind)"-->
        <!--:class="Number(starSystemShowing) === ind ? 'selected' : ''"-->
        <!--:disabled="!starSystem.stats.active"-->
      <!--&gt;{{starSystem.stats.name}}</b-btn>-->
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

    },
    changeStarSystem (e) {
      console.log('changeStarSystem', e.target.value)
      this.activeStarSystem = e.target.value
    }
  },
  mounted () {
    setTimeout(() => {
      this.getLevelStats()
      console.log(Array.from(this.starSystem))
    }, 3000)
  },
  data () {
    return {
      activeStarSystem: '000'
    }
  },

  computed: {

    levelStats () {
      return this.adminControl.levelStats
    },

    getLevelStats () {
      const conts = this.starSystem.planets[this.selectedPlanet].continents.map(i => i.id)

      Promise.all(conts.map(continent => this.adminControl.getLevelStats(continent)))
    },
    continents () {
      return this.starSystem.planets[this.selectedPlanet].continents
    },
    adminControl () {
      return this.$store.getters.getAdminControl
    },
    levelControl () {
      return this.$store.getters.getLevelControl
    },
    selectedPlanet () {
      return this.levelControl.path[3]
    },
    starSystem () {
      return this.levelControl.galaxy.starSystems.find(star => star.id === this.activeStarSystem)
    },
    planets () {
      return this.starSystem.planets
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
