<template>

  <div class="text-left">

    <vue-form @submit.prevent="onSubmit" :state="formstate" class="mb-3">

      <div class="form-group">
        <label for="title">Title</label>
        <validate auto-label class="form-group required-field" :class="fieldClassName(formstate.title)">
          <input
            id="title"
            type="text"
            placeholder="enter a title"
            name="title"
            v-model="userInput.title"
            required
            :class="_klass"
            class="form-control"
          />

          <field-messages name="title" show="$touched || $submitted" class="form-control-feedback">
            <div slot="required">Required field</div>
          </field-messages>
        </validate>
      </div>

      <div class="form-group">

        <label>Date</label>

        <vue-ctk-date-time-picker v-model="userInput.date"></vue-ctk-date-time-picker>
      </div>

      <div class="form-group">
        <label>Description</label>
        <textarea
          name="description"
          rows="5"
          class="form-control"
          v-model.lazy="userInput.description"
          placeholder="describe the event..."
        >
      </textarea>
      </div>

      <div class="form-group">
        <label>Links</label>
        <input
          type="text"
          :state="true"
          class="form-control"
          v-model.lazy="userInput.links"
          placeholder="link1,link2"
        />
      </div>

      <div class="row my-3 d-flex justify-content-end">
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
  computed: {

    _klass () {
      if (!(this.formstate.title && this.formstate.title.$dirty)) return null

      return this.formstate.title && this.formstate.title.$dirty && this.formstate.title.$invalid ? 'is-invalid' : 'is-valid'
    }
  },
  data () {
    const _now = new Date()
    return {
      formstate: {},
      userInput: {
        date: _now.toISOString(),
        title: '',
        description: '',
        links: ''
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

    fieldClassName (field) {
      // for bootstrap classes
      if (!field) {
        return ''
      }
      if ((field.$touched || field.$submitted) && field.$valid) {
        return 'has-success'
      }

      if ((field.$touched || field.$submitted) && field.$invalid) {
        return 'has-danger'
      }
    },

    onSubmit (e) {
      e.preventDefault()
      if (!this.formstate.$valid) {
        const messageBuilder = {
          type: 'warn',
          msg: 'Invalid form'
        }
        return this.$store.dispatch('addMessage', messageBuilder)
      }
      this.handleSubmit(this.userInput)
    }
  },
  props: ['handleSubmit', 'init', 'event']
}
</script>

<style scoped lang="scss">

  @import '~vue-ctk-date-time-picker/dist/vue-ctk-date-time-picker.css';

  $btn-height: 3rem;
  $danger-color: #F25C5C;
  $success-color: #50E3C2;
  $danger-background: rgba(242, 92, 92, 0.2);
  $success-background: rgba(80, 227, 194, 0.2);

  .form-control-feedback {
    position: absolute;
    font-size: 0.75em;
    top: 100%;
  }

  .input-success, .input-failure {
    top: -1px;
    right: -1px;
    bottom: -1px;
    left: -1px;
  }

  .required-field {
    border-radius: 0.25rem;
  }

  .required-field.has-danger {
    border: 1px solid $danger-color;

    input {
      background-color: $danger-background;
    }
  }

  .required-field.has-success {
    border: 1px solid $success-color;

    input {
      color: black;
      font-weight: bold;
      font-size: 0.9em;
      background-color: $success-background;
    }
  }

  .has-danger .form-control-feedback {
    color: $danger-color;
  }

  .has-success .form-control-feedback {
    color: $success-color;
  }

  button {
    text-transform: capitalize;
  }
</style>
