<template>

  <span>
    <img
      :id="_id"
      @click="show = !show"
      class="trash noDrag dialog-button mx-2"
      :src="permanentImages.buttons.trashButton"
    />
    <b-popover
      :show.sync="show"
      :target="_id"
      placement="left"
    >
      <img alt="" class="dialog-button close-popover"
           :src="permanentImages.buttons.xButton"
           @click="show = false"
      />

      <div class="row d-flex justify-content-center align-items-center">

        <div class="alert alert-warning" v-if="event.title">
          You are about to delete the {{event.title}} event
        </div>
      </div>
      <div class="row" style="width: 100%; display: flex; justify-content: space-between;">
        <b-btn
          size="md"
          style="width: 40%;"
          class="float-right"
          variant="secondary"
          @click="show = false"
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
    </b-popover>
  </span>
</template>

<script>

import utils from '../services/utils'
export default {
  name: 'ConfirmDeleteEvent',
  mounted () {
  },
  computed: {
    _id () {
      return 'handle-del/' + this.event.id
    },
    eventsControl () {
      return this.$store.getters.getEventsControl
    },
    permanentImages () {
      return this.$store.getters.getPermanentImages
    },
    adminControl () {
      return this.$store.getters.getAdminControl
    }
  },
  methods: {
    deleteEvent () {
      this.adminControl.delEvent(this.event)
    },
    closePopover: utils.closePopover,
    openPopover: utils.openPopover
  },
  data () {
    return {
      show: false
    }
  },
  props: ['event']
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
            background: $embedded-background !important;
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
