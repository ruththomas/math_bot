export class LevelStats {
  constructor ({
    _id = 'BasicProgramming',
    timesPlayed = 200,
    timesPlayedAvg = 1,
    timesPlayedMax = 1,
    wins = 200,
    winsAvg = 1,
    winsMax = 1,
    id = '00000'
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
