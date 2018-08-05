class PopoverBucket {
  constructor (context, target) {
    this.context = context
    this.$selected = $(target)

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
    $('.pointer').each(function () {
      $(this).remove()
    })
  }

  _setPointer () {
    this.$selected.append(this._pointerTemplate)
  }
}

export default PopoverBucket
