<template>
  <div class="events-list container-fluid">
    <h3 class="page-title mb-3">

      <span @click="toggleView" :class="listVisible ? 'active' : null">
        Event List
      </span>

      or

      <span @click="toggleView" :class="!listVisible ? 'active' : null">
        Add Event
      </span>

    </h3>

    <div>

      <div v-if="listVisible">
        <div class="alert alert-info" v-if="!eventsList.length">
          No events found
        </div>

        <ul class="list-group">
          <li v-for="_event in eventsList" :key="_event.date + _event.description" class="list-group-item">
            <events-detail
              :handle-submit="editEvent"
              :event="_event" :delete-event="deleteEvent" :edit-event="editEvent"></events-detail>
          </li>
        </ul>
      </div>

      <div v-else>
        <events-add
          :handle-submit="createEvent"></events-add>
      </div>
    </div>

  </div>
</template>

<script>

import EventsDetail from './Events_detail'
import { v1 } from 'node-uuid'
import EventsAdd from './Events_add'

class Event {
  constructor (_date, title, description, _id = '') {
    this.id = _id || v1()
    this.date = _date
    this.title = title
    this.description = description
  }
}

class EventsController {
    events = []

    constructor () {
      this.events = this.makeEvents()
    }

    delEvent (event) {
      const index = this.events.findIndex(e => e.id === event.id)

      if (index === -1) {
        console.error('invalid request', event, index)
        return 'invalindex request'
      }

      this.events = [
        ...this.events.slice(0, index),
        ...this.events.slice(index + 1)
      ]
    }

    editEvent (event) {
      const evt = this.events.find(e => e.id === event.id)
      const evtI = this.events.findIndex(e => e.id === event.id)

      if (!evt) {
        return 'invalid requeset'
      }

      this.events = [
        ...this.events.slice(0, evtI),
        Object.assign({}, evt, event),
        ...this.events.slice(evtI + 1)
      ]
    }

    createEvent ({ date, description, title }) {
      this.events.push(
        new Event(date, title, description)
      )
    }

    makeEvents () {
      return [
        new Event(new Date('2018-01-01'), 'new year', 'new year celebration festivities'),
        new Event(new Date('2018-07-04'), 'ind day', 'celebrate USA'),
        new Event(new Date('2018-12-01'), 'dec 1', 'start of holiday season')
      ]
    }
}

export default {
  name: 'Events_list',
  components: { EventsAdd, EventsDetail },
  data () {
    return {
      ec: new EventsController(),
      listVisible: true
    }
  },
  computed: {
    eventsList () {
      return this.ec.events
    }
  },
  methods: {

    toggleView () {
      this.listVisible = !this.listVisible
    },

    deleteEvent (event) {
      this.ec.delEvent(event)
    },
    createEvent (event) {
      this.ec.createEvent(event)

      this.listVisible = true
    },

    editEvent (event) {
      this.ec.editEvent(event)
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

  .page-title {

    padding-top: 100px;
  }

  @media all and (max-width: 476px) {

    .page-title {

      padding-top: 50px;
    }
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
