<template>

  <div class="fs">

    <div class="row">
      minDate: {{eventsControl.minDate}}
    </div>

    <div class="row">
      maxDate: {{eventsControl.maxDate}}
    </div>

    <div class="row">
      verb: {{eventsControl.verb}}
    </div>

    <div class="row">
      opt: {{eventsControl.opt}}
    </div>

    <div class="row">
      <div class="col">
        <select v-model="activeVerb">
          <option v-for="verb in eventsControl.verbs" :key="verb">
            {{verb}}
          </option>
        </select>
      </div>

      <div >
        <div class="col" v-if="['before', 'after', 'on'].includes(activeVerb)">

          <div v-for="event in eventsList" :key="event.id" @click="updateDateEvent(event)">
            {{event.title}}

            <small>
              {{new Date(event.date).toLocaleString()}}
            </small>
          </div>
          <datepicker
            v-model="userInput.date"
          />
        </div>
        <div v-if="'between' === activeVerb">
          <datepicker v-model="userInput.minDate"></datepicker>
          <datepicker v-model="userInput.maxDate"></datepicker>
        </div>
        <div class="col" v-if="['previous', 'next'].includes(activeVerb)">
          <input type="number" v-model="days"/>
        </div>

        <div class="col" v-if="['current', 'previous', 'next'].includes(activeVerb)">
          <select v-model="activeOpts" v-if="'current' === activeVerb">
            <option v-for="_opt in _opts" :key="_opt" >
                {{_opt}}
            </option>
          </select>

          <select v-model="activeOpts" v-else>
            <option v-for="opt in eventsControl.opts" :key="opt">
              {{opt}}
            </option>
          </select>
        </div>
      </div>
    </div>

    <div class="row">
      <b-btn
        @click="handleUpdateFilter"
      >
        Apply Filter

      </b-btn>
    </div>
  </div>
</template>

<script>
import VueFullcalendar from 'vue-fullcalendar'
import Datepicker from 'vuejs-datepicker'

export default {
  name: 'AdminFilterDate',
  components: { Datepicker, VueFullcalendar },
  data () {
    return {
      activeDateRange: 'month',
      activeVerb: 'previous',
      activeOpts: 'days',
      days: 30,
      userInput: {
        days: 30,
        date: new Date(),
        minDate: new Date(),
        maxDate: new Date()
      }
    }
  },
  computed: {
    _opts () {
      return this.eventsControl.opts.map(_i => _i.slice(0, -1))
    },
    eventsList () {
      return this.adminControl.events
    },

    adminControl () {
      return this.$store.getters.getAdminControl
    },

    eventsControl () {
      return this.$store.getters.getEventsControl
    }

  },
  methods: {

    handleUpdateFilter (e) {
      e.preventDefault()

      this.updateFilter()
    },

    updateFilter () {
      this.eventsControl.updateFilter(this.activeVerb, this.days, this.activeOpts, this.userInput.date, this.userInput.minDate, this.userInput.maxDate)
    },

    updateDateEvent (e) {
      const date = new Date(e.date)
      this.userInput.date = date
      this.userInput.minDate = date
      this.userInput.maxDate = date
    },

    last (e) {
      let days = e.target.value

      if (days === '') return
      this.minDate = new Date()

      if (days === 'month') {
        const _date = new Date()

        this.minDate = new Date(_date.getFullYear(), _date.getMonth(), 1)
      } else if (days === 'year') {
        this.minDate = new Date(this.minDate.getFullYear() - 1, this.minDate.getMonth(), this.minDate.getDay())
      } else {
        this.minDate.setDate(this.minDate.getDate() - +days)
      }
      this.maxDate = new Date()
      this.activeDateRange = days
    }

  }
}
</script>

<style scoped>

  .fs {
    transition: opacity 500ms linear;
    border: 2px solid #d7dbde;
    margin-right: 0.85em;
    margin-bottom: 0.5em;
    padding: 0.25em 1em 0.25em 1em;
    font-weight: 600;
    min-height: 30px;
    min-width: 150px;
    color: #74838f;
  }

</style>
