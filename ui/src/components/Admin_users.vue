<template>
  <div>
    <h3 class="text-left my-3">Summary</h3>

    <div class="row mb-3 d-flex justify-content-between align-items-center">

      <div class="card">
        <div class="card-header">Users</div>
        <div class="card-body">

          <h5 class="card-title text-monospace font-weight-bold" id="userCount">{{ userCount.toLocaleString() }}</h5>
          <p class="card-text">
            All time
          </p>
        </div>
      </div>

      <div class="card">
        <div class="card-header">Logins</div>
        <div class="card-body">
          <h5 class="card-title text-monospace font-weight-bold" id="last7DaysLoginCount">
            {{last7DaysLoginCount.toLocaleString()}}
          </h5>
          <p class="card-text">
            Last 7 days
          </p>
        </div>
      </div>
      <div class="card" style="width: 18rem;">
        <div class="card-header">
          Active Users
        </div>
        <div class="card-body">
          <h5 class="card-title text-monospace font-weight-bold" id="activeUserCount">
            {{activeUserCount.toLocaleString()}}
          </h5>
          <p class="card-text">
            Right Now
          </p>
        </div>

      </div>
    </div>

    <div class="row">
      <admin-filter-date></admin-filter-date>
    </div>

    <h3 class="text-left my-3">Signups over time</h3>

      <div class="row d-flex justify-content-center align-items-space-between my-1 font-weight-bold">
             <span>
                {{eventsControl.minDate.toLocaleString()}}
              </span>
        <span>
                  -
            </span>
        <span>
                {{eventsControl.maxDate.toLocaleString()}}
            </span>

      </div>

      <div class="row">
            <span class="text-monospace font-weight-bold mx-3">
              {{signupsOverRange.toLocaleString()}}

            </span>

        <span>
              Signups
            </span>

      </div>

    <div class="row mb-3">
      <user-signups-chart></user-signups-chart>
    </div>
    <div class="row mb-3">
      <total-user-signups-chart v-if="userAccountSignups"></total-user-signups-chart>
    </div>

    <div class="row mb-3">
      <admin-user-signup-calendar v-if="userAccountSignups"></admin-user-signup-calendar>
    </div>
  </div>
</template>

<script>
import AdminMaxLevel from './Admin_max_level'
import UserSignupsChart from './Admin_user_signups_chart'
import AdminUserSignupCalendar from './Admin_user_signup_calandar'
import AdminFilterDate from './AdminFilterDate'
import TotalUserSignupsChart from './Admin_total_signups_chart'

export default {
  name: 'AdminUsers',
  components: { TotalUserSignupsChart, AdminFilterDate, UserSignupsChart, AdminMaxLevel, AdminUserSignupCalendar },
  computed: {

    adminControl () {
      return this.$store.getters.getAdminControl
    },
    userCount () {
      return this.adminControl.userCount
    },

    activeUserCount () {
      return this.adminControl.activeUserCount
    },

    last7DaysLoginCount () {
      return this.adminControl.last7DaysLoginCount
    },
    userAccountSignups () {
      return this.adminControl.userAccountSignups
    },
    eventsControl () {
      return this.$store.getters.getEventsControl
    },

    signupsOverRange () {
      const d = this.adminControl.userAccountSignups.filter(item => {
        const { _id: { year, month, day } } = item

        const date = new Date(year, month, day)

        return this.eventsControl.minDate <= date && date <= this.eventsControl.maxDate
      })
      const signups = d.map(i => i.signups)
      return signups.reduce((accum, cur) => accum + cur, 0)
    }

  },
  watch: {

    userCount (newVal, oldVal) {
      const userCount = document.getElementById('userCount')

      if (!userCount) return
      const klass = newVal > oldVal ? 'increment' : 'decrement'

      userCount.classList.add(klass)

      setTimeout(() => userCount.classList.remove(klass), 2000)
    },

    activeUserCount (newVal, oldVal) {
      const userCount = document.getElementById('activeUserCount')

      if (!userCount) return
      const klass = newVal > oldVal ? 'increment' : 'decrement'

      userCount.classList.add(klass)

      setTimeout(() => userCount.classList.remove(klass), 2000)
    },

    last7DaysLoginCount (newVal, oldVal) {
      const userCount = document.getElementById('last7DaysLoginCount')

      if (!userCount) return
      const klass = newVal > oldVal ? 'increment' : 'decrement'

      userCount.classList.add(klass)

      setTimeout(() => userCount.classList.remove(klass), 2000)
    }
  }
}
</script>

<style scoped>

  #userCount, #last7DaysLoginCount {

    transition: all 0.3s ease-in-out;
  }

  .increment {

    color: limegreen;
  }

  .decrement {

    color: red;
  }

  .card {

    min-width: 10rem;
    max-width: 10rem;
  }

</style>
