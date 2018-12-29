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
    <tr  v-for="continent in sortedLevels"
         :key="continent.id"
         v-show="continentIds.includes(continent.id)"
    >
      <td class="text-monospace text-left">{{levelDisplay(continent.id)}}</td>
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
export default {
  name: 'LevelStatsTable',
  props: ['levelStats', 'continentIds'],
  computed: {
    sortedLevels () {
      return Object.values(this.levelStats)
        .filter(level => level.id.length > 4)
        .sort((a, b) => {
          return a.id.slice(4) - b.id.slice(4)
        })
    }
  },
  methods: {

    levelDisplay (id) {
      const level = id.slice(4)
      if (level.startsWith('0') && level !== '0') {
        return level.slice(1)
      }

      return level
    }
  }

}
</script>

<style scoped>

</style>
