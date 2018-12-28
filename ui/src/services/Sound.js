import {Howl} from 'howler'

class Sound {
  constructor (file) {
    this.sound = new Howl({
      src: [file],
      loop: true,
      volume: 0.1
    })

    this.play = this.play.bind(this)
    this.pause = this.pause.bind(this)
  }

  play () {
    this.sound.play()
  }

  pause () {
    this.sound.pause()
  }
}

export default Sound
