import _ from 'underscore'
import BumpSound from './sounds/BumpSound'
import WalkSound from './sounds/WalkSound'
import TurnSound from './sounds/TurnSound'
import $store from '../store/store'

class SoundControl {
  constructor () {
    this.addSounds([
      {name: 'bumped', sound: new BumpSound()},
      {name: 'walk', sound: new WalkSound()},
      {name: 'turn', sound: new TurnSound()}
    ])
  }

  sounds = {}

  getSound (name) {
    return this.sounds[name]
  }

  addSound ({name, sound}) {
    this.sounds[name] = sound
  }

  playSound (name) {
    if (!$store.state.devSuppress) {
      this.sounds[name].play()
    }
  }

  addSounds (soundList) {
    _.each(soundList, (sound) => {
      this.sounds[sound.name] = sound.sound
    })
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
    if (!$store.state.devSuppress) {
      _.each(this.sounds, (sound, name) => {
        if (filter.includes(name)) {
          sound.play()
        }
      })
    }
  }
}

export default SoundControl
