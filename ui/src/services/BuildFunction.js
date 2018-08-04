import api from './api'
import _ from 'underscore'
import $store from '../store/store'

class BuildFunction {
  $store = $store

  _tokenId () {
    return this.$store.getters.getToken.token_id
  }

  _getCurrentFunction () {
    const editingFunctionIndex = this.$store.getters.getEditingIndex
    if (editingFunctionIndex === null) return this.$store.getters.getMainFunction
    else return this.$store.getters.getActiveFunctions[editingFunctionIndex]
  }

  _getActiveFunctions () {
    return this.$store.getters.getActiveFunctions
  }

  _getMethods () {
    return this.$store.getters.getCommands
  }

  _updatedLambdas (lambdas) {
    // console.log(lambdas)
    this.$store.dispatch('updateLambdas', lambdas)
  }

  _putFunc ({funcToken, override}) {
    api.putFunc({tokenId: this._tokenId(), funcToken, override}, lambdas => {
      this._updatedLambdas(lambdas)
      this._positionBar()
    })
  }

  _activateFunc ({stagedIndex, activeIndex}) {
    api.activateFunction({tokenId: this._tokenId(), stagedIndex, activeIndex}, lambdas => this._updatedLambdas(lambdas))
  }

  _moveFunction ({oldIndex, newIndex}) {
    api.updateActives({tokenId: this._tokenId(), oldIndex, newIndex}, lambdas => this._updatedLambdas(lambdas))
  }

  _$dropZone () {
    return $('.edit-main > .function-drop > .function-drop-drop-zone')
  }

  _$ele (targetClass) {
    return $(`.${targetClass}`)
  }

  _positionBar () {
    const $mainDropZone = this._$dropZone()
    if ($mainDropZone.length) {
      const $bar = $('.bar')
      const mainDropZoneHalf = $mainDropZone.height() / 2
      const mainDropOffsetTop = $mainDropZone.offset().top + mainDropZoneHalf
      const barOffsetTop = $bar.offset().top
      const barPosTop = $bar.position().top
      $bar.animate({top: (barPosTop + (mainDropOffsetTop - barOffsetTop) - 2) + 'px'}, 100)
    }
  }

  updateName () {
    const currentFunction = this._getCurrentFunction()
    this._putFunc({funcToken: currentFunction})
  }

  deleteItemFromFunction () {
    const currentFunction = this._getCurrentFunction()
    this._putFunc({funcToken: currentFunction, override: true})
  }

  deleteFunction () {
    const currentFunction = this._getCurrentFunction()
    currentFunction.func = []
    this._putFunc({funcToken: currentFunction, override: true})
  }

  adjustColor ({color}) {
    const currentFunction = this._getCurrentFunction()
    currentFunction.color = color

    this._putFunc({funcToken: currentFunction})
  }

  addToFunction () {
    const currentFunc = this._getCurrentFunction()
    currentFunc.func = currentFunc.func.map(f => _.omit(f, 'func'))
    this._putFunc({funcToken: currentFunc})
  }

  activateFunction ({stagedIndex, activeIndex}) {
    this._activateFunc({stagedIndex: stagedIndex, activeIndex: activeIndex})
  }

  moveFunction ({oldIndex, newIndex}) {
    this._moveFunction({oldIndex: oldIndex, newIndex: newIndex})
  }
}

const buildUtils = new BuildFunction()
$(window).on('resize', buildUtils._positionBar.bind(buildUtils))
export default buildUtils
