import CodeMirror from '../../node_modules/codemirror/lib/codemirror'
(function (mod) {
  mod(CodeMirror)
})(function (CodeMirror) {
  'use strict'

  CodeMirror.defineMode('mathbotlang', function (config) {
    let spacebefore = false
    let inbracket = false
    let Highlighters = {
      'forward': [/(forward)(?=( |$))/, 'keyword'],
      'right': [/(right)(?=( |$))/, 'keyword'],
      'putdown': [/(putdown)(?=( |$))/, 'keyword'],
      'pickup': [/(pickup)(?=( |$))/, 'keyword'],
      '=>': [/(=>)(?=( |$))/, 'def'],
      'sym': [/(')(\w|\d)+/, 'atom'],
      ')': [/\)/, 'bracket']
    }
    return {
      token: function (stream) {
        if (stream.lineOracle.line === 0 && stream.start === 0) {
          Highlighters = {
            'forward': [/(forward)(?=( |$|\)))/, 'keyword'],
            'right': [/(right)(?=( |$|\)))/, 'keyword'],
            'putdown': [/(putdown)(?=( |$|\)))/, 'keyword'],
            'pickup': [/(pickup)(?=( |$|\)))/, 'keyword'],
            '=>': [/(=>)(?=( |$))/, 'def'],
            'sym': [/(')(\w|\d)+/, 'atom'],
            ')': [/\)/, 'bracket']
          }
        }
        if (stream.start === 0) spacebefore = true
        if (stream.eatSpace()) spacebefore = true
        if (stream.eat('(')) {
          spacebefore = true
          inbracket = true
          return 'bracket'
        }
        if (spacebefore) {
          if (stream.match(/((\w|\d|-|_)+ =>)/)) {
            stream.backUp(3)
            let toturn = stream.current()
            if (!inbracket) toturn = toturn.substr(1)
            toturn = toturn.replace(/\s/g, '')
            const toadd = new RegExp('(' + toturn + ')(?=( |$)|\\))')
            Highlighters[toturn] = [toadd, 'builtin']
            spacebefore = false
            return 'builtin'
          }
        }
        let val
        let foundone = false
        Object.keys(Highlighters).forEach(function (key) {
          if (!foundone) {
            if (stream.eatSpace()) spacebefore = true
            if (spacebefore) {
              if (stream.match(Highlighters[key][0])) {
                spacebefore = false
                foundone = true
                val = Highlighters[key][1]
              }
            }
          }
        })
        if (val) return val
        spacebefore = false
        stream.next()
      }
    }
  })
  CodeMirror.defineMIME('text/x-mathbot-lang', 'mathbotlang')
})
