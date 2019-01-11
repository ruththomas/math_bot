<template>
  <div class="container-fluid admin"
       :class="adminTheme"
  >
    <div class="row my-3">
      <user-gravatar></user-gravatar>
    </div>

    <div class="row">
      <toggle-button

        @change="changeAdminTheme"
        :value="adminTheme === 'dark'"
        color="#82C7EB"
        :sync="true"
        :labels="{checked: 'Dark', unchecked: 'Light'}"

      />
    </div>
    <div class="row">

      <admin-nav
        :update-active="updateActive"
        :active-display="activeDisplay"
      ></admin-nav>
      <div class="col-sm-9">

        <level-stats v-if="activeDisplay === 'levels'"></level-stats>
        <div v-if="activeDisplay === 'users'">
          <admin-users v-if="userAccountSignups.length"></admin-users>
          <div v-else class="container">
            <donut-spinner></donut-spinner>
          </div>
        </div>
        <events-list v-if="activeDisplay === 'events'"></events-list>
      </div>
    </div>
  </div>

</template>

<script>
import UserSignupCalendar from './Admin_user_signup_calandar'
import UserGravatar from './UserGravatar'
import SplashScreen from './Splash_screen'
import StarSystem from './Star_system'
import AdminCurentPath from './Admin_max_level'
import UserSignupsChart from './UserSignupsPerDayChart'
import LevelStats from './Admin_level_stats'
import AdminUsers from './Admin_users'
import EventsList from './Events_list'
import AdminNav from './AdminNav'
import DonutSpinner from './DonutSpinner'

// refresh data every x seconds
const fetchDataInterval = 60 // seconds

const ADMIN_THEME_KEY = '@@ADMIN_THEME@@'

// http://themes.djavaui.com/blankon-fullpack-admin-theme/production/admin/html/chart-c3js.html

export default {
  name: 'Admin',

  data () {
    return {
      userCountInterval: null,
      activeDisplay: 'users'
    }
  },
  mounted () {
    this.handleRoute()
    this.loadTheme()
  },
  methods: {

    changeAdminTheme ({ value }) {
      this._changeAdminTheme(value ? 'dark' : 'light')
    },

    _changeAdminTheme (theme) {
      const validThemes = ['light', 'dark']

      if (!validThemes.includes(theme)) {
        throw new Error('invalid request')
      }
      return this.$store.dispatch('changeAdminTheme', theme).then(() => {
        localStorage.setItem(ADMIN_THEME_KEY, theme)
      })
    },
    loadTheme () {
      let theme = localStorage.getItem(ADMIN_THEME_KEY) || 'light'

      this._changeAdminTheme(theme)
    },

    // toggle view levels & users
    updateActive (_active) {
      this.activeDisplay = _active
    },

    _fetchData () {
      return Promise.all([
        this.adminControl.getDailySignups(),
        this.adminControl.getUserCount(),
        this.adminControl.getActiveUserCount(),
        this.adminControl.getLoginsLastXDays(7),
        this.adminControl.getEvents()
      ])
    },
    handleRoute () {
      this.adminControl.openSocket().then(result => {
        if (result !== 'success') {
          console.error('error', result)
          // todo: handle error
          return false
        }

        this._fetchData().then(() => {
          this.userCountInterval = setInterval(() => {
            this._fetchData()
          }, 1000 * fetchDataInterval)
        })
      }).catch(err => {
        console.error(err)

        this.$router.push('/adminRequest')
      })
    },

    goHome () {
      this.$router.push('/')
    }
  },
  beforeDestroy () {
    clearInterval(this.userCountInterval)

    this.userCountInterval = null
  },
  computed: {

    userAccountSignups () {
      return this.adminControl.userAccountSignups
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

    adminControl () {
      return this.$store.getters.getAdminControl
    },
    levelControl () {
      return this.$store.getters.getLevelControl
    },
    adminTheme () {
      return this.$store.getters.getAdminTheme
    }
  },
  components: {
    DonutSpinner,
    AdminNav,
    EventsList,
    AdminUsers,
    UserSignupsChart,
    StarSystem,
    AdminCurentPath,
    UserGravatar,
    UserSignupCalendar,
    SplashScreen,
    LevelStats

  }
}
</script>

<style lang="scss">

  @import '~c3/c3.min.css';

  .admin {

    height: 100vh;
    overflow-y: scroll;
    background: var(--light);
  }

  .c3-tooltip {
    color: black;
  }

  .admin.dark {

    background-color: #222;
    color: var(--light);

    .table thead th {

      border-bottom: 2px solid #444;

    }

    .table td {

      border-top: 1px solid #444;

    }

    .card {
      background-color: #303030;
      background-clip: border-box;
      border: 1px solid rgba(0, 0, 0, 0.125);

    }

    .card-header {
      padding: 0.75rem 1.25rem;
      margin-bottom: 0;
      color: inherit;
      background-color: #444;
      border-bottom: 1px solid rgba(0, 0, 0, 0.125);
    }

    .c3 text {
      -webkit-user-select: none;
      -moz-user-select: none;
      user-select: none;
      fill: var(--light);
    }

  }

  .star-system-container {

    height: 50vh;
  }

  .list-group-item {

    cursor: pointer;
  }

</style>
