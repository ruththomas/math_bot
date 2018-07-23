class Swiper {
  options = {
    spaceBetween: 0,
    mousewheel: true,
    pagination: {
      el: '.swiper-pagination',
      clickable: true,
      renderBullet (index, className) {
        return `<span class="${className} swiper-pagination-bullet-custom"></span>`
      }
    },
    navigation: {
      nextEl: '.swiper-button-next',
      prevEl: '.swiper-button-prev'
    },
    allowTouchMove: false
  }

  groupFunctions (arr, size, groups) {
    groups = groups || []
    if (!arr.length) return groups
    groups.push(arr.splice(0, size))
    return this.groupFunctions(arr, size, groups)
  }

  _containerWidth (containerClass) {
    return $(`.${containerClass}`).innerWidth()
  }

  _pieceSize (containerClass) {
    return $(`.${containerClass}`).find('.piece:first').outerHeight()
  }

  calculateGroupSize (containerClass) {
    return Math.floor(this._containerWidth(containerClass) / this._pieceSize(containerClass) - (containerClass !== 'edit-main-drop' ? 1 : 0))
  }
}

const swiper = new Swiper()
export default swiper
