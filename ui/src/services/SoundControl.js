import _ from 'underscore'

class SoundControl {
  sounds = {}

  getSound (name) {
    return this.sounds[name]
  }

  addSound ({name, sound}) {
    this.sounds[name] = sound
  }

  removeSounds (names) {
    _.each(this.sounds, (sound, name) => {
      if (names.includes(name)) sound.stop()
    })
    this.sounds = _.omit(this.sounds, names)
  }

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
