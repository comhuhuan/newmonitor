// 菜单 处理component 表
let menu = params => {
  rendering(params)
  return params
}

function rendering (params) {
  params.forEach(function (item) {
    item.component = require('../views/' + item.component + '.vue')
    renderingSecond(item.children)
  })
}

function renderingSecond (params) {
  params.forEach(function (item) {
    item.component = require('../views/' + item.component + '.vue')
  })
}

export default menu
