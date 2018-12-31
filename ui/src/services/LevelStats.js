export class LevelStats {
  constructor ({
    _id = '',
    timesPlayed = 0,
    timesPlayedAvg = 0,
    timesPlayedMax = 0,
    wins = 0,
    winsAvg = 0,
    winsMax = 0,
    id = ''
  }) {
    this._id = _id
    this.timesPlayed = timesPlayed
    this.timesPlayedAvg = timesPlayedAvg
    this.timesPlayedMax = timesPlayedMax
    this.wins = wins
    this.winsAvg = winsAvg
    this.winsMax = winsMax
    this.id = id
  }
}
