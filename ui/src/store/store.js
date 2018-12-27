import Vue from 'vue'
import Vuex from 'vuex'
import VueDefaultValue from 'vue-default-value/dist/vue-default-value'
import permanentImages from '../assets/assets'

// todo use message
import Message from '../services/Message'
import { AuthService } from '../services/AuthService'
import VideoTimer from '../services/VideoTimer'
import RunCompiled from '../services/RunCompiled'
import CompilerControl from '../services/CompilerControl'
import VideoControl from '../services/VideoControl'
import LevelControl from '../services/LevelControl'
import AdminControl from '../services/AdminControl'
import {EventsControl} from '../services/EventsControl'

Vue.use(Vuex)
Vue.use(VueDefaultValue)

function addMessage (state, messageBuilder) {
  const message = new Message(state, messageBuilder)
  message.add()
}

export default new Vuex.Store({
  state: {
    eventsControl: new EventsControl(),
    permanentImages: permanentImages,
    showCongrats: false,
    tryAgainShowing: false,
    functionAreaShowing: 'editMain',
    editingIndex: null,
    trashVisible: false,
    activeFunctionGroups: null,
    messageList: [],
    auth: new AuthService(),
    showMesh: false,
    splashScreenShowing: false,
    hintShowing: {
      showing: false,
      videoURL: ''
    },
    editFunctionEvent: {},
    authErrors: [],
    videoHint: {},
    confirmDeactiveFunction: {},
    compilerControl: {},
    videoTimers: {},
    videoHintControl: {},
    levelControl: {},
    adminControl: {}
  },
  mutations: {
    UPDATE_CONTROLS (state) {
      state.compilerControl = new CompilerControl()
      state.videoHintControl = new VideoControl(state)
      state.levelControl = new LevelControl()
      state.adminControl = new AdminControl()
    },

    CLEAR_AUTH_ERRORS (state) {
      state.authErrors = []
    },
    PUSH_AUTH_ERRORS (state, msg) {
      state.authErrors.push(msg)
    },
    UPDATE_RUN_COMPILED (state, context) {
      state.runCompiled = new RunCompiled(context)
    },
    UPDATE_EDIT_FUNCTION_EVENT (state, evt) {
      state.editFunctionEvent = evt
    },
    ADD_VIDEO_TIMER (state, remainingTime) {
      Vue.set(state.videoTimers, `${remainingTime.level}/${remainingTime.step}`, new VideoTimer({state, level: remainingTime.level, step: remainingTime.step, stars: remainingTime.stars, remainingTime: remainingTime.remainingTime}))
    },
    START_EXISTING_TIMERS (state, remainingTimes) {
      remainingTimes.forEach(rt => {
        Vue.set(state.videoTimers, `${rt.level}/${rt.step}`, new VideoTimer({state, level: rt.level, step: rt.step, stars: rt.stars, remainingTime: rt.remainingTime}))
      })
    },
    TOGGLE_HINT_SHOWING (state, {showing, videoURL}) {
      state.hintShowing = {showing, videoURL}
    },
    UPDATE_SPLASH_SCREEN_SHOWING (state, bool) {
      state.splashScreenShowing = bool
    },
    TOGGLE_SHOW_MESH (state, bool) {
      state.showMesh = bool
    },
    UPDATE_TRASH_VISIBLE (state, bool) {
      state.trashVisible = bool
    },
    UPDATE_FUNCTION_AREA_SHOWING (state, show) {
      state.functionAreaShowing = show
    },
    UPDATE_EDITING_INDEX (state, index) {
      state.editingIndex = index
    },
    ADD_MESSAGE: addMessage,
    DELETE_ALL_MESSAGES (state) {
      state.messageList = []
    },
    REMOVE_MESSAGE (state, ind) {
      state.messageList[ind].delete()
    },
    DELETE_MESSAGES (state) {
      state.messageList.map(m => m.delete())
    },
    CONFIRM_DEACTIVATE_FUNCTION (state, _func) {
      state.confirmDeactiveFunction = _func
    },
    REQUEST_ADMIN (state, result) {
      state.requestAdmin = result
    }
  },
  actions: {

    confirmDeactivateFunction ({commit}, _func) {
      commit('CONFIRM_DEACTIVATE_FUNCTION', _func)
    },
    updateControls ({commit}, tokenId) {
      commit('UPDATE_CONTROLS')
    },
    clearAuthErrors ({commit}) {
      commit('CLEAR_AUTH_ERRORS')
    },
    pushAuthErrors ({commit}, msg) {
      commit('PUSH_AUTH_ERRORS', msg)
    },
    updateRunCompiled ({commit}, context) {
      commit('UPDATE_RUN_COMPILED', context)
    },
    updateEditFunctionEvent ({commit}, evt) {
      commit('UPDATE_EDIT_FUNCTION_EVENT', evt)
    },
    addVideoTimer ({commit}, context) {
      commit('ADD_VIDEO_TIMER', context)
    },
    startExistingTimers ({commit}, remainingTimes) {
      commit('START_EXISTING_TIMERS', remainingTimes)
    },
    toggleHintShowing ({commit}, {showing, videoURL}) {
      commit('TOGGLE_HINT_SHOWING', {showing, videoURL})
    },
    updateSplashScreenShowing ({commit}, bool) {
      commit('UPDATE_SPLASH_SCREEN_SHOWING', bool)
    },
    toggleShowMesh ({commit}, bool) {
      commit('TOGGLE_SHOW_MESH', bool)
    },
    updateTrashVisible ({commit}, bool) {
      commit('UPDATE_TRASH_VISIBLE', bool)
    },
    updateFunctionAreaShowing ({commit}, show) {
      commit('UPDATE_FUNCTION_AREA_SHOWING', show)
    },
    updateEditingIndex ({commit}, index) {
      commit('UPDATE_EDITING_INDEX', index)
    },
    addMessage ({commit}, messageBuilder) {
      commit('ADD_MESSAGE', messageBuilder)
    },
    removeMessage ({commit}, ind) {
      commit('REMOVE_MESSAGE', ind)
    },
    deleteAllMessages ({commit}) {
      commit('DELETE_ALL_MESSAGES')
    },
    deleteMessages ({commit}) {
      commit('DELETE_MESSAGES')
    }
  },
  getters: {
    getEventsControl: state => state.eventsControl,
    getAuth: state => state.auth,
    getConfirmDeactiveFunction: state => state.confirmDeactiveFunction,
    getAdminControl: state => state.adminControl,
    getCompilerControl: state => state.compilerControl,
    getVideoHintControl: state => state.videoHintControl,
    getVideoTimers: state => state.videoTimers,
    getLevelControl: state => state.levelControl,
    getAuthErrors: state => state.authErrors,
    getEditFunctionEvent: state => state.editFunctionEvent,
    getHintShowing: state => state.hintShowing,
    getSplashScreenShowing: state => state.splashScreenShowing,
    getShowMesh: state => state.showMesh,
    getTrashVisible: state => state.trashVisible,
    getPermanentImages: state => state.permanentImages,
    getFunctionAreaShowing: state => state.functionAreaShowing,
    getEditingIndex: state => state.editingIndex,
    getMessageList: state => state.messageList
  }
})
