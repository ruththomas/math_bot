class PopoverBucket {
  constructor (context, target) {
    this.context = context
    this.editingIndex = this.context.$store.getters.getEditingIndex
    this.$pointer = $('.pointer')

    this.$methods = $('.methods')
    this.$popoverBucket = $('.popover-bucket')

    this.$selected = $(target)
    this.$selectedWidth = this.$selected.width()
    // this._updatePointerPosition()

    this.selectedOffsetLeft = this.$selected.offset().left

    this.watcherRunning = true
    this._handlePosition = this._handlePosition.bind(this)
    this._handlePosition()
  }

  killBucket () {
    this.watcherRunning = false
  }

  updateTarget (context, target) {
    this.constructor(context, target)
  }

  _positionPointer (right, left) {
    this.$pointer.css({right, left})
  }

  _positionPopover (right, left) {
    this.$popoverBucket.css({right, left})
  }

  _updatePointerPosition () {
    const pointerWidth = this.$pointer.outerWidth()
    const pointerOffset = Math.floor(((this.$selectedWidth / 2) - (pointerWidth / 2)) + 4)
    const newPointerPosition = this.$selected.position().left + pointerOffset
    const robotWidth = $('#robot').width()
    const pointerRight = newPointerPosition + pointerWidth
    const isFunction = this.$selected.hasClass('piece')
    const $functionsContainer = $('.functions-container')
    const containerOffsetRight = $functionsContainer.offset().left + $functionsContainer.width()
    const selectedOffsetRight = this.$selected.offset().left + this.$pointer.width() + 6

    if (pointerRight > robotWidth && !isFunction) {
      this._positionPopover(robotWidth - pointerRight, pointerRight - robotWidth)
      this._positionPointer(0, 'auto')
      this._togglePointer(true)
    } else {
      this._positionPopover(0, 0)
      this._positionPointer('auto', newPointerPosition)
      this._togglePointer(((((this.$selected.position().left - this.$methods.width()) + (pointerWidth / 2)) + 18) > 0) && (containerOffsetRight - selectedOffsetRight > 0))
    }
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

  _handlePosition () {
    this._updatePointerPosition()
    if (this.watcherRunning) setTimeout(this._handlePosition, 5)
  }
}

export default PopoverBucket
