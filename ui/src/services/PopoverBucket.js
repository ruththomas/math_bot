class PopoverBucket {
  constructor (context, target) {
    this.context = context
    this.$selected = $(target)

    this.$selected = this.$selected.is('div') ? this.$selected : this.$selected.parent().closest('div')

    this.removePointer()
    this._setPointer()
  }

  _pointerTemplate = $(`
    <div class="pointer">
      <div class="pointer-size pointer-border"></div>
      <div class="pointer-size pointer-body"></div>
    </div>
  `)

  removePointer () {
    this.$selected.show()
    $('.pointer').each(function () {
      $(this).remove()
    })
  }

  _setPointer () {
    if (this.$selected.attr('id') === 'open-staged') {
      this.$selected.hide()
    } else {
      this.$selected.append(this._pointerTemplate)
    }
  }
}

export default PopoverBucket
