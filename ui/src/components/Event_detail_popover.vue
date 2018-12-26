<template>
  <div class="detail-popover">
      <b-btn :id="id" @click="show = !show" class="btn-sm">
        Details
      </b-btn>
      <b-popover :show.sync="show" :target="id" :title="_title">

        <div v-for="(value, key) in event" :key="'event_detail/' + key" class="container text-white">
          <div class="row font-weight-bold">
            {{key}}
          </div>
          <div class="row" style="min-width: 55rem;">
            {{value}}
          </div>
        </div>
      </b-popover>
    </div>
</template>
<script>
export default {
  name: 'EventDetailPopover',
  computed: {
    _title () {
      return `${this.title}, ${this.date.toLocaleDateString()}`
    }
  },
  data () {
    return {
      show: false
    }
  },
  props: ['event', 'title', 'id', 'date']
}
</script>
<style scoped lang="scss">

</style>
