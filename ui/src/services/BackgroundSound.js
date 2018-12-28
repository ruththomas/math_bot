import Sound from './Sound'

class BackgroundSound extends Sound {
  constructor () {
    super(require('../assets/sounds/jupiter.mp3'))
    this.play()
  }
}

export default BackgroundSound
