<template>

  <div class="events-detail">
    <div class="row d-flex justify-content-end align-items-center">

      <b-btn
        v-if="!editMode"
        @click="toggleEditMode"

      >
        Edit
      </b-btn>
      <b-btn
        v-if="editMode"
        @click="toggleEditMode"

      >
        Details
      </b-btn>

      <div v-if="confirmDelete">

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
      </div>

      <img
        v-else
        @click="_confirmDelete"
        class="trash noDrag dialog-button mx-2"
        :src="permanentImages.buttons.trashButton"
      />

    </div>

    <div>
      <div v-if="editMode">

        <events-add
          init="1"
          :event="event"
          :handle-submit="handleEditEvent"></events-add>

      </div>
      <div v-else>
        <events-add init="1" :event="event" :read-only="Boolean(1)"></events-add>
      </div>
    </div>

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

  .event-title {

    letter-spacing: .4rem;
    font-weight: bold;
  }
</style>
