import Vue from 'vue'
import Vuex from 'vuex'
import _ from 'underscore'
import api from '../services/api'
import VueDefaultValue from 'vue-default-value/dist/vue-default-value'

import permanentImages from '../assets/assets'
import Message from '../services/Message'

Vue.use(Vuex)
Vue.use(VueDefaultValue)

const robotUp = 'https://res.cloudinary.com/deqjemwcu/image/upload/v1522437134/robotDirections/RobotUp_nbpcu2.png'
const robotRight = 'https://res.cloudinary.com/deqjemwcu/image/upload/v1522437145/robotDirections/PlayerRight_n39yyp.png'
const robotDown = 'https://res.cloudinary.com/deqjemwcu/image/upload/v1522437155/robotDirections/PlayerDown_pjawwx.png'
const robotLeft = 'https://res.cloudinary.com/deqjemwcu/image/upload/v1522437164/robotDirections/RobotLeft_uf8xym.png'

function addMessage (state, messageBuilder) {
  const message = new Message(state, messageBuilder)
  message.add()
}

function orderEm (steps) {
  const stepsInOrder = Object.keys(steps).filter(key => steps[key].prevStep === 'None').map(s => steps[s]);

  (function ordEm () {
    if (stepsInOrder[stepsInOrder.length - 1].nextStep === 'None') return

    stepsInOrder.push(steps[stepsInOrder[stepsInOrder.length - 1].nextStep])

    ordEm()
  })()

  return stepsInOrder
}

class NewRobot {
  constructor ({state, robotCarrying, robotFacing}) {
    this.state = state || 'home'
    this.robotCarrying = robotCarrying || []
    this.robotFacing = robotFacing || 0
    this._robotDirections = {
      '0': robotUp,
      '90': robotRight,
      '180': robotDown,
      '270': robotLeft
    }

    this.trash = []
  };
}

const colors = {
  default: {
    hex: '#FFFFFF',
    next: 'grey'
  },
  grey: {
    hex: '#CCCCCC',
    next: 'blue'
  },
  blue: {
    hex: '#4A90E2',
    next: 'purple'
  },
  purple: {
    hex: '#CA7AFF',
    next: 'green'
  },
  green: {
    hex: '#50E3C2',
    next: 'pink'
  },
  pink: {
    hex: '#FF98B1',
    next: 'red'
  },
  red: {
    hex: '#F25C5C',
    next: 'default'
  }
}
const emptyToken = {
  token_id: '',
  u_id: '',
  randomImages: null,
  lambdas: {
    main: null,
    stagedFuncs: null,
    cmds: null,
    activeFuncs: null
  }
}

