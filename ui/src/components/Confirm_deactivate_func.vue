<template>
  <b-modal
    id="confirm-deactivate-func"
    size="md"
    ref="confirm-deactivate-func"
    :hide-header="false"
    :hide-footer="false"
    :lazy="true"
  >
    <div slot="modal-header">
      <img class="dialog-button close-congrats" @click="hide" :src="permanentImages.buttons.xButton" data-toggle="tooltip" title="Close">
    </div>

    <div class="congrats-icon mb-3">
      <img :src="permanentImages.instructionsRobot">
    </div>
    <h1 class="text-white mb-3">Confirm Deactivation</h1>

    <div class="row d-flex justify-content-center align-items-center">
      <puzzle-pieces
        :id="confirmDeactiveFunction.id"
        :ind="confirmDeactiveFunction.ind"
        :func="confirmDeactiveFunction"
        :piece-to-show="'closed'"
        :show-name="'true'"
      >
      </puzzle-pieces>

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
        @click="deactivateFunction"
      >
        Deactivate
      </b-btn>
    </div>
  </b-modal>
</template>

<script>
import SplashScreen from './Splash_screen'
import SocialSharing from './Social_sharing'
import BuildUtils from '../services/BuildFunction'
import PuzzlePieces from './Puzzle_pieces'

export default {
  name: 'Confirm_deactivate_func',
  mounted () {
  },
  computed: {
    levelControl () {
      return this.$store.getters.getLevelControl
    },
    permanentImages () {
      return this.$store.getters.getPermanentImages
    },

    confirmDeactiveFunction () {
      return this.$store.getters.getConfirmDeactiveFunction
    },
  },
  methods: {

    deactivateFunction () {

      this.levelControl.deactivateFunction(this.confirmDeactiveFunction)
      this.hide()
      this.$store.dispatch('confirmDeactivateFunction', {})
    },
    hide () {
      this.$root.$emit('bv::hide::modal', 'confirm-deactivate-func')
    }
  },
  data () {
    return {
      show: true
    }
  },
  components: {
    SplashScreen,
    SocialSharing,
    PuzzlePieces
  }
}
</script>

<style lang="scss">
  $video-hint-close-top: 0;
  $video-hint-close-left: 100%;
  $dialog-button-size: 3.5vmin;
  $click-color: #B8E986;
  $embedded-background: #1b1e21;
  $dialog-button-size: 2rem;
  $background-color: rgba(0, 0, 0, 1);
  $grid-space-size: 9vmin;
  $share-btn-size: 2.5rem;

  #confirm-deactivate-func {
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
            color: #000000;
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
