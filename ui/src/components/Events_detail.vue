<template>

      <tr @mouseenter="toolsVisible = true" @mouseleave="toolsVisible = false" style="height: 4rem;">
        <td>
          {{event.title}}
        </td>
        <td>{{new Date(event.date).toLocaleDateString()}}</td>
        <td style="min-width: 8rem">
          <span v-show="toolsVisible">
          <event-detail-popover

            :id="'popover/' + event.id"
            :event="event"
            :title="event.title"
            :date="event.date"
          ></event-detail-popover>
          </span>
        </td>
        <td style="min-width: 8rem;">
          <span v-show="toolsVisible">

             <b-btn
               class="btn-sm"
               @click="toggleEditMode(event)"
             >
            Edit
          </b-btn>

          <img
            @click="confirmDelete(event)"
            class="trash noDrag dialog-button mx-2"
            :src="permanentImages.buttons.trashButton"
          />
          </span>

        </td>
      </tr>
</template>

<script>
import EventsAdd from './Events_add'
import EventDetailPopover from './Event_detail_popover'

export default {
  name: 'Events_detail',
  components: { EventDetailPopover, EventsAdd },
  props: ['event', 'deleteEvent', 'editEvent', 'handleSubmit', 'toggleEditMode', 'confirmDelete'],
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

</style>
