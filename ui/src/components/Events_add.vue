<template>

  <div class="text-left">

    <vue-form @submit.prevent="onSubmit" :state="formstate" class="mb-3">

      <div class="form-group">
        <label for="title">Title</label>
        <input
          :readonly="Boolean(readOnly)"
          id="title"
          type="text"
          placeholder="enter a title"
          name="title"
          v-model="userInput.title"
          required
          class="form-control"
        />
      </div>

       <div class="form-group">

         <label>Date</label>

         <vue-ctk-date-time-picker
           v-if="readOnly"
           v-model="userInput.date"
           :disable-dates="true"
           :disable-time="true"

         ></vue-ctk-date-time-picker>
          <vue-ctk-date-time-picker
            v-model="userInput.date"
            v-else
            :disable-dates="readOnly"
            :disable-time="readOnly"

          ></vue-ctk-date-time-picker>
       </div>

      <div class="form-group">
        <label>Description</label>
        <textarea
          name="description"
          :readonly="Boolean(readOnly)"
          rows="5" class="form-control"
          v-model="userInput.description"
          required
          placeholder="describe the event..."
        >
      </textarea>
      </div>

      <div class="form-group">
        <label>Links</label>
        <input
          :readonly="Boolean(readOnly)"
          type="text"
          class="form-control"
          v-model="userInput.links"
          placeholder="link1,link2"
        />
      </div>

      <div class="row my-3 d-flex justify-content-end" v-if="!Boolean(readOnly)">
        <b-btn type="reset" class="mx-2">
          clear
        </b-btn>
        <b-btn type="submit" variant="primary" class="mx-2">
          Save
        </b-btn>
      </div>
    </vue-form>
  </div>
</template>

<script>
import Datepicker from 'vuejs-datepicker'

export default {
  name: 'Events_add',
  data () {
    const _now = new Date()
    return {
      formstate: {},
      userInput: {
        date: _now.toISOString(),
        title: '',
        description: '',
        links: []
      }
    }
  },
  mounted () {
    if (this.init) {
      Object.assign(this.userInput, this.event,
        {
          date: new Date(this.event.date).toISOString()
        }
      )
    }
  },
  components: {
    Datepicker
  },
  methods: {

    onSubmit (e) {
      e.preventDefault()
      if (!this.formstate.$valid) {
        // todo: handle
        console.error('invalid form', this.formstate)
      } else {
        this.handleSubmit(this.userInput)
      }
    }
  },
  props: ['handleSubmit', 'init', 'event', 'readOnly']
}
</script>

<style scoped>

  @import '~vue-ctk-date-time-picker/dist/vue-ctk-date-time-picker.css';
</style>
