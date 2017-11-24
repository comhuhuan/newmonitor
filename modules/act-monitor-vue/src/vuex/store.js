import Vue from 'vue'
import Vuex from 'vuex'
import * as actions from './actions'
import * as getters from './getters'

Vue.use(Vuex)

// 应用初始状态
const state = {
  menuitems: [],
  isLoadRoutes: false,
  // add by  fuminjie  用于新增点击左侧菜单生成tab 用于vuex进行管理   2017-04-27
  activeMenu: [{
    title: 'IDC状态统计',
    name: '/deviceStatus',
    component: require('../views/monitor/deviceStatus/deviceStatus.vue')
  }],
  // activeMenu: storage.get('storageTabMenus') || ACTIVE_MENU,
  // activeMenu:[{title: "test样例", name: "/test",component:require("../views/common/Test/test.vue")}],
  // activeName: "IDC状态统计",
  activeName: '/deviceStatus'
}

// 定义所需的 mutations
const mutations = {
  // 新增菜单
  ADD_MENU (state, menuItems) {
    renderingMenus(menuItems)
    state.menuitems = menuItems
  },
  // 是否已经加载路由标识
  LOAD_ROUTES (state) {
    state.isLoadRoutes = !state.isLoadRoutes
  },
  // 用于新增点击左侧菜单生成tab 用于vuex进行管理
  ADD_ACTIVE (state, tab) {
    addTabs(state, tab)
  },

  FIRST_ACTIVE (state, user) {
    // toFirstPage(state,user);
    state.activeMenu = [{
      title: 'IDC状态统计',
      name: '/deviceStatus',
      component: require('../views/monitor/deviceStatus/deviceStatus.vue')
    }]
    state.activeName = '/deviceStatus'
  },
  SET_ACTIVE_NAME (state, name) {
    state.activeName = name
  },
  // 用于删除tab菜单栏 vuex进行管理
  REMOVE_ACTIVE (state, tab) {
    removeTabs(state, tab)
  }
}
//
// function  toFirstPage(state,user) {
//     let userInfo = JSON.parse(user)
//     let reg = /^\s+|\s+$/g
//     let uuid = userInfo.uuid.replace(reg,"")
//     if("" != uuid){//IDC用户
//         state.activeMenu = [{title: "IDC状态统计", name: "deviceStatus",component:require("../views/monitor/deviceStatus/deviceStatus.vue")}];
//         state.activeName = "IDC状态统计"
//     }else{//管理员
//         state.activeMenu = [{title: "IDC状态统计", name: "deviceStatus",component:require("../views/monitor/deviceStatus/deviceStatus.vue")}];
//         state.activeName = "IDC状态统计"
//     }
// }

function addTabs (state, tab) {
  let exist = false
  state.activeMenu.forEach(
    function (item) {
      if (item.name === tab.name) {
        exist = true
      }
    }
  )
  // 如果不存在则进入方法
  if (!exist) {
    tab.component = require('../views/' + tab.component + '.vue')
    state.activeMenu.push(tab)
  } else {
    removeTabs(state, tab)
  }
  state.activeName = tab.name

  // storage.set('storageTabMenus', state.activeMenu)
}

function removeTabs (state, tab) {
  let newTabs = []
  state.activeMenu.forEach(
    function (item) {
      if (item.name !== tab) {
        // if(item.title != tab){
        newTabs.push(item)
      }
    }
  )
  state.activeMenu = newTabs
  if (state.activeMenu.length > 0) {
    state.activeName = state.activeMenu[state.activeMenu.length - 1].name
  }
}

function renderingMenus (menuItems) {
  menuItems.forEach(function (item) {
    item.component = require('../views/' + item.component + '.vue')
    renderingSecond(item.children)
  })
}

function renderingSecond (params) {
  params.forEach(function (item) {
    item.component = require('../views/' + item.component + '.vue')
  })
}

// 创建 store 实例
export default new Vuex.Store({
  actions,
  getters,
  state,
  mutations
})
