<template>
  <b-modal
    id="confirm-delete-event"
    size="md"
    ref="confirm-delete-event"
    :hide-header="false"
    :hide-footer="false"
    :lazy="true"
  >
    <div slot="modal-header">
      <img class="dialog-button close-congrats"
           @click="hide"
           :src="permanentImages.buttons.xButton"
           data-toggle="tooltip" title="Close">
    </div>

    <div class="congrats-icon mb-3">
      <img :src="permanentImages.instructionsRobot">
    </div>
    <h1 class="mb-3">Confirm delete</h1>

    <div class="row d-flex justify-content-center align-items-center">

      <div class="alert alert-warning" v-if="confirmDeleteEvent.title">
        You are about to delete the {{confirmDeleteEvent.title}} event
      </div>
    </div>
    <div slot="modal-footer" class="row" style="width: 100%; display: flex; justify-content: space-between;">
      <b-btn
        size="md"
        style="width: 40%;"
        class="float-right"
        variant="primary"
        @click="hide"
      >
        Quit
      </b-btn>
      <b-btn
        size="md"
        style="width: 40%"
        class="float-right"
        variant="primary"
        @click="deleteEvent"
      >
        Delete
      </b-btn>
    </div>
  </b-modal>
</template>

<script>

export default {
  name: 'Confirm_delete_event',
  mounted () {
  },
  computed: {
    eventsControl () {
      return this.$store.getters.getEventsControl
    },
    permanentImages () {
      return this.$store.getters.getPermanentImages
    },
    confirmDeleteEvent () {
      return this.adminControl.confirmDeleteEvent
    },
    adminControl () {
      return this.$store.getters.getAdminControl
    }
  },
  methods: {
    deleteEvent () {
      this.adminControl.handleDelEvent(this.confirmDeleteEvent)
      this.hide()
      this.$store.dispatch('confirmDeleteEvent', {})
    },
    hide () {
      this.$root.$emit('bv::hide::modal', 'confirm-delete-event')
    }
  },
  data () {
    return {
      show: true
    }
  }
}
</script>

<style lang="scss">
  $video-hint-close-top: 0;
  $video-hint-close-left: 100%;
  $dialog-button-size: 3.5vmin;
  $click-color: #B8E986;
  $embedded-background: var(--light);
  $dialog-button-size: 2rem;
  $background-color: var(--light);
  $grid-space-size: 9vmin;
  $share-btn-size: 2.5rem;

  #confirm-delete-event {
    color: black;
    .modal-dialog .modal-content {
      left: 0;
      position: relative;
      .modal-header, .modal-body, .modal-footer {
        background-color: $background-color;
        border: none;
      }
      .close-congrats {
        position: absolute;
        height: $dialog-button-size;
        width: $dialog-button-size;
        top: 0;
        right: 0;
      }
      .congrats-icon {
        background-color: white;
        display: inline-block;
        border-radius: 50%;
        padding: 2rem;
      }
      .modal-footer {
        background-color: $background-color;
        border-top: 1px solid $click-color;
        .row {
          margin: 0;
          .btn {
            background-color: $click-color;
            color: black;
            border: none;
          }
        }
      }
      .modal-body {
        padding-bottom: 2rem;
        .player {
          height: calc(100% - #{$share-btn-size});
          width: 100%;
          z-index: 1;
        }

        .hint-spinner {
          position: absolute;
          *:first-child {
            background: $embedded-background!important;
          }
        }
      }
      .social-spot {
        display: flex;
        justify-content: center;
        div {
          .social-sharing {
            .social-links {
              .share-button {
                height: $share-btn-size;
                width: $share-btn-size;

                i {
                  font-size: calc(#{$share-btn-size} - 1rem);
                }
              }
            }
          }
        }
      }
    }
  }
</style>
