class FunctionPlaceholders {
  constructor (context, bucket) {
    this.context = context
    this.stepData = this.context.$store.getters.getStepData
    this.mainMax = this.stepData.mainMax
    this.bucket = bucket

    if (this.mainMax > 0) {
      this._appendPlaceholders()
    }
  }

  _appendPlaceholders () {
    console.log(this.bucket)
  }
}

export default FunctionPlaceholders
