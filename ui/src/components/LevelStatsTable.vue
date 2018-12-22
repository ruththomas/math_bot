<template>
  <table class="table">
    <thead>
    <tr>
      <th>
        level
      </th>
      <th class="text-right text-capitalize">{{parseCamelCase('timesPlayed')}}</th>
      <th class="text-right text-capitalize">{{parseCamelCase('timesPlayedAvg')}}</th>
      <th class="text-right text-capitalize">{{parseCamelCase('timesPlayedMax')}}</th>
      <th class="text-right text-capitalize">{{parseCamelCase('wins')}}</th>
      <th class="text-right text-capitalize">{{parseCamelCase('winsAvg')}}</th>
      <th class="text-right text-capitalize">{{parseCamelCase('winsMax')}}</th>
    </tr>
    </thead>
    <tbody>
    <tr  v-for="continent in sortByLevel()"
         :key="continent.id"
         v-show="continentIds.includes(continent.id)"
    >
      <td>{{continent.id.slice(4)}}</td>
      <td class="text-monospace text-right">{{continent.timesPlayed}}</td>
      <td class="text-monospace text-right">{{continent.timesPlayedAvg}}</td>
      <td class="text-monospace text-right">{{continent.timesPlayedMax}}</td>
      <td class="text-monospace text-right">{{continent.wins}}</td>
      <td class="text-monospace text-right">{{continent.winsAvg}}</td>
      <td class="text-monospace text-right">{{continent.winsMax}}</td>
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
