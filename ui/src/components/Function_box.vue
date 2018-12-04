<template>
  <puzzle-pieces
    :id="id"
    :ind="ind"
    :func="func"
    :piece-to-show="pieceToShow"
    :show-name="showName"
    :method="method"
    :size="size"
  >
  </puzzle-pieces>
</template>

<script>
import uId from 'uid'

import PuzzlePieces from './Puzzle_pieces'

export default {
  name: 'function_box',
  mounted () {
  },
  computed: {
    id () {
      return this.origin === 'functions' ? this.func.created_id : uId(7)
    },
    showName () {
      return this.origin === 'functions'
    },
    pieceToShow () {
      if (this.collection.length === 1 || this.origin === 'functions' || this.origin === 'stagedFunctions' || this.origin === 'editFunction') {
        return 'closed'
      } else if (this.ind === 0) {
        return 'start'
      } else if (this.collection.length === this.ind + 1) {
        return 'end'
      } else {
        return 'middle'
      }
    }
  },
  components: {
    PuzzlePieces
  },
  props: ['func', 'ind', 'origin', 'method', 'collection', 'otherMounted', 'size']
}
</script>

<style scoped lang="scss">
  .function-box {
    display: inline-block;
  }
</style>
