<template>
  <div class="events-list container-fluid">
    <h3 class="page-title mb-3">

      <span @click="toggleView" :class="listVisible ? 'active' : null">
        List
      </span>

      |

      <span @click="toggleView" :class="!listVisible ? 'active' : null">
        Add
      </span>

    </h3>

    <div>

      <div v-if="editMode">

        <div>

          <b-btn
            @click="toggleEditMode"

          >
            back
          </b-btn>
        </div>

        <div>
          <events-add
            init="1"
            :event="editEvent"
            :handle-submit="handleEditEvent"
          ></events-add>

        </div>

      </div>
      <div v-else>
        <div v-if="listVisible">
          <div class="alert alert-info" v-if="!eventsList.length">
            No events found
          </div>

          <table class="table table-striped table-hover" v-else>
            <thead>
            <tr>
              <th>
                title
              </th>
              <th>
                date
              </th>
              <th>
                description
              </th>
              <th>

              </th>
            </tr>
            </thead>

            <tbody>
            <events-detail
              v-for="event in adminControl.events"
              :key="event.id"
              :event="event"
              :toggle-edit-mode="toggleEditMode"
              :delete-event="deleteEvent"
            ></events-detail>
            </tbody>
          </table>
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
      listVisible: true,
      editEvent: null,
      editMode: false
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

    confirmDelete (event) {
      this.confirmDelete = true

      this.confirmDeleteEvent = event
    },

    toggleEditMode (event) {
      this.editMode = !this.editMode

      this.editEvent = event
    },

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

    handleEditEvent (event) {
      this._editEvent(event)
    },

    _editEvent (event) {
      this.adminControl.putEvent(event)

      this.listVisible = true

      this.editEvent = null

      this.editMode = false
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
