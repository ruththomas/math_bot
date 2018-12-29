import CodeMirror from '../../node_modules/codemirror/lib/codemirror'
(function (mod) {
  mod(CodeMirror)
})(function (CodeMirror) {
  'use strict'

  CodeMirror.defineMode('mathbotlang', function (config) {
    return {
      token: function (stream) {
        let match = function (array) {
          let stringarray = array.splice(0)
          if (stringarray.length < 1) return true
          if (!stream.eat(stringarray[0]) || stream === undefined) return false
          stringarray.shift()
          return match(stringarray)
        }
        if (match(['u', 'n', 'l', 'e', 's', 's'])) return 'atom'
        if (match(['i', 'f'])) return 'atom'
        if (match(['r', 'i', 'g', 'h', 't'])) return 'keyword'
        if (match(['p', 'i', 'c', 'k', 'u', 'p'])) return 'keyword'
        if (match(['u', 't', 'd', 'o', 'w', 'n'])) return 'keyword'
        if (match(['f', 'o', 'r', 'w', 'a', 'r', 'd'])) return 'keyword'
        if (match([')'])) return 'bracket'
        if (match(['('])) return 'bracket'
        if (match(['=', '>'])) return 'def'
        stream.next()
      }
    }
  })
  CodeMirror.defineMIME('text/x-mathbot-lang', 'mathbotlang')
})
