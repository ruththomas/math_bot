import urlEncode from 'urlencode'
import Vue from 'vue'

class Ws {
  constructor () {
    this._ws = null
  }

  /*
  * @deprecated
  * checkWsCapable - tests that the browser is able to handle websockets
  * - may be able to switch user to http route instead if not - not being used yet
  * */
  _checkWsCapable (cb) {
    if ('WebSocket' in window) {
      // console.log("WebSocket is supported by your Browser!");
      cb()
    } else {
      console.log('WebSocket NOT supported by your Browser!')
    }
  }

  /*
  * getWsPath - is called to get the correct ws path from the server
  * */
  _getWsPath (tokenId, connection, cb) {
    Vue.http.get('/api/wsPath/' + urlEncode(tokenId) + '/' + connection)
      .then(res => res.data)
      .then(path => cb(path))
      .catch(console.error)
  }

  _wsOnMessage (cb) {
    this._ws.onmessage = (msg) => {
      cb(JSON.parse(msg.data))
    }
  }

  _send (options) {
    this._ws.send(options)
  }
}

export default Ws
