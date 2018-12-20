<template>

  <div>

    <table class="table table-striped table-hover">
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
      <tbody v-if="editMode">
      <tr v-for="_event in adminControl.events" :key="_event.date + _event.description" class="events-detail">

        <td>

          <b-btn
            @click="toggleEditMode"

          >
            Details
          </b-btn>

          <img
            @click="_confirmDelete"
            class="trash noDrag dialog-button mx-2"
            :src="permanentImages.buttons.trashButton"
          />

          <b-btn
            @click="confirmDelete = false"
          >
            Cancel
          </b-btn>
          <b-btn
            @click="handleDeleteEvent"
            variant="danger">
            Confirm Deletion
          </b-btn>
        </td>

        <td colspan="3">
          <events-add
            init="1"
            :event="event"
            :handle-submit="handleEditEvent"></events-add>

        </td>

      </tr>
      </tbody>
      <tbody v-else>
      <tr v-for="event in adminControl.events" :key="event.date + event.description">
        <td>{{event.title}}</td>
        <td>{{new Date(event.date).toLocaleString()}}</td>
        <td>{{event.description}}</td>
        <td>
          <b-btn
            @click="toggleEditMode"
          >
            Edit
          </b-btn>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import EventsAdd from './Events_add'

export default {
  name: 'Events_detail',
  components: { EventsAdd },
  props: ['event', 'deleteEvent', 'editEvent', 'handleSubmit'],
  data () {
    return {
      editMode: false,
      confirmDelete: false
    }
  },
  computed: {
    permanentImages () {
      return this.$store.getters.getPermanentImages
    },
    adminControl () {
      return this.$store.getters.getAdminControl
    }
  },
  methods: {

    _confirmDelete (e) {
      e.preventDefault()

      this.confirmDelete = !this.confirmDelete
    },

    handleDeleteEvent (e) {
      e.preventDefault()

      this.deleteEvent(this.event)

      this.editMode = false
    },
    handleEditEvent (event) {
      this.handleSubmit(event)

      this.editMode = false
    },
    toggleEditMode () {
      this.editMode = !this.editMode
    }
  }
}
</script>

<style scoped>

</style>
