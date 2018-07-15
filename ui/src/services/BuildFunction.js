import api from './api'
import _ from 'underscore'

class BuildFunction {
  _getCurrentFunction (context) {
    const editingFunctionIndex = context.$store.getters.getEditingIndex
    if (editingFunctionIndex === null) return context.$store.getters.getMainFunction
    else return context.$store.getters.getActiveFunctions[editingFunctionIndex]
  }

  _updatedLambdas (context, lambdas) {
    context.$store.dispatch('updateLambdas', lambdas)
  }

  _putFunc ({context, funcToken}) {
    const tokenId = context.$store.getters.getToken.token_id
    api.putFunc({tokenId, funcToken}, lambdas => this._updatedLambdas(context, lambdas))
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
}

export default new BuildFunction()
