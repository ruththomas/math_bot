<template>
  <table class="table">
    <thead>
    <tr>
      <th>
        level
      </th>
      <th class="text-right text-capitalize">{{'timesPlayed' | camelCase}}</th>
      <th class="text-right text-capitalize">{{'wins' | camelCase}}</th>
      <th class="text-right text-capitalize">{{'winPercentage' | camelCase}}</th>
      <th class="text-right text-capitalize">{{'winsAvg' | camelCase}}</th>
      <th class="text-right text-capitalize">{{'winsMax' | camelCase}}</th>
      <th class="text-right text-capitalize">{{'timesPlayedMax' | camelCase}}</th>
      <th class="text-right text-capitalize">{{'timesPlayedAvg' | camelCase}}</th>
    </tr>
    </thead>
    <tbody>
    <tr  v-for="continent in sortByLevel()"
         :key="continent.id"
         v-show="continentIds.includes(continent.id)"
    >
      <td>{{continent.id.slice(4)}}</td>
      <td class="text-monospace text-right">{{continent.timesPlayed  | local}}</td>
      <td class="text-monospace text-right">{{continent.wins | local}}</td>
      <td class="text-monospace text-right">{{continent.wins /  continent.timesPlayed | percentage}}</td>
      <td class="text-monospace text-right">{{continent.winsAvg | int | local}}</td>
      <td class="text-monospace text-right">{{continent.winsMax | local}}</td>
      <td class="text-monospace text-right">{{continent.timesPlayedMax | local}}</td>
      <td class="text-monospace text-right">{{continent.timesPlayedAvg | int  | local}}</td>
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
