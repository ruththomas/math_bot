import Vue from 'vue'

class Ws {
  constructor (connection) {
    this.connection = connection
    this._ws = null
  }

  _openSocket (cb) {
    this._getWsPath(this.connection, path => {
      this._ws = new WebSocket(path)
      this._ws.onopen = () => {
        if (process.env.NODE_ENV === 'development') {
          console.log(`${this.connection.toUpperCase()} WS OPEN`)
        }
        if (cb) cb()
      }
      this._ws.onerror = (err) => {
        console.error(`${this.connection.toUpperCase()} WS FAILED`, err)
      }
      this._ws.onclose = () => {
        console.log(`${this.connection.toUpperCase()} WS CLOSED`)
      }
    })
  }

  /*
  * getWsPath - is called to get the correct ws path from the server
  * */
  _getWsPath (connection, cb) {
    Vue.http.get('/api/wsPath/' + connection)
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
    if (this._ws === null || this._ws.readyState !== 1) {
      this._openSocket(() => {
        this._ws.send(options)
      })
    } else {
      this._ws.send(options)
    }
  }
}

export default Ws
