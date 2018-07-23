class PopoverBucket {
  constructor (context, target) {
    this.context = context
    this.editingIndex = this.context.$store.getters.getEditingIndex
    this.$pointer = $('.pointer')

    this.$methods = $('.methods')
    this.$popoverBucket = $('.popover-bucket')

    this.$selected = $(target)
    this.$selectedPosition = this.$selected.position()
    this.$selectedWidth = this.$selected.width()
    this._updatePointerPosition()

    this.selectedOffsetLeft = this.$selected.offset().left

    this.watcherRunning = true
    this._handlePointerVisibility = this._handlePointerVisibility.bind(this)
    this._handlePointerVisibility()
  }

  killBucket () {
    this.watcherRunning = false
  }

  updateTarget (context, target) {
    this.constructor(context, target)
  }

  _updatePointerPosition () {
    const pointerWidth = this.$pointer.width()
    const css = {
      left: this.$selectedPosition.left
    }

    if (this.$selected.attr('id') === 'open-staged') {
      this.$popoverBucket.addClass('staged-popover-bucket')
      css.left = 'auto'
      css.right = '-1px'
    } else {
      this.$popoverBucket.removeClass('staged-popover-bucket')
      css.left += ((this.$selectedWidth / 2) - (pointerWidth / 4)) + this.$methods.width()
    }

    this.$pointer.css(css)
  }

  _hidePointer () {
    this.$pointer.addClass('pointer-hidden')
  }

  _showPointer () {
    this.$pointer.removeClass('pointer-hidden')
  }

  _togglePointer (show) {
    if (show) {
      this._showPointer()
    } else {
      this._hidePointer()
    }
  }

  _handlePointerVisibility () {
    this._togglePointer(this.$selected.offset().left === this.selectedOffsetLeft)
    if (this.watcherRunning) setTimeout(this._handlePointerVisibility, 100)
  }
}

export default PopoverBucket
