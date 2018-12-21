<template>

      <tr>
        <td>{{event.title}}</td>
        <td>{{new Date(event.date).toLocaleString()}}</td>
        <td>{{event.description}}</td>
        <td>
          <b-btn
            @click="toggleEditMode(event)"
          >
            Edit
          </b-btn>

          <img
            @click="confirmDelete(event)"
            class="trash noDrag dialog-button mx-2"
            :src="permanentImages.buttons.trashButton"
          />

          <!--<b-btn-->
          <!--@click="handleDeleteEvent"-->
          <!--variant="danger">-->
          <!--Confirm Deletion-->
          <!--</b-btn>-->
        </td>
      </tr>
</template>

<script>
import EventsAdd from './Events_add'

export default {
  name: 'Events_detail',
  components: { EventsAdd },
  props: ['event', 'deleteEvent', 'editEvent', 'handleSubmit', 'toggleEditMode', 'confirmDelete'],
  data () {
    return {
      editMode: false
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

  }
}
</script>

<style scoped>

</style>
