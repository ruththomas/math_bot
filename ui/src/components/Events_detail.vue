<template>

  <div class="events-detail">

    <div class="row d-flex justify-content-end" v-if="editMode">
      <b-btn @click="editMode = false">
        x
      </b-btn>
    </div>

    <div>
      <div v-if="editMode">

        <events-add
          init="SI"
          :event="event"
          :handle-submit="handleEditEvent"></events-add>

      </div>
      <div v-else>
        <h3>{{event.title}}</h3>
        {{JSON.stringify(event)}}
      </div>
    </div>

    <div class="row d-flex justify-content-end">
      <div class="btn-group">

        <b-btn
          v-if="!editMode"
          @click="toggleEditMode"

        >
          Edit Event
        </b-btn>
        <b-btn
          v-if="editMode"
          @click="toggleEditMode"

        >
          Close
        </b-btn>

        <b-btn
          @click="handleDeleteEvent"
          variant="danger"
        >
          Delete
        </b-btn>

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
      editMode: false
    }
  },
  methods: {

    handleDeleteEvent (e) {
      e.preventDefault()

      this.deleteEvent(this.event)
    },
    handleEditEvent (event) {
      this.handleSubmit(event)
    },
    toggleEditMode () {
      this.editMode = !this.editMode
    }
  }
}
</script>

<style scoped>

</style>
