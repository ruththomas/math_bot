<template>
  <div class="container-fluid admin">
      <div class="row my-3">
        <user-gravatar></user-gravatar>
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
              <div v-else>
                <h1>Doing Science...</h1>
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

// refresh data every x seconds
const fetchDataInterval = 60 // seconds

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
  },
  methods: {

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
    }
  },
  components: {
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

<style scoped lang="scss">

  .admin {

    height: 100vh;
    overflow-y: scroll;
    background: var(--light);
  }

  .star-system-container {

    height: 50vh;
  }

  .list-group-item {

    cursor: pointer;
  }

</style>