export default new Vuex.Store({
  state: {
    permanentImages: permanentImages,
    currentFunction: 'main',
    programPanelShowing: false,
    editFunctionShowing: false,
    speed: 300,
    currentUser: JSON.parse(localStorage.getItem('profile')),
    fullscreen: false,
    robotDeactivated: false,
    lock: {},
    showCongrats: false,
    tryAgainShowing: false,
    debuggerShowing: false,
    debuggerCurrentCommand: '',
    debuggerCurrentInd: 0,
    debuggerRender: false,
    compiledDone: false,
    currentStepData: {},
    robot: {},
    disToken: emptyToken,
    profileView: 'Arithmetic',
    functionGroupsCalc: null,
    functionGroups: [],
    functionAreaShowing: 'editMain',
    editingIndex: null,
    trashVisible: false,
    colors: colors,
    activeFunctionGroups: null,
    swiperSlide: 0,
    messageList: [],
    auth: null,
    showMesh: false,
    splashScreenShowing: false
  },
  mutations: {
    UPDATE_SPLASH_SCREEN_SHOWING (state, bool) {
      state.splashScreenShowing = bool
    },
    TOGGLE_SHOW_MESH (state, bool) {
      state.showMesh = bool
    },
    SET_AUTH (state, auth) {
      state.auth = auth
    },
    UPDATE_TRASH_VISIBLE (state, bool) {
      state.trashVisible = bool
    },
    CLEAR_CURRENT_FUNCTION (state) {
      const currentFunction = state.currentFunction
      if (currentFunction === 'main') {
        state.disToken.lambdas.main.func = []
      } else {
        state.disToken.lambdas.activeFuncs[currentFunction].func = []
      }
    },
    DEACTIVATE_ROBOT (state) {
      state.robotDeactivated = !state.robotDeactivated
    },
    UPDATE_DEBUGGER_RENDER (state, func) {
      state.debuggerRender = func
    },
    UPDATE_DEBUGGER_CURRENT_COMMAND (state, command) {
      state.debuggerCurrentCommand = command
    },
    UPDATE_DEBUGGER_CURRENT_IND (state, ind) {
      state.debuggerCurrentInd = ind
    },
    TOGGLE_DEBUGGER (state) {
      state.debuggerShowing = !state.debuggerShowing
      if (!state.debuggerShowing) {
        state.speed = 300
      }
    },
    SHOW_CONGRATS (state) {
      state.showCongrats = true
    },
    HIDE_CONGRATS (state) {
      state.showCongrats = false
    },
    SHOW_TRY_AGAIN (state) {
      state.tryAgainShowing = true
    },
    INIT_NEW_GAME (state, context) {
      const tokenId = state.disToken.token_id
      const level = state.disToken.stats.level
      const step = state.disToken.stats.step

      api.getStep({tokenId: tokenId, level: level, step: step}, res => {
        // All data require for this step
        const stepData = res.body
        // console.log(`${level}/${step}:\n`, stepData)

        // Reverses tools for rendering
        const reverseTools = (gridMap) => {
          return gridMap.map(row => {
            return row.map(cell => {
              cell.tools = cell.tools.reverse()
              return cell
            })
          })
        }

        // Display mainMax if not 10000
        if (stepData.mainMax < 10000) {
          addMessage(state, {
            type: 'info',
            msg: `Main max is ${stepData.mainMax}`,
            handlers () {
              const $placeholderContainer = $('.placeholder-container')
              return {
                runBeforeAppend: () => {
                  $placeholderContainer.addClass('pulse')
                },
                runOnDelete: () => {
                  $placeholderContainer.removeClass('pulse')
                }
              }
            }
          })
        }

        reverseTools(stepData.gridMap)

        state.currentStepData = stepData

        state.showCongrats = false
        state.tryAgainShowing = false
        state.robot = new NewRobot({robotFacing: stepData.robotOrientation})

        // update lambdas to step specific lambdas
        context.$store.dispatch('updateLambdas', {lambdas: stepData.lambdas})

        // Since we are going to robot, set level and step state in localstorage
        localStorage.setItem('LEVEL_STEP', JSON.stringify({level: level, step: step}))
        context.$router.push({path: 'robot'})
      })
    },
    UPDATE_LAMBDAS (state, {lambdas}) {
      state.disToken.lambdas = lambdas
    },
    UPDATE_TOKEN (state, args) {
      const token = args[0]
      const cb = args[1]

      state.disToken = token

      // console.log('TOKEN ~>', state.disToken)

      if (cb !== undefined) {
        cb(state.disToken.stats.level, state.disToken.stats.currentEquation)
      }
    },
    UPDATE_STATS (state, {stats, cb}) {
      state.disToken.stats = stats
      if (cb) cb()
    },
    CREATE_LOCK (state) {

    },
    PUSH_RANDOM_IMAGE (state, image) {
      state.disToken.randomImages.push(image)
    },
    CHANGE_CURRENT_FUNCTION (state, args) {
      const type = args[0]
      const ind = args[1]
      if (type === 'main') {
        state.currentFunction = 'main'
      } else {
        state.currentFunction = ind
      }
    },
    ADD_NEW_FUNCTION (state, func) {
      state.disToken.oldVersion.funcs.push(func)
    },
    DELETE_FUNCTION (state, ind, context) {
      const currentFunction = state.currentFunction
      if (currentFunction === 'main') {
        state.disToken.lambdas.main.func.splice(ind, 1)
      } else {
        state.disToken.lambdas.activeFuncs[currentFunction].func.splice(ind, 1)
      }
    },
    PUT_IMAGE_BACK (state, img, context) {
      state.randomImages.unshift(img)
    },
    CONTROL_PROGRAM_PANEL_SHOWING (state) {
      state.programPanelShowing = !state.programPanelShowing
    },
    CONTROL_EDIT_FUNCTION_SHOWING (state, bool) {
      state.editFunctionShowing = bool
    },
    ADD_CURRENT_USER (state, userData) {
      state.currentUser = userData
      state.disToken.token_id = userData.token_id
      state.disToken.u_id = userData._id
    },
    CHANGE_ROBOT_SPEED (state, speed) {
      state.speed = speed
    },
    CHANGE_FULLSCREEN (state) {
      state.fullscreen = !state.fullscreen
    },
    SPLICE_CURRENT (state, args) {
      const ind = args[0]
      const copy = args[1]
      const currentFunc = state.currentFunction
      const funcToUpdate = currentFunc === 'main' ? state.disToken.lambdas.main.func : state.disToken.lambdas.activeFuncs[currentFunc].func
      funcToUpdate.splice(ind, 1)
      funcToUpdate.splice(ind, 0, copy)
    },
    PUSH_CURRENT (state, c) {
      const copy = c
      const currentFunc = state.currentFunction
      const funcToUpdate = currentFunc === 'main' ? state.disToken.lambdas.main.func : state.disToken.lambdas.activeFuncs[currentFunc].func

      funcToUpdate.push(copy)
    },
    INSERT_CURRENT (state, args) {
      const ind = args[0]
      const copy = args[1]
      const currentFunc = state.currentFunction
      const funcToUpdate = currentFunc === 'main' ? state.disToken.lambdas.main.func : state.disToken.lambdas.activeFuncs[currentFunc].func

      funcToUpdate[state.currentFunction].splice(ind, 0, copy)
    },
    UPDATE_PROFILE_VIEW (state, loc) {
      state.profileView = loc
    },
    UPDATE_COURSE (state, course) {
      state.course = course
    },
    UPDATE_COMPILED_DONE (state, bool) {
      state.compiledDone = bool
    },
    UPDATE_PAUSED (state, bool) {
      state.robot.paused = bool
    },
    UPDATE_FUNCTION_AREA_SHOWING (state, show) {
      state.functionAreaShowing = show
    },
    UPDATE_EDITING_INDEX (state, index) {
      state.editingIndex = index
    },
    UPDATE_SWIPER_SLIDE (state, slide) {
      state.swiperSlide = slide
    },
    ADD_MESSAGE: addMessage,
    REMOVE_MESSAGE (state, ind) {
      state.messageList[ind].delete()
    },
    DELETE_MESSAGES (state) {
      state.messageList.map(m => m.delete())
    }
  },
  actions: {
    updateSplashScreenShowing ({commit}, bool) {
      commit('UPDATE_SPLASH_SCREEN_SHOWING', bool)
    },
    toggleShowMesh ({commit}, bool) {
      commit('TOGGLE_SHOW_MESH', bool)
    },
    setAuth ({commit}, auth) {
      commit('SET_AUTH', auth)
    },
    updateTrashVisible ({commit}, bool) {
      commit('UPDATE_TRASH_VISIBLE', bool)
    },
    updateFunctionGroups ({commit}) {
      commit('UPDATE_FUNCTION_GROUPS')
    },
    updateStats ({commit}, {stats, cb}) {
      commit('UPDATE_STATS', {stats, cb})
    },
    clearCurrentFunction ({commit}) {
      commit('CLEAR_CURRENT_FUNCTION')
    },
    deactivateRobot ({commit}) {
      commit('DEACTIVATE_ROBOT')
    },
    updateDebuggerRender ({commit}, func) {
      commit('UPDATE_DEBUGGER_RENDER', func)
    },
    updateDebuggerCurrentCommand ({commit}, command) {
      commit('UPDATE_DEBUGGER_CURRENT_COMMAND', command)
    },
    updateDebuggerCurrentInd ({commit}, ind) {
      commit('UPDATE_DEBUGGER_CURRENT_IND', ind)
    },
    toggleDebugger ({commit}) {
      commit('TOGGLE_DEBUGGER')
    },
    showCongrats ({commit}) {
      commit('SHOW_CONGRATS')
    },
    hideCongrats ({commit}) {
      commit('HIDE_CONGRATS')
    },
    showTryAgain ({commit}) {
      commit('SHOW_TRY_AGAIN')
    },
    initNewGame ({commit}, context) {
      commit('INIT_NEW_GAME', context)
    },
    pushRandomImage ({commit}, image) {
      commit('PUSH_RANDOM_IMAGE', image)
    },
    addRandomImages ({commit}, images) {
      commit('ADD_RANDOM_IMAGES', images)
    },
    addFunctions ({commit}) {
      commit('ADD_FUNCTIONS')
    },
    changeCurrentFunction ({commit}, args) {
      commit('CHANGE_CURRENT_FUNCTION', args)
    },
    addNewFunction ({commit}, func) {
      commit('ADD_NEW_FUNCTION', func)
    },
    deleteFunction ({commit}, ind) {
      commit('DELETE_FUNCTION', ind)
    },
    putImageBack ({commit}, img) {
      commit('PUT_IMAGE_BACK', img)
    },
    controlProgramPanelShowing ({commit}) {
      commit('CONTROL_PROGRAM_PANEL_SHOWING')
    },
    controlEditFunctionShowing ({commit}, bool) {
      commit('CONTROL_EDIT_FUNCTION_SHOWING', bool)
    },
    addCurrentUser ({commit}, userData) {
      commit('ADD_CURRENT_USER', userData)
    },
    changeRobotSpeed ({commit}, speed) {
      commit('CHANGE_ROBOT_SPEED', speed)
    },
    changeFullscreen ({commit}) {
      commit('CHANGE_FULLSCREEN')
    },
    updateLambdas ({commit}, {lambdas}) {
      commit('UPDATE_LAMBDAS', {lambdas})
    },
    updateToken ({commit}, args) {
      commit('UPDATE_TOKEN', args)
    },
    createLock ({commit}) {
      commit('CREATE_LOCK')
    },
    spliceCurrent ({commit}, args) {
      commit('SPLICE_CURRENT', args)
    },
    pushCurrent ({commit}, copy) {
      commit('PUSH_CURRENT', copy)
    },
    insertCurrent ({commit}, args) {
      commit('INSERT_CURRENT', args)
    },
    updateProfileView ({commit}, loc) {
      commit('UPDATE_PROFILE_VIEW', loc)
    },
    updateCourse ({commit}, course) {
      commit('UPDATE_COURSE', course)
    },
    updateCompiledDone ({commit}, bool) {
      commit('UPDATE_COMPILED_DONE', bool)
    },
    updatePaused ({commit}, bool) {
      commit('UPDATE_PAUSED', bool)
    },
    updateFunctionAreaShowing ({commit}, show) {
      commit('UPDATE_FUNCTION_AREA_SHOWING', show)
    },
    updateEditingIndex ({commit}, index) {
      commit('UPDATE_EDITING_INDEX', index)
    },
    updateSwiperSlide ({commit}, slide) {
      commit('UPDATE_SWIPER_SLIDE', slide)
    },
    addMessage ({commit}, messageBuilder) {
      commit('ADD_MESSAGE', messageBuilder)
    },
    removeMessage ({commit}, ind) {
      commit('REMOVE_MESSAGE', ind)
    },
    deleteMessages ({commit}) {
      commit('DELETE_MESSAGES')
    }
  },
  getters: {
    getSplashScreenShowing: state => state.splashScreenShowing,
    getPointerPosition: state => state.pointerPosition,
    getShowMesh: state => state.showMesh,
    getAuth: state => state.auth,
    getTokenId: state => state.disToken.token_id,
    getCurrentStepData: state => state.currentStepData,
    getTrashVisible: state => state.trashVisible,
    // getFunctionGroups: state => state.functionGroups,
    getGrid: state => state.currentStepData.grid,
    getRobotDeactivated: state => state.robotDeactivated,
    getDebuggerCurrentCommand: state => state.debuggerCurrentCommand,
    getDebuggerCurrentInd: state => state.debuggerCurrentInd,
    getDebuggerShowing: state => state.debuggerShowing,
    getDebuggerRender: state => state.debuggerRender || state.disToken.funcs[0],
    getCurrentEquation: state => state.disToken.stats.currentEquation,
    getCongratsShowing: state => state.showCongrats,
    getTryAgainShowing: state => state.tryAgainShowing,
    getGame: state => state.game,
    getRobot: state => state.robot,
    getLevel: state => state.disToken.stats.level,
    getRobotCarrying: state => state.robot.robotCarrying,
    getPermanentImages: state => state.permanentImages,
    getRandomImages: state => state.disToken.randomImages,
    getCommands: state => state.disToken.lambdas.cmds, // <-- Array
    getStagedFunctions: state => state.disToken.lambdas.stagedFuncs, // <-- Array
    getActiveFunctions: state => state.disToken.lambdas.activeFuncs, // <-- Array
    getMainFunction: state => state.disToken.lambdas.main, // <-- Object
    getLambdas: state => state.disToken.lambdas,
    getCurrentFunction: state => state.currentFunction,
    getProgramPanelShowing: state => state.programPanelShowing,
    getEditFunctionShowing: state => state.editFunctionShowing,
    getAuthStatus: state => state.authStatus,
    getAuthShowing: state => state.authShowing,
    getRememberUser: state => state.rememberUser,
    getToken: state => state.disToken,
    getShowDupAlert: state => state.showDupAlert,
    getRobotSpeed: state => state.speed,
    getFullscreen: state => state.fullscreen,
    getSignupLoading: state => state.signupLoading,
    getLock: state => state.lock,
    getSocket: state => state.socket,
    getLevels: state => {
      const levels = state.disToken.stats.levels
      const assembleLevels = _.chain(levels)
        .map((l, k) => {
          return [k, {
            level: l,
            name: k,
            prevStep: _.find(l, v => v.prevStep === 'None').prevLevel,
            nextStep: _.find(l, v => v.nextStep === 'None').nextLevel
          }]
        })
        .object()
        .value()

      return orderEm(assembleLevels)
    },
    getStats: state => state.disToken.stats,
    getStep: state => state.disToken.stats.step,
    getSteps: state => orderEm(state.disToken.stats.levels[state.disToken.stats.level]),
    getStars: state => state.stars,
    getProfileView: state => state.profileView,
    getCourses: state => state.courses,
    getCourse: state => state.course,
    getArithmeticLevels: state => state.arithmeticLevels,
    getCompiledDone: state => state.compiledDone,
    getPaused: state => state.robot.paused,
    getFunctionAreaShowing: state => state.functionAreaShowing,
    getEditingIndex: state => state.editingIndex,
    getColors: state => state.colors,
    getActiveFunctionGroups: state => state.activeFunctionGroups,
    getSwiperSlide: state => state.swiperSlide,
    getMessageList: state => state.messageList
  }
})
