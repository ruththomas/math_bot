function ScrollOptions (addons) {
  const defaults = {
    animation: 100,
    ghostClass: 'ghost',
    chosenClass: 'chosen',
    filter: '.noDrag',
    dragClass: 'dragging',
    sort: true
  }
  return Object.assign(defaults, addons)
}

export default ScrollOptions
