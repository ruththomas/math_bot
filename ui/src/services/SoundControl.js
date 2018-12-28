import _ from 'underscore'

class SoundControl {
  sounds = {}

  pauseSounds (filter) {
    _.each(this.sounds, (sound, name) => {
      if (filter.includes(name)) {
        sound.pause()
      }
    })
  }

  startSounds (filter) {
    _.each(this.sounds, (sound, name) => {
      if (filter.includes(name)) {
        sound.play()
      }
    })
  }
}

export default SoundControl
