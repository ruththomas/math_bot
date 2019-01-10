import CodeMirror from '../../node_modules/codemirror/lib/codemirror'
(function (mod) {
  mod(CodeMirror)
})(function (CodeMirror) {
  'use strict'

  CodeMirror.defineMode('mathbotlang', function (config) {
    let spacebefore = false
    let inbracket = false
    let Highlighters = {
      'forward': [/forward\b/, 'keyword'],
      'right': [/right\b/, 'keyword'],
      'putdown': [/putdown\b/, 'keyword'],
      'pickup': [/pickup\b/, 'keyword'],
      '=>': [/=>\B/, 'def'],
      'sym': [/(')(\w|\d)+/, 'atom']
    }
    return {
      token: function (stream) {
        if (stream.start === 0) spacebefore = true
        if (stream.eatSpace()) spacebefore = true
        if (stream.eat('(')) {
          spacebefore = true
          inbracket = true
          return
        }
        let val
        Object.keys(Highlighters).forEach(function (key) {
          if (stream.eatSpace()) spacebefore = true
          if (spacebefore) {
            if (stream.match(Highlighters[key][0])) {
              spacebefore = false
              val = Highlighters[key][1]
            }
          }
        })
        if (val) return val
        if (spacebefore) {
          if (stream.match(/((\w|\d)+ =>)/)) {
            stream.backUp(3)
            let toturn = stream.current()
            if (!inbracket) toturn = toturn.substr(1)
            const toadd = new RegExp(toturn + '\\' + 'b')
            Highlighters[toturn] = [toadd, 'keyword']
            spacebefore = false
            return 'keyword'
          }
        }
        spacebefore = false
        stream.next()
      }
    }
  })
  CodeMirror.defineMIME('text/x-mathbot-lang', 'mathbotlang')
})
