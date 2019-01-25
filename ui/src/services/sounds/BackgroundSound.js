import Sound from './Sound'

class BackgroundSound extends Sound {
  constructor () {
    super(require('../../assets/sounds/jupiter.mp3'), {loop: true})
  }
}

export default BackgroundSound