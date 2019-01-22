import {Howl} from 'howler'

class Sound {
  constructor (file, configs = {}) {
    this.sound = new Howl(Object.assign({src: [file], volume: 0.1}, configs))

    this.play = this.play.bind(this)
    this.pause = this.pause.bind(this)
  }

  play (sprite) {
    if (process.env.NODE_ENV === 'production') {
      this.sound.play(sprite)
    }
  }

  pause () {
    this.sound.pause()
  }

  stop () {
    this.sound.stop()
  }
}

export default Sound
