<template>
<b-modal
  id="star-system-congrats-modal"
  ref="star-system-congrats-modal"
  v-if="nextStarSystem"
>
  <div slot="modal-header">
    <img
      class="dialog-button close-congrats"
      @click="quit" :src="permanentImages.buttons.xButton" data-toggle="tooltip" title="Close">
  </div>
  <div class="text-minor">
    <div>You unlocked star system {{nextStarSystem.stats.name}}</div>
    <div>{{parseCamelCase(nextStarSystem.stats.name)}}!</div>
  </div>
  <div class="congrats-icon">
    <img :class="nextStarSystem.stats.name" :src="permanentImages.instructionsRobot">
  </div>
  <stars :continent-id="congratsData.path"></stars>
  <div class="text-minor">
    <div>Tell your friends!</div>
  </div>
  <social-sharing :size="'3rem'"></social-sharing>
  <div slot="modal-footer" class="row" style="width: 100%; display: flex; justify-content: space-between;">
    <b-btn
      size="md"
      style="width: 40%;"
      class="float-right"
      variant="primary"
      @click="quit"
    >
      Quit
    </b-btn>
    <b-btn
      size="md"
      style="width: 40%"
      class="float-right"
      variant="primary"
      :show="getCongratsData"
      @click="next"
    >
      Next level
    </b-btn>
  </div>
</b-modal>
</template>

<script>
import utils from '../services/utils'
import Stars from './Stars'
import SocialSharing from './Social_sharing'
export default {
  name: 'Star_system_congrats',

  computed: {
    levelControl () {
      return this.$store.getters.getLevelControl
    },
    nextStarSystem () {
      return this.levelControl.getNextStarSystem()
    },
    permanentImages () {
      return this.$store.getters.getPermanentImages
    },
    runCompiled () {
      return this.levelControl.runCompiled
    }
  },
  data () {
    return {
      congratsData: {
        path: '00000',
        builtContinent: {
          name: ''
        }
      }
    }
  },
  methods: {
    parseCamelCase: utils.parseCamelCase,
    quit () {
      this.runCompiled.quit()
    },
    next () {
      this.runCompiled.initializeNextStep()
      this.$router.push({path: '/robot'})
    },
    closeCongrats () {
      this.runCompiled.stayOnLevel()
    },
    getCongratsData () {
      const data = this.$route.query
      this.congratsData = data.pathAndContinent
      this.$router.push({query: {}})
    }
  },
  components: {
    Stars,
    SocialSharing
  }
}
</script>

<style scoped lang="scss">

</style>
