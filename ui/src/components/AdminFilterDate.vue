<template>

  <div class="fs">

    <div class="row d-flex justify-content-center align-items-center m-3">
      <div class="col">
        <select v-model="activeAction">
          <option v-for="action in eventsControl.actions" :key="action">
            {{action}}
          </option>
        </select>
      </div>

      <div class="row m-3 ">
        <div class="col" v-if="['before', 'after', 'on'].includes(activeAction)">

          <div class="row my-3">
            <h5 class="mx-1">Events</h5>
            <div class="row btn-group d-flex flex-wrap justify-content-center align-items-center">
              <b-btn
                variant="default"
                v-for="event in eventsList"
                :key="event.id"
                @click="handleSetDate(event)"
              >
                {{event.title}}
                <small>
                  {{event.date.toLocaleString()}}
                </small>
              </b-btn>
            </div>

          </div>
          <div class="row my-3">
            <h5 class="mx-1">Date</h5>
            <datepicker
              v-model="userInput.date"
            />
          </div>
        </div>
        <div class="col" v-if="'between' === activeAction">

          <div class="row">
            <div class="col">
              <h5>From</h5>
              <div class="row my-2">
                <datepicker
                  v-model="userInput.minDate"
                />
              </div>
              <div class="btn-group">
                <b-btn
                  variant="default"
                  v-for="event in eventsList"
                  :key="event.id"
                  @click="setDate(event, 1, 0, 0)"
                >
                  {{event.title}}
                  <small>
                    {{event.date.toLocaleString()}}
                  </small>
                </b-btn>
              </div>

            </div>

            <div class="col">
              <h5 class="mx-1">To</h5>
              <div class="row my-2">
                <datepicker
                  v-model="userInput.maxDate"
                />
              </div>
              <div class="row my-2">
                <div class="btn-group">
                  <b-btn
                    variant="default"
                    v-for="_event in eventsList"
                    :key="_event.id"
                    @click="setDate(_event, 0, 1)"
                  >
                    {{_event.title}}
                    <small>
                      {{_event.date.toLocaleString()}}
                    </small>
                  </b-btn>
                </div>

              </div>

            </div>

          </div>

        </div>
        <div class="col" v-if="['previous', 'next'].includes(activeAction)">
          <input type="number"
                 class="text-right text-monospace"
                 min="0" v-model="days"/>
        </div>
        <div class="col" v-if="['current', 'previous', 'next'].includes(activeAction)">
          <select v-model="activeOpts" v-if="'current' === activeAction">
            <option v-for="_opt in eventsControl.opts" :key="_opt" :value="_opt">
              {{_opt.slice(0, -1)}}
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

    <div class="row m-3">
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
    const d = new Date()
    return {
      activeAction: 'current',
      activeOpts: 'months',
      days: 30,
      userInput: {
        days: 30,
        date: d,
        minDate: d,
        maxDate: d
      }
    }
  },
  computed: {

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

    handleSetDate ({date}) {
      if (this.activeAction === 'before') {
        this.userInput.maxDate = date
      } else if (this.activeAction === 'after') {
        this.userInput.minDate = date
      } else if (this.activeAction === 'on') {
        this.userInput.minDate = date
        this.userInput.maxDate = date
      } else {
        throw new Error('Invalid Request')
      }
      this.userInput.date = date
    },

    handleUpdateFilter (e) {
      e.preventDefault()

      this.updateFilter()
    },

    updateFilter () {
      this.eventsControl.updateFilter(this.activeAction, this.days, this.activeOpts, this.userInput.date, this.userInput.minDate, this.userInput.maxDate)
    },

    setDate ({date}, min = false, max = false, _date = false) {
      if (min) {
        this.userInput.minDate = date
      }

      if (max) {
        this.userInput.maxDate = date
      }
      if (_date) {
        this.userInput.date = date
      }
    }

  }
}
</script>

<style scoped>

  .fs {
    border: 2px solid #d7dbde;
    margin-right: 0.85em;
    margin-bottom: 0.5em;
    padding: 0.25em 1em 0.25em 1em;
    font-weight: 600;
    min-height: 30px;
    min-width: 150px;
    color: #74838f;
  }

  input[type=number] {
    color:#2e353b;
    font-size:1.12em;
    padding:.75rem .75rem;
    border:1px solid #d7dbde;
    border-image-source:initial;
    border-image-slice:initial;
    border-image-width:initial;
    border-image-outset:initial;
    border-image-repeat:initial;
    border-radius:4px;
    border-top-left-radius:4px;
    border-top-right-radius:4px;
    border-bottom-right-radius:4px;
    border-bottom-left-radius:4px;
    transition:border .3s linear;
    transition-property:border;
    transition-duration:.3s;
    transition-timing-function:linear;
    transition-delay:0;
    width:65px;
    font-weight:700;
    border-color:#d7dbde;
    border-style:solid;
    border-width:1px
  }

  select
  {
    display:inline-block;
    padding:.6em;
    border:1px solid #d7dbde;
    border-image-source:initial;
    border-image-slice:initial;
    border-image-width:initial;
    border-image-outset:initial;
    border-image-repeat:initial;
    border-radius:4px;
    border-top-left-radius:4px;
    border-top-right-radius:4px;
    border-bottom-right-radius:4px;
    border-bottom-left-radius:4px;
    font-size:14px;
    font-weight:700;
    min-width:90px;
    border-color:#d7dbde;
    border-style:solid;
    border-width:1px
  }

</style>
