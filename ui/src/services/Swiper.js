class Swiper {
  options = {
    slidesPerView: 1,
    centerSlides: true,
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
}

export default Swiper
