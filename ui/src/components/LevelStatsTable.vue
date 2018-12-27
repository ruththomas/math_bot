<template>
  <table class="table">
    <thead>
    <tr>
      <th class="text-left text-capitalize">level</th>
      <th
        class="text-right text-capitalize">{{'timesPlayed' | camelCase}}</th>

      <th
        v-b-tooltip.hover title="Sum of wins on level"
        class="text-right text-capitalize">{{'wins' | camelCase}}</th>
      <th
        v-b-tooltip.hover title="Users Win X % of time"
        class="text-right text-capitalize">{{'winPercentage' | camelCase}}</th>
      <th
        v-b-tooltip.hover title="Average times a user plays the level"
        class="text-right text-capitalize">{{'timesPlayedAvg' | camelCase}}</th>
      <th
        v-b-tooltip.hover title="On avg. user completes level X times"
        class="text-right text-capitalize">{{'winsAvg' | camelCase}}</th>
      <th
        v-b-tooltip.hover title="Max amout of times a user won level"
        class="text-right text-capitalize">{{'winsMax' | camelCase}}</th>
      <th
        v-b-tooltip.hover title="Maximum times a user played this level"
        class="text-right text-capitalize">{{'timesPlayedMax' | camelCase}}</th>
    </tr>
    </thead>
    <tbody>
    <tr  v-for="continent in sortByLevel()"
         :key="continent.id"
         v-show="continentIds.includes(continent.id)"
    >
      <td class="text-monospace text-left">{{continent.id.slice(3)}}</td>
      <td class="text-monospace text-right">{{continent.timesPlayed  | local}}</td>
      <td class="text-monospace text-right">{{continent.wins | local}}</td>
      <td class="text-monospace text-right">{{continent.wins /  continent.timesPlayed | percentage}}</td>
      <td class="text-monospace text-right">{{continent.timesPlayedAvg | int  | local}}</td>
      <td class="text-monospace text-right">{{continent.winsAvg | int | local}}</td>
      <td class="text-monospace text-right">{{continent.winsMax | local}}</td>
      <td class="text-monospace text-right">{{continent.timesPlayedMax | local}}</td>
    </tr>
    </tbody>
  </table>
</template>

<script>
import utils from '../services/utils'
export default {
  name: 'LevelStatsTable',
  props: ['levelStats', 'continentIds'],
  methods: {
    parseCamelCase: utils.parseCamelCase,

    sortByLevel () {
      return Object.values(this.levelStats).sort((a, b) => {
        return a.id.slice(4) - b.id.slice(4)
      })
    }
  }
}
</script>

<style scoped>

</style>
