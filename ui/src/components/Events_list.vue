<template>
  <div class="events-list container-fluid">
    <h3 class="page-title m-3">

      <span @click="toggleView" class="mx-2" :class="listVisible ? 'active' : null">
        <i class="fa fa-list"></i>
        List
      </span>
      <span @click="toggleView" class="mx-2" :class="!listVisible ? 'active' : null">
        <i class="fa fa-plus-circle"></i>
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
          <div class="alert alert-light" v-if="!eventsList.length">
            No events found
          </div>
          <div v-else>
            <div class="row m-3">
              <input
                style="max-width: 15rem;"
                placeholder="filter by title"
                type="text" v-model="userInput.filterEventText"  class="form-control"/>
            </div>
          <table class="my-3 table table-striped table-hover" >
            <thead>
          <tr>
          </tr>
            <tr>
              <th>
                title
              </th>
              <th>
                date
              </th>
              <th colspan="2"></th>
            </tr>
            </thead>

            <tbody>
            <events-detail
              v-for="event in visibleEventsList"
              :key="event.id"
              :event="event"
              :toggle-edit-mode="toggleEditMode"
              :delete-event="deleteEvent"
            ></events-detail>
            </tbody>
          </table>
          </div>
        </div>

        <events-add v-else
                    :handle-submit="postEvent"
        ></events-add>
      </div>
    </div>

  </div>
</template>

<script>

import EventsDetail from './Events_detail'
import EventsAdd from './Events_add'
import ConfirmDeleteEvent from './ConfirmDeleteEvent'

export default {
  name: 'Events_list',
  components: { ConfirmDeleteEvent, EventsAdd, EventsDetail },
  data () {
    return {
      listVisible: true,
      editEvent: null,
      editMode: false,
      userInput: {
        filterEventText: ''
      }
    }
  },
  computed: {

    visibleEventsList () {
      const regex = new RegExp(this.userInput.filterEventText, 'gi')
      return this.eventsList.filter(evt => {
        return evt.title.match(regex)
      })
    },
    eventsList () {
      return this.adminControl.events
    },

    adminControl () {
      return this.$store.getters.getAdminControl
    }

  },
  methods: {

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
    border-bottom: 2px solid transparent;
    transition: border-bottom-color .3s ease;
  }

  span.active {

    color: var(--success);
    border-bottom-color: var(--success)
  }

  span:hover {

    border-bottom-color: var(--info);
  }

  th {

    text-transform: capitalize;
  }

</style>
