let tokenId = null
const calls = {
  unlock () {
    fetch('/api/stats/unlock/' + tokenId)
      .then(_ => location.reload())
      .catch(err => console.error(err.message))
  },
  reset () {
    fetch('/api/stats/reset/' + tokenId)
      .then(_ => location.reload())
      .catch(err => console.error(err.message))
  }
}

function salutations () {
  tokenId = JSON.parse(localStorage.getItem('profile')).user_id
  const $profile = $('.profile')
  const template = `
    <div class="secretTools">
      <button class="unlock">Unlock</button>
      <button class="reset">Reset</button>
    </div>
  `
  $profile.append(template)
  $('.secretTools .unlock').on('click', calls.unlock)
  $('.secretTools .reset').on('click', calls.reset)
}

export default salutations
