import api from './api'
import _ from 'underscore'

class BuildFunction {
  _tokenId (context) {
    return context.$store.getters.getToken.token_id
  }

  _getCurrentFunction (context) {
    const editingFunctionIndex = context.$store.getters.getEditingIndex
    if (editingFunctionIndex === null) return context.$store.getters.getMainFunction
    else return context.$store.getters.getActiveFunctions[editingFunctionIndex]
  }

  _getActiveFunctions (context) {
    return context.$store.getters.getActiveFunctions
  }

  _updatedLambdas (context, lambdas) {
    // console.log(lambdas)
    context.$store.dispatch('updateLambdas', lambdas)
  }

  _putFunc ({context, funcToken, override}) {
    api.putFunc({tokenId: this._tokenId(context), funcToken, override}, lambdas => this._updatedLambdas(context, lambdas))
  }

  _activateFunc ({context, stagedIndex, activeIndex}) {
    api.activateFunction({tokenId: this._tokenId(context), stagedIndex, activeIndex}, lambdas => this._updatedLambdas(context, lambdas))
  }

  _moveFunction ({context, oldIndex, newIndex}) {
    api.updateActives({tokenId: this._tokenId(context), oldIndex, newIndex}, lambdas => this._updatedLambdas(context, lambdas))
  }

  _calcIndex (groupSize, groupInd, funcInd) {
    return groupSize * groupInd + funcInd
  }

  updateName ({context}) {
    const currentFunction = this._getCurrentFunction(context)
    this._putFunc({context, funcToken: currentFunction})
  }

  deleteItemFromFunction ({context}) {
    const currentFunction = this._getCurrentFunction(context)
    this._putFunc({context, funcToken: currentFunction, override: true})
  }

  deleteFunction ({context}) {
    const currentFunction = this._getCurrentFunction(context)
    currentFunction.func = []

    this._putFunc({context, funcToken: currentFunction, override: true})
  }

  adjustColor ({context, color}) {
    const currentFunction = this._getCurrentFunction(context)
    currentFunction.color = color

    this._putFunc({context, funcToken: currentFunction})
  }

  addToFunction ({context, groupSize, groupInd, added}) {
    const swipes = document.querySelector('.functions-swiper-container').swiper
    const currentFunction = this._getCurrentFunction(context)
    const indexInCurrent = this._calcIndex(groupSize, groupInd, added.newIndex)

    console.log(swipes)

    currentFunction.func.splice(added.newIndex, 1)
    currentFunction.func.splice(indexInCurrent, 0, _.omit(added.element, 'func'))

    this._putFunc({context, funcToken: currentFunction})
  }

  activateFunction ({context, groupSize, groupInd, evt}) {
    const stagedIndex = this._calcIndex(14, evt.from.getAttribute('staged-group-ind'), evt.oldIndex)
    const activeIndex = this._calcIndex(groupSize, groupInd, evt.newIndex)
    this._activateFunc({context, stagedIndex: stagedIndex, activeIndex: activeIndex})
  }

  moveFunction ({context, groupSize, groupInd, evt}) {
    const oldIndex = this._calcIndex(groupSize, groupInd, evt.moved.oldIndex)
    const newIndex = this._calcIndex(groupSize, groupInd, evt.moved.newIndex)

    this._moveFunction({context, oldIndex: oldIndex, newIndex: newIndex})
  }
}

export default new BuildFunction()
