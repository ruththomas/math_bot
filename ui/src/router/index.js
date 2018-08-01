import Vue from 'vue'
import Router from 'vue-router'
import VueResource from 'vue-resource'
import Sortable from 'vue-sortable'
import Robot from '@/components/Robot'
import Profile from '@/components/Profile'
import Marketing from '@/components/marketing/Marketing'
import Callback from '@/components/Callback'
import BootstrapVue from 'bootstrap-vue'

import $store from '../store/store'

// require styles
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import 'swiper/dist/css/swiper.css'

Vue.use(BootstrapVue)
Vue.use(VueAwesomeSwiper)
Vue.use(Router)
Vue.use(VueResource)
Vue.use(Sortable)

const router = new Router({
  mode: 'history',
  routes: [
    {
      path: '/robot',
      name: 'Robot',
      secure: true,
      component: Robot
    },
    {
      path: '/signup',
      name: 'Signup',
      component: Robot
    },
    {
      path: '/profile',
      name: 'Profile',
      secure: true,
      component: Profile
    },
    {
      path: '/about',
      name: 'Marketing',
      component: Marketing
    },
    {
      path: '/callback',
      name: 'Callback',
      component: Callback
    },
    {
      path: '*',
      redirect: '/about'
    }
  ]
})

router.beforeEach((to, from, next) => {
  // Look at all routes
  router.options.routes.forEach((route) => {
    // If this is the current route and it's secure
    if (to.matched[0].path === route.path && route.secure && !$store.state.auth.authenticated) {
      return next('/about')
    }
  })
  // Proceed as normal
  return next()
})

export default router
