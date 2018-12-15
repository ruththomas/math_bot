<template>

  <div>

    <h3 class="my-3">
      {{init ? 'Edit Event' : 'Add Event'}}
    </h3>

    <vue-form @submit.prevent="onSubmit" :state="formstate" class="mb-3">

      <div class="input-group">
        <label>Title</label>
        <input type="text" name="title" v-model="userInput.title" required class="form-control"/>
      </div>
      <div class="input-group">
        <label>Date</label>
        <input type="date" v-model="userInput.date" name="event-date" required/>
      </div>
      <div class="input-group">
        <label>Description</label>
        <textarea
          name="description"
          rows="5" class="form-control" v-model="userInput.description" required placeholder="describe the event...">
      </textarea>
      </div>

      <div class="row my-3">
        <b-btn type="reset">
          clear
        </b-btn>
        <b-btn type="submit" variant="primary">
          Save
        </b-btn>
      </div>
    </vue-form>
  </div>
</template>

<script>
export default {
  name: 'Events_add',
  data () {
    return {
      formstate: {},
      userInput: {
        date: '',
        title: '',
        description: ''
      }
    }
  },
  mounted () {
    if (this.init) {
      Object.assign(this.userInput, this.event, {date: String(this.event.date)})
    }
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
  props: ['handleSubmit', 'init', 'event']
}
</script>

<style scoped>

</style>
