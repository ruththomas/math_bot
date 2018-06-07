class PopoverBucket {
  constructor (context) {
    this.context = context
    this.editingIndex = this.context.$store.getters.getEditingIndex
    this.$pointer = $('.pointer')

    if (this.editingIndex > -1) {
      this.$functions = $('.functions')
      this.startScrollTop = this.$functions.scrollTop()
      this._handleVisibility = this._handleVisibility.bind(this)
      this._handleVisibility()
    } else {
      this.$selected = () => $('#open-staged')
    }

    if (this.editingIndex !== null) {
      this.$selectedPosition = this.$selected().position()
      this.$selectedWidth = this.$selected().width()
      this._updatePointerPosition()
    }
  }

  $selected () {
    this.functionsChildren = this.$functions.children()
    return $(this.functionsChildren[this.editingIndex])
  }

  _updatePointerPosition () {
    let left = this.$selectedPosition.left
    const pointerWidth = this.$pointer.width()

    left += ((this.$selectedWidth / 2) - (pointerWidth / 2))

    this.$pointer.css({left: left})
  }

  _togglePointer (show) {
    if (show) {
      this.$pointer.addClass('pointer-hidden')
    } else {
      this.$pointer.removeClass('pointer-hidden')
    }
  }

  _handleVisibility () {
    const scrollTop = this.$functions.scrollTop()
    this._togglePointer(scrollTop !== this.startScrollTop)
    const functionAreaShowing = this.context.$store.getters.getFunctionAreaShowing === 'editFunction'
    if (functionAreaShowing) return setTimeout(this._handleVisibility, 100)
  }
}

export default PopoverBucket
