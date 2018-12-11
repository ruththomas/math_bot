<template>
  <div>


   <div class="card">
     <div class="card-header">
      <h3> Signups over Time</h3>
     </div>
     <div class="card-body">
       <full-calendar class="user-signup-calendar"
                      :events="data"
                      @changeMonth="changeMonth"
                      @eventClick="eventClick"
                      @dayClick="dayClick"
                      @moreClick="moreClick"
       >

       </full-calendar>
     </div>
   </div>
  </div>
</template>
<script>

function mapSignupsToCalendar (signup) {
  const {_id: {month, day, year}, signups} = signup

  const date = new Date(year, month, day)

  return {
    title: signups,
    start: date,
    end: date
  }
}
export default {
  data () {
    return {
      name: 'AdminUserSignupCalendar'
      // fcEvents : demoEvents
    }
  },
  computed: {
    adminControl () {
      return this.$store.getters.getAdminControl
    },

    data () {
      return this.adminControl.userAccountSignups.map(mapSignupsToCalendar)
    }
  },
  methods: {
    'changeMonth' (start, end, current) {
      // console.log('changeMonth', start, end, current)
    },
    'eventClick' (event, jsEvent, pos) {
      // console.log('eventClick', event, jsEvent, pos)
    },
    'dayClick' (day, jsEvent) {
      // console.log('dayClick', day, jsEvent)
    },
    'moreClick' (day, events, jsEvent) {
      // console.log('moreCLick', day, events, jsEvent)
    }
  },
  components: {
    'full-calendar': require('vue-fullcalendar')
  }
}
</script>
<style lang='scss'>
</style>
