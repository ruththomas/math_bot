<template>
  <div class="steps">
    <div class="steps-header-container">
      <div class="steps-header planet-header">{{`${planetName}:`}}</div>
      <div class="steps-header level-header"> {{ parseCamelCase(level) }}</div>
    </div>
    <div class="steps-navigator-container">
      <div
        v-for="(step, value) in steps"
        class="steps-navigator-item"
        :class="step.active ? 'step-active' : 'step-disabled'"
        @click="step.active ? goToRobot(level, step.name) : ''"
        :key="step + ':' + value"
      >
        <div class="step-info-text-container">
          <div class="step-info-text">{{ parseCamelCase(step.name) }}</div>
        </div>
        <div class="step-info-image-container">
          <stars :level="level" :step="step" :star-group="'star-cluster'"></stars>
        </div>
      </div>
      <div
        v-if="steps[steps.length - 1].wins > 0 && nextLevel !== 'None'"
        class="steps-navigator-item step-active"
        @click="goToRobot(nextLevel.name, nextLevel.firstStep)">
        <div class="step-info-text-container">
          <div class="step-info-text">
            <span class="step-info-new-level">Next planet!</span>
            {{parseCamelCase(nextLevel.name)}}
          </div>
        </div>
        <div class="step-info-image-container">
          <img
            class="step-image step-image-planet"
            :class="`step-${nextLevel.planetClass}`"
            :src="permanentImages.planets[nextLevel.planet]"
            alt="Image of a planet"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import utils from '../services/utils'
import api from '../services/api'
import Stars from './Stars'

export default {
  name: 'steps',
  computed: {
    planetName () {
      const planets = {
        BasicProgramming: 'Planet 1',
        Counting: 'Planet 2',
        Numbers: 'Planet 3',
        Recursion: 'Planet 4'
      }
      return planets[this.level]
    },
    level () {
      return this.$store.getters.getLevel
    },
    levels () {
      return this.$store.getters.getLevels
    },
    steps () {
      return this.$store.getters.getSteps
    },
    nextLevel () {
      const name = this.steps[this.steps.length - 1].nextLevel
      if (name === 'None') {
        return 'None'
      } else {
        let planetNumber = 0
        const nextLevelData = {}
        nextLevelData.name = name
        nextLevelData.firstStep = this.findFirstStep(this.levels.find((level, index) => {
          planetNumber = index + 1
          return level.name === name
        }))
        nextLevelData.planet = `planet${planetNumber}`
        nextLevelData.planetClass = `planet-${planetNumber}`
        return nextLevelData
      }
    },
    tokenId () {
      return this.$store.getters.getTokenId
    }
  },
  methods: {
    parseCamelCase: utils.parseCamelCase,
    findFirstStep (level) {
      const levelSteps = utils.orderEm(level.level)[0].name
      return levelSteps
    },
    goToRobot (level, step) {
      api.switchLevel({tokenId: this.tokenId, level: level, step: step}, (res) => {
        this.$store.dispatch('updateStats', res.body)
        this.$router.push({path: '/robot'})
      })
    }
  },
  components: {
    Stars
  },
  props: ['permanentImages']
}
</script>

<style scoped src="../css/scoped/steps.scss" lang="scss"></style>
