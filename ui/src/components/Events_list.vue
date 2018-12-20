<template>
  <div class="events-list container-fluid">
    <h3 class="page-title mb-3">

      <span @click="toggleView" :class="listVisible ? 'active' : null">
        Event List
      </span>

      |

      <span @click="toggleView" :class="!listVisible ? 'active' : null">
        Add Event
      </span>

    </h3>

    <div>

      <div v-if="listVisible">
        <div class="alert alert-info" v-if="!eventsList.length">
          No events found
        </div>
        <events-detail
          :handle-submit="editEvent"
          :event="_event"
          :delete-event="deleteEvent"
          :edit-event="editEvent"
        ></events-detail>
      </div>

      <div v-else>

        <div class="card">
          <div class="card-body">
            <events-add
              :handle-submit="postEvent"></events-add>
          </div>
        </div>
      </div>
    </div>

  </div>
</template>

<script>

import EventsDetail from './Events_detail'
import EventsAdd from './Events_add'

export default {
  name: 'Events_list',
  components: { EventsAdd, EventsDetail },
  data () {
    return {
      listVisible: true
    }
  },
  computed: {
    eventsList () {
      return this.adminControl.events
    },

    adminControl () {
      return this.$store.getters.getAdminControl
    }

  },
  methods: {

    getEvents () {
      return this.adminControl.getEvents()
    },
    toggleView () {
      this.listVisible = !this.listVisible
    },

    deleteEvent (event) {
      this.adminControl.delEvent(event)
    },
    postEvent (event) {
      this.adminControl.postEvent(event)

      this.listVisible = true
    },

    editEvent (event) {
      this.adminControl.putEvent(event)
    }
  }
}
</script>

<style scoped>

  .events-list {

    background: var(--light);
    overflow-y: scroll;
    height: 100vh;
  }

  span {

    cursor: pointer;
    transition: border-bottom-color .3s ease;
  }

  span.active {

    color: var(--success);
    border-bottom: 2px solid var(--success);
  }

  span:hover {

    border-bottom: 2px solid var(--info);
  }

</style>
