<template>
  <div class="space">
    <div v-for="planet in planets" :key="planet.planetName" class="planet-container">
      <img
        v-if="planet.planetState !== 'no-show'"
        class="planet"
        :class="[planet.planetName, planet.planetState]"
        :src="planet.image"
        @click="selectLevel(planet.level)" />
    </div>
  </div>
</template>

<script>
import api from '../services/api'
export default {
  name: 'planets',
  computed: {
    level () {
      return this.$store.getters.getLevel
    },
    levels () {
      return this.$store.getters.getLevels
    },
    steps () {
      return this.$store.getters.getSteps
    },
    planets () {
      return this.levels.map((level, index) => {
        const planetNumber = index + 1
        const planet = {}
        planet.level = level
        planet.planetName = `planet-${planetNumber}`
        planet.planetState = this.level === level.name ? 'selected' : this.isLevelActive(level) ? 'active' : 'inactive'
        planet.image = this.permanentImages.planets[`planet${planetNumber}`]
        return planet
      })
    },
    tokenId () {
      return this.$store.getters.getToken.token_id
    }
  },
  data () {
    return {
      selectedLevel: ''
    }
  },
  methods: {
    isLevelActive (l) {
      const level = l.level
      return Object.keys(level).reduce((bool, step) => {
        if (level[step].active === true) {
          bool = true
        }
        return bool
      }, false)
    },
    selectLevel (level) {
      if (this.isLevelActive(level)) {
        api.switchLevel({tokenId: this.tokenId, level: level.name}, (res) => {
          this.$store.dispatch('updateStats', res.body)
          this.selectedLevel = level
        })
      }
    }
  },
  props: ['permanentImages']
}
</script>

<style scoped src="../css/scoped/space.scss" lang="scss"></style>
