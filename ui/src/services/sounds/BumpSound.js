import Sound from './Sound'

class BumpSound extends Sound {
  constructor () {
    super(require('../../assets/sounds/bump.mp3'), {volume: 0.05})
  }
}

export default BumpSound
