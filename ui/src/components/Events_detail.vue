<template>

      <tr
        @mouseleave="_handleLeave"
        @mouseenter="toolsVisible = true"
        @click="_handleRowClick"
        style="height: 4rem;">
        <td>
          {{event.title}}
        </td>
        <td>{{new Date(event.date).toLocaleDateString()}}</td>
        <td style="min-width: 16rem;" colspan="2">

          <div class="d-flex justify-content-center align-items-center">
            <confirm-delete-event
              :event="event"
              :class="toolsVisible ? 'show' : 'hidden'"
            ></confirm-delete-event>

            <b-btn :id="popoverId"
                   @click="_handleClick"
                   class="btn-sm"
                   :class="toolsVisible ? 'show' : 'hidden'"
            >
              Details
            </b-btn>

            <b-popover

              :show.sync="show" :target="popoverId" :title="_title">

              <div
                @click="_handleRowClick(); toolsVisible = true;"
                v-for="(value, key) in event" :key="'event_detail/' + key" class="container text-white">
                <div class="row font-weight-bold">
                  {{key}}
                </div>
                <div class="row" style="min-width: 55rem;">
                  {{value}}
                </div>
              </div>
            </b-popover>
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
import ConfirmDeleteEvent from './ConfirmDeleteEvent'

export default {
  name: 'Events_detail',
  components: { ConfirmDeleteEvent, EventsAdd },
  props: ['event', 'editEvent', 'handleSubmit', 'toggleEditMode', 'confirmDelete'],
  data () {
    return {
      editMode: false,
      toolsVisible: false,
      show: false
    }
  },
  computed: {

    _title () {
      return `${this.event.title}, ${this.event.date.toLocaleDateString()}`
    },

    popoverId () {
      return 'popover/' + this.event.id
    },
    permanentImages () {
      return this.$store.getters.getPermanentImages
    },
    adminControl () {
      return this.$store.getters.getAdminControl
    }
  },
  methods: {

    _handleClick () {
      this._toggleShow()

      if (this.show) {
        this.toolsVisible = true
      }
    },

    _toggleShow () {
      this.show = !this.show
    },
    _handleRowClick () {
      if (this.show) {
        this.show = false
      }
    },

    _handleLeave () {
      this.toolsVisible = this.show
    }
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
