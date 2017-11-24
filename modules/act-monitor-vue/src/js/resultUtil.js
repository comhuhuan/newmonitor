/**
 * Created by fmj on 2017/4/26.
 * ajax请求公用类
 *
 * Update by fmj on 2017/8/28
 * ajax请求增加顶部进度条
 */
import VueRouter from 'vue-router'
import routes from '../routes'
import superagent from 'superagent'
import progress from 'nprogress' // Progress 进度条
import '../styles/nprogress/nprogress.css'// Progress 进度条 样式

const base = '/act-monitor-web'

const router = new VueRouter({
  routes
})

export const resultUtil = res => {
  if (res.text) {
    const flag = JSON.parse(res.text)
    if (flag.sessionOut) {
      router.push('/login')
      return
    }
  }
  return res.body ? res.body : JSON.parse(res.text)
}

/**
 * @param url ajax请求的url
 * @param params    ajax请求的参数
 * @param NoProgress  是否隐藏顶部进度条/true=> 隐藏 false=> 显示
 * @returns ajax请求返回的json数据
 */

export const ajaxPost = (url, params, NoProgress) => {
  if (!NoProgress) {
    progress.start() // 开启Progress
  }
  const result = superagent.post(`${base}` + url).type('application/x-www-form-urlencoded').set('x-requested-with', 'XMLHttpRequest').accept('application/json').send(params).then(res => resultUtil(res))
  if (!NoProgress) {
    progress.done() // 关闭Progress
  }
  return result
}
