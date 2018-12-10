// CodeMirror, copyright (c) by Marijn Haverbeke and others
// Distributed under an MIT license: https://codemirror.net/LICENSE
import CodeMirror from '../../node_modules/codemirror/lib/codemirror'
(function(mod) {
    mod(CodeMirror);
})(function(CodeMirror) {
"use strict";

CodeMirror.defineMode("commonlisp", function (config) {
  return {
    token: function (stream) {
      let match = function (array) {
        let stringarray = array.splice(0)
        if (stringarray.length < 1) return true
        if (!stream.eat(stringarray[0]) || stream === undefined) return false
        stringarray.shift()
        return match(stringarray)
      }

      if (match(['u', 'n', 'l', 'e', 's', 's'])) return 'atom' // purple 174 129 255
      if (match(['i', 'f'])) return 'atom' // red 245 37 112
      if (match(['r', 'i', 'g', 'h', 't'])) return 'keyword' // yellow 230 219 116
      if (match(['p', 'i', 'c', 'k', 'u', 'p'])) return 'keyword' // yellow 230 219 116
      if (match(['u', 't', 'd', 'o', 'w', 'n'])) return 'keyword' // yellow
      if (match(['f', 'o', 'r', 'w', 'a', 'r', 'd'])) return 'keyword' // yellow 230 219 116
      if (match([')'])) return 'bracket' // white 248 248 242
      if (match(['('])) return 'bracket' // white 248 248 242
      if (match(['=', '>'])) return 'def' // light blue  rgb 102 217 238
      // default orange rgb 240 144 30
      stream.next()
    }
  }
});

CodeMirror.defineMIME("text/x-common-lisp", "commonlisp");

});
