<template>

      <tr
        @mouseleave="toolsVisible = false"
        @mouseenter="toolsVisible = true"
        style="height: 4rem;">
        <td>
          {{event.title}}
        </td>
        <td>{{new Date(event.date).toLocaleDateString()}}</td>
        <td style="min-width: 16rem;" colspan="2">

          <div class="d-flex justify-content-center align-items-center">
            <confirm-delete-event
              :event="event"
            ></confirm-delete-event>
            <event-detail-popover

              :class="toolsVisible ? 'show' : 'hidden'"
              :id="'popover/' + event.id"
              :event="event"
              :title="event.title"
              :date="event.date"
            ></event-detail-popover>
            <span style="min-width: 80px;">

             <b-btn
               class="btn-sm"
               :class="toolsVisible ? 'show' : 'hidden'"
               @click="toggleEditMode(event)"
             >
            Edit
          </b-btn>

          </span>
          </div>

        </td>
      </tr>
</template>

<script>
import EventsAdd from './Events_add'
import EventDetailPopover from './Event_detail_popover'
import ConfirmDeleteEvent from './ConfirmDeleteEvent'

export default {
  name: 'Events_detail',
  components: { ConfirmDeleteEvent, EventDetailPopover, EventsAdd },
  props: ['event', 'editEvent', 'handleSubmit', 'toggleEditMode', 'confirmDelete'],
  data () {
    return {
      editMode: false,
      toolsVisible: false
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

  .hidden {

    visibility: hidden;
  }

  .show {
    visibility: visible;
  }

</style>
