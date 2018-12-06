<template>
  <div class="admin" data-aos="zoom-in">
    <div class="container-fluid">
      <div class="row my-3">
        <user-gravatar></user-gravatar>
      </div>

      <div class="container">

        <div class="row">
          <div class="col-sm-3">

            <ul class="list-group">
              <li class="list-group-item" @click="updateActive('users')" :class="activeDisplay === 'users' ? 'active' : null">Users</li>
              <li class="list-group-item" @click="updateActive('levels')" :class="activeDisplay === 'levels' ? 'active' : null">Levels</li>
            </ul>
          </div>
          <div class="col-sm-9">
            <div class="row mb-3">
              <b-btn
                class="text-capitalize"
                @click="unlockLevels" :disabled="levelControl.unlockedAllLevels">
                {{levelControl.unlockedAllLevels ? "Levels unlocked!" : 'unlock all levels'}}
              </b-btn>
            </div>

            <div v-if="activeDisplay === 'levels'">

              <star_system_admin></star_system_admin>

            </div>

            <div v-if="activeDisplay === 'users' && userAccountSignups.length">

              <div class="row mb-3 d-flex justify-content-between align-items-center">

                <div class="card">
                  <div class="card-header">Users</div>
                  <div class="card-body">

                    <h5 class="card-title" id="userCount">{{ userCount.toLocaleString() }}</h5>
                    <p class="card-text">
                      All time
                    </p>
                  </div>
                </div>

                <div class="card">
                  <div class="card-header">Logins</div>
                  <div class="card-body">
                    <h5 class="card-title" id="last7DaysLoginCount">
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
                    <h5 class="card-title" id="activeUserCount">
                      {{activeUserCount.toLocaleString()}}
                    </h5>
                    <p class="card-text">
                      Right Now
                    </p>
                  </div>

                </div>
              </div>

              <div class="row mb-3">
                <line-chart
                  :data="userAccountSignups"
                ></line-chart>
              </div>

              <div class="row" v-if="adminControl.currentPath.length > 0">
                <div class="col-xs-12">
                  <admin_curent_path></admin_curent_path>
                </div>
              </div>

              <div class="row">
                <div class="col-md-8 offset-md-2">
                  <user-signup-calendar></user-signup-calendar>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
import UserSignupCalendar from './UserSignupCalendar'
import LineChart from './LineChart'
import UserGravatar from './UserGravatar'
import SplashScreen from './Splash_screen'
import Star_system from './Star_system'
import Star_system_admin from './Star_system_admin'
import Admin_curent_path from './Admin_curent_path'

export default {
  name: 'Admin',

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
  },
  data () {
    return {
      userCountInterval: null,
      activeDisplay: 'users',
    }
  },
  mounted () {
    this.handleRoute()
  },
  methods: {

    updateActive (_active) {
      this.activeDisplay = _active
    },

    unlockLevels () {
      this.levelControl.getUnlock()
    },

    _fetchData () {
      return Promise.all([
        this.adminControl.getDailySignups(),
        this.adminControl.getUserCount(),
        this.adminControl.getActiveUserCount(),
        this.adminControl.getLastWeekLogins(),
        this.adminControl.getCurrentPath()
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
          }, 1000 * 60)
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
    Admin_curent_path,
    Star_system_admin,
    Star_system,
    UserGravatar,
    LineChart,
    UserSignupCalendar,
    SplashScreen

  }
}
</script>

<style scoped lang="scss">

  .card {

    min-width: 10rem;
    max-width: 10rem;
  }

  .admin {

    height: 100vh;
    overflow-y: scroll;
    background: var(--light);
  }

  #userCount, #last7DaysLoginCount {

    transition: all 0.3s ease-in-out;
  }

  #userCount.increment, #last7DaysLoginCount.increment {

    color: limegreen;
  }

  #userCount.decrement, #last7DaysLoginCount.decrement {

    color: red;
  }

  .star-system-container {

    height: 50vh;
  }

  .list-group-item {

    cursor: pointer;
  }

</style>
