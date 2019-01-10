<template>

  <div class="admin-request container">
    <div class="row d-flex justify-content-end align-items-center">
     <user-gravatar></user-gravatar>
    </div>

    <p v-if="isAdmin" class="mb-3">
      You're an admin user
    </p>

    <div class="row d-flex justify-content-center align-items-center">
      <div class="card">
        <div class="card-header">
          <b-btn
            :disabled="Boolean(requestAdminResult) || loadingRequest"
            class="btn btn-block"
            @click="handleAdminRequest"
          >
            Request Admin Privileges
          </b-btn>
        </div>
        <div class="card-body" v-if="Boolean(requestAdminResult) || loadingRequest">
          <h5 v-if="loadingRequest">
            Beep boop...
          </h5>
          <h5>
            {{requestAdminResult}}
          </h5>
        </div>
      </div>
    </div>

  </div>
</template>

<script>

import UserGravatar from './UserGravatar'
import api from '../services/api'

export default {

  name: 'RequestAdmin',
  components: {UserGravatar},
  mounted () {
    this.adminControl.openSocket().then(adminUser => {
      this.isAdmin = true
    }).catch(nonAdminError => {
      // non admin user

      this.isAdmin = false
    })
  },
  data () {
    return {
      isAdmin: false,
      loadingRequest: false,
      requestAdminResult: null
    }
  },
  computed: {
    adminControl () {
      return this.$store.getters.getAdminControl
    }
  },
  methods: {

    handleAdminRequest () {
      this.loadingRequest = true
      api.requestAdmin(this.$store.state.auth.userProfile.sub || this.$store.state.auth.userProfile.user_id)
        .then(result => {
          this.requestAdminResult = result
          return result
        })
        .finally(result => {
          this.loadingRequest = false
        })
    },
    goHome () {
      this.$router.push('/')
    }
  }
}
</script>

<style scoped lang="scss">

  .admin-request {

    padding: 100px 0;

    .card {

      min-width: 30rem;
      max-width: 30rem;
    }
  }
</style>
