import Ws from './Ws'

class VideoHintSocket extends Ws {
  constructor (tokenId) {
    super()
    this.tokenId = tokenId
  }

  _openSocket (cb) {
    if (this._ws === null || this._ws.readyState !== 1) {
      this._getWsPath(this.tokenId, 'videohint', path => {
        this._ws = new WebSocket(path)
        this._ws.onopen = () => {
          console.log(`VIDEOHINT WS OPEN`)
          if (cb) cb()
        }
        this._ws.onerror = (err) => {
          console.error('COMPILER WS FAILED', err)
        }
        this._ws.onclose = () => {
          console.log('COMPILER WS CLOSED')
        }
      })
    } else {
      if (cb) cb()
    }
  }

  _requestHint () {
    this._send(JSON.stringify({action: 'get-hint', tokenId: this.tokenId}))
  }

  getHint (cb) {
    this._openSocket(() => {
      this._wsOnMessage(cb)
      this._requestHint()
    })
  }
}

export default VideoHintSocket
