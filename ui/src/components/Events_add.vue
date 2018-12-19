<template>

  <div class="text-left">
    <h3 class="my-3 text-center event-title">
      {{userInput.title || 'Event'}}
    </h3>

    <vue-form @submit.prevent="onSubmit" :state="formstate" class="mb-3">

      <div class="form-group">
        <label for="title">Title</label>
        <input
          :readonly="Boolean(readOnly)"
          id="title"
          type="text"
          name="title"
          v-model="userInput.title"
          required
          class="form-control"
        />
      </div>

      <div class="form-group">
        <label>Date</label>
        <datepicker
          :disabled="readOnly"
          :value="userInput.date"
          @selected="updateDate"/>
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
    return {
      formstate: {},
      userInput: {
        date: '',
        title: '',
        description: '',
        links: []
      }
    }
  },
  mounted () {
    if (this.init) {
      Object.assign(this.userInput, this.event)
    }
  },
  components: {
    Datepicker
  },
  methods: {

    updateDate (date) {
      Object.assign(this.userInput, {
        date
      })
    },
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

  .event-title {

    letter-spacing: .4rem;
    font-weight: bold;
  }
</style>
