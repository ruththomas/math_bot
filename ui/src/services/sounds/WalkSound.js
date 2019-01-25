import Sound from './Sound'

class WalkSound extends Sound {
  constructor () {
    super(require('../../assets/sounds/walk.mp3'), {sprite: {oneStep: [0, 100]}})
  }

  play () {
    this.sound.play('oneStep')
  }
}

export default WalkSound
