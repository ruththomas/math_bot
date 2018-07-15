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
    console.log(lambdas)
    context.$store.dispatch('updateLambdas', lambdas)
  }

  _updateActives (context, actives) {
    context.$store.dispatch('updateActives', actives)
  }

  _putFunc ({context, funcToken}) {
    api.putFunc({tokenId: this._tokenId(context), funcToken}, lambdas => this._updatedLambdas(context, lambdas))
  }

  _activateFunc ({context, stagedIndex, activeIndex}) {
    api.activateFunction({tokenId: this._tokenId(context), stagedIndex, activeIndex}, lambdas => this._updatedLambdas(context, lambdas))
  }

  _moveFunction ({context, actives}) {
    api.updateActives({tokenId: this._tokenId(context), actives: actives}, actives => this._updateActives(context, actives))
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
    this._putFunc({context, funcToken: currentFunction})
  }

  deleteFunction ({context}) {
    const currentFunction = this._getCurrentFunction(context)
    currentFunction.func = []

    this._putFunc({context, funcToken: currentFunction})
  }

  adjustColor ({context, color}) {
    const currentFunction = this._getCurrentFunction(context)
    currentFunction.color = color

    this._putFunc({context, funcToken: currentFunction})
  }

  addToFunction ({context, groupSize, groupInd, added}) {
    const currentFunction = this._getCurrentFunction(context)

    currentFunction.func.splice(added.newIndex, 1)
    currentFunction.func.splice(this._calcIndex(groupSize, groupInd, added.newIndex), 0, _.omit(added.element, 'func'))

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
    const activeFunctions = this._getActiveFunctions(context)

    console.log('oi', oldIndex)
    console.log('ni', newIndex)

    this._updateActives(context, activeFunctions)
    // this._moveFunction({context, actives: activeFunctions})
  }
}

export default new BuildFunction()
