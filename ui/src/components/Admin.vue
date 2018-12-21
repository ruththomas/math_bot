<template>
  <div class="container-fluid admin">
      <div class="row my-3">
        <user-gravatar></user-gravatar>
      </div>

      <div class="container">

        <div class="row">
          <div class="col-sm-3">

            <ul class="list-group">
              <li class="list-group-item" @click="updateActive('users')"
                  :class="activeDisplay === 'users' ? 'active' : null">Users
              </li>
              <li class="list-group-item" @click="updateActive('levels')"
                  :class="activeDisplay === 'levels' ? 'active' : null">Levels
              </li>
              <li class="list-group-item" @click="updateActive('events')"
                  :class="activeDisplay === 'events' ? 'active' : null">
                Events
              </li>
            </ul>
          </div>
          <div class="col-sm-9">
            <div class="row mb-3">

            </div>

            <div v-if="activeDisplay === 'levels'">

              <level-stats></level-stats>

            </div>

            <div v-if="activeDisplay === 'users'">
              <admin-users v-if="userAccountSignups.length"></admin-users>
            </div>

            <div v-if="activeDisplay === 'events'">
              <events-list></events-list>
            </div>
          </div>
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
import UserSignupsChart from './Admin_user_signups_chart'
import LevelStats from './Admin_level_stats'
import AdminUsers from './Admin_users'
import EventsList from './Events_list'

// refresh data every x seconds
const fetchDataInterval = 30 // seconds

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
        this.adminControl.getLastWeekLogins(),
        // this.adminControl.getCurrentPath(),
        this.adminControl.getMaxLevelStats(),
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

    last7DaysLoginCount () {
      return this.adminControl.last7DaysLoginCount
    },

    adminControl () {
      return this.$store.getters.getAdminControl
    },
    tokenId () {
      return this.$store.getters.getTokenId
    },
    levelControl () {
      return this.$store.getters.getLevelControl
    }
  },
  components: {
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
