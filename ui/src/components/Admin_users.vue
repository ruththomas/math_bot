<template>
  <div>
    <h3 class="text-left my-3">Summary</h3>

    <div class="row mb-3 d-flex justify-content-between align-items-center">

      <div class="card col-xs-6 col-lg-4">
        <div class="card-body">

          <h5 class="card-title text-monospace font-weight-bold">
            {{last30DaySignups | local}}
          </h5>
          <p class="card-text">New accounts last 30 days</p>

        </div>
      </div>

      <div class="card col-xs-6 col-lg-4">
        <div class="card-body"
             title="user growth compared from previous 30 days and prev 60 days">

          <h5 :class="0 < userGrowth ? 'text-success' : 'text-danger'" class="text-monospace font-weight-bold">
            {{userGrowth | percentage}}
          </h5>
          <p>User Growth</p>

        </div>
      </div>
      <div class="card col-xs-6 col-lg-4">
        <div class="card-body">
          <h5 class="card-title text-monospace font-weight-bold" id="userCount">{{ userCount | local }}</h5>
          <p class="card-text">
            All time Users
          </p>
        </div>
      </div>

      <div class="card col-xs-6 col-lg-4">
        <div class="card-body">
          <h5 class="card-title text-monospace font-weight-bold" id="lastXDaysLoginCount">
            {{lastXDaysLoginCount | local}}
          </h5>
          <p class="card-text">
            Logins Last 7 days
          </p>
        </div>
      </div>
      <div class="card col-xs-6 col-lg-4">
        <div class="card-body">
          <h5 class="card-title text-monospace font-weight-bold" id="activeUserCount">
            {{activeUserCount | local}}
          </h5>
          <p class="card-text">
            Active Users
          </p>
        </div>

      </div>
    </div>

    <h3 class="text-left my-3">Signups over time</h3>

    <div class="row mb-3">
      <total-user-signups-chart v-if="userAccountSignups"></total-user-signups-chart>
    </div>

    <div class="row d-flex justify-content-center align-items-space-between my-1 font-weight-bold">
             <span>
                {{eventsControl.minDate | local }}
              </span>
      <span>

          <span class="m-2">
            -
          </span>
            </span>
      <span>
                {{eventsControl.maxDate | local }}
            </span>

    </div>

    <div class="row my-3">
            <span class="text-monospace font-weight-bold mx-3">
              {{signupsOverRange | local}}

            </span>

      <span>
              New Player Accounts
            </span>

    </div>

    <div class="row mb-3">
      <user-signups-chart></user-signups-chart>
    </div>

    <div class="row m-3">
      <admin-filter-date></admin-filter-date>
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
import moment from 'moment'

export default {
  name: 'AdminUsers',
  components: { TotalUserSignupsChart, AdminFilterDate, UserSignupsChart, AdminMaxLevel, AdminUserSignupCalendar },
  computed: {

    userGrowth () {
      const lastMonth = this.adminControl.userAccountSignups
        .filter(item => this.filterSignups(item, moment().subtract(90, 'days').toDate(), moment().subtract(60, 'days').toDate()))
        .reduce((accum, item) => accum + item.signups, 0)

      return ((this.last30DaySignups - lastMonth) / (lastMonth))
    },

    last30DaySignups () {
      const minDate = new Date(new Date().getTime() - 1000 * 60 * 60 * 24 * 30)
      return this.adminControl.userAccountSignups
        .filter(item => this.filterSignups(item, minDate, new Date()))
        .reduce((accum, item) => accum + item.signups, 0)
    },

    adminControl () {
      return this.$store.getters.getAdminControl
    },
    userCount () {
      return this.adminControl.userCount
    },

    activeUserCount () {
      return this.adminControl.activeUserCount
    },

    lastXDaysLoginCount () {
      return this.adminControl.lastXDaysLoginCount
    },
    userAccountSignups () {
      return this.adminControl.userAccountSignups
    },
    eventsControl () {
      return this.$store.getters.getEventsControl
    },

    signupsOverRange () {
      const d = this.adminControl.userAccountSignups.filter(item => this.filterSignups(item, this.eventsControl.minDate, this.eventsControl.maxDate))
      const signups = d.map(i => i.signups)
      return signups.reduce((accum, cur) => accum + cur, 0)
    }

  },
  methods: {

    filterSignups (item, minDate, maxDate) {
      const { _id: { year, month, day } } = item

      const date = new Date(year, month, day)

      return minDate <= date && date <= maxDate
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

    lastXDaysLoginCount (newVal, oldVal) {
      const userCount = document.getElementById('lastXDaysLoginCount')

      if (!userCount) return
      const klass = newVal > oldVal ? 'increment' : 'decrement'

      userCount.classList.add(klass)

      setTimeout(() => userCount.classList.remove(klass), 2000)
    }
  }
}
</script>

<style scoped>

  #userCount, #lastXDaysLoginCount {

    transition: all 0.3s ease-in-out;
  }

  .increment {

    color: limegreen;
  }

  .decrement {

    color: red;
  }

  .card {
    width: 15rem;
    height: 10rem;
  }
</style>
